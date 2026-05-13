import { useState } from "react"; // Import useState for controlled form inputs
import { useNavigate } from "react-router-dom"; // Import useNavigate for page navigation
import { useAuth } from "../auth/useAuth"; // Import custom auth hook from non-component module

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
    <main className="auth-shell">
      <section className="auth-card">
        <div className="brand-mark">BS</div> {/* Compact bank initials for visual identity */}
        <p className="eyebrow">Secure banking portal</p> {/* Small label that explains the screen context */}
        <h1>Welcome back</h1> {/* Main page heading */}
        <p className="muted-text">Sign in to view customers and manage your banking workspace.</p> {/* Short supporting copy */}

        <div className="form-stack">
          <label>
            <span>Username</span> {/* Visible label improves accessibility and clarity */}
            <input
              type="text" // Username input type
              placeholder="Enter your username" // Placeholder text
              value={username} // Controlled value for username
              onChange={(e) => setUsername(e.target.value)} // Update username state on typing
            />
          </label>

          <label>
            <span>Password</span> {/* Visible label improves accessibility and clarity */}
            <input
              type="password" // Password input type
              placeholder="Enter your password" // Placeholder text
              value={password} // Controlled value for password
              onChange={(e) => setPassword(e.target.value)} // Update password state on typing
            />
          </label>

          <button className="primary-button" onClick={handleLogin}>Login</button> {/* Button to trigger login */}
          {error && <p className="error-message">{error}</p>} {/* Show error message only if it exists */}
        </div>
      </section>
    </main>
  ); // Render login form
}

export default Login; // Export Login component
