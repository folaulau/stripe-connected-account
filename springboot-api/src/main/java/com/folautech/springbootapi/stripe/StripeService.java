package com.folautech.springbootapi.stripe;

import com.stripe.model.Account;
import com.stripe.model.AccountLink;

public interface StripeService {

    StripeAccountDTO createExpressAccount();
    
    StripeAccountDTO createStandardAccount();

//    AccountLink getExpressUrl(String accountId);

    StripeAccountDTO createCustomAccount();

    StripeAccountLinkDTO getAccountLinkUrl(String accountId);

    StripeChargeDTO charge(String accountId, Double amount);

    PaymentIntentDTO createPaymentIntent(String accountId, Double amount);

    PaymentIntentDTO capturePaymentIntent(String accountId, String paymentIntentId);
}
