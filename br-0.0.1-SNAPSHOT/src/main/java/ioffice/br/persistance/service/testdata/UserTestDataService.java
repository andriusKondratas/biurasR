package ioffice.br.persistance.service.testdata;

import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.RoleService;
import ioffice.br.persistance.service.administration.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserTestDataService")
@Transactional(readOnly = false)
@Scope("singleton")
public class UserTestDataService {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String createUsers() {
		try {
			createUser("test", "iždis-user1@atea.lt", "Adomas", "Lastas");
			createUser("test", "iždis-user2@atea.lt", "Petras", "Petrauskas");
			createUser("test", "external-user3@atea.lt", "Jonas", "Jonaitis");
			createUser("test", "external-user1@atea.lt", "Kazimieras", "Mačiulis");
			createUser("test", "commission-user2@atea.lt", "Jurgis", "Jurgelis");
			createUser("test", "commission-user3@atea.lt", "Hugo", "Boss");
			return "Users test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createUser(String password, String email, String name, String lastName) throws Exception {
		User user = new User();
		user.setPassword(userService.encodeUserPassword(password));
		user.setEmail(email);
		user.setName(name);
		user.setLastname(lastName);
		user.setActive(true);
		//Role role = roleService.findByCode(roleCode);
		//user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		//if (role == null) {
		//	throw new RuntimeException("Cannot find role: " + roleCode);
		//}
		try {
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			throw new Exception("Unable to add user with username: " + email + "<br>");
		}
	}

}
