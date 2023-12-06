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

import com.ifba.crudPweb.dtos.MedicoRecordDto;
import com.ifba.crudPweb.models.MedicoModel;
import com.ifba.crudPweb.repository.MedicoRepository;

import jakarta.validation.Valid;
import lombok.experimental.var;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;;

@RestController
@RequestMapping("/medico")
public class MedicoController {
	
	@Autowired
	MedicoRepository medicoRepository;
	
	@PostMapping
	public ResponseEntity<MedicoModel> saveMedico(@RequestBody @Valid 
												MedicoRecordDto medicoRecordDto){
		var medicoModel = new MedicoModel();
		BeanUtils.copyProperties(medicoRecordDto, medicoModel);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(medicoRepository.save(medicoModel)); 
	}
	
	@GetMapping
	public ResponseEntity<List<MedicoModel>> getAllMedicos(){
		List<MedicoModel> medicosList = medicoRepository.findAllByOrderByNomeAsc();
		if(!medicosList.isEmpty())
			for (MedicoModel medico : medicosList) {
				Long id = medico.getId();
				medico.add(linkTo(methodOn(MedicoController.class)
								.getMedicoForId(id)).withSelfRel());
			}
		return ResponseEntity.status(HttpStatus.OK).body(medicosList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getMedicoForId(@PathVariable(value = "id") Long id){
		Optional<MedicoModel> medicoOptional = medicoRepository.findById(id);
		if(medicoOptional.isEmpty()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
		medicoOptional.get().add(linkTo(methodOn(MedicoController.class)
				.getAllMedicos()).withRel("Products List"));
		
		medicoOptional.get();
		return ResponseEntity.status(HttpStatus.OK).body(medicoOptional.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarMedicoForId(@PathVariable(value = "id") Long id,
														@RequestBody @Valid MedicoRecordDto
																			medicoRecordDto){
		Optional<MedicoModel> medicoOptional = medicoRepository.findById(id);
		if(medicoOptional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
		
		var medico = medicoOptional.get();
		medico.setNome(medicoRecordDto.getNome());
		medico.setNumero(medicoRecordDto.getNumero());
		medico.setEndereco(medicoRecordDto.getEndereco());
		//BeanUtils.copyProperties(medicoRecordDto, medico);
		return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.save(medico));
	} 
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMedico(@PathVariable(value = "id") Long id){
		Optional<MedicoModel> medicoOptional = medicoRepository.findById(id);
		if(medicoOptional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
		var medico = medicoOptional.get();
		medico.setAtivo(false); 
		medicoRepository.save(medico);
		return ResponseEntity.status(HttpStatus.OK).body("Médico " + medico.getNome() 
														+ " inativado");
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteAllMedicos(){
		List<MedicoModel> medicosList = medicoRepository.findAll();
		if(medicosList.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum registro a ser deletado");
		medicoRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("Todos o médicos foram deletados");
	}
	
}
