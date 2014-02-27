package view;

import java.awt.BorderLayout;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;

import controller.MainController;

public class MainView{

	private class PopClickListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    public void mouseReleased(MouseEvent e){
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    private void doPop(MouseEvent e){
	    	
	    	
	    	JPopupMenu popup = new JPopupMenu();
			Action copyAction = contenu.getActionMap().get(DefaultEditorKit.copyAction);
			Action cutAction = contenu.getActionMap().get(DefaultEditorKit.cutAction);
			Action pasteAction = contenu.getActionMap().get(DefaultEditorKit.pasteAction);
			popup.add (cutAction);
			popup.add (copyAction);
			popup.add (pasteAction);
		
			
	        popup.show(e.getComponent(), e.getX(), e.getY());
	        
	    }
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5739927886065724973L;

	private JTextArea contenu;
	private JFrame mainFrame;
	private JTextField commande;
	
	private MainController mainController;

	public MainView() {
		/**
		 * Initialisation du controller de commandes
		 */
		mainController = new MainController(this);
		
		/*
		 * Initialisation des composants de la vue
		 */
		mainFrame = new JFrame("Gestion des consultants");
		mainFrame.setLayout(new BorderLayout());
				
		contenu = new JTextArea();
		contenu.setEnabled(true);
		contenu.setEditable(false);		
		
		
		contenu.addMouseListener(new PopClickListener());
		commande = new JTextField();
		commande.addMouseListener(new PopClickListener());


		
		//Initialisation des evenements du textfield
		commande.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent event) {
				if(event.getKeyChar() == '\n')
				{
					try{
					mainController.commande(commande.getText());
					}catch (Exception e) {
						afficher(e.getMessage());
					}
					commande.setText("");
				}
				else if (event.getKeyChar() == ' ' && event.isControlDown()) {
					String retour = mainController.autoComplete(commande.getText());
					if (retour != null)
						commande.setText(retour);
					else
						System.out.println("Pas de commande correspondate");
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {			
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		/*
		 * Ajout des composants a la vue
		 */
		mainFrame.add(contenu, BorderLayout.CENTER);		
		mainFrame.add(commande, BorderLayout.PAGE_END);
		
		//Initilisation des attributs de la vue
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(800, 400);
		mainFrame.setVisible(true);	
		
	}	
	
	/**
	 * Affiche le contenu sur l'ecran principal
	 * 
	 * @param contenu
	 */
	public void afficher(String message)
	{
		//"\n" permet un retour a la ligne
		contenu.append(message+"\n");
		contenu.repaint();
	}	
	
	/**
	 * Efface tout ce qui est affiché sur l'ecran principal
	 */
	public void effacer()
	{
		contenu.setText("");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainView frame = new MainView();		
	}
}
