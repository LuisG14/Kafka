import { NavLink } from "react-router-dom";
import "./Navbar.css"; // Importa el CSS específico para Navbar

function Navbar() {
    return (
    <nav className="navbar">
        <div className="nav-container">
        <NavLink to="/" className="nav-link">Home</NavLink>
        <NavLink to="/clientes" className="nav-link">Clientes</NavLink>
        <NavLink to="/menus" className="nav-link">Menus</NavLink>
        </div>
        <div>
        
        </div>
    </nav>
    );
}

export default Navbar;