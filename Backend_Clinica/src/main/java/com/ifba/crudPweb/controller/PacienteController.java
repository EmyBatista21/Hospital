package com.ifba.crudPweb.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifba.crudPweb.dtos.PacienteRecordDto;
import com.ifba.crudPweb.models.PacienteModel;
import com.ifba.crudPweb.repository.PacienteRepository;

import jakarta.validation.Valid;
import lombok.experimental.var;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@PostMapping
	public ResponseEntity<PacienteModel> saveMedico(@RequestBody @Valid 
												PacienteRecordDto pacienteRecordDto){
		var pacienteModel = new PacienteModel();
		BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(pacienteRepository.save(pacienteModel)); 
	}
	
	@GetMapping
	public ResponseEntity<List<PacienteModel>> getAllMedicos(){
		List<PacienteModel> pacientesList = pacienteRepository.findAllByOrderByNomeAsc();
		if(!pacientesList.isEmpty())
			for (PacienteModel paciente : pacientesList) {
				Long id = paciente.getId();
				paciente.add(linkTo(methodOn(PacienteController.class)
								.getMedicoForId(id)).withSelfRel());
			}
		return ResponseEntity.status(HttpStatus.OK).body(pacientesList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMedicoForId(@PathVariable(value = "id") Long id){
		Optional<PacienteModel> pacienteOptional = pacienteRepository.findById(id);
		if(pacienteOptional.isEmpty()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
		pacienteOptional.get().add(linkTo(methodOn(PacienteController.class)
				.getAllMedicos()).withRel("Products List"));
		
		pacienteOptional.get();
		return ResponseEntity.status(HttpStatus.OK).body(pacienteOptional.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarMedicoForId(@PathVariable(value = "id") Long id,
														@RequestBody @Valid PacienteRecordDto
														pacienteRecordDto){
		Optional<PacienteModel> pacienteOptional = pacienteRepository.findById(id);
		if(pacienteOptional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
		
		var paciente = pacienteOptional.get();
		paciente.setNome(pacienteRecordDto.getNome());
		paciente.setNumero(pacienteRecordDto.getNumero());
		paciente.setEndereco(pacienteRecordDto.getEndereco());
		//BeanUtils.copyProperties(medicoRecordDto, medico);
		return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(paciente));
	} 
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMedico(@PathVariable(value = "id") Long id){
		Optional<PacienteModel> pacienteOptional = pacienteRepository.findById(id);
		if(pacienteOptional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
		var paciente = pacienteOptional.get();
		paciente.setAtivo(false); 
		pacienteRepository.save(paciente);
		return ResponseEntity.status(HttpStatus.OK).body("Paciente " + paciente.getNome() 
														+ " inativado");
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteAllMedicos(){
		List<PacienteModel> pacientesList = pacienteRepository.findAll();
		if(pacientesList.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro a ser deletado");
		pacienteRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("Todos o pacientes foram deletados");
	}
	
}
