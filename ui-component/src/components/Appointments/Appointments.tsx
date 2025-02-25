import React, { useContext, useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendar, faCancel, faCheck, faClock, faPencil, faStickyNote, faTrash, faUser, faUserDoctor } from "@fortawesome/free-solid-svg-icons";
import { getAllAppointments, getAllAppointmentsByDoctor, getAllAppointmentsByPatient, updateAppointment } from "../../services/service";
import Swal from "sweetalert2";
import { spinnerContext } from "../Spinner/spinnerContext";

const Appointments = () => {
    const [appointments, setAppointments] = useState<any>([]);
    const [doctor, setDoctor] = useState<any>({});
    const [showModal, setShowModal] = useState(false);
    const [updateModal, setUpdateModal] = useState(false);
    const [currentRole, setCurrentRole] = useState<any>("");
    const { setShowSpinner } = useContext(spinnerContext);
    const user: any = JSON.parse(localStorage.getItem("user") || "{}");
    console.log(user);

    useEffect(() => {
        console.log("Component mounted. Fetching data...");
        fetchData();
    }, []);

    const fetchData = () => {
        const role = localStorage.getItem("userRole");
        console.log("User Role:", role);
        console.log("User Data:", user);
        setCurrentRole(role);
        setShowSpinner(true);

        if (role === "ROLE_PATIENT") {
            console.log(user);
            if (user && user.id) {
                console.log("Fetching appointments for patient...");
                getAllAppointmentsByPatient(user.id)
                    .then((res: any) => {
                        console.log(res);
                        setShowSpinner(false);
                        const aps = res?.data|| [];
                        console.log("Appointments fetched for patient:", aps);
                        setAppointments(aps);
                    })
                    .catch((err) => {
                        setShowSpinner(false);
                        console.error("Error fetching patient appointments:", err);
                    });
            } else {
                console.warn("User ID not found for ROLE_PATIENT.");
                setShowSpinner(false);
            }
        } else if (role === "ROLE_DOCTOR") {
            if (user && user.id) {
                console.log("Fetching appointments for doctor...");
                getAllAppointmentsByDoctor(user.id)
                    .then((res: any) => {
                        setShowSpinner(false);
                        const aps = res?.data || [];
                        console.log("Appointments fetched for doctor:", aps);
                        setAppointments(aps);
                    })
                    .catch((err) => {
                        setShowSpinner(false);
                        console.error("Error fetching doctor appointments:", err);
                    });
            } else {
                console.warn("User ID not found for ROLE_DOCTOR.");
                setShowSpinner(false);
            }
        } else {
            console.log("Fetching all appointments for admin...");
            getAllAppointments()
                .then((res: any) => {
                    setShowSpinner(false);
                    const aps = res?.data || [];
                    console.log("All appointments fetched for admin:", aps);
                    setAppointments(aps);
                })
                .catch((err) => {
                    setShowSpinner(false);
                    console.error("Error fetching all appointments:", err);
                });
        }
    };

    const handleShow = (doctor: any) => {
        console.log("Displaying doctor details:", doctor);
        setDoctor(doctor);
        setShowModal(true);
    };

    const handleClose = () => {
        console.log("Closing doctor details modal.");
        setShowModal(false);
    };

    const updateModalShow = () => {
        console.log("Opening update modal.");
        setUpdateModal(true);
    };

    const updateClose = () => {
        console.log("Closing update modal.");
        setUpdateModal(false);
    };

    const formatDate = (date) => {
        const formattedDate = new Intl.DateTimeFormat("en-GB", {
            day: "2-digit",
            month: "short",
            year: "numeric",
        }).format(new Date(date));
        console.log("Formatted date:", formattedDate);
        return formattedDate;
    };

    const isPast = (time) => {
        const isPastDate = new Date() > new Date(time);
        console.log(`Is ${time} in the past?`, isPastDate);
        return isPastDate;
    };

    const updateAppointmentStatus = (appointment, status) => {
        console.log("Updating appointment status:", { appointment, status });
        let currStatus = appointment.status;
        appointment.status = status;
        setShowSpinner(true);
        updateAppointment(appointment)
            .then((res: any) => {
                setShowSpinner(false);
                if (res.status === 200) {
                    console.log("Appointment status updated successfully.");
                    fetchData();
                } else {
                    console.error("Failed to update appointment status.");
                    appointment.status = currStatus;
                    Swal.fire({ title: "Error", text: "Failed to update", icon: "error" });
                }
            })
            .catch((err) => {
                setShowSpinner(false);
                appointment.status = currStatus;
                Swal.fire({ title: "Error", text: "Failed to update", icon: "error" });
                console.error("Error updating appointment status:", err);
            });
    };

    const updateAfterCheckup = (appointment, e) => {
        e.preventDefault();
        console.log("Updating appointment after checkup:", appointment);
        let currComment = appointment.doctorComments;
        appointment.doctorComments = e.target[3].value;
        appointment.status = "COMPLETED";
        setShowSpinner(true);
        updateAppointment(appointment)
            .then((res: any) => {
                setShowSpinner(false);
                if (res.status === 200) {
                    console.log("Appointment updated successfully after checkup.");
                    fetchData();
                    updateClose();
                } else {
                    console.error("Failed to update appointment after checkup.");
                    Swal.fire({ title: "Error", text: "Failed to update", icon: "error" });
                }
            })
            .catch((err) => {
                setShowSpinner(false);
                console.error("Error updating appointment after checkup:", err);
            });
    };

    const deleteAppointment = (appointment) => {
        console.log("Rejecting appointment:", appointment);
        appointment.status = "REJECTED";
        setShowSpinner(true);
        updateAppointment(appointment)
            .then((res: any) => {
                setShowSpinner(false);
                if (res.status === 200) {
                    console.log("Appointment rejected successfully.");
                    fetchData();
                } else {
                    console.error("Failed to reject appointment.");
                    Swal.fire({ title: "Error", text: "Failed to update", icon: "error" });
                }
            })
            .catch((err) => {
                setShowSpinner(false);
                console.error("Error rejecting appointment:", err);
            });
    };

    return (
        <div>
            {appointments.map((appointment: any) => (
                <div
                    key={appointment.id}
                    className="card my-2 w-100"
                    style={{
                      border: appointment.status && appointment.status.toLowerCase() === "pending"
                          ? "2px solid yellow"
                          : appointment.status && appointment.status.toLowerCase() === "confirmed"
                          ? "2px solid purple"
                          : appointment.status && appointment.status.toLowerCase() === "completed"
                          ? "2px solid green"
                          : isPast(appointment.appointmentTime)
                          ? "2px solid blue"
                          : "2px solid red",
                    }}
                >
                    <div className="card-body">
                        <div className="d-flex justify-content-between">
                            <div>
                                <h5 className="card-title">
                                    <FontAwesomeIcon icon={faUser} className="mx-1" />
                                    {appointment.patient.firstName.trim() + " " + appointment.patient.lastName.trim()}
                                </h5>
                                <div className="d-flex justify-content-between align-items-center">
                                    <p className="card-text m-0">
                                        <FontAwesomeIcon icon={faCalendar} className="mx-1" /> {formatDate(appointment.appointmentTime.split("T")[0])} <br />
                                        <FontAwesomeIcon icon={faClock} className="mx-1" /> {appointment.appointmentTime.split("T")[1].split(":").slice(0, 2).join(":")}
                                    </p>
                                </div>
                                <p className="card-text m-0">
                                    <FontAwesomeIcon icon={faStickyNote} className="mx-1" />
                                    {appointment.notes.slice(0, 40)}
                                </p>
                            </div>
                            {/* Buttons and Modals */}
                            <div className="d-flex flex-column justify-content-between align-items-center">
								{showModal && (
									<div className="modal show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
										<div className="modal-dialog modal-dialog-centered">
											<div className="modal-content">
												<div className="modal-header">
													<h5 className="modal-title">Doctor</h5>
													<button type="button" className="btn-close" onClick={handleClose}></button>
												</div>
												<div className="modal-body">
													<div className="my-2">
														<h4>{doctor.firstName.trim() + " " + doctor.lastName.trim()}</h4>
														<em>{doctor.specialty}</em>
													</div>
													<p className="my-1">Email: {doctor.email}</p>
													<p className="my-1">Phone: {doctor.phone}</p>
													<p className="my-1">Status: {doctor.status.toLowerCase() === "available" ? "Available" : doctor.status.toLowerCase() === "not_available" ? "Not Available" : "Left the hospital"}</p>
												</div>
											</div>
										</div>
									</div>
								)}
								{updateModal && (
									<div className="modal show d-block" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
										<div className="modal-dialog modal-dialog-centered">
											<div className="modal-content">
												<div className="modal-header">
													<h5 className="modal-title">Appointment</h5>
													<button type="button" className="btn-close" onClick={updateClose}></button>
												</div>
												<div className="modal-body">
													<form onSubmit={(e) => updateAfterCheckup(appointment, e)}>
														<div className="my-4">
															<label htmlFor="patientName" className="form-label">
																Patient Name:
															</label>
															<input required value={appointment.patient.firstName + " " + appointment.patient.lastName} type="text" className="form-control" id="patientName" disabled />
														</div>
														<div className="my-4">
															<label htmlFor="appointmentTime" className="form-label">
																Appointment Time
															</label>
															<input required value={appointment.appointmentTime} type="datetime-local" className="form-control" id="appointmentTime" disabled />
														</div>
														<div className="my-4">
															<label htmlFor="notes" className="form-label">
																Patient Comments:
															</label>
															<textarea required value={appointment.notes} rows={3} className="form-control" id="notes" disabled />
														</div>
														<div className="my-4">
															<label htmlFor="doctorComments" className="form-label">
																Doctor Comments:
															</label>
															<input required name="doctorComments" type="text" className="form-control" id="doctorComments" />
														</div>
														<div className="my-4 d-flex">
															<button className="btn btn-success" type="submit">
																Update
															</button>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>
								)}
								{/* Show Doctor button for non doctor roles to view doctor details */}
								{currentRole !== "ROLE_DOCTOR" && (
									<button className="bg-transparent border-0 p-1">
										<FontAwesomeIcon icon={faUserDoctor} className="mx-1" onClick={() => handleShow(appointment.doctor)} />
									</button>
								)}
								{currentRole === "ROLE_PATIENT" && !isPast(appointment.appointmentTime) && appointment.status !== "REJECTED" && (
									<button className="bg-transparent border-0 p-1">
										<FontAwesomeIcon icon={faTrash} className="mx-1" onClick={() => deleteAppointment(appointment)} />
									</button>
								)}
								{/* At the time of patient screening doctor leaves comments */}
								{currentRole === "ROLE_DOCTOR" && !isPast(appointment.appointmentTime) && appointment.status === "CONFIRMED" && appointment?.doctorComments?.length === 0 && (
									<button className="bg-transparent border-0 p-1">
										<FontAwesomeIcon icon={faPencil} className="mx-1" onClick={() => updateModalShow()} />
									</button>
								)}
								{/* Receiving new appointment (Confirm or reject) */}
								{currentRole === "ROLE_DOCTOR" && !isPast(appointment.appointmentTime) && appointment.status === "PENDING" && (
									<div>
										<button className="bg-transparent border-0 p-1">
											<FontAwesomeIcon icon={faCancel} className="mx-1" onClick={() => updateAppointmentStatus(appointment, "REJECTED")} />
										</button>
										<button className="bg-transparent border-0 p-1">
											<FontAwesomeIcon icon={faCheck} className="mx-1" onClick={() => updateAppointmentStatus(appointment, "CONFIRMED")} />
										</button>
									</div>
								)}
							</div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default Appointments;
