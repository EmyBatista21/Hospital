package com.ifba.crudPweb.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifba.crudPweb.dtos.ConsultaDTO;
import com.ifba.crudPweb.models.Consulta;
import com.ifba.crudPweb.models.MedicoModel;
import com.ifba.crudPweb.models.PacienteModel;
import com.ifba.crudPweb.repository.ConsultaRepository;
import com.ifba.crudPweb.repository.MedicoRepository;
import com.ifba.crudPweb.repository.PacienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void marcarConsulta(ConsultaDTO consultaDTO) {
        // Verificar se o ID do médico existe no banco de dados
        Optional<MedicoModel> medicoOptional = medicoRepository.findById(consultaDTO.getMedicoId());
        if (medicoOptional.isEmpty() || !medicoOptional.get().getAtivo()) {
        	if(medicoOptional.isEmpty())
        		throw new EntityNotFoundException("Médico não encontrado " + consultaDTO.getMedicoId());
            throw new EntityNotFoundException("Médico não está ativo com ID: " + consultaDTO.getMedicoId());
        }
        
        // Verificar se os ID do paciente existe no banco de dados
        Optional<PacienteModel> pacienteOptional = pacienteRepository.findById(consultaDTO.getPacienteId());
        if (pacienteOptional.isEmpty() || !pacienteOptional.get().getAtivo()) {
        	if(pacienteOptional.isEmpty())
        		throw new EntityNotFoundException("Paciente não encontrado " + consultaDTO.getPacienteId());
            throw new EntityNotFoundException("Paciente não está ativo com ID: " + consultaDTO.getPacienteId());
        }
        
        boolean consultaExistente = consultaRepository.existsByPacienteAndDataConsulta(
                pacienteOptional.get(), consultaDTO.getDataHora().toLocalDate());
        
        if (consultaExistente) {
            throw new RuntimeException("Paciente já possui uma consulta marcada para essa data.");
        }
        
        consultaExistente = consultaRepository.existsByMedicoAndDataHoraConsulta(
        						medicoOptional.get(), consultaDTO.getDataHora());
        if (consultaExistente) {
            throw new IllegalStateException("Médico já possui uma consulta agendada na mesma data/hora.");
        }
        
        // Criar a consulta
        Consulta consulta = new Consulta();
        consulta.setMedico(medicoOptional.get());
        consulta.setPaciente(pacienteOptional.get());
        consulta.setDataHora(consultaDTO.getDataHora());

        // Salvar a consulta no banco de dados
        consultaRepository.save(consulta);
    }

	

	

    
    
}
