package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.pageflow.administration.datamodels.AuditLogListDataObject;
import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.AuditLog;

public interface AuditLogDao extends AbstractHibernateDao<AuditLog> {

	List<AuditLogListDataObject> findByObjectIdAndObjectType(Long objectId, Long objTypeId);
}
