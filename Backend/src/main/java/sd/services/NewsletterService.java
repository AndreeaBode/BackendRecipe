package sd.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sd.entities.Subscriber;
import sd.repositories.SubscriberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
@SuppressWarnings("unused")
public class NewsletterService {
    private static final Logger logger = LoggerFactory.getLogger(NewsletterService.class);

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mailjet.apikey.public}")
    private String apiKeyPublic;

    @Value("${mailjet.apikey.private}")
    private String apiKeyPrivate;

    public String s(String email) {
        if (subscriberRepository.findByEmail(email).isPresent()) {
            return "Already subscribed";
        }
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(email);
        subscriberRepository.save(subscriber);
        return "Subscribed successfully";
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String recipeTitle) {
        String from = fromEmail;
        String subject = "New Recipe Added: " + recipeTitle;
        String body = "Hello,\n\nA new recipe has been added: " + recipeTitle + ".\n\nEnjoy cooking!\n\nBest regards,\nYour Recipe Service";
        String replyTo = "noreply@recipe.com";

        List<Subscriber> emails = subscriberRepository.findAll();
        System.out.println("EM " + emails);
        for (Subscriber subscriber : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                System.out.println("subbb " + subscriber);
                message.setFrom(from);
                message.setTo(subscriber.getEmail());
                message.setSubject(subject);
                message.setText(body);
                message.setReplyTo(replyTo);

                System.out.println("Message " + message);
                mailSender.send(message);
                System.out.println("Email sent to: " + subscriber.getEmail());
                logger.info("Email sent to: {}", subscriber.getEmail());
            } catch (Exception e) {
                System.err.println("Failed to send email to: " + subscriber.getEmail());
                logger.error("Failed to send email to: {}", subscriber.getEmail(), e);
                e.printStackTrace();
            }
        }
    }
}