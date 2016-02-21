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
import ioffice.br.persistance.model.core.AuditableEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "ADM_PROFILE")
@SequenceGenerator(name = "default_gen", sequenceName = "profile_seq", allocationSize = 1)
public class UserProfile extends AuditableEntity {

	@Column(length=50)
	private String name;
	
	
	@Column(length=50)
	private String blka;

}
