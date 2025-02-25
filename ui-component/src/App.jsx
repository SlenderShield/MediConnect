import React from "react";
import Navbar from "./components/Navbar/Navbar";
import Home from "./pages/Home/Home";
import AddDoctor from "./pages/AddDoctor/AddDoctor";
import AddAppointment from "./pages/AddAppointment/AddAppointment";
import Login from "./pages/Login/Login";
import Unauthorized from "./pages/Unauthorized";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import { Route, Routes } from "react-router-dom";
import AuthProvider from "./auth/AuthProvider";
import SpinnerProvider from "./components/Spinner/SpinnerProvider";

function App() {
  return (
    <>
      {/* Wrapping the app in SpinnerProvider and AuthProvider to provide context globally */}
      <SpinnerProvider>
        <AuthProvider>
          <Routes>
            {/* Default route for authenticated users with roles ROLE_ADMIN, ROLE_PATIENT, ROLE_DOCTOR */}
            <Route
              path="/"
              element={
                <ProtectedRoute
                  roles={["ROLE_ADMIN", "ROLE_PATIENT", "ROLE_DOCTOR"]}
                >
                  <Navbar />
                  <Home />
                </ProtectedRoute>
              }
            ></Route>
            {/* Login route for users to sign in */}
            <Route path="/login" element={<Login />}></Route>
            {/* Unauthorized route for users trying to access restricted pages */}
            <Route path="/unauthorized" element={<Unauthorized />}></Route>
            {/* Route to add a new doctor, only accessible by admins */}
            <Route
              path="/newdoctor"
              element={
                <ProtectedRoute roles={["ROLE_ADMIN"]}>
                  <Navbar />
                  <AddDoctor />
                </ProtectedRoute>
              }
            ></Route>
            {/* Route to add a new appointment, only accessible by patients */}
            <Route
              path="/newappointment"
              element={
                <ProtectedRoute roles={["ROLE_PATIENT"]}>
                  <Navbar />
                  <AddAppointment />
                </ProtectedRoute>
              }
            ></Route>
          </Routes>
        </AuthProvider>
      </SpinnerProvider>
    </>
  );
}

export default App;
