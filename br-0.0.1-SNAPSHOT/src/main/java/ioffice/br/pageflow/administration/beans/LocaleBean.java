package ioffice.br.pageflow.administration.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ioffice.br.pageflow.common.beans.ApplicationBean;
import ioffice.br.pageflow.common.beans.BasicBean;

@ManagedBean(name = "localeBean")
@SessionScoped
public class LocaleBean extends BasicBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Locale locale;//FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    private List<String> languages;
     
    @PostConstruct
    public void loadModel() {
    	languages = new ArrayList<String>();
    	languages.add("lt");
    	languages.add("en");
    	
    	locale = new Locale("lt");
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
        
        ApplicationBean applicationBean = (ApplicationBean) facesContext().getApplication().getELResolver().getValue(elContext(), null, "applicationMB");
		applicationBean.loadModel();
        
        
    }

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
}
