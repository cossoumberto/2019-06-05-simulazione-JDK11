package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class District implements Comparable<District> {
	
	private Integer id;
	private LatLng coord;
	private Integer numAgenti;
	
	public District(Integer id, Double latitude, Double longitude) {
		super();
		this.id = id;
		coord = new LatLng(latitude, longitude);
		this.numAgenti = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LatLng getCoord() {
		return coord;
	}

	public void setCoord(LatLng coord) {
		this.coord = coord;
	}

	public Integer getNumAgenti() {
		return numAgenti;
	}

	public void setNumAgenti(Integer numAgenti) {
		this.numAgenti = numAgenti;
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
		District other = (District) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", coord=" + coord + "]";
	}

	@Override
	public int compareTo(District o) {
		return this.id-o.id;
	}
}
