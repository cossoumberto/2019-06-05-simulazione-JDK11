package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<District, DefaultWeightedEdge> grafo;
	private List<District> districtsList;
	
	public Model() {
		dao = new EventsDao();
	}

	public List<Integer> getYears() {
		return dao.listYears();
	}
	
	public void creaGrafo(Integer year) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		districtsList = dao.listDistrictForYears(year);
		Graphs.addAllVertices(grafo, districtsList);
		for(District d1 : districtsList)
			for(District d2 : districtsList) 
				if(!d1.equals(d2) && !grafo.containsEdge(d1, d2)) {
					Double distanza = LatLngTool.distance(d1.getCoord(), d2.getCoord(), LengthUnit.KILOMETER);
					Graphs.addEdge(grafo, d1, d2, distanza);
			}
	}
	
	public List<DistrictDistanza> getVicini(District district){
		List<DistrictDistanza> list = new ArrayList<>();
		for(DefaultWeightedEdge e : grafo.edgesOf(district)) {
			DistrictDistanza dd = new DistrictDistanza(Graphs.getOppositeVertex(grafo, e, district), grafo.getEdgeWeight(e));
			list.add(dd);
		}
		Collections.sort(list);
		return list;
	}
	
	public List<District> getDistrictsList(){
		Collections.sort(districtsList);
		return districtsList;
	}
	
}
