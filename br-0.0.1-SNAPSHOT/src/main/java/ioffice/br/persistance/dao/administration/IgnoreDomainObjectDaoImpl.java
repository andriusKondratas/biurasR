package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.IgnoreDomainObject;
import ioffice.br.persistance.model.administration.User;

import org.springframework.stereotype.Repository;

@Repository
public class IgnoreDomainObjectDaoImpl extends AbstractHibernateDaoImpl<IgnoreDomainObject> implements IgnoreDomainObjectDao {

	@Override
	public List<IgnoreDomainObject> findByDomainObjectTypeAndUser(DomainObject domainObject, User user) {
		@SuppressWarnings("unchecked")
		List<IgnoreDomainObject> objects = getCurrentSession().createQuery("from IgnoreDomainObject a where a.object=? and a.user=?")
				.setParameter(0, domainObject).setParameter(1, user).list();

		return objects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IgnoreDomainObject findByDomainObjectTypeAndUserAndIgnoreId(DomainObject domainObject, User user, Long ignore_id) {

		List<IgnoreDomainObject> objects = getCurrentSession().createQuery("from IgnoreDomainObject a where a.object=? and a.user=? and a.ignore_id=?")
				.setParameter(0, domainObject).setParameter(1, user).setParameter(2, ignore_id).list();

		if (objects.size() != 1) {
			throw new RuntimeException("error.ignoreDomainObject.multiple.objects");
		}
		return objects.get(0);

	}
}
