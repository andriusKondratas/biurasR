package ioffice.br.persistance.model.administration;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ioffice.br.persistance.model.core.AbstractEntity;

@Entity
@Table(name = "ADM_AUDITLOG", indexes = { @Index(columnList = "user_id", name = "audit_user_id_fk_idx"),
		@Index(columnList = "domain_object_id", name = "audit_domain_object_id_idx"), @Index(columnList = "audit_action_id", name = "audit_audit_action_id_idx"),
		@Index(columnList = "poid", name = "audit_poid_idx"), @Index(columnList = "coid", name = "audit_coid_idx") })
@SequenceGenerator(name = "default_gen", sequenceName = "audit_log_seq", allocationSize = 1)
public class AuditLog extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AUDIT_ACTION_ID")
	private AuditAction auditAction;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DOMAIN_OBJECT_ID")
	private DomainObject domainObject;

	@NotNull
	private Date eventDate;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;
	@NotNull
	private String comments;

	private Long poid;
	private Long coid;

	public AuditAction getAuditAction() {
		return auditAction;
	}

	public void setAuditAction(AuditAction auditAction) {
		this.auditAction = auditAction;
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getPoid() {
		return poid;
	}

	public void setPoid(Long poid) {
		this.poid = poid;
	}

	public Long getCoid() {
		return coid;
	}

	public void setCoid(Long coid) {
		this.coid = coid;
	}
}
