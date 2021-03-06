package uqbar.arena.persistence.testDomain;

import org.uqbar.commons.model.Entity;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;

@PersistentClass
public class Celular extends Entity {
	private static final long serialVersionUID = 1L;

	private Modelo modelo;
	private String numero;
	private Persona duenio;
	private Double precioPorMinuto;

	public Celular() {

	}

	public Celular(Modelo modelo, String numero, Persona duenio, Double precioPorMinuto) {
		this();
		this.modelo = modelo;
		this.numero = numero;
		this.duenio = duenio;
		this.precioPorMinuto = precioPorMinuto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((precioPorMinuto == null) ? 0 : precioPorMinuto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celular other = (Celular) obj;
		if (duenio == null) {
			if (other.duenio != null)
				return false;
		} else if (!duenio.equals(other.duenio))
			return false;
		if (modelo != other.modelo)
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (precioPorMinuto == null) {
			if (other.precioPorMinuto != null)
				return false;
		} else if (!precioPorMinuto.equals(other.precioPorMinuto))
			return false;
		return true;
	}

	@PersistentField
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

	@Relation
	public Persona getDuenio() {
		return duenio;
	}

	public void setDuenio(Persona duenio) {
		this.duenio = duenio;
	}

	@PersistentField
	public Double getPrecioPorMinuto() {
		return precioPorMinuto;
	}

	public void setPrecioPorMinuto(Double precioPorMinuto) {
		this.precioPorMinuto = precioPorMinuto;
	}
}
