package model;

import java.util.Date;

public class Mission {
	private Date dateDebut;
	private Date dateFin;
	private Client client;
	private Consultant [] consultant;
	private String description;
	
	public Mission(Date pdateDebut,Date pdateFin ,Client pclient ,Consultant pconsultant,String pDescription){
		dateDebut = pdateDebut;
		dateFin = pdateFin;
		client=pclient;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public Consultant[] getConsultant() {
		return consultant;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
