// ConsultaMarcadaModal.js
import React from 'react';
import './index.css'; 
const ConsultaMarcadaModal = ({ onClose }) => {
  return (
    <div className="modal">
      <div className="modal-content">
        <h2>Consulta Marcada</h2>
        <p>Consulta marcada com sucesso!</p>
        <button onClick={onClose}>OK</button>
      </div>
    </div>
  );
};

export default ConsultaMarcadaModal;
