package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.IgnoreDomainObject;
import ioffice.br.persistance.model.administration.User;

public interface IgnoreDomainObjectDao extends AbstractHibernateDao<IgnoreDomainObject> {

	List<IgnoreDomainObject> findByDomainObjectTypeAndUser(DomainObject domainObject, User user);
	
	IgnoreDomainObject findByDomainObjectTypeAndUserAndIgnoreId(DomainObject domainObject, User user, Long ignore_id);

}
