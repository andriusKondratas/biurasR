package ioffice.br.pageflow.common.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import officeI.br.persistance.model.limit.Limit;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.enums.PersonState;
import ioffice.br.persistance.model.classification.Activity;
import ioffice.br.persistance.model.classification.Country;
import ioffice.br.persistance.model.classification.EconomicZone;
import ioffice.br.persistance.model.classification.MeasureUnit;
import ioffice.br.persistance.model.classification.Region;
import ioffice.br.persistance.model.classification.Rfmo;
import ioffice.br.persistance.model.classification.Species;
import ioffice.br.persistance.model.core.AbstractEntity;
import ioffice.br.persistance.service.administration.AdministrationObjectCache;
import ioffice.br.persistance.service.classification.ClassificationObjectCache;

@ManagedBean(name = "applicationMB")
@SessionScoped
public class ApplicationBean extends BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{AdministrationObjectCache}")
	AdministrationObjectCache administrationObjectCache;

	@ManagedProperty(value = "#{ClassificationObjectCache}")
	ClassificationObjectCache classificationObjectCache;

	List<SelectItem> personActivities;
	List<SelectItem> personStates;
	List<SelectItem> regions;
	List<SelectItem> actionTypes;
	List<SelectItem> yesNo;
	List<SelectItem> rfmos;
	List<SelectItem> economicZones;
	List<SelectItem> countries;
	List<SelectItem> species;
	List<SelectItem> years;
	List<SelectItem> measureUnits;
	List<SelectItem> limits;
	List<SelectItem> vesselCategories;

	public void loadModel() {
		
		administrationObjectCache.init();
		classificationObjectCache.init();
		
		personActivities = new ArrayList<SelectItem>();
		for (Activity activity : classificationObjectCache.getAllActivities()) {
			personActivities.add(new SelectItem(activity, getMessage(activity.getCode().getInlLabel())));

		}
		personStates = new ArrayList<SelectItem>();
		for (PersonState personState : PersonState.values()) {
			personStates.add(new SelectItem(personState, getMessage(personState.getInlLabel())));
		}
		regions = new ArrayList<SelectItem>();
		for (Region region : classificationObjectCache.getAllRegions()) {
			regions.add(new SelectItem(region, getMessage(region.getCode().getInlLabel())));
		}
		actionTypes = new ArrayList<SelectItem>();
		for (AuditActionType actionType : AuditActionType.values()) {
			actionTypes.add(new SelectItem(actionType, getMessage(actionType.getInlLabel())));
		}
		yesNo = new ArrayList<SelectItem>();
		yesNo.add(new SelectItem("",""));
		yesNo.add(new SelectItem(Boolean.TRUE.toString(), getMessage("common.yes")));
		yesNo.add(new SelectItem(Boolean.FALSE.toString(), getMessage("common.no")));
		
		economicZones = new ArrayList<SelectItem>();
		for (EconomicZone economicZone : classificationObjectCache.getAllEconomicZones()) {
			economicZones.add(new SelectItem(economicZone, economicZone.getName()));
		}
		
		rfmos = new ArrayList<SelectItem>();
		for (Rfmo rfmo : classificationObjectCache.getAllRFMOs()) {
			rfmos.add(new SelectItem(rfmo, rfmo.getName()));
		}
		
		countries = new ArrayList<SelectItem>();
		for (Country country : classificationObjectCache.getAllCountries()) {
			countries.add(new SelectItem(country, country.toLocalizedString(FacesContext.getCurrentInstance().getViewRoot().getLocale())));
		}
		//TODO It should be some generic way to reach the same
		countries.sort(new Comparator<SelectItem>() {
			@Override
			public int compare(SelectItem arg0, SelectItem arg1) {
				return arg0.getLabel().compareTo(arg1.getLabel());
			}
		});
		
		species = new ArrayList<SelectItem>();
		for (Species specie : classificationObjectCache.getAllSpecies()) {
			species.add(new SelectItem(specie, specie.getName()));
		}
		
		
		years = new ArrayList<SelectItem>();
		for(int i = applicationProperties.getYearListMin(); i <= applicationProperties.getYearListMax(); i++ ){			
			years.add(new SelectItem(i, String.valueOf(i)));
		}
		
		measureUnits = new ArrayList<SelectItem>();
		for(MeasureUnit measureUnit : classificationObjectCache.getAllMeasureunits()){
			measureUnits.add(new SelectItem(measureUnit, measureUnit.getName()));
		}
		
		limits = new ArrayList<SelectItem>();
		for(Limit limit : classificationObjectCache.getAllLimits()){
			limits.add(new SelectItem(limit, limit.getName()));
		}
		
		vesselCategories = getConvertableList(classificationObjectCache.getAllVesselCategories());
	}
	
	private List<SelectItem> getConvertableList(Collection<? extends AbstractEntity> convertables){
		ArrayList<SelectItem> itemList = new ArrayList<SelectItem>();
		for (AbstractEntity ae : convertables) {
			itemList.add(new SelectItem(ae, ae.toLocalizedString(getLocale())));
		}
		return itemList;
	}
	
	public List<SelectItem> getLimits() {
		return limits;
	}

	public List<SelectItem> getMeasureUnits() {
		return measureUnits;
	}

	public List<SelectItem> getYears() {
		return years;
	}

	public List<SelectItem> getSpecies() {
		return species;
	}

	public List<SelectItem> getRfmos() {
		return rfmos;
	}

	public List<SelectItem> getEconomicZones() {
		return economicZones;
	}
	
	public List<SelectItem> getPersonActivities() {
		return personActivities;
	}

	public List<SelectItem> getPersonStates() {
		return personStates;
	}

	public List<SelectItem> getRegions() {
		return regions;
	}

	public List<SelectItem> getActionTypes() {
		return actionTypes;
	}

	public List<SelectItem> getYesNo() {
		return yesNo;
	}
	
	public List<SelectItem> getCountries() {
		return countries;
	}

	public List<SelectItem> getVesselCategories() {
		return vesselCategories;
	}

	public AdministrationObjectCache getAdministrationObjectCache() {
		return administrationObjectCache;
	}

	public void setAdministrationObjectCache(AdministrationObjectCache administrationObjectCache) {
		this.administrationObjectCache = administrationObjectCache;
	}

	public ClassificationObjectCache getClassificationObjectCache() {
		return classificationObjectCache;
	}

	public void setClassificationObjectCache(ClassificationObjectCache classificationObjectCache) {
		this.classificationObjectCache = classificationObjectCache;
	}
}
