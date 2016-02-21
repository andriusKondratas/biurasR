package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.pageflow.common.datamodels.EntityDataModel;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.UserService;

@ManagedBean(name = "userListMB")
@ViewScoped
public class UserListBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	UserService userService;

	User selectedUser;
	List<User> filteredUsers;
	EntityDataModel<User> userListModel;

	@Override
	public void loadModel() {
		super.loadModel();
		userListModel = new EntityDataModel<User>(getUserService().loadAll());
	}

	public String edit() {
		flashScope().put("userId", requestScope().getParameter("userId"));
		return "user-create?faces-redirect=true&includeViewParams=true";
	}

	public void view() {
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public EntityDataModel<User> getUserListModel() {
		return userListModel;
	}
	
	@Override
	public DomainObjectType getDomainObjectType() {
		return DomainObjectType.USER;
	}
}
