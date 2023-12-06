import React, { useState } from 'react';
import './index.css';

const RegistroPaciente = () => {
  const estadosBrasileiros = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA',
    'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN',
    'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
  ];

  const [paciente, setPaciente] = useState({
    nome: '',
    email: '',
    numero: '',
    cpf: '',
    endereco: {
      logradouro: '',
      numero: '',
      complemento: '',
      bairro: '',
      cidade: '',
      uf: '',
      cep: '',
    },
    ativo: true
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPaciente((prevPaciente) => ({
      ...prevPaciente,
      [name]: value,
    }));
  };

  const handleChangeEndereco = (event) => {
    const { name, value } = event.target;
    setPaciente((prevPaciente) => ({
      ...prevPaciente,
      endereco: {
        ...prevPaciente.endereco,
        [name]: value,
      },
    }));
  };

  const [mensagem, setMensagem] = useState('');
  const exibirMensagem = (mensagem) => {
    setMensagem(mensagem);

    // Limpar a mensagem após 5 segundos
    setTimeout(() => {
      setMensagem('');
    }, 8000);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const resposta = await fetch('http://localhost:8080/paciente', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(paciente),
      });

      const dados = await resposta.json();

      if (!resposta.ok) {
        // Se o status da resposta não for OK, trata como erro
        throw new Error(`Erro ao cadastrar paciente: ${dados.message}`);
      }
      exibirMensagem('Paciente cadastrado com sucesso!');
      console.log('Paciente cadastrado com sucesso:', dados);
    } catch (error) {
      exibirMensagem(`${error.message}`);
      console.error(error.message);
    }
  };

  return (
    <div className="container">
      <h1>Cadastro de Paciente</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Nome:
          <input
            type="text"
            name="nome"
            value={paciente.nome}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          E-mail:
          <input
            type="email"
            name="email"
            value={paciente.email}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Telefone:
          <input
            type="tel"
            name="numero"
            value={paciente.numero}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          CPF:
          <input
            type="text"
            name="cpf"
            value={paciente.cpf}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Logradouro:
          <input
            type="text"
            name="logradouro"
            value={paciente.endereco.logradouro}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          Número:
          <input
            type="text"
            name="numero"
            value={paciente.endereco.numero}
            onChange={handleChangeEndereco}
          />
        </label>

        <label>
          Complemento:
          <input
            type="text"
            name="complemento"
            value={paciente.endereco.complemento}
            onChange={handleChangeEndereco}
          />
        </label>

        <label>
          Bairro:
          <input
            type="text"
            name="bairro"
            value={paciente.endereco.bairro}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          Cidade:
          <input
            type="text"
            name="cidade"
            value={paciente.endereco.cidade}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          UF:
          <select
            name="uf"
            value={paciente.endereco.uf}
            onChange={handleChangeEndereco}
            required
          >
            <option value="">Selecione um estado</option>
            {estadosBrasileiros.map((sigla) => (
              <option key={sigla} value={sigla}>
                {sigla}
              </option>
            ))}
          </select>
        </label>

        <label>
          CEP:
          <input
            type="text"
            name="cep"
            value={paciente.endereco.cep}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <button type="submit">Cadastrar Paciente</button>
      </form>

      {mensagem && (
        <div className="mensagem-temporaria">
          <p>{mensagem}</p>
        </div>
      )}
    </div>
  );
};

export default RegistroPaciente;
