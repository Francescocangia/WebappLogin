import { useNavigate } from "react-router-dom";

function UtenteLoggato() {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div>
      <h1>Benvenuto Utente</h1>
     

      <button onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}

export default UtenteLoggato;