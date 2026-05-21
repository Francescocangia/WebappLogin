import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message , setmessage] = useState("");
  const handleLogin = async (e) => {
    e.preventDefault();
    console.log("Login:", { username, password });
    alert(`Login con: ${username}`);
  

    //nota il codice fetch dovrebbe essere questo http://localhost:9090/auth/login --> in realtà l'ho modificato perchè sto usando docker quindi servivano delle moifiche che chiedendo a chatgpt mi ha portato a scrivere quello che ho scritto dentro fetch
// per docker usare questo path /auth/login"

  const response = await fetch("http://localhost:9090/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      login: username,
      password: password,
    }),
  });

  const data = await response.json();
  console.log(data);


if (response.ok) {
  setmessage("Login effettuato ✔");
} else {
  setmessage("Password errata ❌");
}




};


  return (
    <>
    <div className="container">
<form onSubmit={handleLogin}>
        <h2>login</h2>

        <input
          type="text"
          placeholder="Username o Email"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="submit">Accedi</button>
      </form>

    <p>{message}</p>
    </div>
    
  );

    </>
  )
};


export default App
