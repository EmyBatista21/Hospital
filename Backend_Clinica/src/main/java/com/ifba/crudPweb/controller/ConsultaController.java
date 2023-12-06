package com.ifba.crudPweb.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.crudPweb.Service.ConsultaService;
import com.ifba.crudPweb.dtos.CancelamentoConsultaDTO;
import com.ifba.crudPweb.dtos.ConsultaDTO;
import com.ifba.crudPweb.models.CancelamentoConsulta;
import com.ifba.crudPweb.models.Consulta;
import com.ifba.crudPweb.models.MedicoModel;
import com.ifba.crudPweb.models.MotivoCancelamento;
import com.ifba.crudPweb.repository.ConsultaRepository;

import lombok.experimental.var;



@RestController
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @PostMapping
    public ResponseEntity<String> marcarConsulta(@RequestBody ConsultaDTO consultaDTO) {
    	consultaService.marcarConsulta(consultaDTO);
        return ResponseEntity.ok("Consulta marcada com sucesso!");
    }
    
    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
    	List<Consulta> consultasList = consultaRepository.findAll();	
    	return ResponseEntity.status(HttpStatus.OK).body(consultasList);
    }
    
    @GetMapping("/ativas")
    public ResponseEntity<List<Consulta>> listarConsultasAtivas() {
    	List<Consulta> consultasList = consultaRepository.findByCanceladoFalse();	
    	return ResponseEntity.status(HttpStatus.OK).body(consultasList);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<Object> listarConsultaPorId(@PathVariable(value = "id") Long id){
		Optional<Consulta> consultaOptional = consultaRepository.findById(id);
		if(consultaOptional.isEmpty()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
		consultaOptional.get().add(linkTo(methodOn(MedicoController.class)
				.getAllMedicos()).withRel("Products List"));
		
		consultaOptional.get();
		return ResponseEntity.status(HttpStatus.OK).body(consultaOptional.get());
	}
    
    @DeleteMapping
	public ResponseEntity<Object> deletarConsultas(){
		List<Consulta> consultasList = consultaRepository.findAll();
		if(consultasList.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro a ser deletado");
		consultaRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("Todas as consultas foram deletas");
	}
    
    @PostMapping("/{consultaId}/cancelar")
    public ResponseEntity<String> cancelarConsulta(@PathVariable Long consultaId, @RequestBody CancelamentoConsultaDTO cancelamentoConsultaDTO) {
        Consulta consulta = consultaRepository.findById(consultaId).orElse(null);

        if (consulta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada");
        }

        MotivoCancelamento motivoCancelamento = cancelamentoConsultaDTO.getMotivoCancelamento();
        if (motivoCancelamento == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Motivo do cancelamento é obrigatório");
        }

//        if (motivoCancelamento != MotivoCancelamento.PACIENTE_DESISTIU &&
//            motivoCancelamento != MotivoCancelamento.MEDICO_CANCELOU &&
//            motivoCancelamento != MotivoCancelamento.OUTROS) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Motivo do cancelamento inválido");
//        }

        consulta.setCancelado(true);
        consultaRepository.save(consulta);

        return ResponseEntity.ok("Consulta cancelada com sucesso");
    }

}

