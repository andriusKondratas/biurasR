package ioffice.br.pageflow.administration.converters;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ioffice.br.persistance.service.administration.AdministrationObjectCache;

@FacesConverter("ioffice.br.pageflow.administration.converters.RoleConverter")
public class RoleConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ELContext elContext = context.getELContext();
		AdministrationObjectCache cache = (AdministrationObjectCache) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "AdministrationObjectCache");
		
		return cache.getRoleById(Long.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}
}
