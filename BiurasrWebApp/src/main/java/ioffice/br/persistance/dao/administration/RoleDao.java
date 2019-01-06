package ioffice.br.persistance.dao.administration;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.Role;

public interface RoleDao extends AbstractHibernateDao<Role> {

	Role findByCode(String code);

}
