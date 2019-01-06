package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.Role;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractHibernateDaoImpl<Role> implements RoleDao {

	@Override
	public Role findByCode(String code) {

		@SuppressWarnings("unchecked")
		List<Role> roles = getCurrentSession().createQuery("from Role r where r.code=?").setParameter(0, code).list();

		if (roles.size() > 0) {
			return roles.get(0);
		} else {
			return null;
		}
	}

}
