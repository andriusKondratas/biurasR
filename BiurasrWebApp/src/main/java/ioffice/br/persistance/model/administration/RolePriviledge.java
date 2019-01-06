package ioffice.br.persistance.model.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import ioffice.br.persistance.enums.PriviledgeType;
import ioffice.br.persistance.model.core.AbstractEntity;

@Entity
@Table(name = "ADM_ROLE_PRIVILEDGE")
@SequenceGenerator(name = "default_gen", sequenceName = "role_priviledge_seq", allocationSize = 1)
public class RolePriviledge extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DOMAIN_OBJECT_ID", nullable=false)
	private DomainObject domainObject;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID", nullable=false)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PRIV_CODE", nullable=false, length=50)
	private PriviledgeType priviledgeTypeCode;
	
	@Column(columnDefinition="number(1,0) default 0 not null")
	private boolean active;
	
	@Transient
	private String name;
	@Transient
	private String styleClass;

	public RolePriviledge() {
	}
	
	public RolePriviledge(String name, String styleClass) {
		this.name = name;
		this.styleClass = styleClass;
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PriviledgeType getPriviledgeTypeCode() {
		return priviledgeTypeCode;
	}

	public void setPriviledgeTypeCode(PriviledgeType priviledgeTypeCode) {
		this.priviledgeTypeCode = priviledgeTypeCode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
}
