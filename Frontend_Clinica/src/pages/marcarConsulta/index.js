import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format } from 'date-fns';



const MarcarConsulta = () => {
  const [pacientes, setPacientes] = useState([]);
  const [medicos, setMedicos] = useState([]);
  const [selectedPaciente, setSelectedPaciente] = useState('');
  const [selectedMedico, setSelectedMedico] = useState('');
  const [dataHora, setDataHora] = useState('');
  const [mensagem, setMensagem] = useState('');

  useEffect(() => {
    const fetchPacientes = async () => {
      try {
        const response = await axios.get('http://localhost:8080/paciente');
        setPacientes(response.data);
      } catch (error) {
        console.error('Erro ao buscar pacientes:', error);
      }
    };

    const fetchMedicos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/medico');
        setMedicos(response.data);
      } catch (error) {
        console.error('Erro ao buscar médicos:', error);
      }
    };

    fetchPacientes();
    fetchMedicos();
  }, []);

  const handlePacienteChange = (event) => {
    setSelectedPaciente(event.target.value);
  };

  const handleMedicoChange = (event) => {
    setSelectedMedico(event.target.value);
  };

  const handleDataHoraChange = (event) => {
    const selectedDate = new Date(event.target.value);
    const formattedDate = format(selectedDate, "yyyy-MM-dd'T'HH:mm:ss");
    setDataHora(formattedDate);
  };

  const exibirMensagem = (mensagem) => {
    setMensagem(mensagem);

    // Limpar a mensagem após 5 segundos
    setTimeout(() => {
      setMensagem('');
    }, 8000);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const resposta = await fetch('http://localhost:8080/consultas', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        pacienteId: selectedPaciente,
        medicoId: selectedMedico,
        dataHora,
      }),
    });

    exibirMensagem('Consulta Marcada com Sucesso');
    if (resposta.status >= 200 && resposta.status < 300) {
      exibirMensagem('Consulta Marcada com Sucesso');
    } else {
      const dadosErro = await resposta.json();
      console.error(dadosErro);
      exibirMensagem(`${dadosErro.message}`);
    }
  };


  return (
    <div className="container">
      <h1>Marcar Consulta</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Paciente:
          <select
            name="paciente"
            value={selectedPaciente}
            onChange={handlePacienteChange}
            required
          >
            <option value="">Selecione um paciente</option>
            {pacientes.map((paciente) => (
              <option key={paciente.id} value={paciente.id}>
                {paciente.nome}
              </option>
            ))}
          </select>
        </label>

        <label>
          Médico:
          <select
            name="medico"
            value={selectedMedico}
            onChange={handleMedicoChange}
            required
          >
            <option value="">Selecione um médico</option>
            {medicos.map((medico) => (
              <option key={medico.id} value={medico.id}>
                {medico.nome} - {medico.especialidade}
              </option>
            ))}
          </select>
        </label>

        <label>
          Data e Hora da Consulta:
          <input
            type="datetime-local"
            name="dataHora"
            value={dataHora}
            onChange={handleDataHoraChange}
            required
          />
        </label>
        <button type="submit">Marcar Consulta</button>
      </form>

      {mensagem && (
        <div className="mensagem-temporaria">
          <p>{mensagem}</p>
        </div>
      )}
    </div>
  );
};

export default MarcarConsulta;
