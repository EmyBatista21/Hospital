import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Erro from '../pages/erro';
import Menu from '../componentes/Menu';
import VerConsultas from '../pages/verConsultas';
import Home from '../pages/home';
import CadastroMedico from '../pages/cadastroMedico';
import ListarMedicos from '../pages/listarMedicos'
import MarcarConsulta from '../pages/marcarConsulta';
import RegistroPaciente from '../pages/registroPaciente';
import './index.css'
function Rotas() {
    return (
        <BrowserRouter>
            <Menu/>
            <Routes>
                <Route exact path="/" element={<Home />} />
                <Route exact path='/consulta' element={<VerConsultas/>}/>
                <Route exact path='/listarMedicos' element={<ListarMedicos/>}/>
                <Route exact path='/marcarConsulta' element={<MarcarConsulta/>}/>
                <Route exact path='/cadastroMedico' element={<CadastroMedico/>}/>
                <Route exact path='/registroPaciente' element={<RegistroPaciente/>}/>
                <Route path="*" element={<Erro />} />
            </Routes>
        </BrowserRouter>
    );
}

export default Rotas;
 