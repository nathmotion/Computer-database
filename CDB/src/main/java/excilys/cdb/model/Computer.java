package main.java.excilys.cdb.model;

import java.sql.Timestamp;

public class Computer {

	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Long companyId;

	public Computer() {

	}

	public Computer(Long id, String name, Timestamp introduction, Timestamp discontinuation,Long company_id) {
		this.id=id;
		this.name = name;
		this.introduced = introduction;
		this.discontinued = discontinuation;
		this.companyId=company_id;

	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCompany_id() {
		return companyId;
	}


	public void setCompany_id(Long company_id) {
		this.companyId = company_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Timestamp introduction) {
		this.introduced = introduction;
	}


	public Timestamp getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Timestamp discontinuation) {
		discontinued = discontinuation;
	}

	@Override
	public String toString() {
		return "\nComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + companyId + "] \n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
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
