import { useState } from "react"; // Import React state hook for auth state
import PropTypes from "prop-types"; // Import PropTypes for props validation
import axiosInstance from "../api/axiosInstance"; // Import the configured axios instance
import AuthStateContext from "./AuthStateContext"; // Import auth context from a non-component module for Fast Refresh

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null); // Store the logged in user state

  const login = async (credentials) => {
    const response = await axiosInstance.post("/api/auth/login", credentials); // Send login request to backend
    const token = response.data.token; // Get token from response
    const userData = response.data.user || null; // Get user data if backend returns it

    localStorage.setItem("token", token); // Save token in localStorage
    setUser(userData); // Update user state
  }; // Define login function

  const logout = () => {
    localStorage.removeItem("token"); // Remove token from localStorage
    setUser(null); // Clear user state
  }; // Define logout function

  return (
    <AuthStateContext.Provider value={{ user, login, logout }}>
      {children} {/* Render child components with auth context */}
    </AuthStateContext.Provider>
  ); // Provide auth state and functions to the app
} // Export provider component

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired, // Validate that children is passed in
}; // Add props validation for AuthProvider
