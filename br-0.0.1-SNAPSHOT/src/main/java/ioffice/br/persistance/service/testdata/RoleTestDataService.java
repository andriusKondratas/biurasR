package ioffice.br.persistance.service.testdata;

import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.service.administration.AuditActionService;
import ioffice.br.persistance.service.administration.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("RoleTestDataService")
@Transactional(readOnly = false)
@Scope("singleton")
public class RoleTestDataService {

	@Autowired
	private AuditActionService actionService;

	@Autowired
	private RoleService roleService;

	@Transactional(readOnly = false)
	public String createRoles() {
		try {
			createRole("IÅ½DIS vartotojas");
			createRole("IÅ?oriniai vartotojai");
			createRole("Komisija");
			return "Roles test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createRole(String roleTitle) throws Exception {
		Role role = new Role();
		role.setRoleTitle(roleTitle);

		try {
			roleService.saveOrUpdate(role);
		} catch (Exception e) {
			throw new Exception("Unable to add role with code: " + roleTitle, e);
		}
	}
}
