package it.polito.tdp.crimes.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<District, DefaultWeightedEdge> grafo; //NELLA CORREZIONE DISTRETTI GESTITI COME INTERI E SENZA IDMAP
	private Map<Integer, District> idMap;
	private Simulator s;
	
	public Model() {
		dao = new EventsDao();
		idMap = new HashMap<>();
	}

	public List<Integer> getYears() {
		return dao.listYears();
	}
	
	public void creaGrafo(Integer year) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listDistrictForYears(year ,idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		for(Integer d1 : idMap.keySet())
			for(Integer d2 : idMap.keySet()) 
				if(!d1.equals(d2) && !grafo.containsEdge(idMap.get(d1), idMap.get(d2))) { //NELLA CORREZIONE LAT E LONG MEDIA PRESI DAL DAO SEPARATI
					Double distanza = LatLngTool.distance(idMap.get(d1).getCoord(), idMap.get(d2).getCoord(), LengthUnit.KILOMETER);
					Graphs.addEdge(grafo, idMap.get(d1), idMap.get(d2), distanza);
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
		List<District> districtsList = new ArrayList<>();
		districtsList.addAll(idMap.values());
		Collections.sort(districtsList);
		return districtsList;
	}
	
	public Graph<District, DefaultWeightedEdge> getGrafo(){
		return grafo;
	}
	
	public Integer simula(Integer anno, Integer mese, Integer giorno, Integer N){
		try {
			s = new Simulator();
			LocalDate ld = LocalDate.of(anno, mese, giorno);
			s.init(this, dao.listEventsDate(ld, idMap), N, dao.localizzaCentralePolizia(anno, idMap));
			s.run();
			return s.getMalGestiti();
		} catch (DateTimeException e){
			e.printStackTrace();
			return -1;
		}
	}
	
}
