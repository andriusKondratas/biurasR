package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import ioffice.br.pageflow.common.beans.ApplicationBean;
import ioffice.br.pageflow.common.beans.BasicBean;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.service.administration.UserService;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginBean extends BasicBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName = null;
	private String password = null;
	private String email = null;
	private String passwordToken = null;
	private String passwordTokenRepeat = null;

	List<User> userList;

	@ManagedProperty(value = "#{UserService}")
	UserService userService;

	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager = null;

	public void login() {
		if (performLogin()) {
			MenuBean menuBean = (MenuBean) facesContext().getApplication().getELResolver().getValue(elContext(), null,
					"menuMB");
			menuBean.loadModel();

			ApplicationBean applicationBean = (ApplicationBean) facesContext().getApplication().getELResolver()
					.getValue(elContext(), null, "applicationMB");
			applicationBean.loadModel();

			((ConfigurableNavigationHandler) facesContext().getApplication().getNavigationHandler())
					.performNavigation("welcome");
		}
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		// facesContext().getExternalContext().invalidateSession();

		((ConfigurableNavigationHandler) facesContext().getApplication().getNavigationHandler())
				.performNavigation("/pages/welcome?faces-redirect=true");
	}

	public void goTologin() {
		SecurityContextHolder.getContext().setAuthentication(null);
		((ConfigurableNavigationHandler) facesContext().getApplication().getNavigationHandler())
				.performNavigation("/pages/login");
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
			facesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Prisijungimo Klaida",
					"Neteisingas naudojo vardas arba slaptažodis"));
			return false;
		}
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}

	public String getPasswordTokenRepeat() {
		return passwordTokenRepeat;
	}

	public void setPasswordTokenRepeat(String passwordTokenRepeat) {
		this.passwordTokenRepeat = passwordTokenRepeat;
	}
}
