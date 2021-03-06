import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';

import { 
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import './index.css';
import App from './App';
import MyPaymentMethod from './payment_method';
import reportWebVitals from './reportWebVitals';
import PaymentDetails from './payment_details'

console.log("api url: " + process.env.REACT_APP_API_URL);
console.log("app url: " + process.env.REACT_APP_APP_URL);

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  // <React.StrictMode>
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/payment-method" element={<MyPaymentMethod />} />
          <Route path="/payment-details" element={<PaymentDetails />} />
        </Routes>
     </BrowserRouter>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
