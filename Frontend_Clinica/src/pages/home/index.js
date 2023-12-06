import React from 'react'
import { Link } from 'react-router-dom';
import './index.css';

const Home = () => {
  return (
    <div className='container' >
      <div className='link-container'>
        <Link to="/marcarConsulta">Marcar Consulta</Link>
      </div>

      <div className='link-container'>
        <Link to="/cadastroMedico">Cadastrar Medico</Link>
      </div>

      <div className='link-container'>
        <Link to="/registroPaciente">Cadastrar Paciente</Link>
      </div>

    </div>
  )
}

export default Home