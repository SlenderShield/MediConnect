import React, { useContext, useState } from "react";
import { AuthContext } from "../../auth/AuthContext";
import { navigateTo } from "../../services/navigateUtil";
import { spinnerContext } from "../../components/Spinner/spinnerContext";
import { signIn } from "../../services/service";

const Login = () => {
  // State variables to manage the user input for username and password
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  // Accessing login function from AuthContext
  const { login } = useContext<any>(AuthContext);

  // Accessing setShowSpinner to control the spinner visibility during login
  const { setShowSpinner } = useContext(spinnerContext);

  // Handle form submission when user tries to log in
  const handleSubmit = async (e: any) => {
    e.preventDefault();
    try {
      setShowSpinner(true);
      // Attempt to sign in with the provided username and password
      signIn(userName, password)
        .then((response) => {
          setShowSpinner(false);
          login(response.data);
        })
        .catch((err) => {
          setShowSpinner(false);
          navigateTo("/login");
        });
    } catch (error) {
      setShowSpinner(false);
      navigateTo("/login");
    }
  };

  return (
    <div>
      {/* Modal styling to create a login popup */}
      <div
        className="modal show d-block"
        style={{ backgroundColor: "rgba(18, 19, 19, 0.85)" }}
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-body">
              <h4 className="mb-3 text-center">LOGIN</h4>
              <div className="row justify-content-center">
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="userName" className="form-label">
                      User Name
                    </label>
                    <input
                      className="form-control"
                      id="userName"
                      value={userName}
                      onChange={(e) => setUserName(e.target.value)}
                      placeholder="Enter your username"
                      required
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                      Password
                    </label>
                    <input
                      type="password"
                      className="form-control"
                      id="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      placeholder="Enter your password"
                      required
                    />
                  </div>
                  <button type="submit" className="btn btn-dark w-100">
                    Login
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
