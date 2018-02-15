package com.excilys.java.IHM;

import java.io.Console;
import java.io.IOException;
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
				continue;
				
			case "2" :
				ArrayList<Computer> tableComputer= servcomputer.getDao().getAll();
				afficheListComputer(tableComputer);
				continue;
				
			case "3" :

				
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
	
	public void afficheFindbyId(Scanner sc, ServiceComputer servcomputer) {
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
		for(int clear = 0; clear < 20; clear++)
		  {
		     System.out.println() ;
		  }
	}

}
