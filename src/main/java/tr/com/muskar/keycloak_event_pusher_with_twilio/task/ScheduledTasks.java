package tr.com.muskar.keycloak_event_pusher_with_twilio.task;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tr.com.muskar.keycloak_event_pusher_with_twilio.service.KeycloakService;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final KeycloakService keycloakService;

    @Scheduled(initialDelay = 20, fixedDelay = 10,timeUnit = TimeUnit.MINUTES)
    public void controlKeycloakEvents(){
        keycloakService.checkAdminEvents();
    }
}
