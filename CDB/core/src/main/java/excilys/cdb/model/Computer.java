package main.java.excilys.cdb.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "introduced")
	private Timestamp introduced;

	@Column(name = "discontinued")
	private Timestamp discontinued;

	@ManyToOne
	@PrimaryKeyJoinColumn
	private Company company;

	public Computer() {

	}

	public Computer(Long id, String name, Timestamp introduction, Timestamp discontinuation, Company company_id) {
		this.id = id;
		this.name = name;
		if (introduction == null) {
			this.introduced = null;
		} else {
			this.introduced = introduction;
		}
		if (discontinued == null) {
			this.discontinued = null;
		} else {
			this.discontinued = discontinuation;
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
		if (introduced != null) {
			return introduced.toLocalDateTime().toLocalDate();
		} else {
			return null;
		}
	}

	public void setIntroduced(LocalDate introduction) {
		if (introduction != null) {
			this.introduced = Timestamp.valueOf(introduction.atStartOfDay());
		} else {
			this.introduced=null;
		}
	}

	public LocalDate getDiscontinued() {
		if (discontinued != null) {
			return discontinued.toLocalDateTime().toLocalDate();
		}
		return null;
	}

	public void setDiscontinued(LocalDate discontinuation) {
		if (discontinuation != null) {
			discontinued = Timestamp.valueOf(discontinuation.atStartOfDay());
		} else {
			discontinued = null;
		}
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
