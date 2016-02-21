package ioffice.br.persistance.model.administration;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.core.AuditableEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "ADM_ROLE")
@SequenceGenerator(name = "default_gen", sequenceName = "role_seq", allocationSize = 1)
public class Role extends AuditableEntity {

	@Column(unique = true, nullable=false, length=50)
	private String roleTitle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "role")
    @Fetch(FetchMode.SUBSELECT)
	private List<RolePriviledge> rolePriviledges;

	public Role() {
		this.domainObjectType = DomainObjectType.ROLE;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	public List<RolePriviledge> getRolePriviledges() {
		return rolePriviledges;
	}

	public void setRolePriviledges(List<RolePriviledge> rolePriviledges) {
		this.rolePriviledges = rolePriviledges;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
}
