import React, { useEffect, useState } from "react";
import {
  PaymentElement,
  useStripe,
  useElements
} from "@stripe/react-stripe-js";
import Api from "./api/Api";
import { useNavigate } from "react-router-dom";

export default function CheckoutForm(props) {

  let navigate = useNavigate();

  const stripe = useStripe();
  const elements = useElements();

  const [message, setMessage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [paymentIntentId, setPaymentIntentId] = useState(null);
  const [paymentIntentStatus, setPaymentIntentStatus] = useState(null);

  useEffect(() => {
    console.log("CheckoutForm")
    if (!stripe) {
        console.log("stripe is null")
        return;
    }

    const clientSecret = props.clientSecret;

    console.log("clientSecret ", clientSecret)
    if (!clientSecret) {
        console.log("clientSecret is null")
        return;
    }

    stripe.retrievePaymentIntent(clientSecret).then(({ paymentIntent }) => {
      console.log("paymentIntent.status, ", paymentIntent.status)
      setPaymentIntentStatus(paymentIntent.status)

      switch (paymentIntent.status) {
        case "succeeded":
          setMessage("Payment succeeded!");
          break;
        case "processing":
          setMessage("Your payment is processing.");
          break;
        case "requires_payment_method":
          setMessage("Your payment was not successful, please try again.");
          break;
        case "requires_confirmation":
          setMessage("You are using your existing payment method. You just need to confirm");
          break;
        default:
          setMessage("Something went wrong.");
          break;
      }
    });
  }, [stripe]);

  const handleSubmit = async (e) => {
    console.log("handleSubmit, paymentIntentStatus=", paymentIntentStatus)
    e.preventDefault();

    if (!stripe || !elements) {
      // Stripe.js has not yet loaded.
      // Make sure to disable form submission until Stripe.js has loaded.
      return;
    }

    setIsLoading(true);

    const { error } = await stripe.confirmPayment({
      elements,
      confirmParams: {
        // Make sure to change this to your payment completion page
        return_url: process.env.REACT_APP_APP_URL+"/payment-details?amount="+props.chargeAmount,
      },
    });

    // This point will only be reached if there is an immediate error when
    // confirming the payment. Otherwise, your customer will be redirected to
    // your `return_url`. For some payment methods like iDEAL, your customer will
    // be redirected to an intermediate site first to authorize the payment, then
    // redirected to the `return_url`.
    if (error.type === "card_error" || error.type === "validation_error") {
      setMessage(error.message);
    } else {
      setMessage("An unexpected error occured.");
    }

    setIsLoading(false);
  };

  const giveConsent = () => {
    console.log("confirmpayment, paymentIntentStatus=", paymentIntentStatus)
    console.log("clientSecret=", props.clientSecret)

    stripe.confirmCardPayment( props.clientSecret)
    .then(function(result) {
      console.log("confirmCardPayment result")
      console.log(result)
      console.log(result.paymentIntent)
      navigate("/payment-details?amount="+props.chargeAmount+"&payment_intent="+result.paymentIntent.id);
    }).catch(error => {
      console.log("confirmCardPayment error");
      console.log(error.response.data);
    });
  }

  return (
    <form id="payment-form" onSubmit={handleSubmit}>
      <div>PaymentIntent Status: {message}</div>
      {paymentIntentStatus==="requires_payment_method" && 
        <div>
          <PaymentElement id="payment-element" />
          <button disabled={isLoading || !stripe || !elements} id="submit">
            <span id="button-text">
              {isLoading ? <div className="spinner" id="spinner"></div> : "Pay now"}
            </span>
          </button>
        </div>
      }
      {paymentIntentStatus==="requires_confirmation" && <button type="button" onClick={giveConsent}>confirm</button>}
      {/* Show any error or success messages */}
      {message && <div id="payment-message">{message}</div>}
    </form>
  );
}