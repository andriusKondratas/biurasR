package ioffice.br.persistance.service.administration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.Role;

@Service("AdministrationObjectCache")
@Transactional(readOnly = true)
@Scope("singleton")
public class AdministrationObjectCache {

	Map<String, DomainObject> allDomainObjects;
	Map<Long, Role> allRoles;

	@Autowired
	DomainObjectService domainObjectService;

	@Autowired
	RoleService roleService;

	public void init() {
		this.allDomainObjects = new HashMap<String, DomainObject>();
		for (DomainObject domainObject : domainObjectService.loadAll()) {
			allDomainObjects.put(domainObject.getCode().name(), domainObject);
		}

		this.allRoles = new HashMap<Long, Role>();
		for (Role role : roleService.loadAll()) {
			allRoles.put(role.getId(), role);
		}
	}

	public Collection<DomainObject> getAllDomainObjects() {
		return allDomainObjects.values();
	}

	public DomainObject getDomainObjectByCode(String code) {

		return allDomainObjects.get(code);
	}


	public Map<Long, Role> getAllRoles() {
		return allRoles;
	}

	public Role getRoleById(Long id) {
		return allRoles.get(id);
	}
}
