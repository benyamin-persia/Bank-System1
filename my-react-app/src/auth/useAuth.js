import { useContext } from "react"; // Import useContext to read the nearest auth provider value
import AuthStateContext from "./AuthStateContext"; // Import the shared auth context instance

export function useAuth() {
  return useContext(AuthStateContext); // Return the auth state and actions from AuthProvider
} // Export custom hook for components that need authentication data
