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
				ArrayList<Company>  tableCompany= servcompany.getDao().getPage(0);
				afficheListCompany(tableCompany);
				pause(sc);
				continue;

			case "2" :
				int offset = 0;
				ArrayList<Computer> tableComputer= servcomputer.getDao().getPage(0);
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

				continue;
			case "6" :
				affichageSupprOrdinateur(sc,servcomputer);
				sc.nextLine();
				continue;
			default : 
				System.out.println(" L'option que vous avez choisie n'est pas dans la liste de choix ");
				pause(sc);
				clearConsole(20);
			}


		}while(!choix.equals("exit"));

	}


	
	/**
	 * 	AFFICHE DE LA LISTE DES COMPUTER
	 * @param tableComputer
	 */
	public static void afficheListComputer(ArrayList<Computer> tableComputer) {
		clearConsole(20);
		afficheMenu(" LISTE DES ORDINATEURS ");
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
		clearConsole(20);
		afficheMenu(" LISTE DES COMPANY ");
		tableCompany.forEach((company ->{
			System.out.println("id: "+company.getId());
			System.out.println("name: "+company.getName());

		}));

	}
	/**
	 * 
	 * @param sc
	 * @param servcomputer
	 */
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


	/**
	 * 
	 * @param sc
	 * @param servcomputer
	 */
	public static void affichageAjoutOrdinateur(Scanner sc, ServiceComputer servcomputer) {
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir le nom de nouveau ordinateur: ");
		String name = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir la date d'introduction du nouveau ordinateur(AAAA\\MM\\JJ): ");
		String date_introduced = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir la date d'introduction du nouveau ordinateur(AAAA\\MM\\JJ): ");
		String date_discontinued = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir l'id de la company : ");
		Long company_id = sc.nextLong();
		sc.nextLine();

		ajoutOrdinateur(servcomputer,name,date_introduced,date_discontinued,company_id);
	}
	/**
	 * 
	 * @param servcomputer
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param company_id
	 */
	public static void ajoutOrdinateur(ServiceComputer servcomputer,String name, String date_introduced, String date_discontinued,Long company_id) {
		Computer computer= new Computer();
		computer.setName(name);
		computer.setIntroduced(Timestamp.valueOf(LocalDateTime.now()));
		computer.setDiscontinued(null);
		computer.setCompany_id(company_id);
		servcomputer.getDao().create(computer);
	}
	/**
	 * 
	 * @param sc
	 * @param servcomputer
	 */
	public static void affichageSupprOrdinateur(Scanner sc, ServiceComputer servcomputer) {
		afficheMenu("SUPPR . D'UN ORDINATEUR");
		System.out.println("Veuillez saisir le nom de ordinateur que vous souhaiter suppr.: ");
		Long id = sc.nextLong();
		sc.nextLine();
		Computer computer = new Computer();
		computer.setId(id);
		servcomputer.getDao().delete(computer);

	}

	/**
	 *  AFFICHE LE MENU 
	 */
	public static void printMenu() {
		afficheMenu(" M E N U ");
		System.out.println();
		System.out.println("1) Afficher listes des compagnies				2) Afficher listes des ordinateurs ");
		System.out.println("3) Affiches Les detail Ordinateur				4) Ajouter un ordinateur ");
		System.out.println("5) mise a jour d'un ordinateur					6) Supprimer d'un ordinateur ");
		System.out.println("Saisir votre choix : ");
	}
	public static void pause(Scanner sc) {
		System.out.println("Appuyer sur entree pour continuer .....");
		sc.nextLine();
		clearConsole(20);
	}

	public final static void clearConsole(int l)
	{
		for(int clear = 0; clear < l; clear++)
		{
			System.out.println() ;
		}
	}
	public static void afficheMenu(String menu) {
		clearConsole(20);
		System.out.println(" 					========= "+menu+" ===========					");
		clearConsole(3);
	}

}
