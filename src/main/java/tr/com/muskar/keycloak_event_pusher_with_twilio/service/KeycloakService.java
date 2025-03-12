package tr.com.muskar.keycloak_event_pusher_with_twilio.service;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    @NotNull
    private final Keycloak keycloak;
    @Value("${keycloak.client.realm}")
    private String realm;
    private UsersResource usersResource;
    private RealmResource realmResource;
    private final TwilioService twilioService;

    @PostConstruct
    public void init() {
        realmResource = keycloak.realm(realm);
        usersResource = realmResource.users();
    }

    public void checkAdminEvents() {
        List<AdminEventRepresentation> adminEventRepresentationList = realmResource.getAdminEvents();
        realmResource.clearAdminEvents();
        for (AdminEventRepresentation adminEventRepresentation : adminEventRepresentationList) {
            String userId = adminEventRepresentation.getAuthDetails().getUserId();
            UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
            Map<String, List<String>> attributes = userRepresentation.getAttributes();
            if (attributes.containsKey("phoneNumber")) {
                List<String> phoneNumbers = attributes.get("phoneNumber");
                if (!phoneNumbers.isEmpty()) {
                    String phoneNumber = phoneNumbers.get(0);
                    String message = createEventMessageFrom(adminEventRepresentation);
                    twilioService.sendSms(phoneNumber,message);
                    twilioService.sendWhatsApp(phoneNumber,message);
                } else {
                    log.error("Phone number not set.");
                }
            } else {
                log.error("Phone Number attribute not found.");
            }
        }
    }

    private String createEventMessageFrom(AdminEventRepresentation adminEventRepresentation){
        StringBuilder sb = new StringBuilder();
        sb.append("Process"+ adminEventRepresentation.getResourceType()).append(System.lineSeparator());
        sb.append("Operation Type "+adminEventRepresentation.getOperationType());
        return sb.toString();
    }
}
