package sd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.entities.User;
import sd.repositories.UserRepository;
import sd.services.PaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public PaymentController(PaymentService paymentService, UserRepository userRepository) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
    }

    @GetMapping("/payment/can-make-request")
    public ResponseEntity<Map<String, String>> canMakeRequest(@RequestParam int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, String> response = new HashMap<>();
        if (paymentService.canUserMakeRequest(user)) {
            response.put("message", "User can make a request");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Search limit exceeded. Please make a payment.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }



    @PostMapping("/payment/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestParam int userId) {
        try {
            String sessionUrl = paymentService.createCheckoutSession(userId);
            if (sessionUrl != null) {
                Map<String, String> response = new HashMap<>();
                response.put("url", sessionUrl);
                try {
                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Failed to create checkout session");
                try {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Exception while creating checkout session: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/payment/users/premium")
    public ResponseEntity<Map<String, String>> upgradeToPremium1(@RequestParam int userId) {
        try {
            String message = paymentService.upgradeToPremium(userId);
            if (message.startsWith("Congratulations") ){
                Map<String, String> response = new HashMap<>();
                response.put("message", message);
                try {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                } catch (Exception e) {

                }
                return ResponseEntity.ok(response);
            }
            else if (message.startsWith("Oops") || message.startsWith("Admin") || message.startsWith("You") || message.startsWith("User")) {
                System.out.println("BBB" + message);
                Map<String, String> response = new HashMap<>();
                response.put("message", message);

                try {
                    return ResponseEntity.ok(response);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Unexpected error occurred.");

                try {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Exception while creating checkout session: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/payment/users/{userId}/premium")
    public ResponseEntity<String> upgradeToPremium(@PathVariable int userId) {
        String message = paymentService.upgradeToPremium(userId);
        if (message.startsWith("Congratulations")) {
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else if (message.startsWith("Oops") || message.startsWith("Admin") || message.startsWith("You") || message.startsWith("User")) {
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");

        }
    }
}
