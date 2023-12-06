import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import './index.css';
Modal.setAppElement('#root');


// Função para formatar a data
const formatarData = (dataString) => {
  const [ano, mes, dia, horas, minutos] = dataString;
  const anoFormatado = ano.toString();
  const mesFormatado = mes.toString().padStart(2, '0');
  const diaFormatado = dia.toString().padStart(2, '0');
  const horasFormatadas = horas.toString().padStart(2, '0');
  const minutosFormatados = minutos.toString().padStart(2, '0');

  return `${anoFormatado}/${mesFormatado}/${diaFormatado} - ${horasFormatadas}:${minutosFormatados}`;
};


function VerConsultas() {
  const [consultas, setConsultas] = useState([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [localSelectedReason, setLocalSelectedReason] = useState(''); // Novo estado local
  const [cancelReasons] = useState(['SELECIONE O MOTIVO','PACIENTE_DESISTIU', 'MEDICO_CANCELOU', 'OUTROS']);
  const [consultaIdToCancel, setConsultaIdToCancel] = useState(null);

  useEffect(() => {
    const fetchConsultas = async () => {
      try {
        const response = await axios.get('http://localhost:8080/consultas/ativas');
        setConsultas(response.data);
      } catch (error) {
        console.error('Erro ao buscar consultas:', error);
      }
    };

    fetchConsultas();
  }, []);

  const openModal = (consultaId, motivoCancelamento) => {
    setConsultaIdToCancel(consultaId);
    setLocalSelectedReason(motivoCancelamento || ''); // Definir o motivo atual ou vazio
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setConsultaIdToCancel(null);
    setModalIsOpen(false);
  };

  const handleReasonChange = (event) => {
    setLocalSelectedReason(event.target.value);
  };

  const cancelarConsulta = async () => {
    try {
      console.log(consultaIdToCancel);
      console.log(localSelectedReason); // Usar o estado local
      if (consultaIdToCancel && localSelectedReason) {
        // Atualiza a lista de consultas antes de chamar a função de cancelamento
        const response = await axios.get('http://localhost:8080/consultas/ativas');
        setConsultas(response.data);

        // Em seguida, chama a função de cancelamento com o motivo
        await axios.post(`http://localhost:8080/consultas/${consultaIdToCancel}/cancelar`, {
          motivoCancelamento: localSelectedReason
        });

        // Fecha o modal após o cancelamento
        closeModal();
      } else {
        console.error('ID da consulta ou motivo do cancelamento não estão definidos.');
      }
    } catch (error) {
      console.error('Erro ao cancelar a consulta:', error);
    }
  };

  return (
    <div>
      <h1>Consultas Marcadas</h1>
      <table>
        <thead>
          <tr>
            <th>Paciente</th>
            <th>CPF</th>
            <th>Médico</th>
            <th>Data da Consulta</th>
            <th>Desmarcar</th>
          </tr>
        </thead>
        <tbody>
          {consultas.map((consulta) => (
            <tr key={consulta.id} className='dados'>
              <td>{consulta.paciente.nome}</td>
              <td>{consulta.paciente.cpf}</td>
              <td>{consulta.medico.nome}</td>
              <td>{formatarData(consulta.dataHora)}</td>
              <td>
                <button onClick={() => openModal(consulta.id, consulta.motivoCancelamento)}>
                  Cancelar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="Cancelar Consulta"
      >
        <h2>Selecione o motivo do cancelamento:</h2>
        <select value={localSelectedReason} onChange={handleReasonChange}>
          {cancelReasons.map((reason, index) => (
            <option key={index} value={reason}>
              {reason}
            </option>
          ))}
        </select>
        <button onClick={cancelarConsulta}>
          Confirmar Cancelamento
        </button>
      </Modal>
    </div>
  );
}

export default VerConsultas;
