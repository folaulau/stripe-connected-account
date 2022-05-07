import {
  Link
} from "react-router-dom";

export default function Header() {
  return (
    <header className="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
      <a href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
        <span className="fs-4">Stripe Demo</span>
      </a>

      <ul className="nav nav-pills">
        <li className="nav-item">
          <Link to="/" className="nav-link">Home</Link>
        </li>
        <li className="nav-item">
          <Link to="/payment-method" className="nav-link">PaymentIntent</Link>
        </li>
        <li className="nav-item">
          <Link to="/payment-method-setup" className="nav-link">Setup Intent</Link>
        </li>
      </ul>
      <br/>
    </header>
  );
}