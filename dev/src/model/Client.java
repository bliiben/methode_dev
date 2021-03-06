package model;

public class Client {
	
	private String nomEntreprise;
	private String numTel;
	private String adresseMail; 
	

	public Client(String _nomEntreprise, String _numTel, String _adresseMail)
	{
		this.nomEntreprise = _nomEntreprise; 
		this.numTel = _numTel; 
		this.adresseMail = _adresseMail; 
	}
	
	
	// Observateur 
	
	public String getNomEntreprise()
	{
		return nomEntreprise; 
	}
	
	public String getNumTel()
	{
		return numTel; 
	}
	
	public String getAdresseMail()
	{
		return adresseMail; 
	}
	
	// Modifieurs
	public void setNomEntreprise(String _nomEntreprise)
	{
		this.nomEntreprise = _nomEntreprise; 
	}
	
	public void setNumTel(String _numTel)
	{
		this.numTel = _numTel; 
	}
	
	public void setAdresseMail(String _adresseMail)
	{
		this.adresseMail = _adresseMail; 
	}

	
	@Override
	public String toString()
	{
		return "Client [nom entreprise = " + nomEntreprise + ", num�ro entreprise : " + numTel + ", adresse mail de l'entreprise : " + adresseMail + "]"; 
	}
}
