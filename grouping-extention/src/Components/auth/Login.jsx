import React from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import NIBMLogo from "../../assets/images/NIBMLogo.png";
import StudentGroupImage from "../../assets/images/StudentGroupImage.png";

const Login = () => {
  const navigate = useNavigate();

  const handleSignIn = (e) => {
    e.preventDefault(); // stop page refresh
    navigate("/groupsettings");
  };

  return (
    <div className="login-page">

      {/* Left Container */}
      <div className="login-left">
        <img src={NIBMLogo} alt="NIBM Logo" className="logo" />
        <img src={StudentGroupImage} alt="Background" className="Sg-image" />
      </div>

      {/* Right Container */}
      <div className="login-right">
        <h1 className="brand">GroupMe</h1>

        <div className="login-box">
          <h2 className="welcome">Welcome Back</h2>
          <p className="para">Sign in to manage your student groups</p>

          <form className="login-form" onSubmit={handleSignIn}>
            <div className="form-group">
              <label>Email Address</label>
              <input
                className="email-input"
                type="email"
                placeholder="lecturer@university.edu"
              />
            </div>

            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                placeholder="Enter your password"
              />
            </div>

            <button type="submit" className="login-btn">
              Sign In
            </button>
          </form>

          <a href="#" className="forgot-link">
            Forgot password?
          </a>
        </div>
      </div>

    </div>
  );
};

export default Login;
