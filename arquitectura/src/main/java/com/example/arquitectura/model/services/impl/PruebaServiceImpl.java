package com.example.arquitectura.model.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arquitectura.model.dto.ClienteDTO;
import com.example.arquitectura.model.entity.Cliente;
import com.example.arquitectura.model.repository.IPruebaRepository;
import com.example.arquitectura.model.services.IPruebaService;

@Service
public class PruebaServiceImpl implements IPruebaService {
//	private final Logger logger = LoggerFactory.getLogger(PruebaServiceImpl.class);

	@Autowired
	private IPruebaRepository pruebaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> getClientes() {
		List<ClienteDTO> clientesDTO = new ArrayList<ClienteDTO>();
		List<Cliente> clientes = pruebaRepository.findAll();
		clientes.forEach(cliente -> {
			clientesDTO.add(new ClienteDTO(cliente.getId(), cliente.getPrimerNombre(), cliente.getSegundoNombre(),
					cliente.getPrimerApellido(), cliente.getSegundoApellido(), cliente.getIdentificacion(),
					cliente.getCorreoElectronico()));
		});
		return clientesDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO getCliente(String primerNombre) {
		Cliente cliente = pruebaRepository.findByPrimerNombre(primerNombre);

		if (cliente == null) {
			return new ClienteDTO();
		}
		return new ClienteDTO(cliente.getId(), cliente.getPrimerNombre(), cliente.getSegundoNombre(),
				cliente.getPrimerApellido(), cliente.getSegundoApellido(), cliente.getIdentificacion(),
				cliente.getCorreoElectronico());
	}

	@Override
	public ClienteDTO getClientePorApellido(String primerApellido) {
		Cliente cliente = pruebaRepository.findByApellido(primerApellido);
		if (cliente == null) {
			return new ClienteDTO();
		}
		return new ClienteDTO(cliente.getId(), cliente.getPrimerNombre(), cliente.getSegundoNombre(),
				cliente.getPrimerApellido(), cliente.getSegundoApellido(), cliente.getIdentificacion(),
				cliente.getCorreoElectronico());
	}

	@Override
	public ResponseEntity<?> create(ClienteDTO clienteDTO) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteNew = new Cliente(clienteDTO.getId(), clienteDTO.getPrimerNombre(),
				clienteDTO.getSegundoNombre(), clienteDTO.getPrimerApellido(), clienteDTO.getSegundoApellido(),
				clienteDTO.getIdentificacion(), clienteDTO.getCorreoElectronico());

		try {
			clienteNew = pruebaRepository.save(clienteNew);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error guardando el Cliente");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Cliente creado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> update(Long id, @Valid ClienteDTO clienteDTO) {
		Map<String, Object> response = new HashMap<>();
		Cliente clienteEditado = null;
		Cliente clienteOld = pruebaRepository.findById(id).orElse(null);
		if (clienteOld == null) {
			response.put("mensaje", "Error: no se pudo encotro el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		clienteOld.setPrimerNombre(clienteDTO.getPrimerNombre());
		clienteOld.setSegundoNombre(clienteDTO.getSegundoNombre());
		clienteOld.setPrimerApellido(clienteDTO.getPrimerApellido());
		clienteOld.setSegundoApellido(clienteDTO.getSegundoApellido());
		clienteOld.setIdentificacion(clienteDTO.getIdentificacion());
		clienteOld.setCorreoElectronico(clienteDTO.getCorreoElectronico());
		try {
			clienteEditado = pruebaRepository.save(clienteOld);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Cliente actualizado con éxito!");
		response.put("cliente", clienteEditado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = pruebaRepository.findById(id).orElse(null);
		if (cliente == null) {
			response.put("mensaje", "Error: no se pudo encotro el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			pruebaRepository.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Cliente eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
