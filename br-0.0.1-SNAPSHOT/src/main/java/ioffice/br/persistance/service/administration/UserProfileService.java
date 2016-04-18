
package ioffice.br.persistance.service.administration;

import ioffice.br.persistance.dao.administration.UserProfileDao;
import ioffice.br.persistance.model.administration.UserProfile;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserProfileService")
@Transactional(readOnly = false)
@Scope("singleton")
public class UserProfileService extends GenericService<UserProfile> {

	@Autowired
	public UserProfileService(UserProfileDao dao) {
		super(dao);
	}

	public UserProfile findByUsername(String username) {
		return ((UserProfileDao) this.dao).findByUsername(username);
	}
}
