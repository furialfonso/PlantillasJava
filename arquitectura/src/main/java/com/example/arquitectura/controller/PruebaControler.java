package com.example.arquitectura.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.arquitectura.model.dto.ClienteDTO;
import com.example.arquitectura.model.services.IPruebaService;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class PruebaControler {

	@Autowired
	private IPruebaService pruebaService;

	@GetMapping("/ping")
	public String readResource() {
		return "pong";
	}

	@GetMapping("/clientes")
	public List<ClienteDTO> getClientes() {
		return pruebaService.getClientes();
	}

	@GetMapping("/nombre/{primerNombre}")
	public ClienteDTO getCliente(@PathVariable String primerNombre) {
		return pruebaService.getCliente(primerNombre);
	}

	@GetMapping("/apellido/{primerApellido}")
	public ClienteDTO getClientePorApellido(@PathVariable String primerApellido) {
		return pruebaService.getClientePorApellido(primerApellido);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
		if (bindingResult.getFieldErrors().size() > 0) {
			return validator(bindingResult);
		}
		return pruebaService.create(clienteDTO);
	}

	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
		if (bindingResult.getFieldErrors().size() > 0) {
			return validator(bindingResult);
		}
		return pruebaService.update(id, clienteDTO);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return pruebaService.delete(id);
	}

	private ResponseEntity<?> validator(BindingResult bindingResult) {
		Map<String, Object> response = new HashMap<>();
		List<String> errors = bindingResult.getFieldErrors().stream()
				.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
				.collect(Collectors.toList());
		response.put("errors", errors);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
}
