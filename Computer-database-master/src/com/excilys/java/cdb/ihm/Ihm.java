package com.excilys.java.cdb.ihm;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.excilys.java.cdb.dao.DaoComputer;
import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.service.ServiceCompany;
import com.excilys.java.cdb.service.ServiceComputer;

public class Ihm {
	final static Logger logger = Logger.getLogger(Ihm.class);

	ServiceCompany servcompany = ServiceCompany.INSTANCE;
	ServiceComputer servcomputer = ServiceComputer.INSTANCE;
	Scanner sc =  new Scanner(System.in);
	static String choix;

	public static void main(String[] args) {
		Ihm myCLI= new Ihm();
		myCLI.startCLI();
	}
	/**
	 * 												================	 C L I   ================
	 */
	public void  startCLI() {
		do {	
			printMenu();
			choix = sc.nextLine();

			switch(choix){
			case "1": 
				int offsetCompany = 0;
				ArrayList<Company>  tableCompany= servcompany.daoGetPage(offsetCompany);
				afficheListCompany(tableCompany);
				pause(sc);
				continue;

			case "2" :
				int offset = 0;
				int nbComputer =servcomputer.daoGetNbComputer();
				do {
					ArrayList<Computer> tableComputer= servcomputer.daoGetPage(offset);
					afficheListComputer(tableComputer);
					offset = optionPage(sc,offset,nbComputer);
				}while(!choix.equals("quit"));
				continue;

			case "3" :
				afficheFindbyId(sc,servcomputer);
				pause(sc);
				continue;
			case "4" :
				affichageAjoutOrdinateur(sc,servcomputer);
				pause(sc);
				continue;
			case "5":

			case "6" :
				affichageSupprOrdinateur(sc,servcomputer);
				pause(sc);
				continue;
			case "exit":
				System.out.println(" Bye ...");
				continue;
			default : 
				System.out.println(" L'option que vous avez choisie n'est pas dans la liste de choix ");
				pause(sc);
				clearConsole(20);
			}


		}while(!choix.equals("exit"));
	}

	/**
	 * 										=============	AFFICHE DE LA LISTE DES COMPUTER	=============
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
	 * 										=============	AFFICHE LA LISTE DES COMPANY 	=============
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
	 *  										=============	AFFICHAGE DU RESULTATS RECHERCHE PAR ID 	=============
	 * @param sc
	 * @param servcomputer
	 */
	public static void afficheFindbyId(Scanner sc, ServiceComputer servcomputer) {
		System.out.println("Veullez saisir l' id de l'ordinateur ");
		int id = sc.nextInt();
		sc.nextLine();
		Optional<Computer> opcomputer = servcomputer.daoFindById(id)	;
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
			System.out.println("Aucun Computer n'a été trouver avec cet id = "+ id);
		}

	}


	/**
	 * 										=============	AFFICHAGE DU MENU D'AJOUT	D'UN ORDINATEUR	 	=============
	 * @param sc
	 * @param servcomputer
	 */
	public static void affichageAjoutOrdinateur(Scanner sc, ServiceComputer servcomputer) {
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir le nom de nouveau ordinateur: ");
		String name = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez l'année la date d'introduction du nouveau ordinateur: ");
		String year = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez le mois la date d'introduction du nouveau ordinateur: ");
		String month = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez le jour la date d'introduction du nouveau ordinateur: ");
		String day = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez l'année la date de retrait du nouveau ordinateur: ");
		String yeardisc = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez le mois la date de retrait du nouveau ordinateur: ");
		String monthdisc = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Entrez le jour la date de retrait du nouveau ordinateur: ");
		String daydisc = sc.nextLine();
		afficheMenu(" AJOUT D'UN ORDINATEUR");
		System.out.println("Veuillez saisir l'id de la company : ");
		Long company_id = sc.nextLong();
		sc.nextLine();

		if( Integer.parseInt(year)>Integer.parseInt(yeardisc) ) {
			System.out.println(" Date de discontinued est inferieur "+  Integer.parseInt(year)+" > "+ Integer.parseInt(yeardisc) );
			return;
		}
		String date_introduced =year+"-"+month+"-"+day;
		String date_discontinued = yeardisc+"-"+monthdisc+"-"+daydisc;
		ajoutOrdinateur(servcomputer,name,date_introduced,date_discontinued,company_id);
	}




	/**
	 * 										=============	GESTION DU CLI D'AJOUT	D'UN ORDINATEUR	 	=============
	 * @param servcomputer
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param company_id
	 */
	public static void ajoutOrdinateur(ServiceComputer servcomputer,String name, String string_date_introduced, String string_date_discontinued,Long company_id) {
		Computer computer= new Computer();
		computer.setName(name);

		if(string_date_introduced.isEmpty()) {
			computer.setIntroduced(Timestamp.valueOf(LocalDateTime.now()));
		}
		else{
			LocalDate date_introduced=LocalDate.parse(string_date_introduced);
			computer.setIntroduced(convertLocalDatetoTimestamp(date_introduced));
		}

		if(string_date_discontinued.isEmpty()) {
			LocalDate date_discontinued = LocalDate.parse("0000-00-00");
			computer.setDiscontinued(convertLocalDatetoTimestamp(date_discontinued));
		}
		else {
			LocalDate date_discontinued = LocalDate.parse(string_date_discontinued);
			computer.setDiscontinued(convertLocalDatetoTimestamp(date_discontinued));
		}
		computer.setCompany_id(company_id);
		servcomputer.daoCreate(computer);
	}

	/**
	 * 										=============	AFFICHAGE DU MENU SUPPR. D'UN ORDINATEUR	 	=============
	 * @param sc
	 * @param servcomputer
	 */
	public static void affichageSupprOrdinateur(Scanner sc, ServiceComputer servcomputer) {
		afficheMenu("SUPPR . D'UN ORDINATEUR");
		System.out.println("Veuillez saisir l'id de ordinateur que vous souhaiter suppr.: ");
		Long id = sc.nextLong();
		sc.nextLine();
		Computer computer = new Computer();
		computer.setId(id);
		servcomputer.daoDelete(computer);
	}

	/**	
	 *										=============	CONVERSITION LOCALDATE TO TIMESTAMP	 	=============
	 * @param stringDate
	 * @return
	 */
	public static Timestamp convertLocalDatetoTimestamp(LocalDate Date) {	
		Timestamp timestamp = Timestamp.valueOf(Date.atStartOfDay());
		return timestamp;
	}

	/**
	 *  										=============	AFFICHAGE DU MENU		=============
	 */
	public static void printMenu() {
		afficheMenu(" M E N U ");
		System.out.println();
		System.out.println("1) Afficher listes des compagnies				2) Afficher listes des ordinateurs ");
		System.out.println("3) Affiches Les detail Ordinateur				4) Ajouter un ordinateur ");
		System.out.println("5) mise a jour d'un ordinateur					6) Supprimer un ordinateur ");
		System.out.println("Saisir votre choix : ");
	}
	/**
	 * 										=============	AFFICHAGE DE PAUSE 	=============
	 * @param sc
	 */
	public static void pause(Scanner sc) {
		System.out.println("Appuyer sur entree pour continuer .....");
		sc.nextLine();
		clearConsole(20);
	}
	/**
	 * 										=============	AFFICHAGE D'OPTION DE LA PAGE	 	=============
	 * @param sc
	 * @param offset
	 * @return
	 */
	public static int optionPage(Scanner sc,int offset, int nbComputer) {
		int pagecourant = (offset/10)+1;
		int totalPage = nbComputer/10;

		System.out.println("					"+pagecourant+"/"+totalPage+"			");
		System.out.println("			quit : for exit			enter: for continue	");
		choix =sc.nextLine();

		if(!choix.equals("quit")) {
			if(pagecourant<totalPage) {
				offset+=10;
			}
		}
		clearConsole(20);
		return offset;
	}
	/**
	 * 										=============	AFFICHAGE DU MENU D'AJOUT	D'UN ORDINATEUR	 	=============
	 * @param l
	 */
	public final static void clearConsole(int l){
		for(int clear = 0; clear < l; clear++)
		{
			System.out.println() ;
		}
	}
	/**
	  										=============	AFFICHAGE DES ENTETES DE MENU	 	=============
	 * @param menu
	 */
	public static void afficheMenu(String menu) {
		clearConsole(20);
		System.out.println(" 					========= "+menu+" ===========					");
		clearConsole(3);
	}
}
