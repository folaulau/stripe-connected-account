import logo from './logo.svg';
import './App.css';
import {
  Link
} from "react-router-dom";
import Header from './layout/header';
import Footer from './layout/footer';

function App() {
  return (
    <>
      <Header/>
        <div className='container'>
          <div className='row'>
            <div className='col-12'>
              Welcome to Stripe Demo
            </div>
          </div>
        </div>
      <Footer/>
    </>
  );
}

export default App;
