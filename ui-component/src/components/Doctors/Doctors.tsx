import React, { useContext, useEffect, useState } from "react";
import "./Doctors.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendar,
  faClock,
  faList,
  faTrash,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import Swal from "sweetalert2";
import {
  deleteDoctorById,
  getAllAppointmentsByDoctor,
  getAllDoctors,
} from "../../services/service";
import { formatDate, isPast } from "../../services/util.service";
import { spinnerContext } from "../Spinner/spinnerContext";

// Doctors Component: Displays a list of doctors, allows viewing their appointments, and deleting doctors.
const Doctors = () => {
  // State to store the list of doctors
  const [doctors, setDoctors] = useState([]);
  // State to store the appointments of a selected doctor
  const [appointments, setAppointments] = useState([]);
  // State to control the visibility of the popup
  const [showModal, setShowModal] = useState(false);
  // Access the spinner context to show/hide loading indicators
  const { setShowSpinner } = useContext(spinnerContext);

  // useEffect to fetch the list of doctors when the component loads
  useEffect(() => {
    setShowSpinner(true);
    getAllDoctors()
      .then((res: any) => {
        setShowSpinner(false);
        // Set the doctors list or an empty array if no data is returned
        const aps = res?.data?.data === undefined ? [] : res?.data?.data;
        setDoctors(aps);
      })
      .catch((err) => {
        setShowSpinner(false);
        console.log(err);
      });
  }, []);

  // Fetch appointments for a specific doctor
  const handleShow = (doctorId) => {
    setShowSpinner(true);
    getAllAppointmentsByDoctor(doctorId)
      .then((res: any) => {
        setShowSpinner(false);
        const aps = res?.data === undefined ? [] : res?.data;
        setAppointments(aps);
      })
      .catch((err) => {
        setShowSpinner(false);
        console.log(err);
      });
    setShowModal(true);
  };

  // Close the appointments popup
  const handleClose = () => setShowModal(false);

  // Delete a doctor and refresh the doctors list
  const handleDelete = (id) => {
    Swal.fire({
      title: "Confirm",
      text: "Do you want to delete",
      icon: "question",
      confirmButtonText: "Go Ahead",
      showCancelButton: true,
    }).then((value) => {
      if (value.isConfirmed) {
        setShowSpinner(true);
        deleteDoctorById(id)
          .then(() => {
            getAllDoctors()
              .then((res: any) => {
                setShowSpinner(false);
                const aps =
                  res?.data?.data === undefined ? [] : res?.data?.data;
                setDoctors(aps);
              })
              .catch((err) => {
                setShowSpinner(false);
                console.log(err);
              });
          })
          .catch((err) => {
            setShowSpinner(false);
            console.log(err);
          });
      }
    });
  };

  return (
    <div>
      {/* List of doctors */}
      {doctors.map((doctor: any) => (
        <div
          className="card my-2 w-100"
          style={{
            border:
              doctor.status.toLowerCase() == "available"
                ? "2px solid green"
                : doctor.status.toLowerCase() == "not_available"
                ? "2px solid yellow"
                : "2px solid red",
          }}
        >
          <div className="card-body">
            <div className="d-flex justify-content-between">
              <div>
                <h5 className="card-title">
                  <FontAwesomeIcon icon={faUser} className="mx-1" />
                  {doctor.firstName.trim() + " " + doctor.lastName.trim()}
                </h5>

                {doctor.specialty ? (
                  <h6 className="card-subtitle mb-2 text-muted">
                    {doctor.specialty.trim()}
                  </h6>
                ) : (
                  ""
                )}
                <div className="d-flex justify-content-between align-items-center">
                  <p className="card-text m-0">
                    Email: {doctor.email} <br />
                    Phone: {doctor.phone}
                  </p>
                </div>
              </div>
              <div className="d-flex flex-column align-items-center justify-content-between">
                <button
                  className="bg-transparent border-0"
                  onClick={() => handleShow(doctor.id)}
                >
                  <FontAwesomeIcon icon={faList} />
                </button>
                {doctor.status.toLowerCase() !== "disabled" && (
                  <button
                    className="bg-transparent border-0"
                    onClick={() => handleDelete(doctor.id)}
                  >
                    <FontAwesomeIcon icon={faTrash} />
                  </button>
                )}
              </div>
            </div>
          </div>
          {/* Popup for appointments of a doctor */}
          {showModal && (
            <div
              className="modal show d-block"
              style={{ backgroundColor: "rgba(0,0,0,0.35)" }}
            >
              <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title">Appointment</h5>
                    <button
                      type="button"
                      className="btn-close"
                      onClick={handleClose}
                    ></button>
                  </div>
                  <div className="modal-body">
                    {appointments.map((appointment: any) => (
                      <div
                        className="card my-2 w-100"
                        style={{
                          border: isPast(appointment.appointmentTime)
                            ? "2px solid blue"
                            : appointment.status.toLowerCase() === "pending"
                            ? "2px solid yellow"
                            : appointment.status.toLowerCase() === "confirmed"
                            ? "2px solid green"
                            : "2px solid red",
                        }}
                      >
                        <div className="card-body">
                          <div className="d-flex justify-content-between">
                            <div>
                              <h5 className="card-title">
                                <FontAwesomeIcon
                                  icon={faUser}
                                  className="mx-1"
                                />
                                {appointment.patient.firstName.trim() +
                                  " " +
                                  appointment.patient.lastName.trim()}
                              </h5>
                              <div className="d-flex justify-content-between align-items-center">
                                <p className="card-text m-0">
                                  <FontAwesomeIcon
                                    icon={faCalendar}
                                    className="mx-1"
                                  />{" "}
                                  {formatDate(
                                    appointment.appointmentTime.split("T")[0]
                                  )}{" "}
                                  <br />
                                  <FontAwesomeIcon
                                    icon={faClock}
                                    className="mx-1"
                                  />{" "}
                                  {appointment.appointmentTime
                                    .split("T")[1]
                                    .split(":")
                                    .slice(0, 2)
                                    .join(":")}
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default Doctors;
