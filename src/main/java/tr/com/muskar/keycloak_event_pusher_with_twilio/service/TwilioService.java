package tr.com.muskar.keycloak_event_pusher_with_twilio.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioService {

    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;
    @Value("${twilio.whatsAppNumber}")
    private String twilioWhatsAppPhoneNumber;

    @PostConstruct
    public void init(){
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void sendSms(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        System.out.println("SMS Sent! SID: " + message.getSid());
    }

    public void sendWhatsApp(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + toPhoneNumber),
                new com.twilio.type.PhoneNumber("whatsapp:" + twilioWhatsAppPhoneNumber),
                messageBody
        ).create();

        System.out.println("WhatsApp Message Sent! SID: " + message.getSid());
    }
}
