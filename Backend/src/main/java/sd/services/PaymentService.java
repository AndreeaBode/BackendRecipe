package sd.services;

import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sd.entities.User;
import sd.repositories.UserPremiumRepository;
import sd.repositories.UserRepository;
import java.util.logging.Logger;

@Service
public class PaymentService {

    private final UserRepository userRepository;

    private final UserPremiumRepository userPremiumRepository;
    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    @Value("${stripe.api.key}")
    private String stripeApiKey;


    public PaymentService(UserRepository userRepository, UserPremiumRepository userPremiumRepository) {
        this.userRepository = userRepository;
        this.userPremiumRepository = userPremiumRepository;
    }



    @Transactional
    public String upgradeToPremium(int userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return "Oops! Something went wrong. Please try again.";
            }

            if ("Admin".equals(user.getRole()) || user.isPremium()) {
                return "You are already a premium user.";
            }

            user.setRole("Premium");
            userRepository.save(user);
            return "Congratulations! You are now a premium user.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Oops! Something went wrong. Please try again.";
        }
    }




    public String createCheckoutSession(int userId) throws StripeException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/success")
                .setCancelUrl("http://localhost:4200/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(2000L)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Premium Subscription")
                                        .build())
                                .build())
                        .build())
                .putMetadata("user_id", String.valueOf(userId))
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }


    public boolean canUserMakeRequest(User user) {
        return user.getWeeklyRequestCount() < 3;
    }
}
