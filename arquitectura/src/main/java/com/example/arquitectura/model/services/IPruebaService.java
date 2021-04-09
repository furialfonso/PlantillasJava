package com.example.arquitectura.model.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.example.arquitectura.model.dto.ClienteDTO;

public interface IPruebaService {
	public List<ClienteDTO> getClientes();

	public ClienteDTO getCliente(String primerNombre);

	public ClienteDTO getClientePorApellido(String primerApellido);

	public ResponseEntity<?> create(ClienteDTO clienteDTO);

	public ResponseEntity<?> update(Long id, @Valid ClienteDTO clienteDTO);

	public ResponseEntity<?> delete(Long id);
}
