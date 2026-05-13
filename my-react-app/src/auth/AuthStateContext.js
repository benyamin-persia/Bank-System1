import { createContext } from "react"; // Import createContext to define shared auth state

const AuthStateContext = createContext(); // Create the authentication context shared by provider and hook

export default AuthStateContext; // Export the context from a uniquely named non-component module
