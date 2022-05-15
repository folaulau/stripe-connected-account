package com.folautech.springbootapi.stripe;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Source;
import com.stripe.model.Transfer;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.Company.Address;
import com.stripe.param.AccountCreateParams.TosAcceptance;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentRetrieveParams;
import com.stripe.param.TransferCreateParams;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StripeServiceImp implements StripeService {

    @Value("${stripe.secret.key}")
    private String             stripeSecretKey;

    @Autowired
    private StripeTokenService stripeTokenService;

    @Override
    public StripeAccountDTO createExpressAccount() {
        Stripe.apiKey = stripeSecretKey;

        // @formatter:off

        Map<String,String> metadata = new HashMap<>();
        metadata.put("env", "testing");
        AccountCreateParams params =
                AccountCreateParams
                  .builder()
                  .setEmail("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE) + "@gmail.com")
                  .setMetadata(metadata)
                  .setCountry("US")

                  .setType(AccountCreateParams.Type.EXPRESS)
                  .setCapabilities(
                    AccountCreateParams.Capabilities
                      .builder()
                      .setCardPayments(
                        AccountCreateParams.Capabilities.CardPayments
                          .builder()
                          .setRequested(true)
                          .build()
                      )
                      .setTransfers(
                        AccountCreateParams.Capabilities.Transfers
                          .builder()
                          .setRequested(true)
                          .build()
                      )
                      .build()
                  )
                  .setBusinessType(AccountCreateParams.BusinessType.COMPANY)
                  .setCompany(com.stripe.param.AccountCreateParams.Company.builder()
                          .setPhone("310"+RandomUtils.nextLong(1000000, 9999999))
                          .setName("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE))
                          .setAddress(Address.builder()
                                  .setCity("Lehi")
                                  .setCountry("US")
                                  .setState("UTAH")
                                  .setPostalCode("84043")
                                  .setLine1("545 N Center St")
                                  .build())
                          
                          .build())
                  .setBusinessProfile(
                    AccountCreateParams.BusinessProfile
                      .builder()
                      .setName("Folaudev"+RandomUtils.nextLong(10000, Long.MAX_VALUE))
                      .setProductDescription("testing product")
//                      .setUrl("https://example.com")
                      .build()
                  )
                  .build();
        // @formatter:on

        Account account = null;
        try {
            account = Account.create(params);

            System.out.println("account=" + account.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convert(account);
    }

    @Override
    public StripeAccountDTO createStandardAccount() {
        Stripe.apiKey = stripeSecretKey;

        // @formatter:off
        
        Map<String,String> metadata = new HashMap<>();
        metadata.put("env", "testing");
        AccountCreateParams params =
                AccountCreateParams
                  .builder()
                  .setEmail("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE) + "@gmail.com")
                  .setMetadata(metadata)
                  .setCountry("US")
                  .setType(AccountCreateParams.Type.STANDARD)
                  .setBusinessType(AccountCreateParams.BusinessType.COMPANY)
                  .setCompany(com.stripe.param.AccountCreateParams.Company.builder()
                          .setPhone("310"+RandomUtils.nextLong(1000000, 9999999))
                          .setName("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE))
                          .setAddress(Address.builder()
                                  .setCity("Lehi")
                                  .setCountry("US")
                                  .setState("UTAH")
                                  .setPostalCode("84043")
                                  .setLine1("545 N Center St")
                                  .build())
                          .build())
                  
                  .setBusinessProfile(
                    AccountCreateParams.BusinessProfile
                      .builder()
                      .setName("Folaudev"+RandomUtils.nextLong(10000, Long.MAX_VALUE))
                      .setProductDescription("testing product")
//                      .setUrl("https://example.com")
                      .build()
                  )
                  .build();
        // @formatter:on

        Account account = null;
        try {
            account = Account.create(params);

            System.out.println("account=" + account.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convert(account);
    }

    @Override
    public StripeAccountDTO createCustomAccount() {
        Stripe.apiKey = stripeSecretKey;

        // @formatter:off
        
//        AccountCreateParams params = AccountCreateParams.builder()
//                .setType(AccountCreateParams.Type.EXPRESS)
//                .setEmail("folaudev" + RandomUtils.nextLong(10000, Long.MAX_VALUE) + "@gmail.com")
//                .build();
       
//        File upload =null;
//        
//try {
//    //Please upload a file smaller than 512 kB
//    FileCreateParams fileparams =
//            FileCreateParams.builder()
//              .setFile(new ClassPathResource("cat.png").getFile())
//              .setPurpose(FileCreateParams.Purpose.BUSINESS_ICON)
//              .build();
//     upload = File.create(fileparams);
//} catch (Exception e) {
//    e.printStackTrace();
//}
//             

        Map<String, String> metadata = new HashMap<>();
        metadata.put("env", "local");
        
        AccountCreateParams params =
                AccountCreateParams
                  .builder()
                  .setEmail("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE) + "@gmail.com")
                  .setCountry("US")
                  .setMetadata(metadata)
                  .setType(AccountCreateParams.Type.CUSTOM)
                  .setCapabilities(
                    AccountCreateParams.Capabilities
                      .builder()
                      .setCardPayments(
                        AccountCreateParams.Capabilities.CardPayments
                          .builder()
                          .setRequested(true)
                          .build()
                      )
                      .setTransfers(
                        AccountCreateParams.Capabilities.Transfers
                          .builder()
                          .setRequested(true)
                          .build()
                      )
                      .build()
                  )
                  .setBusinessType(AccountCreateParams.BusinessType.COMPANY)
                  .setCompany(com.stripe.param.AccountCreateParams.Company.builder()
                          .setPhone("310"+RandomUtils.nextLong(1000000, 9999999))
                          .setName("folaudev+" + RandomUtils.nextLong(10000, Long.MAX_VALUE))
                          .setAddress(Address.builder()
                                  .setCity("Lehi")
                                  .setCountry("US")
                                  .setState("UTAH")
                                  .setPostalCode("84043")
                                  .setLine1("545 N Center St")
                                  .build())
                          .setTaxId("123456789")
                          .setVerification(com.stripe.param.AccountCreateParams.Company.Verification.builder()
                                  // documents
                                  .build())
                          .build())
                  .setBusinessProfile(
                    AccountCreateParams.BusinessProfile
                      .builder()
                      .setName("Folaudev"+RandomUtils.nextLong(10000, Long.MAX_VALUE))
                      .setProductDescription("testing product")
//                      .setUrl("https://example.com")
                      .build()
                  )
                  .setTosAcceptance(TosAcceptance.builder()
                          .setUserAgent("Chrome/{Chrome Rev} Mobile Safari/{WebKit Rev}")
                          .setIp("174.52.151.8")
                          .setDate(Instant.now().getEpochSecond())
                          .build())
                  
                  .build();
        // @formatter:on

        Account account = null;
        try {
            account = Account.create(params);

            System.out.println("account=" + account.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convert(account);
    }

    private StripeAccountDTO convert(Account account) {
        return new StripeAccountDTO(account.getId(), account.getType());
    }

    @Override
    public StripeAccountLinkDTO getAccountLinkUrl(String accountId) {
        // @formatter:off
        AccountLinkCreateParams params =
                AccountLinkCreateParams
                  .builder()
                  .setAccount(accountId)
                  .setRefreshUrl("http://localhost:3000/reauth")
                  .setReturnUrl("http://localhost:3000/return")
                  .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                  .build();

        // @formatter:on

        AccountLink accountLink = null;
        try {
            accountLink = AccountLink.create(params);

            System.out.println("accountLink=" + accountLink.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new StripeAccountLinkDTO(LocalDateTime.ofInstant(Instant.ofEpochSecond(accountLink.getExpiresAt()), TimeZone.getDefault().toZoneId()), accountLink.getUrl());
    }

    @Override
    public StripeChargeDTO charge(String accountId, Double amount) {
        Stripe.apiKey = stripeSecretKey;

        log.info("create({}, {})", accountId, amount);
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", paymentMethodTypes);
        params.put("amount", amount.longValue() * 100);
        params.put("currency", "usd");

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(accountId).build();
        PaymentIntent paymentIntent = null;

        try {
            paymentIntent = PaymentIntent.create(params, requestOptions);
            System.out.println("paymentIntent");
            System.out.println(paymentIntent.toJson());
        } catch (StripeException e) {
            log.warn("StripeException, msg={}", e.getMessage());
            e.printStackTrace();
        }

        StripeToken stripeToken = stripeTokenService.getCreditCardTokenFromStripe(accountId, "Folau Dev");

        //

        try {

            Map<String, Object> sourceParams = new HashMap<>();
            sourceParams.put("type", "ach_credit_transfer");
            sourceParams.put("currency", "usd");
            Map<String, Object> ownerParams = new HashMap<>();
            ownerParams.put("email", "jenny.rosen@example.com");
            sourceParams.put("owner", ownerParams);

            Source source = Source.create(sourceParams, requestOptions);

            Customer customer = Customer.create(CustomerCreateParams.builder().setName("folau").setPaymentMethod(stripeToken.getPaymentMethodId()).build(), requestOptions);

            log.info("customer={}", customer.toJson());
        } catch (Exception e) {
            log.warn("StripeException, msg={}", e.getMessage());
            e.printStackTrace();
        }

        log.info("paymentIntent.getId()={}", paymentIntent.getId());

        try {

            Thread.sleep(4000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {

            paymentIntent = PaymentIntent.retrieve(paymentIntent.getId(), PaymentIntentRetrieveParams.builder()
                    // .setClientSecret(paymentIntent.getClientSecret())
                    .build(), requestOptions);

            log.info("retrievdPaymentIntent={}", paymentIntent.toJson());

            PaymentIntent updatedPaymentIntent = paymentIntent.confirm(PaymentIntentConfirmParams.builder().setPaymentMethod(stripeToken.getPaymentMethodId()).build(), requestOptions);

            log.info("confirm paymentintent={}", updatedPaymentIntent.toJson());
        } catch (StripeException e) {
            log.warn("StripeException, msg={}", e.getMessage());
            e.printStackTrace();
        }
        // try {
        //
        // Thread.sleep(2000);
        // } catch (Exception e) {
        // // TODO: handle exception
        // }

        //
        // Map<String, Object> metadata = new HashMap<>();
        // metadata.put("order_id", "6735");
        // asparams = new HashMap<>();
        // params.put("metadata",metadata );
        //
        //
        // try {
        // PaymentIntent updatedPaymentIntent =
        // paymentIntent.confirm(asparams);
        //
        // log.info("confirm paymentintent={}",updatedPaymentIntent.toJson());
        // } catch (StripeException e) {
        // log.warn("StripeException, msg={}", e.getMessage());
        // e.printStackTrace();
        // }

        return null;
    }

    @Override
    public PaymentIntentDTO createPaymentIntent(String accountId, Double amount) {
        Stripe.apiKey = stripeSecretKey;

        CustomerCreateParams customerParams = CustomerCreateParams.builder().setDescription("Test customer holder").setName("Test customer").build();
        Customer customer = null;

        try {
            customer = Customer.create(customerParams);
        } catch (Exception e) {
            log.warn("StripeException, customer, msg={}", e.getMessage());
        }

        String customerId = "cus_Lgyk8DhX8TytPQ";

        log.info("create({}, {})", accountId, amount);
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        long applicationFeeAmount = 10 * 100;
        long chargeAmount = (amount.longValue() * 100) + applicationFeeAmount;
        long stripeFeeAmount = (long) ((2.9 / 100) * chargeAmount);
        long totalCharge = chargeAmount + stripeFeeAmount;

        log.info("chargeAmount({}, {}, {}, {})", chargeAmount, stripeFeeAmount, totalCharge, applicationFeeAmount);

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", paymentMethodTypes);
        // params.put("application_fee_amount", applicationFeeAmount);
        params.put("amount", totalCharge);
        params.put("currency", "usd");

        // Map<String, Object> transferDataParams = new HashMap<>();
        // transferDataParams.put("destination", accountId);
        // params.put("transfer_data", transferDataParams);

        // PaymentIntentCreateParams sdfsd = PaymentIntentCreateParams.builder()
        // .setAmount(totalCharge)
        // .setApplicationFeeAmount(applicationFeeAmount)
        // .setCurrency("usd")
        // .setTransferData(PaymentIntentCreateParams.TransferData.builder()
        // .setDestination(accountId)
        // .build())
        // .build();

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .addPaymentMethodType("card")
                .setAmount(totalCharge)
                .setCurrency("usd")
                // .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                .setCustomer(customerId)
                .setPaymentMethod("pm_1KzamuCRM62QoG6sXvIVLj5y")
                // .setConfirm(true)
                .setTransferGroup("group-" + UUID.randomUUID().toString())
                .build();

        PaymentIntent paymentIntent = null;

        try {
            paymentIntent = PaymentIntent.create(createParams);
            System.out.println(paymentIntent.toJson());
        } catch (StripeException e) {
            log.warn("StripeException, msg={}", e.getMessage());
        }

        PaymentIntentDTO paymentIntentDTO = PaymentIntentDTO.builder()
                .amount((double) (paymentIntent.getAmount() / 100))
                .clientSecret(paymentIntent.getClientSecret())
                .accountId(accountId)
                .status(paymentIntent.getStatus())
                .id(paymentIntent.getId())
                .build();

        return paymentIntentDTO;
    }

    @Override
    public PaymentIntentDTO capturePaymentIntent(String accountId, String paymentIntentId) {

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(accountId).build();

        PaymentIntent paymentIntent = null;

        try {
            paymentIntent = PaymentIntent.retrieve(paymentIntentId, requestOptions);

            System.out.println(paymentIntent.toJson());

            paymentIntent = paymentIntent.confirm();

            System.out.println("confirmed paymentIntent: " + paymentIntent.toJson());

        } catch (StripeException e) {
            log.warn("StripeException, msg={}", e.getMessage());
        }

        return null;
    }

    @Override
    public PaymentIntentDTO confirmPaymentIntent(String paymentIntentId, Double amount) {
        log.info("paymentIntentId={}, amount={}", paymentIntentId, amount);

        long transferAmount = (long) (amount * .2 * 100);

        PaymentIntent paymentIntent = null;

        try {
            paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            System.out.println("confirmed paymentIntent: " + paymentIntent.toJson());

            String transferGroup = paymentIntent.getTransferGroup();

            List<com.stripe.model.Charge> charges = paymentIntent.getCharges().getData();

            com.stripe.model.Charge charge = charges.get(charges.size() - 1);

            /**
             * make sure TransferGroup is the same from the payment intent
             */
            TransferCreateParams transferParams = TransferCreateParams.builder()
                    .setAmount(transferAmount)
                    .setCurrency("usd")
                    .setDestination("acct_1Kwbek2EkAQclp9I")
                    
                    //https://stripe.com/docs/connect/charges-transfers#transfer-availability
                    .setSourceTransaction(charge.getId())
                    .setTransferGroup(transferGroup)
                    .build();

            System.out.println("transferParams: " + transferParams.toMap().toString());

            Transfer transfer = Transfer.create(transferParams);

            System.out.println("transfer: " + transfer.toJson());

        } catch (StripeException e) {
            log.warn("StripeException, msg={}", e.getMessage());
        }

        return null;
    }

    // @Override
    // public PaymentIntentDTO confirmPaymentIntent(String paymentIntentId, Double amount) {
    // log.info("paymentIntentId={}, amount={}",paymentIntentId, amount);
    //
    // int transferAmount = (int)(amount * .5 * 100);
    //
    // log.info("transferAmount={}",transferAmount);
    //
    // Map<String, Object> params = new HashMap<>();
    // params.put("amount", transferAmount);
    // params.put("currency", "usd");
    // params.put("destination", "acct_1Kwbek2EkAQclp9I");
    // params.put("transfer_group", "ORDER_" + UUID.randomUUID().toString());
    //
    // log.info("params={}",params.toString());
    //
    // try {
    // com.stripe.model.Transfer transfer = com.stripe.model.Transfer.create(params);
    //
    // log.info("transfer={}", transfer.toJson());
    // } catch (StripeException e) {
    // log.warn("StripeException, msg={}", e.getMessage());
    // }
    //
    // return null;
    // }

}
