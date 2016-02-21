package ioffice.br.pageflow.administration.datamodels;

import ioffice.br.pageflow.common.datamodels.AbstractDataObject;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.enums.DomainObjectType;

public class AuditLogListDataObject extends AbstractDataObject {

	private AuditActionType actionType;
	private DomainObjectType domainObjectType;
	private String actionCode;
	private String domainObjectCode;
	private Long objectId;
	private Long actionId;
	private String eventDate;
	private String eventUser;
	private String comments;
	private Long poid;
	private Long coid;

	public AuditLogListDataObject() {
	};

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEventUser() {
		return eventUser;
	}

	public void setEventUser(String eventUser) {
		this.eventUser = eventUser;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
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

	public AuditActionType getActionType() {
		return actionType;
	}

	public void setActionType(AuditActionType actionType) {
		this.actionType = actionType;
	}

	public DomainObjectType getDomainObjectType() {
		return domainObjectType;
	}

	public void setDomainObjectType(DomainObjectType domainObjectType) {
		this.domainObjectType = domainObjectType;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getDomainObjectCode() {
		return domainObjectCode;
	}

	public void setDomainObjectCode(String domainObjectCode) {
		this.domainObjectCode = domainObjectCode;
	}
}
