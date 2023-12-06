import React, { useState } from 'react';
import './index.css'


const CadastroMedico = () => {

  const estadosBrasileiros = [
    'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA',
    'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN',
    'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
  ];

  const [medico, setMedico] = useState({
    nome: '',
    email: '',
    numero: '',
    crm: '',
    especialidade: '',
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
    setMedico((prevMedico) => ({
      ...prevMedico,
      [name]: value,
    }));
  };

  const handleChangeEndereco = (event) => {
    const { name, value } = event.target;
    setMedico((prevMedico) => ({
      ...prevMedico,
      endereco: {
        ...prevMedico.endereco,
        [name]: value,
      },
    }));
  };

  // No input do formulário
  <input
    type="text"
    name="logradouro"
    value={medico.endereco.logradouro}
    onChange={handleChangeEndereco}
    required
  />
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
      const resposta = await fetch('http://localhost:8080/medico', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(medico),
      });

      const dados = await resposta.json();

      if (!resposta.ok) {
        // Se o status da resposta não for OK, trata como erro
        throw new Error(`Erro ao cadastrar médico: ${dados.message}`);
      }

      exibirMensagem('Médico cadastrado com sucesso');
      console.log('Médico cadastrado com sucesso:', dados);
    } catch (error) {
      exibirMensagem(`${error.message}`);
      console.error(error.message);
    }
  };

  return (
    <div className="container">
      <h1>Cadastro de Médico</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Nome:
          <input
            type="text"
            name="nome"
            value={medico.nome}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          E-mail:
          <input
            type="email"
            name="email"
            value={medico.email}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Telefone:
          <input
            type="tel"
            name="numero"
            value={medico.numero}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          CRM:
          <input
            type="text"
            name="crm"
            value={medico.crm}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Especialidade:
          <select
            name="especialidade"
            value={medico.especialidade}
            onChange={handleChange}
            required
          >
            <option value="">Selecione uma especialidade</option>
            <option value="Ortopedia">Ortopedia</option>
            <option value="Cardiologia">Cardiologia</option>
            <option value="Ginecologia">Ginecologia</option>
            <option value="Dermatologia">Dermatologia</option>
          </select>
        </label>


        <label>
          Logradouro
          <input
            type="text"
            name="logradouro"
            value={medico.endereco.logradouro}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          Complemento:
          <input
            type="text"
            name="complemento"
            value={medico.endereco.complemento}
            onChange={handleChangeEndereco}
          />
        </label>

        <label>
          Bairro:
          <input
            type="text"
            name="bairro"
            value={medico.endereco.bairro}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          Cidade:
          <input
            type="text"
            name="cidade"
            value={medico.endereco.cidade}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          UF:
          <select
            name="uf"
            value={medico.endereco.uf}
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
            value={medico.endereco.cep}
            onChange={handleChangeEndereco}
            required
          />
        </label>

        <label>
          Número:
          <input
            type="text"
            name="numero"
            value={medico.endereco.numero}
            onChange={handleChangeEndereco}
          />
        </label>

        <button type="submit">Cadastrar Médico</button>
      </form>

      {mensagem && (
        <div className="mensagem-temporaria">
          <p>{mensagem}</p>
        </div>
      )}
    </div>
  );
};

export default CadastroMedico;
