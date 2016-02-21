package ioffice.br.persistance.service.administration;

import java.util.List;

import ioffice.br.persistance.dao.administration.UserDao;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@Transactional(readOnly = false)
@Scope("singleton")
public class UserService extends GenericService<User> {
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	public UserService(UserDao dao) {
		super(dao);
	}

	public User findByUsername(String username) {
		return ((UserDao) this.dao).findByUsername(username);
	}

	public void deleteByIds(List<Long> selectedIds) {
		for (Long id : selectedIds) {
			User item = this.dao.findById(id);
			dao.delete(item);
		}
	}
	
	public String encodeUserPassword(String password){
		return passwordEncoder.encode(password);
	}
}
