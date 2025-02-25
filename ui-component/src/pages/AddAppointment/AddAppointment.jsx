import React, { useContext, useEffect, useState } from "react";
import Swal from "sweetalert2";
import { addAppointment, getAllDoctors } from "../../services/service";
import { getCurrentTime } from "../../services/util.service";
import { AuthContext } from "../../auth/AuthContext";
import { spinnerContext } from "../../components/Spinner/spinnerContext";

const AddAppointment = () => {
	const { setShowSpinner } = useContext(spinnerContext);
	const [doctors, setDoctors] = useState([]); // State to store available doctors
	const [currentTime, setCurrentTime] = useState(Date.now()); // Current timestamp for appointment validation
	const { user } = useContext(AuthContext); // Accessing user context for patient info

	// Initial appointment state to store form data
	const initialAppointment = {
		doctorId: "",
		patientId: user?.id,
		appointmentTime: "",
		status: "PENDING",
		notes: "",
		doctorComments: "",
	};
	const [appointment, setAppointment] = useState(initialAppointment); // State to store appointment details

	useEffect(() => {
		setCurrentTime(getCurrentTime()); // Set the current time for appointment validation
		setShowSpinner(true);

		// Fetch available doctors from the backend
		getAllDoctors()
			.then((res) => {
				setShowSpinner(false);
				const aps = res?.data?.data === undefined ? [] : res?.data?.data;
				// Filter the doctors who are available
				setDoctors(aps.filter((doctor) => doctor.status === "AVAILABLE"));

				// Set the first available doctor as default in the form
				if (aps?.length > 0) initialAppointment.doctorId = aps[0].id;
				setAppointment(initialAppointment);
				// Update the state with initial appointment
			})
			.catch((e) => {
				setShowSpinner(false);
			});
	}, []);

	// Handle form submission to add appointment
	const handleSubmit = (e) => {
		e.preventDefault();
		if (doctors.length === 0) {
			// If no doctors are available, show an alert
			Swal.fire({
				title: "Sorry, No Doctors available",
				timer: 2000,
				icon: "info",
			});
			return;
		}

		// Prepare the appointment data to be submitted
		const appointmentTemp = appointment;
		appointmentTemp.patientId = user?.id;
		setShowSpinner(true);
		// Make API call to add the appointment
		addAppointment(appointmentTemp)
			.then((res) => {
				setShowSpinner(false);
				if (res.status === 200) {
					// Show success alert if appointment is added successfully
					Swal.fire({
						title: "Success",
						text: "Appointment added",
						icon: "info",
					});
				}
				handleReset(); // Reset form fields after submission
			})
			.catch((e) => {
				setShowSpinner(false);
				Swal.fire({
					title: "Failed",
					text: "Error encountered",
					icon: "error",
				});
			});
	};

	// Reset the appointment form fields to initial state
	const handleReset = () => {
		setAppointment(initialAppointment);
	};

	// Handle changes in form input fields
	const handleChange = (event) => {
		const { id, value, type, checked } = event.target;
		// Update only the changed field in the appointment state
		setAppointment((prevData) => ({
			...prevData,
			[id]: type === "checkbox" ? checked : value, // Update only the changed field
		}));
	};
	return (
		<div className="content-wrapper row m-0 justify-content-center align-items-content">
			<div className="col-4 my-5">
				<h2>Add appointment</h2>
				<form onSubmit={handleSubmit} onReset={handleReset}>
					<div className="my-4">
						<label htmlFor="doctorId" className="form-label">
							Doctor
						</label>
						<select required className="form-select" onChange={handleChange} id="doctorId" disabled={doctors.length === 0}>
							{doctors.length > 0 &&
								// If doctors are available, map them to dropdown options
								doctors.map(
									(doctor) =>
										doctor.status.toLowerCase() === "available" && (
											<option key={doctor.id} value={doctor.id}>
												{doctor.firstName.trim() + " " + doctor.lastName.trim()}
											</option>
										)
								)}
							{/* // If no doctors are available, show a placeholder */}
							{doctors.length === 0 && <option disabled>Sorry, No Doctors available</option>}
						</select>
					</div>
					<div className="my-4">
						<label htmlFor="appointmentTime" className="form-label">
							Appointment Time
						</label>
						<input required value={appointment.appointmentTime} onChange={handleChange} type="datetime-local" className="form-control" id="appointmentTime" min={currentTime} />
					</div>
					<div className="my-4">
						<label htmlFor="notes" className="form-label">
							Notes
						</label>
						<input required value={appointment.notes} onChange={handleChange} type="text" className="form-control" id="notes" />
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

export default AddAppointment;
