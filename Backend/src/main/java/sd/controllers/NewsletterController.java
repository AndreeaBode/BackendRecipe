package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.services.NewsletterService;

@RestController
@RequestMapping("/s")
public class NewsletterController {

    private final  NewsletterService newsletterService;

    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("")
    public ResponseEntity<String> handleSubscribeRequest(@RequestBody String email) {
        System.out.println("EMAIL " + email);
        String message = newsletterService.s(email);

        try {
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

