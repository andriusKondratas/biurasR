package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ioffice.br.pageflow.common.beans.ApplicationBean;
import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.UserService;
import ioffice.br.persistance.service.testdata.ActionTestDataService;
import ioffice.br.persistance.service.testdata.PersonTestDataService;
import ioffice.br.persistance.service.testdata.RoleTestDataService;
import ioffice.br.persistance.service.testdata.UserTestDataService;

import org.primefaces.event.SelectEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginBean extends BasicBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName = null;
	private String password = null;

	List<User> userList;

	@ManagedProperty(value = "#{UserService}")
	UserService userService;

	// TODO: following properties has to be removed to possibly some request scoped new bean
	@ManagedProperty(value = "#{RoleTestDataService}")
	private RoleTestDataService roleTestDataService = null;

	@ManagedProperty(value = "#{ActionTestDataService}")
	private ActionTestDataService actionTestDataService = null;

	@ManagedProperty(value = "#{UserTestDataService}")
	private UserTestDataService userTestDataService = null;

	@ManagedProperty(value = "#{PersonTestDataService}")
	private PersonTestDataService personTestDataService = null;

	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager = null;

	public void login() {
	
		if (performLogin()) {
			MenuBean menuBean = (MenuBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "menuMB");
			menuBean.loadModel();
			
			ApplicationBean applicationBean = (ApplicationBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "applicationMB");
			applicationBean.loadModel();
		
			navigate("welcomeVolt");
		}
	}

	@PreDestroy
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		//facesContext().getExternalContext().invalidateSession();
		navigate("/pages/loginVolt.xhtml");
	}

	public void install() {
		StringBuffer status = new StringBuffer();
		status.append(getActionTestDataService().createDomainObjects()).append("<br>");
		status.append(getRoleTestDataService().createRoles()).append("<br>");
		status.append(getUserTestDataService().createUsers()).append("<br>");
		status.append(getPersonTestDataService().createRegions()).append("<br>");
		status.append(getPersonTestDataService().createActivities()).append("<br>");
		status.append(getPersonTestDataService().createPersons()).append("<br>");
		this.userList = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dėmesio", status.toString()));
	}

	public void onRowSelect(SelectEvent event) {
		User user = (User) event.getObject();
		this.password = "test";
		this.userName = user.getEmail();
		login();
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleTestDataService getRoleTestDataService() {
		return roleTestDataService;
	}

	public void setRoleTestDataService(RoleTestDataService roleTestDataService) {
		this.roleTestDataService = roleTestDataService;
	}

	public ActionTestDataService getActionTestDataService() {
		return actionTestDataService;
	}

	public void setActionTestDataService(ActionTestDataService actionTestDataService) {
		this.actionTestDataService = actionTestDataService;
	}

	public UserTestDataService getUserTestDataService() {
		return userTestDataService;
	}

	public void setUserTestDataService(UserTestDataService userTestDataService) {
		this.userTestDataService = userTestDataService;
	}

	public PersonTestDataService getPersonTestDataService() {
		return personTestDataService;
	}

	public void setPersonTestDataService(PersonTestDataService personTestDataService) {
		this.personTestDataService = personTestDataService;
	}

	public List<User> getUserList() {
		if (userList == null) {
			userList = new ArrayList<User>();
			userList.addAll(getUserService().loadAll());
		}
		return userList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private boolean performLogin() {
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
		} catch (AuthenticationException e) {
			facesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Prisijungimo Klaida", "Neteisingas naudojo vardas arba slaptažodis"));
			return false;
		}
		return true;
	}
}
