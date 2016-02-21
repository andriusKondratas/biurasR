package ioffice.br.persistance.dao.administration;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.DomainObject;

public interface DomainObjectDao extends AbstractHibernateDao<DomainObject> {

	DomainObject findByCode(String code);

}
