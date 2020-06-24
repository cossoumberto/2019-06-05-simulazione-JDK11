package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

public class District implements Comparable<District> {
	
	private Integer id;
	private LatLng coord;
	private List<Double> distanze;
	
	public District(Integer id, Double latitude, Double longitude) {
		super();
		this.id = id;
		coord = new LatLng(latitude, longitude);
		distanze = new ArrayList<>();
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
