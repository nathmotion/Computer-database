package com.excilys.java.IHM;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.service.ServiceCompany;
import com.excilys.java.cdb.service.ServiceComputer;

public class Testdb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServiceCompany servcompany = ServiceCompany.INSTANCE;
		ServiceComputer servcomputer = ServiceComputer.INSTANCE;
		
		//ArrayList<Company>  tableCompany= servcompany.getDao().getAll();
		Computer test= new Computer();
		test.setName("Ordi nath");
		test.setIntroduced(Timestamp.valueOf(LocalDateTime.now()));
		test.setDiscontinued(Timestamp.valueOf(LocalDateTime.now()));
		test.setCompany_id((long) 1);
		servcomputer.getDao().create(test);
		ArrayList<Computer> tableComputer= servcomputer.getDao().getAll();
	
		
		// affihce le contenu de list de compagnie
		/*tableCompany.forEach((company ->{
		System.out.println();	
		System.out.println("id: "+company.getId());
		System.out.println("name: "+company.getName());
		System.out.println();	
		}));*/
		
		tableComputer.forEach((computer ->{
			System.out.println();	
			System.out.println("id: "+computer.getId());
			System.out.println("name: "+computer.getName());
			System.out.println("introduced: "+computer.getIntroduced());
			System.out.println("discontinued: "+computer.getDiscontinued());
			System.out.println("company_id: "+computer.getCompany_id());
			System.out.println();	
			}));
}

}
