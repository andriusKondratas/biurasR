package ioffice.br.persistance.dao.administration;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.administration.UserProfile;

public interface UserProfileDao extends AbstractHibernateDao<UserProfile> {
	
	UserProfile findByUsername(String username);
}
