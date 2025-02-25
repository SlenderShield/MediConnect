import axios from "axios";
import { navigateTo } from "../services/navigateUtil";

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("jwt");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const status = error.response?.status;
    if (status === 403) {
      navigateTo("/unauthorized");
    } else if (status === 401) {
      navigateTo("/login");
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
