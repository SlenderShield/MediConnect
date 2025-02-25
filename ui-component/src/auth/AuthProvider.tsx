import React, { useEffect, useState } from "react";
import { AuthContext } from "./AuthContext";
import { useNavigate } from "react-router-dom";
import { setNavigate } from "../services/navigateUtil";
import { getDoctorByEmail, getPatientByEmail } from "../services/service";

const AuthProvider = ({ children }: any) => {
  // State to manage user, auth details, token, and user role from local storage.
  const [user, setUser] = useState(localStorage.getItem("user") || null);
  const [auth, setAuth] = useState<any>(localStorage.getItem("auth") || null);
  const [token, setToken] = useState(localStorage.getItem("jwt") || null);
  const [userRole, setUserRole] = useState(
    localStorage.getItem("userRole") || null
  );
  const navigate = useNavigate();

  // Fetch user details if token and auth exist or token changes
  useEffect(() => {
    if (token && auth) {
      if (typeof auth === "string") {
        fetchUser(JSON.parse(auth)?.email);
      } else if (typeof auth === "object") {
        fetchUser(auth?.email);
      }
    }
  }, [token]);

  // Set global navigation utility whenever navigate changes.
  useEffect(() => {
    setNavigate(navigate);
  }, [navigate]);

  // Fetch user details based on role and save it to state and local storage for furthur use.
  const fetchUser = async (email) => {
    try {
      if (userRole === "ROLE_DOCTOR") {
        getDoctorByEmail(email).then((res) => {
          setUser(res?.data?.data);
          localStorage.setItem("user", JSON.stringify(res?.data?.data));
          navigate("/");
        });
      } else if (userRole === "ROLE_PATIENT") {
        getPatientByEmail(email).then((res) => {
          setUser(res?.data?.data);
          localStorage.setItem("user", JSON.stringify(res?.data?.data));
          navigate("/");
        });
      }
    } catch (e) {
      console.log(e);
    }
  };

  // Login function to set user session states, store in localstorage and navigate to home screen.
  const login = (res: any) => {
    setUserRole(res.roles[0]);
    setToken(res.token);
    setAuth(res);
    fetchUser(res?.email);
    localStorage.setItem("jwt", res.token);
    localStorage.setItem("auth", JSON.stringify(res));
    localStorage.setItem("userRole", res.roles[0]);
    if(res.roles[0] === "ROLE_ADMIN") navigate("/");
  };

  // Logout function to update states and delete details stored in localstorage (navigate back to login screen)
  const logout = () => {
    setToken(null);
    setUser(null);
    setUserRole(null);
    localStorage.removeItem("jwt");
    localStorage.removeItem("userRole");
    localStorage.removeItem("user");
    localStorage.removeItem("auth");
    navigate("/login");
  };
  return (
    <AuthContext.Provider
      // Drilling down the values to child components
      value={{
        user: typeof user === "string" ? JSON.parse(user) : user,
        token,
        currentRole: userRole,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
