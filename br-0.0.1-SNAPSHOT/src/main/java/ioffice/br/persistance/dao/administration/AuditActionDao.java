package ioffice.br.persistance.dao.administration;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.model.administration.AuditAction;

public interface AuditActionDao extends AbstractHibernateDao<AuditAction> {

	AuditAction findByType(AuditActionType type);

}
