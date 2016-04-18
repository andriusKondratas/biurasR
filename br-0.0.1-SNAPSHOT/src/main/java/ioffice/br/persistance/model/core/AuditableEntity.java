package ioffice.br.persistance.model.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import ioffice.br.persistance.model.administration.User;

@MappedSuperclass
public abstract class AuditableEntity extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_CREATED_ID", columnDefinition="number(19) default 0 not null")
	private User createdBy;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_MODIFIED_ID")
	private User modifiedBy;		
	
	@Column(name = "DATE_CREATED", nullable=false, columnDefinition = "timestamp(6) default sysdate")
	private Date dateCreated;	
	
	@Column(name = "DATE_MODIFIED")
	private Date dateModified;		

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
}
