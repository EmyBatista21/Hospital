import { NavLink } from "react-router-dom"
import "./index.css"

function Menu(){
    return(
        <div className="menu">
            <ul className="menu-bar">
                <li className="btn-menu">
                  <NavLink to="/" exact className='nav' activeClassName="active">HOME</NavLink>
                </li>
                <li className="btn-menu">
                  <NavLink to="/consulta" exact className='nav' activeClassName="active">VER CONSULTAS</NavLink>
                </li>
                <li className="btn-menu">
                  <NavLink to="/listarMedicos" exact className='nav' activeClassName="active">MÉDICOS DISPONÍVEIS</NavLink>
                </li> 
                <li className="btn-menu">
                  <NavLink to="/paciente" exact className='nav' activeClassName="active">SOBRE</NavLink>
                </li>      
            </ul>
        </div>

    )

}export default Menu