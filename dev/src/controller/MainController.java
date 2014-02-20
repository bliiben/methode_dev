package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import model.Client;
import model.Consultant;
import model.Mission;

import service.ConsultantService;
import view.MainView;


public class MainController {

	private MainView mainView;
	private ArrayList<Consultant> listeConsultants;
	private ArrayList<Client> listeClient;
	private ArrayList<Mission> listeMission;
	
	public MainController(MainView mainView) {
		super();
		listeConsultants = ConsultantService.listeConsultants();
		listeClient=new ArrayList<Client>();
		listeMission=new ArrayList<Mission>();
		this.mainView = mainView;
	}
	
	@SuppressWarnings("deprecation")
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
				throw new Exception("Il est n�c�ssaire de mettre plus de param�tre "+res.length);
			listeConsultants.add(new Consultant(res[0],res[1],res[2],res[3]));
			mainView.afficher("Consultant ajout� : "+listeConsultants.get(listeConsultants.size()-1));
		}
		else if(commande.matches("supprimerconsultant.*")){//delete a consultant 
			String res[]=parametre(commande, "supprimerconsultant");
			if( res.length!=2)
				throw new Exception("Il manque un param�tre");
			for(Consultant c : listeConsultants){
				if( c.getPrenom().equals(res[1]) && c.getNom().equals(res[0]) ){
					listeConsultants.remove(c);
					mainView.afficher("Consultant supprim�");
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
			
			if( res.length != 3){	
				throw new Exception("Il est n�c�ssaire de mettre plus de param�tre "+res.length);
			}
			Client s=null;
			
			s = new Client(res[0],res[1],res[2]);
			listeClient.add(s);
			
			mainView.afficher("Client ajout� : "+listeClient.get(listeClient.size()-1));
			
			
		}
		else if(commande.matches("supprimerclient.*")){//delete a client 
			String res[]=parametre(commande, "supprimerclient");
			if( res.length!=1)
				throw new Exception("Il manque un param�tre");
			for(Client c : listeClient){
				if( c.getNomEntreprise().equals(res[0])){
					listeClient.remove(c);
					mainView.afficher("Client supprim�");
					break;
				}
			}
		}
		else if(commande.matches("affichermissionvacantes.*")){
			for(Mission m : listeMission){
				if(m.isMissionVacante()){
					mainView.afficher(m.toString());
				}
			}
		}
		/**
		 * 
		 * public Mission(Date pdateDebut,Date pdateFin ,Client pclient ,ArrayList<Consultant> pconsultant,String pDescription){
		dateDebut = pdateDebut;
		dateFin = pdateFin;
		client=pclient;
		consultant = pconsultant; 
	}
		 * 
		 */
		else if(commande.matches("ajoutermission.*")){//add a mission 
			String res [] = parametre(commande, "ajoutermission");
			
			if( res.length != 4){	
				throw new Exception("Il est n�c�ssaire de mettre plus de param�tre "+res.length);
			}
			Mission m=null;
			Client tmp = null;
			for ( Client c : listeClient){
				
				tmp =c;
				if( c.getNomEntreprise().equals(res[2]));
					break;
			}
			if(tmp==null)
				throw new Exception("Le client n'existe pas");
			
			m = new Mission(new Date(res[0]),new Date(res[1]),tmp,new ArrayList<Consultant>(),res[3]);
			//ajoutermission 11/01/14;11/01/14;b;une description folll
			listeMission.add(m);
			
			mainView.afficher("Mission ajout�e : "+listeMission.get(listeMission.size()-1));
		}
		else if(commande.matches("partirenmission.*")){ //partir en mission 
			String res [] = parametre(commande, "partirenmission");
			
			if( res.length != 4){	
				throw new Exception("Il est n�c�ssaire de mettre plus de param�tre "+res.length);
			}
			//date client consultant 2
			//date nomEntreprise nomConsultant prenomConsultant
			Consultant tmpConsultant= null;
			for(Consultant c : listeConsultants){
				if( c.getPrenom().equals(res[3]) && c.getNom().equals(res[2]) ){
					tmpConsultant=c;
					break;
				}
			}
			if( tmpConsultant==null)
				throw new Exception("Sorry pas de consultant");
			
			Client tmpClient = null;
			for ( Client c : listeClient){	
				tmpClient =c;
				if( c.getNomEntreprise().equals(res[2]));
					break;
			}
			if( tmpClient==null)
				throw new Exception("Sorry pas de client");
			
			Date d = new Date(res[0]);
			Mission mnms=null;
			for( Mission m : listeMission){
				if(m.getClient()== tmpClient){
					mnms=m;
					break;
				}
			}
			if( mnms==null)
				throw new Exception("Pas de missions correspondante aux parametre");
			
			mnms.envoyerMission(tmpConsultant);
			mainView.afficher("Consultant envoy� : "+ mnms.getConsultant()+ " � "+mnms.getClient());
		}
		//cas ou la commande n'est pas reconnue
		else
		{
			mainView.afficher("commande '"+commande+"' inconnue.");
		}
		//ajouterclient benj;444;555
		//ajoutermission 11/01/2014;11/02/2014;benj;description
		//partirenmission 11/01/2014;benj;Roda;Sophie
	}
	
	public static String [] parametre(String commande, String commandeAttendu){
		return commande.replaceAll(commandeAttendu+" (.*)", "$1").split(";");
	}
}
