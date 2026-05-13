import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom"; // Import routing components from react router
import { AuthProvider } from "./auth/AuthContext"; // Import AuthProvider for app wide auth state
import Login from "./pages/Login"; // Import Login page component
import Customers from "./pages/Customers"; // Import Customers page component

function App() {
  return (
    <AuthProvider> {/* Wrap app with auth context provider */}
      <BrowserRouter> {/* Enable routing in the app */}
        <Routes> {/* Define all app routes */}
          <Route path="/login" element={<Login />} /> {/* Render Login at /login */}
          <Route path="/customers" element={<Customers />} /> {/* Render Customers at /customers */}
          <Route path="*" element={<Navigate to="/login" replace />} /> {/* Redirect any unknown route to /login */}
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  ); // Render app with auth and routing
} // Define App component

export default App; // Export App component
