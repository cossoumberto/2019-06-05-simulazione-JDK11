package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class EventSimulator implements Comparable<EventSimulator>{

	//ATTESA NON GESTITA
	public enum EventType {
		AGENTE_ARRIVATO, AGENTE_LIBERATO, DA_ASSEGNARE, /*IN_ATTESA*/;
	}
	
	private LocalDateTime time;
	private EventType type;
	private Event event;
	private District district; //NON USA ATTRIBUTO DISTRICT (contenuto gia in  event) E NEANCHE ATTRIBUTO AGENTE (non crea neanche classe agente)
	private Agente agente;
	//private Double ritardo; 
	
	public EventSimulator(LocalDateTime time, EventType type, Event event, District district, Agente agente/*, Double ritardo*/) {
		super();
		this.time = time;
		this.type = type;
		this.event = event;
		this.district = district;
		this.agente = agente;
		//this.ritardo = ritardo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
	/*
	
	public Double getRitardo() {
		return ritardo;
	}

	public void setRitardo(Double ritardo) {
		this.ritardo = ritardo;
	}
	*/
	
	@Override
	public String toString() {
		String s = "EventSimulator [time=" + time + ", type=" + type + ", event=" + event.getIncident_id() + ", district=" + district.getId()
		+ "]";
		if(agente!=null)
			s += (" " + agente.getId());
		/*if(ritardo!=null)
			s += (" " + ritardo);*/
		return s;
	}

	@Override
	public int compareTo(EventSimulator o) {
		return this.time.compareTo(o.time);
	}
	
}
