package it.polito.tdp.crimes.model;

public class Agente {
	
	private Integer id;
	private District posizione;
	private Boolean libero;
	
	public Agente(Integer id, District posizione) {
		super();
		this.id = id;
		this.posizione = posizione;
		this.libero = Boolean.TRUE;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public District getPosizione() {
		return posizione;
	}

	public void setPosizione(District posizione) {
		this.posizione = posizione;
	}

	public Boolean getLibero() {
		return libero;
	}

	public void setLibero(Boolean libero) {
		this.libero = libero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Agente other = (Agente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
