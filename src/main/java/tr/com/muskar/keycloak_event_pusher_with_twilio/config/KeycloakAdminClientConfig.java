package tr.com.muskar.keycloak_event_pusher_with_twilio.config;

import jakarta.ws.rs.client.Client;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class KeycloakAdminClientConfig {

    @Value("${keycloak.server.url}")
    private String url;

    @Value("${keycloak.client.master}")
    private String master;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.user.name}")
    private String userName;

    @Value("${keycloak.user.password}")
    private String password;

    @Bean
    public Keycloak buildKeycloak() {
        Client client = ResteasyClientBuilderImpl.newBuilder()
                .connectTimeout(10,
                        TimeUnit.SECONDS)
                .readTimeout(12,
                        TimeUnit.SECONDS)
                .build();

        KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(master)
                .username(userName)
                .password(password)
                .clientId(clientId)
                .resteasyClient(client)
                .build();
        return Keycloak.getInstance(url, master, userName, password, clientId);

    }
}
