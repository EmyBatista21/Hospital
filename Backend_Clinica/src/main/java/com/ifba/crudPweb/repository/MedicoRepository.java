package com.ifba.crudPweb.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifba.crudPweb.models.MedicoModel;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, Long>{
	Page<MedicoModel> findAll(Pageable pageable);
	List<MedicoModel> findAllByOrderByNomeAsc();
}
