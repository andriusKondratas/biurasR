package ioffice.br.persistance.service.administration;

import java.util.HashMap;
import java.util.Map;

import ioffice.br.persistance.dao.administration.IgnoreDomainObjectDao;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.model.administration.IgnoreDomainObject;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("IgnoreDomainObjectService")
@Transactional(readOnly = false)
@Scope("singleton")
public class IgnoreDomainObjectService extends GenericService<IgnoreDomainObject> {

	@Autowired
	UserService userService;

	@Autowired
	AdministrationObjectCache administrationObjectCache;

	@Autowired
	public IgnoreDomainObjectService(IgnoreDomainObjectDao dao) {
		super(dao);
	}

	public Map<Long, IgnoreDomainObject> findByDomainObjectTypeAndUser(DomainObjectType domainObjectType) {

		Map<Long, IgnoreDomainObject> idoMap = new HashMap<Long, IgnoreDomainObject>();
		for (IgnoreDomainObject ido : ((IgnoreDomainObjectDao) this.dao).findByDomainObjectTypeAndUser(
				administrationObjectCache.getDomainObjectByCode(domainObjectType.name()),
				userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))) {
			idoMap.put(ido.getIgnore_id(), ido);
		}
		return idoMap;
	}

	@Transactional
	public void ignore(DomainObjectType domainObjectType, Long ignore_id) {
		IgnoreDomainObject ido = new IgnoreDomainObject();
		ido.setIgnore_id(ignore_id);
		// TODO from cache
		ido.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		ido.setObject(administrationObjectCache.getDomainObjectByCode(domainObjectType.name()));
		this.dao.insert(ido);
	}

	@Transactional
	public void accept(DomainObjectType domainObjectType, Long ignore_id) {
		IgnoreDomainObject ido = findByDomainObjectTypeAndUserAndIgnoreId(administrationObjectCache.getDomainObjectByCode(domainObjectType.name()),
				userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()), ignore_id);
		this.dao.delete(ido);
	}

	private IgnoreDomainObject findByDomainObjectTypeAndUserAndIgnoreId(DomainObject domainObject, User user, Long ignore_id) {
		return ((IgnoreDomainObjectDao) this.dao).findByDomainObjectTypeAndUserAndIgnoreId(domainObject, user, ignore_id);
	}

}
