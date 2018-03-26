package main.java.excilys.cdb.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {

	}

	public Computer(Long id, String name, Timestamp introduction, Timestamp discontinuation, Company company_id) {
		this.id = id;
		this.name = name;
		if (introduction == null) {
			this.introduced = null;
		} else {
			this.introduced = introduction.toLocalDateTime().toLocalDate();
		}
		if (discontinued == null) {
			this.discontinued = null;
		} else {
			this.discontinued = discontinuation.toLocalDateTime().toLocalDate();
		}
		this.company = company_id;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company_id) {
		this.company = company_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduction) {
		this.introduced = introduction;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinuation) {
		discontinued = discontinuation;
	}

	@Override
	public String toString() {
		return "\nComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company_id=" + company + "] \n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
