package ioffice.br.pageflow.common.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import ioffice.br.persistance.model.core.AbstractEntity;
import ioffice.br.persistance.service.administration.AdministrationObjectCache;


@ManagedBean(name = "applicationMB")
@SessionScoped
public class ApplicationBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AdministrationObjectCache}")
	AdministrationObjectCache administrationObjectCache;

	List<SelectItem> personActivities;
	List<SelectItem> personStates;
	List<SelectItem> yesNo;
	List<SelectItem> years;

	public void loadModel() {
		
		administrationObjectCache.init();
		//classificationObjectCache.init();
		
		yesNo = new ArrayList<SelectItem>();
		yesNo.add(new SelectItem("",""));
		yesNo.add(new SelectItem(Boolean.TRUE.toString(), getMessage("common.yes")));
		yesNo.add(new SelectItem(Boolean.FALSE.toString(), getMessage("common.no")));
		
		years = new ArrayList<SelectItem>();
		for(int i = applicationProperties.getYearListMin(); i <= applicationProperties.getYearListMax(); i++ ){			
			years.add(new SelectItem(i, String.valueOf(i)));
		}
	}
	
	private List<SelectItem> getConvertableList(Collection<? extends AbstractEntity> convertables){
		ArrayList<SelectItem> itemList = new ArrayList<SelectItem>();
		for (AbstractEntity ae : convertables) {
			itemList.add(new SelectItem(ae, ae.toLocalizedString(getLocale())));
		}
		return itemList;
	}
	

	public List<SelectItem> getYears() {
		return years;
	}
	
	public List<SelectItem> getPersonActivities() {
		return personActivities;
	}

	public List<SelectItem> getPersonStates() {
		return personStates;
	}

	public List<SelectItem> getYesNo() {
		return yesNo;
	}

	public AdministrationObjectCache getAdministrationObjectCache() {
		return administrationObjectCache;
	}

	public void setAdministrationObjectCache(AdministrationObjectCache administrationObjectCache) {
		this.administrationObjectCache = administrationObjectCache;
	}
}
