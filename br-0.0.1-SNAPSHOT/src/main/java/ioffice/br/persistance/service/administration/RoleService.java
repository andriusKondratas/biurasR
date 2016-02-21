package ioffice.br.persistance.service.administration;

import ioffice.br.persistance.dao.administration.RoleDao;
import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.service.core.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("RoleService")
@Transactional(readOnly = false)
@Scope("singleton")
public class RoleService extends GenericService<Role> {

	@Autowired
	public RoleService(RoleDao dao) {
		super(dao);
	}

	public Role findByCode(String code) {
		return ((RoleDao) this.dao).findByCode(code);
	}
}
