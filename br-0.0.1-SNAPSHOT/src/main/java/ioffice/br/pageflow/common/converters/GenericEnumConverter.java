package ioffice.br.pageflow.common.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ioffice.br.pageflow.common.beans.ApplicationBean;
import ioffice.br.persistance.enums.GenericEnum;

@FacesConverter(value = "genericEnumConverter")
public class GenericEnumConverter implements Converter {

	private static final String ATTRIBUTE_ENUM_TYPE = "GenericEnumConverter.enumType";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof GenericEnum) {
			component.getAttributes().put(ATTRIBUTE_ENUM_TYPE, value.getClass());

			ApplicationBean applicationBean = (ApplicationBean) FacesContext.getCurrentInstance().getApplication().getELResolver()
					.getValue(context.getELContext(), null, "applicationMB");

			return applicationBean.getMessage(((GenericEnum) value).getInlLabel());
		} else {
			throw new ConverterException(new FacesMessage("Given object class does not implement GenericEnum: " + value.getClass()));
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Class<Enum> enumType = (Class<Enum>) component.getAttributes().get(ATTRIBUTE_ENUM_TYPE);
		try {
			return Enum.valueOf(enumType, value);
		} catch (IllegalArgumentException e) {
			throw new ConverterException(new FacesMessage("Value is not an enum of type: " + enumType));
		}
	}
}