package ioffice.br.pageflow.common.beans;

import ioffice.br.config.ApplicationProperties;
import ioffice.br.config.CustomResourceBundle;

import java.io.Serializable;
import java.util.Locale;

import javax.el.ELContext;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ioffice.br.pageflow.administration.beans.LocaleBean;
import ioffice.br.pageflow.common.enums.ObjectState;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.enums.DomainType;
import ioffice.br.persistance.enums.PriviledgeType;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{ApplicationProperties}")
	ApplicationProperties applicationProperties;
	
	protected ObjectState objectState = ObjectState.UNKNOWN;

	public String getMessage(String key) {
		String msg = CustomResourceBundle.getInstance().getString(key);
		return msg;
	}

	public void loadModel() {
		//do nothing for now
		/*String viewId = facesContext().getViewRoot().getViewId();
		MenuBean menuBean = (MenuBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "menuMB");
		menuBean.updateBreadCrumbModel(viewId);*/
	}
	
	public void goBack() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("canLooseState", canLooseState());
	}
	
	public void navigate(String url){
		((ConfigurableNavigationHandler) facesContext().getApplication()
				.getNavigationHandler()).performNavigation(url+"?faces-redirect=true");
	}
	
	public FacesContext facesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public ELContext elContext(){
		return FacesContext.getCurrentInstance().getELContext();
	}
	
	public HttpServletRequest requestScope(){
		return (HttpServletRequest)facesContext().getExternalContext().getRequest();
	}
	
	public Flash flashScope(){
		return facesContext().getExternalContext().getFlash();
	}
	
	public HttpSession sessionScope(){
		return (HttpSession)facesContext().getExternalContext().getSession(false);
	}
	
	public UIComponent findComponent(String id){
	  return facesContext().getViewRoot().findComponent(id);	
	}
	
	public boolean getAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

	public String getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = "";

		if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
			userName = authentication.getName();
		}
		return userName;
	}
	
	public Locale getLocale() {
		LocaleBean localeBean = (LocaleBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "localeBean");
		return localeBean.getLocale();
	}
	
	public boolean hasAccess(DomainObjectType domainObjectType, PriviledgeType priviledgeType) {
		if(!applicationProperties.isPriviledgeCheckEnabled()){
			return true;
		}
		
		if(facesContext().getExternalContext().isUserInRole(domainObjectType.name()+":"+priviledgeType.name())){
			return true;
		}
		return false;
	}
	
	public boolean hasAccess(PriviledgeType priviledgeType) {
		if(getDomainObjectType() == null){
			return false;
		}
		if(hasAccess(getDomainObjectType(), priviledgeType)){
			return true;
		}
		return false;
	}
	
	public boolean hasAnyAccess(DomainType domainType, PriviledgeType priviledgeType) {
		for (DomainObjectType domainObjectType : DomainObjectType.values()) {
			if (domainObjectType.getDomain().equals(domainType) && !domainObjectType.isHidden()) {
				if (hasAccess(domainObjectType, priviledgeType)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasAnyNestedAccess(DomainType domainType, PriviledgeType priviledgeType) {
		if(hasAnyAccess(domainType, priviledgeType)){
			return true;
		}
		for (DomainType domainTypeChild : domainType.getChildDomains()) {
			if(hasAnyAccess(domainTypeChild, priviledgeType)){
				return true;
			}
		}
		return false;
	}
	
	public void setImageContent(byte[] content){
		FileUploadPreviewBean fileUploadPreviewBean = (FileUploadPreviewBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "fileMB");
		fileUploadPreviewBean.setImageContent(content);
	}
	
	public byte[] getImageContent(){
		FileUploadPreviewBean fileUploadPreviewBean = (FileUploadPreviewBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "fileMB");
		return fileUploadPreviewBean.getImageContent();
	}

	public void viewChangeLog() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("canLooseState", canLooseState());
	}

	public boolean canLooseState() {
		return objectState.anyOf(ObjectState.UNKNOWN);
	}

	public ObjectState getObjectState() {
		return objectState;
	}

	public DomainObjectType getDomainObjectType() {
		return null;
	}

	public void setObjectState(ObjectState objectState) {
		this.objectState = objectState;
	}

	public ApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
}