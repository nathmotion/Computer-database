package com.excilys.java.IHM;

import java.io.Console;
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
		/*
		 * 
		 * 			C L I 
		 * 
		 * */
		Scanner sc =  new Scanner(System.in);
		Console console= System.console();
		do {	
			//choix=null;
			System.out.println("") ;
			System.out.println("") ;
			System.out.println("") ;
			System.out.println("1) Afficher listes des compagnies				2) Afficher listes des ordinateurs ");
			
			System.out.println("3) Affiches Les detail Ordinateur				4) Ajouter un ordinateur ");
			
			System.out.println("5) mise a jour d'un ordinateur					6) Supprimer d'un ordinateur ");
			System.out.println();
			System.out.println("Saisir votre choix : ");
			choix = sc.nextLine();
	    
			switch(choix){
			case "1": 
				ArrayList<Company>  tableCompany= servcompany.getDao().getAll();
				tableCompany.forEach((company ->{
					System.out.println();	
					System.out.println("id: "+company.getId());
					System.out.println("name: "+company.getName());
					System.out.println();	
				}));
				continue;
			case "2" :
				// affihce le contenu de list de compagnie
	
				ArrayList<Computer> tableComputer= servcomputer.getDao().getAll();
				tableComputer.forEach((computer ->{
					System.out.println();	
					System.out.println("id: "+computer.getId());
					System.out.println("name: "+computer.getName());
					System.out.println();	
					}));
				break;
			case "3" :
				System.out.println("") ;
				System.out.println("") ;
				System.out.println("") ;
				System.out.println("") ;
				System.out.println("") ;
				System.out.println("") ;
				System.out.println("Veullez saisir l' id de l'ordinateur ");
				System.out.println();
				Long id = sc.nextLong();
				Optional<Computer> opcomputer = servcomputer.getDao().findById(id)	;
				if(opcomputer.isPresent())
				{
					Computer computer = opcomputer.get();
					System.out.println();	
					System.out.println("id: "+computer.getId());
					System.out.println("name: "+computer.getName());
					System.out.println("introduced: "+computer.getIntroduced());
					System.out.println("discontinued: "+computer.getDiscontinued());
					System.out.println("company_id: "+computer.getCompany_id());
					System.out.println();	
				}
				else {
					//System.out.println(" error aucun computer trouver");
				}
				break;
			}
		
	
	}while(!choix.equals("exit"));
	
	//	console.readLine();
		
		
}

}
