package controller;

import java.util.ArrayList;

import model.Client;
import model.Consultant;

import service.ConsultantService;
import view.MainView;

public class MainController {

	private MainView mainView;
	private ArrayList<Consultant> listeConsultants;
	private ArrayList<Client> listeClient;
	
	public MainController(MainView mainView) {
		super();
		listeConsultants = ConsultantService.listeConsultants();
		this.mainView = mainView;
	}
	
	public void commande(String commande) throws Exception
	{
		if(commande.matches("listeconsultant.*"))
		{			
			mainView.afficher("Liste des consultants :");
			for(Consultant consultant:listeConsultants)
			{
				mainView.afficher(consultant.toString());
			}
		}
		else if(commande.matches("listeconsultantlibre.*"))
		{
			mainView.afficher("Liste des consultants actuellement libre :");
		}
		else if(commande.matches("clear.*"))
		{
			mainView.effacer();
		}
		else if(commande.matches("ajouterconsultant.*")){//add a consultant
			String res [] = parametre(commande, "ajouterconsultant");
			if( res.length != 4)
				throw new Exception("Il est nécéssaire de mettre plus de paramètre "+res.length);
			listeConsultants.add(new Consultant(res[0],res[1],res[2],res[3]));
			mainView.afficher("Consultant ajouté : "+listeConsultants.get(listeConsultants.size()-1));
		}
		else if(commande.matches("supprimerconsultant.*")){//delete a consultant 
			String res[]=parametre(commande, "supprimerconsultant");
			if( res.length!=2)
				throw new Exception("Il manque un paramètre");
			for(Consultant c : listeConsultants){
				if( c.getPrenom().equals(res[1]) && c.getNom().equals(res[0]) ){
					listeConsultants.remove(c);
					mainView.afficher("Consultant supprimé");
					break;
				}
			}
		}
		else if (commande.matches("listeclient.*"))
		{			
			mainView.afficher("Liste des consultants :");
			for(Client client:listeClient)
			{
				mainView.afficher(client.toString());
			}
		}
		else if(commande.matches("ajouterclient.*")){//add a client
			String res [] = parametre(commande, "ajouterclient");
			if( res.length != 3)
				throw new Exception("Il est nécéssaire de mettre plus de paramètre "+res.length);
			listeClient.add(new Client(res[0],res[1],res[2]));
			mainView.afficher("Consultant ajouté : "+listeClient.get(listeClient.size()-1));
		}
		else if(commande.matches("supprimerclient.*")){//delete a client 
			String res[]=parametre(commande, "supprimerclient");
			if( res.length!=1)
				throw new Exception("Il manque un paramètre");
			for(Client c : listeClient){
				if( c.getNomEntreprise().equals(res[0])){
					listeClient.remove(c);
					mainView.afficher("Client supprimé");
					break;
				}
			}
		}
		
		//cas ou la commande n'est pas reconnue
		else
		{
			mainView.afficher("commande '"+commande+"' inconnue.");
		}
	}
	
	public static String [] parametre(String commande, String commandeAttendu){
		return commande.replaceAll(commandeAttendu+" (.*)", "$1").split(";");
	}
}
