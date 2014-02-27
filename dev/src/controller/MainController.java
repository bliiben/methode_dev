package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JTextField;

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
	public DateFormat d = new SimpleDateFormat("dd/mm/yyyy");
	
	public MainController(MainView mainView) {
		super();
		listeConsultants = ConsultantService.listeConsultants();
		listeClient=new ArrayList<Client>();
		listeMission=new ArrayList<Mission>();
		this.mainView = mainView;
		
		listeClient.add(new Client("blagnac","0102030405","toto@gmail.com"));
		try {
			listeMission.add(new Mission(d.parse("27/02/2014"),d.parse("27/02/2014"),listeClient.get(0),new ArrayList<Consultant>(),"essais"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			listeMission.get(0).envoyerMission(listeConsultants.get(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
			
			this.mainView.afficher("Consultant disponibles : ");
			for(Consultant c : this.listeConsultants){
				if(c.isDisponible()){
					this.mainView.afficher(c.toString());
				}
			}
		}
		else if (commande.matches("disponibiliteconsultant.*")){
			String res [] = parametre(commande, "disponibiliteconsultant");
			if( res.length != 2){
				throw new Exception("Il n'y a pas assé d'arguments");
			}
			Consultant temp = null;
			for( Consultant c : this.listeConsultants){
				if( c.getPrenom().equals(res[1]) && c.getNom().equals(res[0]) ){
					temp = c;
				}
			}
			if( temp == null){
				throw new Exception("Aucun client trouvé avec ces paramètres");
			}
			boolean missionDuClientTrouve = false;
			
			for (Mission m : listeMission){
				for(Consultant c : m .getConsultant()){
					if( c == temp ){
						missionDuClientTrouve=true;
					}
				}
				if( missionDuClientTrouve ){
					mainView.afficher("Le consultant : "+temp.toString()+" est disponible à partir du "+m.getDateDebut());
					break;
				}
			}
			if( missionDuClientTrouve != true){
				mainView.afficher("Le consultant : "+temp.toString()+" est déjà disponible");
			}
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
			mainView.afficher("Liste des clients :");
			for(Client client:listeClient)
			{
				mainView.afficher(client.toString());
			}
		}
		else if(commande.matches("ajouterclient.*")){//add a client
			String res [] = parametre(commande, "ajouterclient");
			
			if( res.length != 3){	
				throw new Exception("Il est nécéssaire de mettre plus de paramètre "+res.length);
			}
			Client s=null;
			
			s = new Client(res[0],res[1],res[2]);
			listeClient.add(s);
			
			mainView.afficher("Client ajouté : "+listeClient.get(listeClient.size()-1));
			
			
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
				throw new Exception("Il est nécéssaire de mettre plus de paramètre "+res.length);
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
			
			m = new Mission(d.parse(res[0]),d.parse(res[1]),tmp,new ArrayList<Consultant>(),res[3]);
			//ajoutermission 11/01/14;11/01/14;b; une description foll
			listeMission.add(m);
			
			mainView.afficher("Mission ajoutée : "+listeMission.get(listeMission.size()-1));
		}
		else if (commande.matches("clientsansmission.*")){//Afficher client sans missions
			boolean clientSansMission = false;
			mainView.afficher("Affichage des clients sans missions");
			for (Client c: listeClient ){
				clientSansMission = false;
				for (Mission m : listeMission){
					if (c == m.getClient()){
						clientSansMission = true;
						break;
					}
				}
				if (clientSansMission == false)
					mainView.afficher(c.toString());
			}
		}

		else if(commande.matches("partirenmission.*")){ //partir en mission 
			String res [] = parametre(commande, "partirenmission");
			
			if( res.length != 4){	
				throw new Exception("Il est nécéssaire de mettre plus de paramètre "+res.length);
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
			mainView.afficher("Consultant envoyé : "+ mnms.getConsultant()+ " à "+mnms.getClient());
		}
		else if(commande.matches("exit.*")){
			System.exit(0);
		}
		else if ( commande.matches("retourmission.*")){
			String res[] = parametre(commande, "retourmission");
			if( res.length != 3)
				throw new Exception("Il n'y a pas assez de paramètres");
			
			Mission temp = null;
			for (Mission m : listeMission){
				
				if( m.getClient().getNomEntreprise().equals(res[0]) && d.format(m.getDateDebut()).equals(res[1]) && d.format(m.getDateFin()).equals(res[2]) ){
					temp=m;
				}
			}
			if(temp==null)
				throw new Exception("Pas de mission trouvé");
			
			mainView.afficher("Mission fini, consultant libéré :");
			for ( Consultant c : temp.getConsultant() ){
				c.setDisponible(true);
				mainView.afficher(c.toString());
				
			}
			
			temp.setFini(true);
			
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

	public String autoComplete(String commande) {

		String listeDesCommande[] = {
				"listeconsultant",
				"listeconsultantlibre",
				"disponibiliteconsultant",
				"ajouterconsultant",
				"supprimerconsultant",
				"listeclient",
				"ajouterclient",
				"supprimerclient",
				"affichermissionvacantes",
				"ajoutermission",
				"clientsansmission",
				"partirenmission",
				"exit",
				"retourmission"
		};
		int nombre=0;
		String ss="";
		for ( String s : listeDesCommande){
			
			System.out.println(s);
			if( s.length() > commande.length() && commande.equals(s.substring(0, commande.length())) ){
				ss=s;
				nombre++;
			}
		}
		if( nombre != 1)
			return null;
		else
			return ss;
	}
}
