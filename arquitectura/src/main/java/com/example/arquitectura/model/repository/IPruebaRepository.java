package com.example.arquitectura.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.arquitectura.model.entity.Cliente;

public interface IPruebaRepository extends JpaRepository<Cliente, Long> {

	public Cliente findByPrimerNombre(String primerNombre);

	@Query("SELECT o FROM Cliente o WHERE o.primerApellido = ?1")
	public Cliente findByApellido(String primerApellido);
}
