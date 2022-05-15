package com.folautech.springbootapi.stripe;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Account;
import com.stripe.model.AccountLink;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Stripe", description = "Stripe Operations")
@Slf4j
@RestController
@RequestMapping("/stripe")
public class StripeRestController {

    @Autowired
    private StripeService stripeService;

    @Operation(summary = "Create Express Account", description = "Create Express Account")
    @PostMapping(value = "/account/express")
    public ResponseEntity<StripeAccountDTO> createExpressAccount() {
        return new ResponseEntity<>(stripeService.createExpressAccount(), OK);
    }
    
    @Operation(summary = "Create Standard Account", description = "Create Standard Account")
    @PostMapping(value = "/account/standard")
    public ResponseEntity<StripeAccountDTO> createStandardAccount() {
        return new ResponseEntity<>(stripeService.createStandardAccount(), OK);
    }
    
    @Operation(summary = "Create Custom Account", description = "Create Custom Account")
    @PostMapping(value = "/account/custom")
    public ResponseEntity<StripeAccountDTO> createCustomAccount() {
        return new ResponseEntity<>(stripeService.createCustomAccount(), OK);
    }
    
    @Operation(summary = "Get Account Link", description = "Get Account Link")
    @PostMapping(value = "/account/link/{accountId}")
    public ResponseEntity<StripeAccountLinkDTO> getAccountLink(@PathVariable String accountId) {
//        stripeService.createAccount("express").getId();
        return new ResponseEntity<>(stripeService.getAccountLinkUrl(accountId), OK);
    }
    
    @Operation(summary = "Create Charge", description = "Create Charge")
    @PostMapping(value = "/account/charge")
    public ResponseEntity<StripeChargeDTO> charge(@RequestParam String accountId,@RequestParam Double amount) {
        return new ResponseEntity<>(stripeService.charge(accountId,amount), OK);
    }
    
    @Operation(summary = "Create PaymentIntent", description = "Create PaymentIntent")
    @PostMapping(value = "/paymentintent")
    public ResponseEntity<PaymentIntentDTO> chasdrge(@RequestParam String accountId,@RequestParam Double amount) {
        return new ResponseEntity<>(stripeService.createPaymentIntent(accountId,amount), OK);
    }
    
    @Operation(summary = "Confirm PaymentIntent", description = "Confirm PaymentIntent")
    @PostMapping(value = "/paymentintent/confirm")
    public ResponseEntity<PaymentIntentDTO> confirmPaymentIntent(@RequestParam String paymentIntentId,@RequestParam Double amount) {
        return new ResponseEntity<>(stripeService.confirmPaymentIntent(paymentIntentId, amount), OK);
    }
    
    @Operation(summary = "Capture PaymentIntent", description = "Capture PaymentIntent")
    @PostMapping(value = "/paymentintent/capture")
    public ResponseEntity<PaymentIntentDTO> capturePaymentIntent(@RequestParam String paymentIntentId,@RequestParam String accountId) {
        return new ResponseEntity<>(stripeService.capturePaymentIntent(accountId, paymentIntentId), OK);
    }
}
