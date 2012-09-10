package uqbar.arena.persistence.testDomain;

import org.uqbar.commons.model.Entity;

import uqbar.arena.persistence.annotations.Cascade;
import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;

@PersistentClass
public class Celular extends Entity{
	private static final long serialVersionUID = 1L;
	
	private Modelo modelo;
	private String numero;
	private Persona duenio;

	public Celular(){
		
	}
	
	public Celular(Modelo modelo, String numero, Persona duenio) {
		this();
		this.modelo = modelo;
		this.numero = numero;
		this.duenio = duenio;
	}

	@Relation(cascade = {Cascade.CREATE, Cascade.UPDATE, Cascade.DELETE})
	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	@PersistentField
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Relation(cascade = {Cascade.CREATE, Cascade.UPDATE, Cascade.DELETE})
	public Persona getDuenio() {
		return duenio;
	}

	public void setDuenio(Persona duenio) {
		this.duenio = duenio;
	}
}
