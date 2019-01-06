package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.DomainObject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DomainObjectDaoImpl extends AbstractHibernateDaoImpl<DomainObject> implements DomainObjectDao {

	@SuppressWarnings("unchecked")
	@Override
	public DomainObject findByCode(DomainObjectType domainObjectType) {
		Criteria criteria = createCriteria();

		if (domainObjectType != null) {
			criteria.add(Restrictions.eq("code", domainObjectType));
		}

		List<DomainObject> objects = criteria.list();

		if (objects.size() > 0) {
			return objects.get(0);
		} else {
			return null;
		}
	}

}
