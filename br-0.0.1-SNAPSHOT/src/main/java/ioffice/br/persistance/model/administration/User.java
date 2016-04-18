package ioffice.br.persistance.model.administration;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.core.AbstractEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "ADM_USER")
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User extends AbstractEntity {

	@Email
	@Column(unique = true, nullable=false, length=50)
	private String email;

	@Column(nullable=false, length=50)
	private String name;

	@Column(nullable=false, length=50)
	private String lastname;

	@Column(nullable=false, length=255)
	private String password;

	@Pattern(regexp = "(^[+]?[0-9]{10,13}$|^$)")
	@Column(length=50)
	private String phoneNumber;
	
	@Column(length=20)
	private String fidesId;

	@Column(length=100)
	private String jobTitle;

	@Column(columnDefinition="number(1,0) default 0 not null")
	private boolean active;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "ADM_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private List<Role> roles;

	public User() {
		this.domainObjectType = DomainObjectType.USER;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFidesId() {
		return fidesId;
	}

	public void setFidesId(String fidesId) {
		this.fidesId = fidesId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean isActive) {
		this.active = isActive;
	}
	
}
