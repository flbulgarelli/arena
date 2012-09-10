package uqbar.arena.persistence.testDomain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.uqbar.commons.model.Entity;

import uqbar.arena.persistence.annotations.Cascade;
import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;

@PersistentClass
public class Persona extends Entity {
	private static final long serialVersionUID = 1L;

	private List<Celular> celulares;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private int legajo;

	public Persona() {
		celulares = new ArrayList<Celular>();
	}

	public Persona(String nombre, String apellido, Date fechaNacimiento,
			int legajo) {
		this();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.legajo = legajo;
	}

	@Relation(cascade = {Cascade.CREATE, Cascade.UPDATE, Cascade.DELETE})
	public List<Celular> getCelulares() {
		return celulares;
	}

	public void setCelulares(List<Celular> celulares) {
		this.celulares = celulares;
	}

	@PersistentField
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@PersistentField
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@PersistentField
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@PersistentField
	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}
}
