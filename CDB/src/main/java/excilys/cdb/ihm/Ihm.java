package main.java.excilys.cdb.ihm;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.excilys.cdb.model.Company;
import main.java.excilys.cdb.model.Computer;
import main.java.excilys.cdb.model.Page;
import main.java.excilys.cdb.service.ServiceCompany;
import main.java.excilys.cdb.service.ServiceComputer;

public class Ihm {
	final static Logger LOGGER = LogManager.getLogger(Ihm.class);
	ServiceCompany servCompany = ServiceCompany.INSTANCE;
	ServiceComputer servComputer = ServiceComputer.INSTANCE;
	Scanner sc = new Scanner(System.in);
	static String choix;

	public static void main(String[] args) {
		Ihm myCLI = new Ihm();
		myCLI.startCLI();
	}

	/**
	 * ================ C L I ================
	 */
	public void startCLI() {

		do {
			printMenu();
			choix = sc.nextLine();
			switch (choix) {
			case "1":
				ArrayList<Company> tableCompany = servCompany.getAllEntities();
				LOGGER.debug(" salut");
				afficheListCompany(tableCompany);
				pause(sc);
				continue;

			case "2":
				int offset = 0;
				int nbComputer = servComputer.getNbComputer();
				do {
					Page<Computer> pageComputer = new Page<Computer>(offset, 10);
					pageComputer = servComputer.getPage(pageComputer);
					afficheListComputer(pageComputer);
					offset = optionPage(sc, offset, nbComputer);
				} while (!choix.equals("quit"));
				continue;

			case "3":
				afficheFindbyId(sc, servComputer);
				pause(sc);
				continue;

			case "4":
				affichageAjoutOrdinateur(sc, servComputer);
				pause(sc);
				continue;

			case "5":
				affichageMiseAjourOrdinateur(sc, servComputer);
				pause(sc);
				continue;

			case "6":
				affichageSupprOrdinateur(sc, servComputer);
				pause(sc);
				continue;
			case "7":
				affichageSupprCompany(sc, servCompany);
				pause(sc);
				continue;
			case "exit":
				System.out.println(" Bye ...");
				continue;

			default:
				System.out.println(" L'option que vous avez choisie n'est pas dans la liste de choix ");
				pause(sc);
				clearConsole(20);
			}

		} while (!choix.equals("exit"));
	}

	/**
	 * ============= AFFICHE DE LA LISTE DES COMPUTER =============
	 * 
	 * @param tableComputer
	 */
	public static void afficheListComputer(Page<Computer> page) {
		clearConsole(20);
		afficheMenu(" LISTE DES ORDINATEURS ");
		page.getElementsPage().forEach((computer -> {
			System.out.println("id: " + computer.getId());
			System.out.println("name: " + computer.getName());
			System.out.println(" nom company :" + computer.getCompany().getName());
		}));
	}

	/**
	 * ============= AFFICHE LA LISTE DES COMPANY =============
	 * 
	 * @param tableCompany
	 */
	public static void afficheListCompany(ArrayList<Company> tableCompany) {
		clearConsole(20);
		afficheMenu(" LISTE DES COMPANY ");
		tableCompany.forEach((company -> {
			System.out.println("id: " + company.getId());
			System.out.println("name: " + company.getName());
		}));
	}

	/**
	 * ============= AFFICHAGE DU RESULTATS RECHERCHE PAR ID =============
	 * 
	 * @param sc
	 * @param servComputer
	 */
	public static void afficheFindbyId(Scanner sc, ServiceComputer servComputer) {
		System.out.println("Veullez saisir l' id de l'ordinateur ");
		int id = sc.nextInt();
		sc.nextLine();
		Optional<Computer> opcomputer = servComputer.findById(id);

		if (opcomputer.isPresent()) {
			Computer computer = opcomputer.get();
			System.out.println("id: " + computer.getId());
			System.out.println("name: " + computer.getName());
			System.out.println("introduced: " + computer.getIntroduced());
			System.out.println("discontinued: " + computer.getDiscontinued());
			System.out.println("company_id: " + computer.getCompany().getId());
		} else {
			System.out.println("Aucun Computer n'a été trouver avec cet id = " + id);
		}
	}

	/**
	 * ============= AFFICHAGE DU MENU DE MISE JOUR D'UN ORDINATEUR =============
	 * 
	 * @param sc
	 * @param servcomputer
	 */
	public static void affichageMiseAjourOrdinateur(Scanner sc, ServiceComputer servcomputer) {
		afficheMenu(" MISE A JOUR D'UN ORDINATEUR");
		System.out.println("Entrez l'id de l'ordinateur a mettre a jour : ");
		int id = sc.nextInt();
		sc.nextLine();
		Optional<Computer> optcomputer = servcomputer.findById(id);
		if (optcomputer.isPresent()) {
			Computer computer = optcomputer.get();
			System.out.println("id: " + computer.getId());
			System.out.println("name: " + computer.getName());
			System.out.println("introduced: " + computer.getIntroduced());
			System.out.println("discontinued: " + computer.getDiscontinued());
			System.out.println("company_id: " + computer.getCompany().getId());
			miseajourOrdinateur(servcomputer, sc, computer);
		} else {
			System.out.println("Aucun Computer n'a été trouver avec cet id = " + id);
		}
	}

	/**
	 * ============= GESTION DU CLI D'AJOUT D'UN ORDINATEUR =============
	 * 
	 * @param servComputer
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param company_id
	 */
	public static void miseajourOrdinateur(ServiceComputer servComputer, Scanner sc, Computer computer) {
		System.out.println("Que voulez vous modifier  : ");
		System.out.println("	1)Name		2)Date Introduced		3)Date Discontinued		4)Company id");
		String choice = sc.nextLine();

		do {
			switch (choice) {

			case "1":
				System.out.println("Entrez le nouveau name de l'ordinateur :");
				choice = sc.nextLine();
				computer.setName(choice);
				servComputer.update(computer);

			case "2":
				System.out.println("Entrez l'année la date d'introduction du nouveau ordinateur: ");
				String year = sc.nextLine();
				System.out.println("Entrez le mois la date d'introduction du nouveau ordinateur: ");
				String month = sc.nextLine();
				System.out.println("Entrez le jour la date d'introduction du nouveau ordinateur: ");
				String day = sc.nextLine();
				String string_date_introduced = year + "-" + month + "-" + day;

				try {
					LocalDate date_introduced = LocalDate.parse(string_date_introduced);
					if (date_introduced.getYear() < (int) 1970) {
						System.out.println(" Une des Dates saissies est inferieur a 1970  ");
						choix = "0";
						break;
					}
					computer.setIntroduced(convertLocalDatetoTimestamp(date_introduced));
					servComputer.update(computer);
				} catch (DateTimeParseException e) {
					LOGGER.error(" le format de la date introduced saisie est mauvais ou ce sont pas des nombres  ");
				}
				continue;

			case "3":
				System.out.println("Entrez l'année la date d'introduction du nouveau ordinateur: ");
				String yeardisc = sc.nextLine();
				System.out.println("Entrez le mois la date d'introduction du nouveau ordinateur: ");
				String monthdisc = sc.nextLine();
				System.out.println("Entrez le jour la date d'introduction du nouveau ordinateur: ");
				String daydisc = sc.nextLine();
				String string_date_discontinued = yeardisc + "-" + monthdisc + "-" + daydisc;

				try {
					LocalDate date_discontinued = LocalDate.parse(string_date_discontinued);
					computer.setIntroduced(convertLocalDatetoTimestamp(date_discontinued));
					servComputer.update(computer);
					if (date_discontinued.getYear() < (int) 1970) {
						System.out.println(" Une des Dates saissies est inferieur a 1970  ");
						choix = "0";
						break;
					}
				} catch (DateTimeParseException e) {
					LOGGER.error(" le format de la date discontinued saisie est mauvais ou ce sont pas des nombres  ");
				}
				continue;

			case "4":
				System.out.println("Entrez le nouveau id de l'ordinateur :");
				Long id = sc.nextLong();
				sc.nextLine();
				computer.getCompany().setId(id);
				;
				servComputer.update(computer);
				continue;

			default:
				System.out.println(" commande saissie incomprehensible ");
			}
		} while (!choice.equals("1") || !choice.equals("2") || !choice.equals("3") || !choice.equals("4"));
	}

	/**
	 * ============= AFFICHAGE DU MENU D'AJOUT D'UN ORDINATEUR =============
	 * 
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

		try {
			if (!yeardisc.equals("")) {
				if (Integer.parseInt(year) > Integer.parseInt(yeardisc)) {
					System.out.println(" Date de discontinued est inferieur " + Integer.parseInt(year) + " > "
							+ Integer.parseInt(yeardisc));
					return;
				}
				if (Integer.parseInt(yeardisc) < 1970) {
					System.out.println(" Une des Dates saissies est inferieur a 1970  ");
					return;
				}
			}
			if (Integer.parseInt(year) < 1970) {
				System.out.println(" Une des Dates saissies est inferieur a 1970  ");
				return;
			}
		} catch (NumberFormatException e) {
			LOGGER.error("Année saisie n'est pas du bon format ou pas un nombre");
			return;
		}
		String date_introduced = year + "-" + month + "-" + day;
		String date_discontinued = yeardisc + "-" + monthdisc + "-" + daydisc;
		ajoutOrdinateur(servcomputer, name, date_introduced, date_discontinued, company_id);
	}

	/**
	 * ============= GESTION DU CLI D'AJOUT D'UN ORDINATEUR =============
	 * 
	 * @param servComputer
	 * @param name
	 * @param date_introduced
	 * @param date_discontinued
	 * @param company_id
	 */
	public static void ajoutOrdinateur(ServiceComputer servComputer, String name, String string_date_introduced,
			String string_date_discontinued, Long company_id) {
		Computer computer = new Computer();
		computer.setName(name);

		if (string_date_introduced.equals("--")) {
			computer.setIntroduced(Timestamp.valueOf(LocalDateTime.now()));
		} else {
			try {
				LocalDate date_introduced = LocalDate.parse(string_date_introduced);
				computer.setIntroduced(convertLocalDatetoTimestamp(date_introduced));
			} catch (DateTimeParseException e) {
				LOGGER.error(" le format de la date continued saisie est mauvais ou ce sont pas des nombres  ");
			}
		}

		if (string_date_discontinued.equals("--")) {
			computer.setDiscontinued(null);
		} else {
			try {
				LocalDate date_discontinued = LocalDate.parse(string_date_discontinued);
				computer.setDiscontinued(convertLocalDatetoTimestamp(date_discontinued));
			} catch (DateTimeParseException e) {
				LOGGER.error(" le format de la date discontinued saisie est mauvais ou ce sont pas des nombres  ");
			}
		}
		computer.getCompany().setId(company_id);
		;
		servComputer.create(computer);
	}

	/**
	 * ============= AFFICHAGE DU MENU SUPPR. D'UN COMPANY =============
	 * 
	 * @param sc
	 * @param servComputer
	 */
	public static void affichageSupprCompany(Scanner sc, ServiceCompany servCompany) {
		afficheMenu("SUPPR . D'UNE COMPANIE ");
		System.out.println("Veuillez saisir l'id de la companie que vous souhaiter suppr.: ");
		Long id = sc.nextLong();
		sc.nextLine();
		Company company = new Company();
		company.setId(id);
		servCompany.delete(company);
	}

	/**
	 * ============= AFFICHAGE DU MENU SUPPR. D'UN ORDINATEUR =============
	 * 
	 * @param sc
	 * @param servComputer
	 */
	public static void affichageSupprOrdinateur(Scanner sc, ServiceComputer servComputer) {
		afficheMenu("SUPPR . D'UN ORDINATEUR");
		System.out.println("Veuillez saisir l'id de ordinateur que vous souhaiter suppr.: ");
		Long id = sc.nextLong();
		sc.nextLine();
		Computer computer = new Computer();
		computer.setId(id);
		servComputer.delete(computer);
	}

	/**
	 * ============= CONVERSITION LOCALDATE TO TIMESTAMP =============
	 * 
	 * @param stringDate
	 * @return
	 */
	public static Timestamp convertLocalDatetoTimestamp(LocalDate Date) {
		Timestamp timestamp = Timestamp.valueOf(Date.atStartOfDay());
		return timestamp;
	}

	/**
	 * ============= AFFICHAGE DU MENU =============
	 */
	public static void printMenu() {
		afficheMenu(" M E N U ");
		System.out.println();
		System.out.println("1) Afficher liste des compagnies				2) Afficher liste des ordinateurs ");
		System.out.println("3) Affiches les détails Ordinateur				4) Ajouter un ordinateur ");
		System.out.println("5) Mise à jour d'un ordinateur					6) Supprimer un ordinateur ");
		System.out.println("7) Supprimer une compagnie");
		System.out.println("Saisir votre choix : ");
	}

	/**
	 * ============= AFFICHAGE DE PAUSE =============
	 * 
	 * @param sc
	 */
	public static void pause(Scanner sc) {
		System.out.println("Appuyer sur entree pour continuer .....");
		sc.nextLine();
		clearConsole(20);
	}

	/**
	 * ============= AFFICHAGE D'OPTION DE LA PAGE =============
	 * 
	 * @param sc
	 * @param offset
	 * @return
	 */
	public static int optionPage(Scanner sc, int offset, int nbComputer) {
		int pagecourant = (offset / 10) + 1;
		int totalPage = nbComputer / 10;

		System.out.println("					" + pagecourant + "/" + totalPage + "			");
		System.out.println("			quit : for exit			enter: for continue	");
		choix = sc.nextLine();

		if (!choix.equals("quit")) {
			if (pagecourant < totalPage) {
				offset += 10;
			}
		}
		clearConsole(20);
		return offset;
	}

	/**
	 * ============= AFFICHAGE DU MENU D'AJOUT D'UN ORDINATEUR =============
	 * 
	 * @param l
	 */
	public final static void clearConsole(int l) {

		for (int clear = 0; clear < l; clear++) {
			System.out.println();
		}
	}

	/**
	 * ============= AFFICHAGE DES ENTETES DE MENU =============
	 * 
	 * @param menu
	 */
	public static void afficheMenu(String menu) {
		clearConsole(20);
		System.out.println(" 					========= " + menu + " ===========					");
		clearConsole(3);
	}
}
