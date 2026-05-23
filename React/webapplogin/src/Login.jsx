import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {

  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const response = await fetch("/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        login: username,
        password: password,
      }),
    });

    if (!response.ok) {
      setMessage("Errore login ❌");
      return;
    }

    const data = await response.json();
    console.log("RISPOSTA BACKEND:", data);

    // 🔥 FIX: estrazione corretta del token
    const token = Object.values(data)[0];

    console.log("TOKEN:", token);

    if (!token) {
      setMessage("Token non trovato ❌");
      return;
    }

    localStorage.setItem("token", token);

    // decodifica JWT // questa è la parte per decodificare i jwt ed estrarre il valore ruolo 
    const payload = JSON.parse(atob(token.split(".")[1]));
    console.log("PAYLOAD:", payload);

    const isAdmin =
      payload.role?.includes("ADMIN") ||
      payload.role?.includes("ROLE_ADMIN");

    if (isAdmin) {
      localStorage.setItem("role", "ADMIN");
      navigate("/admin");
    } else {
      localStorage.setItem("role", "USER");
      navigate("/utente-loggato");
    }

    setMessage("Login effettuato ✔");

  } catch (error) {
    console.error(error);
    setMessage("Errore di connessione ❌");
  }
};

  return (
    <div>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <input
          placeholder="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="submit">Accedi</button>
      </form>

      <p>{message}</p>
    </div>
  );
}
export default Login;