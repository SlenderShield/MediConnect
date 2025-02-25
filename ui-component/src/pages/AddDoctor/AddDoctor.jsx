import React, { useContext, useState } from "react";
import Swal from "sweetalert2";
import { addDoctor } from "../../services/service";
import { spinnerContext } from "../../components/Spinner/spinnerContext";

const AddDoctor = () => {
  // Initial state structure for doctor form
  const initialDoctor = {
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    speciality: "",
    yearsOfExperience: 0,
    status: "AVAILABLE", // Default status when adding a doctor
  };
  const [doctor, setDoctor] = useState(initialDoctor); // State to track doctor form data
  const { setShowSpinner } = useContext(spinnerContext);

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent page refresh on form submit
    console.log(doctor); // Logging the doctor data for debug purposes

    setShowSpinner(true);

    // Make API call to add doctor
    addDoctor(doctor)
      .then((res) => {
        setShowSpinner(false); // Hide spinner after the API response
        if (res.status === 200) {
          // Show success message if doctor is successfully added
          Swal.fire({
            title: "Success",
            text: "Doctor added",
            icon: "info",
          });
        }
        handleReset(); // Reset form after successful submission
      })
      .catch(() => {
        setShowSpinner(false); // Hide spinner in case of error
        // Show error message if adding doctor fails
        Swal.fire({
          title: "Failed",
          text: "Error encountered",
          icon: "error",
        });
      });
  };

  // Reset form to initial state
  const handleReset = () => {
    setDoctor(initialDoctor);
  };

  // Handle changes in form input fields
  const handleChange = (event) => {
    const { id, value, type, checked } = event.target;
    // Update only the changed field in the doctor state
    setDoctor((prevData) => ({
      ...prevData,
      [id]: type === "checkbox" ? checked : value,
    }));
  };
  return (
    <div className="content-wrapper row m-0 justify-content-center align-items-content">
      <div className="col-4 my-5">
        <h2>Add Doctor</h2>
        <form onSubmit={handleSubmit} onReset={handleReset}>
          <div className="my-4">
            <label for="firstName" className="form-label">
              First Name
            </label>
            <input
              required
              value={doctor.firstName}
              onChange={handleChange}
              type="text"
              className="form-control"
              id="firstName"
            />
          </div>
          <div className="my-4">
            <label for="lastName" className="form-label">
              Last Name
            </label>
            <input
              required
              value={doctor.lastName}
              onChange={handleChange}
              type="text"
              className="form-control"
              id="lastName"
            />
          </div>
          <div className="my-4">
            <label for="email" className="form-label">
              Email
            </label>
            <input
              required
              value={doctor.email}
              onChange={handleChange}
              type="email"
              className="form-control"
              id="email"
            />
          </div>
          <div className="my-4">
            <label for="phone" className="form-label">
              Phone
            </label>
            <input
              required
              value={doctor.phone}
              onChange={handleChange}
              type="text"
              className="form-control"
              id="phone"
            />
          </div>
          <div className="my-4">
            <label for="speciality" className="form-label">
              Speciality
            </label>
            <input
              required
              value={doctor.specialty}
              onChange={handleChange}
              type="text"
              className="form-control"
              id="speciality"
            />
          </div>
          <div className="my-4">
            <label for="yearsOfExperience" className="form-label">
              Years of Experience
            </label>
            <input
              required
              value={doctor.yearsOfExperience}
              onChange={handleChange}
              type="number"
              className="form-control"
              min={0}
              id="yearsOfExperience"
            />
          </div>
          <div className="my-4 d-flex">
            <button className="btn btn-primary" type="submit">
              Submit
            </button>
            <button className="btn btn-danger mx-2" type="reset">
              Reset
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddDoctor;
