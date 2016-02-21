package ioffice.br.persistance.dao.administration;

import java.util.ArrayList;
import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.UserProfile;

import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDaoImpl extends AbstractHibernateDaoImpl<UserProfile>
		implements UserProfileDao {

	@SuppressWarnings("unchecked")
	public UserProfile findByUsername(String username) {

		List<UserProfile> usersProfiles = new ArrayList<UserProfile>();
		usersProfiles = getCurrentSession()
				.createQuery("from UserProfile u where u.name=?")
				.setParameter(0, username).list();

		if (usersProfiles.size() > 0) {
			return usersProfiles.get(0);
		} else {
			return null;
		}
	}
}
