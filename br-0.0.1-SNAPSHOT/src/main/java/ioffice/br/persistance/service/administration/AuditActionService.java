package ioffice.br.persistance.service.administration;

import ioffice.br.persistance.dao.administration.AuditActionDao;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.model.administration.AuditAction;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ActionService")
@Transactional(readOnly = false)
@Scope("singleton")
public class AuditActionService extends GenericService<AuditAction> {

	@Autowired
	public AuditActionService(AuditActionDao dao) {
		super(dao);
	}

	public AuditAction findByType(AuditActionType type) {
		return ((AuditActionDao) this.dao).findByType(type);
	}
}
