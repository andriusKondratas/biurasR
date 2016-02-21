package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.pageflow.common.enums.ObjectState;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.RoleService;
import ioffice.br.persistance.service.administration.UserService;

@ManagedBean(name = "userEditMB")
@ViewScoped
public class UserEditBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	UserService userService;
	@ManagedProperty(value = "#{RoleService}")
	RoleService roleService;
	Role[] selectedRoles;
	List<SelectItem> allRoles;
	User user;
	String passwordMatch;
	String password;
	boolean passwordDestroyed;

	public void save() {
		try {

			// TODO encode psw validation messages
			// TODO user unique custom validator
			// if(passwordDestroyed){
			// user.setPassword(userService.encodeUserPassword(password));
			// }

			if (this.objectState.equals(ObjectState.NEW)) {
				user.setPassword(userService.encodeUserPassword(password));
			}

			user.setRoles(Arrays.asList(selectedRoles));
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida", e.toString()));
			return;
		}

		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler();

		configurableNavigationHandler.performNavigation("user-list?faces-redirect=true");
	}

	public void delete() {
		try {
			user.setDeleted(true);
			userService.saveOrUpdate(user);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Klaida", e.toString()));
			return;
		}

		ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
				.getNavigationHandler();

		configurableNavigationHandler.performNavigation("user-list?faces-redirect=true");
	}

	@Override
	public void loadModel() {
		super.loadModel();
		String userId = (String) flashScope().get("userId");
		if (userId != null) {
			this.user = userService.findById(Long.valueOf(userId));
			this.selectedRoles = user.getRoles().toArray(new Role[user.getRoles().size()]);
			// this.password = "12";
			// this.passwordMatch = "12";
			this.objectState = ObjectState.OLD;
		} else {
			this.user = new User();
			this.objectState = ObjectState.NEW;
			this.selectedRoles = new Role[0];
		}

		this.allRoles = new ArrayList<SelectItem>();
		for (Role role : roleService.loadAll()) {
			allRoles.add(new SelectItem(role, role.getRoleTitle()));
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<SelectItem> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<SelectItem> allRoles) {
		this.allRoles = allRoles;
	}

	public Role[] getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(Role[] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPasswordMatch() {
		return passwordMatch;
	}

	public void setPasswordMatch(String passwordMatch) {
		this.passwordMatch = passwordMatch;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void destroyPassword() {
		this.passwordDestroyed = true;
	}
	
	@Override
	public DomainObjectType getDomainObjectType() {
		return DomainObjectType.USER;
	}
}
