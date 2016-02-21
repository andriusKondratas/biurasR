package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import ioffice.br.pageflow.administration.datamodels.AuditLogListDataObject;
import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.pageflow.common.datamodels.DefaultDataModel;
import ioffice.br.persistance.service.administration.AdministrationObjectCache;
import ioffice.br.persistance.service.administration.AuditLogService;

import org.primefaces.component.api.UIColumn;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "auditLogViewMB")
@ViewScoped
public class AuditLogViewBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AuditLogService}")
	AuditLogService auditLogService;

	@ManagedProperty(value = "#{AdministrationObjectCache}")
	AdministrationObjectCache administrationObjectCache;

	DefaultDataModel<AuditLogListDataObject> auditLogListModel;

	List<AuditLogListDataObject> filteredAuditLogEntries;

	List<SortMeta> preSortOrder;

	private String objectId;
	private String domainObjectCode;

	public void init() {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent column = viewRoot.findComponent("auditLogViewForm:auditLogTable:eventDate");

		SortMeta sm1 = new SortMeta();
		sm1.setSortBy((UIColumn) column);
		sm1.setSortField("eventDate");
		sm1.setSortOrder(SortOrder.DESCENDING);
		preSortOrder = new ArrayList<SortMeta>();
		preSortOrder.add(sm1);
	}

	public void loadAuditLog() {
		if (this.objectId != null && domainObjectCode != null) {
			List<AuditLogListDataObject> auditLog = auditLogService.findByObjectIdAndObjectType(Long.valueOf(this.objectId), administrationObjectCache
					.getDomainObjectByCode(domainObjectCode).getId());
			auditLogListModel = new DefaultDataModel<AuditLogListDataObject>(auditLog);
		} else {
			auditLogListModel = new DefaultDataModel<AuditLogListDataObject>(Collections.emptyList());
		}
	}

	public AuditLogService getAuditLogService() {
		return auditLogService;
	}

	public void setAuditLogService(AuditLogService auditLogService) {
		this.auditLogService = auditLogService;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public DefaultDataModel<AuditLogListDataObject> getAuditLogListModel() {
		return auditLogListModel;
	}

	public void setAuditLogListModel(DefaultDataModel<AuditLogListDataObject> auditLogListModel) {
		this.auditLogListModel = auditLogListModel;
	}

	public List<AuditLogListDataObject> getFilteredAuditLogEntries() {
		return filteredAuditLogEntries;
	}

	public void setFilteredAuditLogEntries(List<AuditLogListDataObject> filteredAuditLogEntries) {
		this.filteredAuditLogEntries = filteredAuditLogEntries;
	}

	public AdministrationObjectCache getAdministrationObjectCache() {
		return administrationObjectCache;
	}

	public void setAdministrationObjectCache(AdministrationObjectCache administrationObjectCache) {
		this.administrationObjectCache = administrationObjectCache;
	}

	public String getDomainObjectCode() {
		return domainObjectCode;
	}

	public void setDomainObjectCode(String domainObjectCode) {
		this.domainObjectCode = domainObjectCode;
	}

	public List<SortMeta> getPreSortOrder() {
		return preSortOrder;
	}

	public void setPreSortOrder(List<SortMeta> preSortOrder) {
		this.preSortOrder = preSortOrder;
	}
}
