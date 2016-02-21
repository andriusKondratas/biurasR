package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.pageflow.common.datamodels.EntityDataModel;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.service.administration.RoleService;

@ManagedBean(name = "roleListMB")
@ViewScoped
public class RoleListBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RoleService}")
	RoleService roleService;

	Role selectedRole;
	List<Role> filteredRoles;
	EntityDataModel<Role> roleListModel;

	@Override
	public void loadModel() {
		super.loadModel();
		roleListModel = new EntityDataModel<Role>(getRoleService().loadAll());
	}

	public String edit() {
		flashScope().put("roleId", requestScope().getParameter("roleId"));
		return "role-create?faces-redirect=true&includeViewParams=true";
	}

	public void view() {
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public List<Role> getFilteredRoles() {
		return filteredRoles;
	}

	public void setFilteredRoles(List<Role> filteredRoles) {
		this.filteredRoles = filteredRoles;
	}

	public EntityDataModel<Role> getRoleListModel() {
		return roleListModel;
	}

	public void setRoleListModel(EntityDataModel<Role> roleListModel) {
		this.roleListModel = roleListModel;
	}
	
	@Override
	public DomainObjectType getDomainObjectType() {
		return DomainObjectType.ROLE;
	}
}
