import React, { useContext, useEffect, useState } from "react";
import "./Home.css";
import Appointments from "../../components/Appointments/Appointments";
import Doctors from "../../components/Doctors/Doctors";
import Patients from "../../components/Patients/Patients";
import { updateDoctor } from "../../services/service";
import Swal from "sweetalert2";
import { AuthContext } from "../../auth/AuthContext";
import { spinnerContext } from "../../components/Spinner/spinnerContext";

const Home = () => {
  // State to manage the current section (Appointments, Doctors, or Patients)
  const [currentSection, setCurrentSection] = useState("Appointments");
  // State to manage doctor's availability status (true if available, false if not)
  const [doctorAvailability, setDoctorAvailability] = useState(false);
  // Access user data and role from AuthContext
  const { user, currentRole } = useContext<any>(AuthContext);
  // Access spinner context to show/hide the loading spinner during API calls
  const { setShowSpinner } = useContext(spinnerContext);

  // Effect to check and set the doctor's availability when the component mounts or when role/user data changes
  useEffect(() => {
    if (currentRole === "ROLE_DOCTOR")
      // Set doctor's availability based on the current status in user data
      setDoctorAvailability(user?.status === "AVAILABLE");
  }, [currentRole, user]);

  // Handle change in the selected section (Appointments, Doctors, or Patients)
  const handleSectionChange = (e) => {
    setCurrentSection(e.target.value); // Update the currentSection state with the selected section
  };

  // Handle the toggling of doctor's availability status
  const handleDoctorStatus = () => {
    // Prepare the payload with updated doctor status
    const doctorPayload = {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      phone: user.phone,
      speciality: user.speciality,
      yearsOfExperience: user.yearsOfExperience,
      status: doctorAvailability ? "NOT_AVAILABLE" : "AVAILABLE",
    };

    setShowSpinner(true);

    // Make API call to update the doctor's status
    updateDoctor(doctorPayload, user.id)
      .then((res: any) => {
        setShowSpinner(false);
        if (res.status === 200) {
          setDoctorAvailability(!doctorAvailability);
        } else {
          Swal.fire({
            title: "Error",
            text: "Not Updated!",
            icon: "error",
          });
        }
      })
      .catch((err) => {
        setShowSpinner(false);
        console.log(err);
      });
  };

  return (
    <div className="content-wrapper d-flex m-0 align-items-center justify-content-center">
      <div className="w-50" style={{ maxHeight: "80vh", overflowY: "auto" }}>
        {/* Section for selecting the current section (Appointments, Doctors, Patients) */}
        <div className="d-flex m-0 align-items-center justify-content-between">
          <select
            className="form-select w-25"
            style={{ border: "2px solid skyblue" }}
            defaultValue={currentSection}
            onChange={handleSectionChange}
          >
            {/* Only show Doctors and Patients options for ROLE_ADMIN */}
            {currentRole === "ROLE_ADMIN" && (
              <option value="Doctors">Doctors</option>
            )}
            <option value="Appointments">Appointments</option>
            {currentRole === "ROLE_ADMIN" && (
              <option value="Patients">Patients</option>
            )}
          </select>

          {/* Show doctor's availability toggle only for ROLE_DOCTOR */}
          {currentRole === "ROLE_DOCTOR" && (
            <div className="form-check d-inline">
              <input
                className="form-check-input"
                type="checkbox"
                value=""
                id="availableCheck"
                onChange={handleDoctorStatus}
                checked={doctorAvailability}
              />
              <label className="form-check-label" htmlFor="availableCheck">
                Available
              </label>
            </div>
          )}
        </div>
        {/* Render different sections based on the currentSection value */}
        {currentSection === "Doctors" ? (
          <Doctors />
        ) : currentSection === "Patients" ? (
          <Patients />
        ) : (
          <Appointments />
        )}
      </div>
    </div>
  );
};
export default Home;
