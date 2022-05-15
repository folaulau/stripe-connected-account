import axios from 'axios';

var instance = axios.create({
    baseURL: process.env.REACT_APP_API_URL
});

const Api = {

    generatePaymentIntent: (payload, accountId, amount) => {

        const options = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return instance.post('/stripe/paymentintent?accountId='+accountId+'&amount='+amount, JSON.stringify(payload), options);
    },
    confirmPaymentIntent: (paymentIntentId, amount) => {

        const options = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return instance.post('/stripe/paymentintent/confirm?amount='+amount+'&paymentIntentId='+paymentIntentId, JSON.stringify({}), options);
    }
}

export default  Api;