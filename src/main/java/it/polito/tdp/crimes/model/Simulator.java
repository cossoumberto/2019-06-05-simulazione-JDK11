package it.polito.tdp.crimes.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.EventSimulator.EventType;

public class Simulator {

	//PARAMETRI 
	private Model model;
	private Graph<District, DefaultWeightedEdge> grafo;
	private List<Event> eventi;
	private Integer N;
	
	//STATO DEL SISTEMA
	private List<Agente> agenti;
	 
	//OUTPUT
	private Integer malGestiti;
	
	//CODA EVENTI
	private Queue<EventSimulator> queue;
	private Queue<EventSimulator> attesa;
	
	public void init(Model model, List<Event> eventi, Integer N, District centrale) {
		this.model = model;
		this.grafo = model.getGrafo();
		this.eventi = eventi;
		this.N = N;
		this.agenti = new ArrayList<>();
		for(int i=1; i<=N; i++) {
			Agente a = new Agente(i, centrale);
			agenti.add(a);
		}
		this.malGestiti = 0;
		this.queue = new PriorityQueue<>();
		this.attesa = new PriorityQueue<>();
		for(Event e : eventi) {
				EventSimulator es = new EventSimulator(e.getReported_date(), EventType.DA_ASSEGNARE, e, e.getDistrict(), null, null);
				queue.add(es);
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			EventSimulator e = queue.poll();
			System.out.println(e);
			System.out.println(attesa.size());
			processEvent(e);
		}
	}

	private void processEvent(EventSimulator e) {
		switch (e.getType()) {
			case DA_ASSEGNARE:
				boolean assegnato = false;
				for(DistrictDistanza dd : model.getVicini(e.getDistrict())) {
					for(Agente a : agenti) {
						if(a.getPosizione().equals(dd.getDistrict()) && a.getLibero()==Boolean.TRUE) {
							EventSimulator newES = new EventSimulator(e.getTime().plus(dd.getDist().longValue(), ChronoUnit.MINUTES), EventType.AGENTE_ARRIVATO, e.getEvent(), e.getDistrict(), a, null);
							if(dd.getDist().longValue()>15 || (e.getRitardo()!=null && (e.getRitardo()+dd.getDist().longValue())>15) )
								malGestiti++;
							queue.add(newES);
							a.setLibero(Boolean.FALSE);
							a.setPosizione(e.getDistrict());
							assegnato = true;
							break;
						}
					}
					if(assegnato==true)
						break;
				}
				if(assegnato==false) {
					EventSimulator newES = new EventSimulator(e.getTime(), EventType.IN_ATTESA, e.getEvent(), e.getDistrict(), null, null);
					attesa.add(newES);
				}
				break;
			case AGENTE_ARRIVATO:
				Long durataIntervento = (long)2;
				if(e.getEvent().getOffense_category_id().equals("all-other-crimes") && Math.random()<0.5)
					durataIntervento =(long)1;
				EventSimulator newES = new EventSimulator(e.getTime().plus(durataIntervento, ChronoUnit.HOURS), EventType.AGENTE_LIBERATO, e.getEvent(), e.getDistrict(), e.getAgente(), null);
				queue.add(newES);
				break;
			case AGENTE_LIBERATO:
				if(!attesa.isEmpty()) {
					EventSimulator inAttesa = attesa.poll();
					EventSimulator newES1 = new EventSimulator(e.getTime(), EventType.DA_ASSEGNARE, inAttesa.getEvent(), 
							inAttesa.getDistrict(), null, (double)inAttesa.getTime().until(e.getTime(), ChronoUnit.MINUTES));
					queue.add(newES1);
				} else {
					e.getAgente().setLibero(Boolean.TRUE);
				}
				break;
			case IN_ATTESA:
				break;
		}
	}
	
	public Integer getMalGestiti() {
		return malGestiti;
	}
}
