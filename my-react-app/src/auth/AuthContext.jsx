import { createContext, useContext, useState } from "react"; // Import React context and state hooks
import PropTypes from "prop-types"; // Import PropTypes for props validation
import axiosInstance from "../api/axiosInstance"; // Import the configured axios instance

const AuthContext = createContext(); // Create the authentication context

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
    <AuthContext.Provider value={{ user, login, logout }}>
      {children} {/* Render child components with auth context */}
    </AuthContext.Provider>
  ); // Provide auth state and functions to the app
} // Export provider component

AuthProvider.propTypes = {
  children: PropTypes.node.isRequired, // Validate that children is passed in
}; // Add props validation for AuthProvider

export function useAuth() {
  return useContext(AuthContext); // Return the auth context value
} // Export custom hook
