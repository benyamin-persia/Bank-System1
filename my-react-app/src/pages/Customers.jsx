import { useEffect, useState } from "react"; // Import hooks for lifecycle and state
import { useNavigate } from "react-router-dom"; // Import navigate hook for redirecting
import axiosInstance from "../api/axiosInstance"; // Import configured axios instance
import { useAuth } from "../auth/useAuth"; // Import auth hook from non-component module

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
    <main className="dashboard-shell">
      <section className="dashboard-panel">
        <header className="dashboard-header">
          <div>
            <p className="eyebrow">Customer directory</p> {/* Small label that explains this dashboard section */}
            <h1>Customers</h1> {/* Page title */}
            <p className="muted-text">Review the customers currently available from the banking API.</p> {/* Short supporting copy */}
          </div>
          <button className="secondary-button" onClick={handleLogout}>Logout</button> {/* Logout button */}
        </header>

        {error && <p className="error-message">{error}</p>} {/* Show error message if one exists */}

        <div className="table-card">
          <table>
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
                  <td>{customer.name || `${customer.firstName ?? ""} ${customer.lastName ?? ""}`.trim() || "Unnamed customer"}</td> {/* Show customer name with API shape fallback */}
                  <td>{customer.email}</td> {/* Show customer email */}
                </tr>
              ))} {/* Render one table row per customer */}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  ); // Render customers page
}

export default Customers; // Export Customers component
