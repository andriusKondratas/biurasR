package ioffice.br.persistance.dao.administration;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.User;

public interface UserDao extends AbstractHibernateDao<User> {
	User findByUsername(String username);
}
