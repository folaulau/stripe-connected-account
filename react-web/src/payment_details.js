import './payment_method.css';
import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Link,
  useLocation
} from "react-router-dom";
import Api from './api/Api';
import Header from './layout/header';
import Footer from './layout/footer';

function useQuery() {
  const { search } = useLocation();

  return React.useMemo(() => new URLSearchParams(search), [search]);
}

export default function PaymentDetails() {

  let query = useQuery();

  const [paymentIntentId, setPaymentIntentId] = useState(query.get("payment_intent"));
  const [chargeAmount, setChargeAmount] = useState(query.get("amount"));

  useEffect(() => {
    console.log("useEffect with []")
    // Create PaymentIntent as soon as the page loads
    confirmPaymentIntent()
  },[]);

  const confirmPaymentIntent = () => {

    console.log("paymentIntentId, ", paymentIntentId);
    console.log("chargeAmount, ", chargeAmount);
    Api.confirmPaymentIntent(paymentIntentId, chargeAmount)
    .then(response => {
        console.log("response");
        console.log(response);
        let accountDetails = response.data
        console.log(accountDetails);
    }).catch(error => {
        console.log("error");
        console.log(error.response.data);
    });
  }

  return (
    <>
      <Header/>
      <div className='row'>
        <div className='col-6 offset-3'>
          <h3>Payment Details</h3>
          <div>Amount: ${chargeAmount}</div>
          
        </div>
      </div>
      <Footer/>
    </>
  );
}
