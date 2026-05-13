import axios from "axios"; // Import axios for HTTP requests

const axiosInstance = axios.create({
  baseURL: "http://localhost:8081", // Set your Spring Boot backend base URL
}); // Create a reusable axios instance

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token"); // Read JWT token from localStorage

    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // Attach token as Bearer token if it exists
    }

    return config; // Return updated request config
  },
  (error) => {
    return Promise.reject(error); // Forward request error
  }
);

axiosInstance.interceptors.response.use(
  (response) => {
    return response; // Return successful response as is
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      window.location.href = "/login"; // Redirect to login page on 401 Unauthorized
    }

    return Promise.reject(error); // Forward response error
  }
);

export default axiosInstance; // Export the configured axios instance