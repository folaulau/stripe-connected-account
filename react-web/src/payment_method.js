import './payment_method.css';
import React, { useState, useEffect } from "react";
import {loadStripe} from '@stripe/stripe-js';
import {
  Elements
} from '@stripe/react-stripe-js';
import CheckoutForm from './checkout_form';
import Api from './api/Api';
import Header from './layout/header';
import Footer from './layout/footer';

const stripePromise = loadStripe("pk_test_51KaS6tCRM62QoG6sqWVwwxEtIaoeOUYfa5faVn8Yu1tJO0LgHDAZGi9cuUr6HQDRvUS9bV5ZaNBm5S3HjMZNyCSW000EyK1QZx");

export default function MyPaymentMethod() {

  const [clientSecret, setClientSecret] = useState(null);

  useEffect(() => {
    console.log("useEffect with []")
    // Create PaymentIntent as soon as the page loads
    loadClientSecret()
  },[]);

  const loadClientSecret = () => {
    if(clientSecret!=null && clientSecret.length>0){
      return
    }
    console.log("clientSecret, ", clientSecret);
    let payload = {}
    let accountId = "acct_1Kvuna2E8yUfNXhV"
    let amount = 120
    Api.generatePaymentIntent(payload, accountId, amount)
    .then(response => {
        console.log("response");
        console.log(response);
        let accountDetails = response.data
        console.log(accountDetails);
        setClientSecret(accountDetails.clientSecret)
    }).catch(error => {
        console.log("error");
        console.log(error.response.data);
    });
  }

  

  const appearance = {
    theme: 'stripe',
  };
  const options = {
    clientSecret,
    appearance,
  };

  return (
    <>
      <Header/>
      <div className='row'>
        <div className='col-6 offset-3'>
          <h3>Payment Method</h3>
            {clientSecret && (
            <Elements options={options} stripe={stripePromise}>
              <CheckoutForm clientSecret={clientSecret} />
            </Elements>
            )}
        </div>
      </div>
      <Footer/>
    </>
  );
}
