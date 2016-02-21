package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.model.administration.AuditAction;

import org.springframework.stereotype.Repository;

@Repository
public class AuditActionDaoImpl extends AbstractHibernateDaoImpl<AuditAction> implements AuditActionDao {

	@Override
	public AuditAction findByType(AuditActionType type) {

		@SuppressWarnings("unchecked")
		List<AuditAction> actions = getCurrentSession().createQuery("from Action a where a.code=?").setParameter(0, type).list();

		if (actions.size() > 0) {
			return actions.get(0);
		} else {
			return null;
		}
	}

}
