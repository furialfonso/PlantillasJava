package com.example.arquitectura.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

	private Long id;
	@NotEmpty(message = "Obligatorio")
	@Size(min = 4, max = 10, message = "El tamaño esta entre 4 y 10")
	private String primerNombre;
	private String segundoNombre;
	@NotEmpty(message = "Obligatorio")
	@Size(min = 4, max = 10, message = "El tamaño esta entre 4 y 10")
	private String primerApellido;
	private String segundoApellido;
	@NotEmpty(message = "Obligatorio")
	@Size(min = 4, max = 10, message = "El tamaño esta entre 4 y 10")
	private String identificacion;
	@NotEmpty(message = "Obligatorio")
	@Email(message = "No tiene formato de correo")
	@Size(min = 4, max = 40, message = "El tamaño esta entre 4 y 40")
	private String correoElectronico;
}
