package ioffice.br.pageflow.common.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "genericCheckMarkConverter")
public class GenericCheckMarkConverter implements Converter {

	private static final String ATTRIBUTE_HEX_CHECKMARK = "<span style=\"color:#5C9CCC\">&#x2714;</span>";
	private static final String ATTRIBUTE_HEX_BALLOT = "<span style=\"color:#E17009\">&#x2718;</span>";

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && (Boolean) value) {
			return ATTRIBUTE_HEX_CHECKMARK;
		} else {
			return ATTRIBUTE_HEX_BALLOT;
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && ATTRIBUTE_HEX_CHECKMARK.equals(value)) {
			return true;
		} else {
			return false;
		}
	}
}