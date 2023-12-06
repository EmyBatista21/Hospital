import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './index.css';

function ListarMedicos() {
  const [medicos, setMedicos] = useState([]);

  useEffect(() => {
    const fetchMedicos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/medico');
        setMedicos(response.data);
      } catch (error) {
        console.error('Erro ao buscar médicos:', error);
      }
    };

    fetchMedicos();
  }, []);

  return (
    <div>
      <h2>Listar Médicos</h2>
      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>CRM</th>
            <th>Especialidade</th>
          </tr>
        </thead>
        <tbody>
          {medicos.map((medico) => (
            <tr key={medico.id} className='dados'>
              <td>{medico.nome}</td>
              <td>{medico.email}</td>
              <td>{medico.crm}</td>
              <td>{medico.especialidade}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ListarMedicos;
