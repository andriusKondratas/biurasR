package ioffice.br.persistance.service.administration;

import ioffice.br.persistance.dao.administration.DomainObjectDao;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DomainObjectService")
@Transactional(readOnly = false)
@Scope("singleton")
public class DomainObjectService extends GenericService<DomainObject> {

	@Autowired
	public DomainObjectService(DomainObjectDao dao) {
		super(dao);
	}

	public DomainObject findByCode(String code) {
		return ((DomainObjectDao) this.dao).findByCode(code);
	}
}
