package ioffice.br.persistance.dao.administration;

import java.util.ArrayList;
import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractHibernateDaoImpl<User> implements UserDao {

	@SuppressWarnings("unchecked")
	public User findByUsername(String username) {
		List<User> users = new ArrayList<User>();
		users = getCurrentSession().createQuery("from User u where u.email=?").setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
}
