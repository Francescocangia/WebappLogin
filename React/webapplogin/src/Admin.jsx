import { useNavigate } from "react-router-dom";

function Admin() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
        fontFamily: "Arial",
        backgroundColor: "#f0f0f0",
      }}
    >
      <h1>Benvenuto Admin</h1>
      <button
        onClick={handleLogout}
        style={{
          marginTop: "20px",
          padding: "10px 20px",
          border: "none",
          borderRadius: "6px",
          backgroundColor: "#d32f2f",
          color: "white",
          cursor: "pointer",
        }}
      >
        Logout
      </button>
    </div>
  );
}

export default Admin;