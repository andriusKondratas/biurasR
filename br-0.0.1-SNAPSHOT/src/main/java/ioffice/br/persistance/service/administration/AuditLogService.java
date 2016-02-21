package ioffice.br.persistance.service.administration;

import java.util.Date;
import java.util.List;

import ioffice.br.pageflow.administration.datamodels.AuditLogListDataObject;
import ioffice.br.persistance.dao.administration.AuditLogDao;
import ioffice.br.persistance.model.administration.AuditAction;
import ioffice.br.persistance.model.administration.AuditLog;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AuditLogService")
@Transactional(readOnly = false)
@Scope("singleton")
public class AuditLogService extends GenericService<AuditLog> {

	@Autowired
	UserService userService;
	
	@Autowired
	public AuditLogService(AuditLogDao dao) {
		super(dao);
	}

	public List<AuditLogListDataObject> findByObjectIdAndObjectType(Long objectId, Long objTypeId) {
		return ((AuditLogDao) this.dao).findByObjectIdAndObjectType(objectId, objTypeId);
	}

	public void logAuditEvent(DomainObject domainObject, AuditAction auditAction, Long oldObjId, Long newObjId, String actionComments) {
		// TODO sanityCheck
		AuditLog logEntry = new AuditLog();
		logEntry.setAuditAction(auditAction);
		logEntry.setComments(actionComments);
		logEntry.setEventDate(new Date());
		logEntry.setPoid(oldObjId);
		logEntry.setCoid(newObjId);
		// TODO from cache
		logEntry.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		logEntry.setDomainObject(domainObject);

		this.saveOrUpdate(logEntry);
	}

}
