import {
	ADD_APPOINTMENT_PROD,
	ADD_DOCTOR_PROD,
	DELETE_APPOINTMENT_BY_ID_PROD,
	DELETE_DOCTOR_BY_ID_PROD,
	DELETE_PATIENT_BY_ID_PROD,
	GET_ALL_APPOINTMENTS_BY_DOCTOR_PROD,
	GET_ALL_APPOINTMENTS_BY_PATIENT_PROD,
	GET_ALL_APPOINTMENTS_PROD,
	GET_ALL_DOCTORS_PROD,
	GET_ALL_PATIENTS_PROD,
	GET_DOCTOR_BY_EMAIL_PROD,
	GET_DOCTOR_BY_ID_PROD,
	GET_PATIENT_BY_EMAIL_PROD,
	SIGNIN_PROD,
	UPDATE_APPOINTMENT_PROD,
	UPDATE_DOCTOR_PROD,
} from "../constants/RouteConstants";
import axiosInstance from "./axiosInstance";

export const signIn = async (username, password) => {
	const response = await axiosInstance.post(SIGNIN_PROD, {
		username: username,
		password: password,
	});
	return response;
};

export const addAppointment = async (appointment) => {
	const res = await axiosInstance.post(ADD_APPOINTMENT_PROD, appointment);
	return res;
};

export const addDoctor = async (doctor) => {
	const res = await axiosInstance.post(ADD_DOCTOR_PROD, doctor);
	return res;
};

export const getAllDoctors = async () => {
	const jsonData = await axiosInstance.get(GET_ALL_DOCTORS_PROD);
	return jsonData;
};

export const getAllPatients = async () => {
	const jsonData = await axiosInstance.get(GET_ALL_PATIENTS_PROD);
	return jsonData;
};

export const deleteDoctorById = async (doctorId) => {
	try {
		const dltrequest = await axiosInstance.delete(`${DELETE_DOCTOR_BY_ID_PROD}/${doctorId}`, {
			method: "DELETE",
		});
	} catch (e) {
		console.log("error");
	}
};

export async function deleteAppointmentById(appointmentId) {
	try {
		const dltrequest = await axiosInstance.delete(`${DELETE_APPOINTMENT_BY_ID_PROD}/${appointmentId}`);
		return dltrequest;
	} catch (e) {
		console.log("error");
	}
}

export async function deletePatientById(patientId) {
	try {
		const dltrequest = await axiosInstance.delete(`${DELETE_PATIENT_BY_ID_PROD}/${patientId}`);
		return dltrequest;
	} catch (e) {
		console.log("error");
	}
}

export async function getDoctorById(doctorId) {
	try {
		const doctor = await axiosInstance.get(`${GET_DOCTOR_BY_ID_PROD}/${doctorId}`);
		return doctor;
	} catch (e) {
		console.log("error");
	}
}

export async function getDoctorByEmail(email: any) {
	try {
		const res = await axiosInstance.get(`${GET_DOCTOR_BY_EMAIL_PROD}/${email}`);
		return res;
	} catch (e) {
		console.log(e);
	}
}

export async function getPatientByEmail(email: any) {
	try {
		const res = await axiosInstance.get(`${GET_PATIENT_BY_EMAIL_PROD}/${email}`);
		return res;
	} catch (e) {
		console.log(e);
	}
}

export const getAllAppointments = async () => {
	const jsonData = await axiosInstance.get(GET_ALL_APPOINTMENTS_PROD);
	return jsonData;
};

export async function getAllAppointmentsByDoctor(doctorId) {
	try {
		const doctors = await axiosInstance.get(`${GET_ALL_APPOINTMENTS_BY_DOCTOR_PROD}/${doctorId}`);
		return doctors;
	} catch (e) {
		console.log("error");
	}
}

export async function getAllAppointmentsByPatient(patientId) {
	try {
		const patients = await axiosInstance.get(`${GET_ALL_APPOINTMENTS_BY_PATIENT_PROD}/${patientId}`);
		return patients;
	} catch (e) {
		console.log("error");
	}
}

export async function updateAppointment(appointment) {
	try {
		const data = {
			id: appointment.id,
			appointmentTime: appointment.appointmentTime,
			status: appointment.status,
			notes: appointment.notes,
			doctorComments: appointment.doctorComments,
			patientId: appointment.patient.id,
			doctorId: appointment.doctor.id,
		};
		const res = await axiosInstance.put(UPDATE_APPOINTMENT_PROD, data, { method: "PUT" });
		return res;
	} catch (e) {
		console.log("error");
	}
}

export async function updateDoctor(doctor, id) {
	try {
		const res = await axiosInstance.put(`${UPDATE_DOCTOR_PROD}/${id}`, doctor, { method: "PUT" });
		return res;
	} catch (e) {
		console.log("error");
	}
}
