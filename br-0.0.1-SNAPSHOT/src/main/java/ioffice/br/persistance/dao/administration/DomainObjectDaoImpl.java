package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.DomainObject;

import org.springframework.stereotype.Repository;

@Repository
public class DomainObjectDaoImpl extends AbstractHibernateDaoImpl<DomainObject> implements DomainObjectDao {

	@Override
	public DomainObject findByCode(String code) {

		@SuppressWarnings("unchecked")
		List<DomainObject> objects = getCurrentSession().createQuery("from DomainObject a where a.code=?").setParameter(0, code).list();

		if (objects.size() > 0) {
			return objects.get(0);
		} else {
			return null;
		}
	}

}
