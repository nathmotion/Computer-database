package com.excilys.java.IHM;

import java.io.Console;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.service.ServiceCompany;
import com.excilys.java.cdb.service.ServiceComputer;


public class Testdb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServiceCompany servcompany = ServiceCompany.INSTANCE;
		ServiceComputer servcomputer = ServiceComputer.INSTANCE;
		String choix ;
		
						//===========	 C L I	===========
		Scanner sc =  new Scanner(System.in);
		do {	
			printMenu();
			choix = sc.nextLine();

			switch(choix){
			case "1": 
				ArrayList<Company>  tableCompany= servcompany.getDao().getAll();
				afficheListCompany(tableCompany);
				pause(sc);
				continue;
				
			case "2" :
				ArrayList<Computer> tableComputer= servcomputer.getDao().getAll();
				afficheListComputer(tableComputer);
				pause(sc);

				continue;
				
			case "3" :
				afficheFindbyId(sc,servcomputer);
				sc.nextLine();
				pause(sc);

				continue;
			case "4" :
				affichageAjoutOrdinateur(sc,servcomputer);
				//sc.nextLine();
				continue;
			case "5" :
				afficheFindbyId(sc,servcomputer);
				sc.nextLine();
				continue;
			}


		}while(!choix.equals("exit"));

	}
	
	
	/**
	 *  AFFICHE LE MENU 
	 */
	public static void printMenu() {
		
		System.out.println("1) Afficher listes des compagnies				2) Afficher listes des ordinateurs ");
		System.out.println("3) Affiches Les detail Ordinateur				4) Ajouter un ordinateur ");
		System.out.println("5) mise a jour d'un ordinateur					6) Supprimer d'un ordinateur ");
		System.out.println("Saisir votre choix : ");
	}
	
	/**
	 * 	AFFICHE DE LA LISTE DES COMPUTER
	 * @param tableComputer
	 */
	public static void afficheListComputer(ArrayList<Computer> tableComputer) {
		clearConsole();

		tableComputer.forEach((computer ->{
			System.out.println("id: "+computer.getId());
			System.out.println("name: "+computer.getName());

		}));
	}
	
	/**	
	 * 		AFFICHE LA LISTE DES COMPANY
	 * @param tableCompany
	 */
	public static void afficheListCompany(ArrayList<Company> tableCompany) {
		clearConsole();

		tableCompany.forEach((company ->{
			System.out.println("id: "+company.getId());
			System.out.println("name: "+company.getName());

		}));

	}
	
	public static void afficheFindbyId(Scanner sc, ServiceComputer servcomputer) {
		System.out.println("Veullez saisir l' id de l'ordinateur ");
		int id = sc.nextInt();


		Optional<Computer> opcomputer = servcomputer.getDao().findById(id)	;
		if(opcomputer.isPresent())
		{
			Computer computer = opcomputer.get();
			System.out.println("id: "+computer.getId());
			System.out.println("name: "+computer.getName());
			System.out.println("introduced: "+computer.getIntroduced());
			System.out.println("discontinued: "+computer.getDiscontinued());
			System.out.println("company_id: "+computer.getCompany_id());
		}
		else {
			//System.out.println(" error aucun computer trouver");
		}
	}
	
	public final static void clearConsole()
	{
		for(int clear = 0; clear < 12; clear++)
		  {
		     System.out.println() ;
		  }
	}
	public static void afficheMenuAjout() {
	clearConsole();
		System.out.println(" 					=========  AJOUT  D' UN ORDINATEUR  ===========					");
		clearConsole();
	}
	public static void affichageAjoutOrdinateur(Scanner sc, ServiceComputer servcomputer) {
			afficheMenuAjout();
			System.out.println("Veuillez saisir le nom de nouveau ordinateur: ");
			String name = sc.nextLine();
			afficheMenuAjout();
			System.out.println("Veuillez saisir la date d'introduction du nouveau ordinateur(AAAA\\MM\\JJ): ");
			String date_introduced = sc.nextLine();
			afficheMenuAjout();
			System.out.println("Veuillez saisir la date d'introduction du nouveau ordinateur(AAAA\\\\MM\\\\JJ): ");
			String date_discontinued = sc.nextLine();
			afficheMenuAjout();
			System.out.println("Veuillez saisir l'id de la company : ");
			Long company_id = sc.nextLong();
			sc.nextLine();
			
			ajoutOrdinateur(servcomputer,name,date_introduced,date_discontinued,company_id);
	}

	public static void ajoutOrdinateur(ServiceComputer servcomputer,String name, String date_introduced, String date_discontinued,Long company_id) {
		Computer computer= new Computer();
		computer.setName(name);
		computer.setIntroduced(Timestamp.valueOf(LocalDateTime.now()));
		computer.setDiscontinued(null);
		computer.setCompany_id(company_id);
		servcomputer.getDao().create(computer);
	}
	
	public static void pause(Scanner sc) {
		System.out.println("Appuyer sur entree pour continuer .....");
		sc.nextLine();
		clearConsole();
	}
}
