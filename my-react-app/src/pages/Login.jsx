import { useState } from "react"; // Import useState for controlled form inputs
import { useNavigate } from "react-router-dom"; // Import useNavigate for page navigation
import { useAuth } from "../auth/AuthContext"; // Import custom auth hook

function Login() {
  const [username, setUsername] = useState(""); // Store username input value
  const [password, setPassword] = useState(""); // Store password input value
  const [error, setError] = useState(""); // Store login error message
  const { login } = useAuth(); // Get login function from auth context
  const navigate = useNavigate(); // Create navigate function

  const handleLogin = async () => {
    try {
      setError(""); // Clear old error before trying login
      await login({ username, password }); // Call login function with form data
      navigate("/customers"); // Go to customers page if login succeeds
    } catch {
      setError("Login failed. Please check your username and password."); // Show error if login fails
    }
  }; // Define login click handler

  return (
    <div>
      <h2>Login</h2> {/* Page title */}
      <input
        type="text" // Username input type
        placeholder="Username" // Placeholder text
        value={username} // Controlled value for username
        onChange={(e) => setUsername(e.target.value)} // Update username state on typing
      />
      <br /> {/* Line break for spacing */}
      <input
        type="password" // Password input type
        placeholder="Password" // Placeholder text
        value={password} // Controlled value for password
        onChange={(e) => setPassword(e.target.value)} // Update password state on typing
      />
      <br /> {/* Line break for spacing */}
      <button onClick={handleLogin}>Login</button> {/* Button to trigger login */}
      {error && <p>{error}</p>} {/* Show error message only if it exists */}
    </div>
  ); // Render login form
}

export default Login; // Export Login component
