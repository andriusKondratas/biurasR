package ioffice.br.persistance.service.core;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.DomainObjectService;
import ioffice.br.persistance.service.administration.UserService;

@Service("BootstrapService")
@Transactional(readOnly = false)
@Scope("singleton")
public class BootstrapService {
	private final Log log = LogFactory.getLog(BootstrapService.class);

	@Inject
	UserService userService;

	@Inject
	DomainObjectService domainObjectService;

	private boolean firstRun = true;

	public void boot() {
		if (firstRun) {
			synchronized (BootstrapService.class) {/* Just in case booting in clustered application server environments */
				if (firstRun) {
					log.info("Synchronize users");
					synchUsers();
					log.info("Synchronize domain objects");
					synchDomainObjects();
					firstRun = false;
				}
			}
		}
	}

	private void synchUsers() {
		if(userService.getCountAll() > 0){
			for (User user : userService.loadAll()) {
				if (user.getPassword() != null && user.getPassword().length() < 60) {
					user.setPassword(userService.encodeUserPassword(user.getPassword()));
					userService.saveOrUpdate(user);
				}
			}	
		}
		else{
			createUser("test", "pridavimai@gmail.com", "Andrius", "Test");
		}
	}

	private void synchDomainObjects() {
		for (DomainObjectType objectType : DomainObjectType.values()) {
			if (domainObjectService.findByCode(objectType) != null) {
				continue;
			}
			createDomainObject(objectType);
		}
	}

	private void createDomainObject(DomainObjectType objectType) {
		DomainObject domainObject = new DomainObject();
		domainObject.setCode(objectType);
		domainObjectService.saveOrUpdate(domainObject);
	}

	private void createUser(String password, String email, String name, String lastName) {
		User user = new User();
		user.setPassword(userService.encodeUserPassword(password));
		user.setEmail(email);
		user.setName(name);
		user.setLastname(lastName);
		user.setActive(true);
		userService.saveOrUpdate(user);
	}
}
