package model;

import java.util.ArrayList;
import java.util.Date;

public class Mission {
	private Date dateDebut;
	private Date dateFin;
	private Client client;
	private ArrayList<Consultant> consultant;
	private String description;
	
	public Mission(Date pdateDebut,Date pdateFin ,Client pclient ,ArrayList<Consultant> pconsultant,String pDescription){
		dateDebut = pdateDebut;
		dateFin = pdateFin;
		client=pclient;
		consultant = pconsultant; 
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
	public ArrayList<Consultant> getConsultant() {
		return consultant;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isMissionVacante(){
		return consultant.isEmpty();
	}
	public void envoyerMission(Consultant c)
	{
		consultant.add(c);			
	}
	
	public Consultant envoyerMission()
	{
		return null;	
	}
	
	
}
