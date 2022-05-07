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
    confirmPaymentIntent: (accountId, paymentIntentId) => {

        const options = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return instance.post('/stripe/paymentintent?accountId='+accountId+'&paymentIntentId='+paymentIntentId, JSON.stringify({}), options);
    }
}

export default  Api;