package com.example.arquitectura.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_cliente")
	@SequenceGenerator(name = "sec_cliente", sequenceName = "sec_cliente", allocationSize = 1)
	private Long id;
	@Column(nullable = false)
	private String primerNombre;
	private String segundoNombre;
	@Column(nullable = false)
	private String primerApellido;
	private String segundoApellido;
	@Column(nullable = false)
	private String identificacion;
	@Column(nullable = false)
	private String correoElectronico;

}
