import { useEffect, useState } from "react"; // Import hooks for lifecycle and state
import { useNavigate } from "react-router-dom"; // Import navigate hook for redirecting
import axiosInstance from "../api/axiosInstance"; // Import configured axios instance
import { useAuth } from "../auth/AuthContext"; // Import auth hook

function Customers() {
  const [customers, setCustomers] = useState([]); // Store customer list
  const [error, setError] = useState(""); // Store error message
  const { logout } = useAuth(); // Get logout function from auth context
  const navigate = useNavigate(); // Create navigate function

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const response = await axiosInstance.get("/api/customers"); // Fetch customers from backend
        setCustomers(response.data); // Save returned customers into state
      } catch {
        setError("Failed to load customers."); // Show error if request fails
      }
    }; // Define fetch function

    fetchCustomers(); // Call fetch function when component loads
  }, []); // Empty dependency array means run once on mount

  const handleLogout = () => {
    logout(); // Clear auth state and token
    navigate("/login"); // Redirect to login page
  }; // Define logout handler

  return (
    <div>
      <h2>Customers</h2> {/* Page title */}
      <button onClick={handleLogout}>Logout</button> {/* Logout button */}

      {error && <p>{error}</p>} {/* Show error message if one exists */}

      <table border="1">
        <thead>
          <tr>
            <th>ID</th> {/* Customer ID column */}
            <th>Name</th> {/* Customer name column */}
            <th>Email</th> {/* Customer email column */}
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.id}>
              <td>{customer.id}</td> {/* Show customer ID */}
              <td>{customer.name}</td> {/* Show customer name */}
              <td>{customer.email}</td> {/* Show customer email */}
            </tr>
          ))} {/* Render one table row per customer */}
        </tbody>
      </table>
    </div>
  ); // Render customers page
}

export default Customers; // Export Customers component
