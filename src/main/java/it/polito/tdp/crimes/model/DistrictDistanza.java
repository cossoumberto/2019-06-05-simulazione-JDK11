package it.polito.tdp.crimes.model;

public class DistrictDistanza implements Comparable<DistrictDistanza>{
	
	private District district;
	private Double dist;
	
	public DistrictDistanza(District district, Double dist) {
		super();
		this.district = district;
		this.dist = dist;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Double getDist() {
		return dist;
	}

	public void setDist(Double dist) {
		this.dist = dist;
	}

	@Override
	public String toString() {
		return "District: " + district.getId() + " distanza= " + dist;
	}

	@Override
	public int compareTo(DistrictDistanza o) {
		return this.dist.compareTo(o.getDist());
	}
	
	
	
}
