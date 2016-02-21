package ioffice.br.pageflow.common.converters;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ioffice.br.persistance.model.core.Convertable;

@FacesConverter(value = "classification.GenericConverter")
public class GenericConvertableConverter implements Converter {

	private static final String key = "ioffice.br.pageflow.common.converters.GenericConverter";

	private Map<String, Object> getViewMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> idMap = (Map) viewMap.get(key);
		if (idMap == null) {
			idMap = new HashMap<String, Object>();
			viewMap.put(key, idMap);
		}
		return idMap;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String value) {
		if (value.isEmpty()) {
			return null;
		}
		return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object value) {
		String id = "";

		if (value == null) {
			return "";
		}

		if (value instanceof Convertable) {
			id = ((Convertable) value).toConverterString();
		}

		getViewMap(context).put(id, value);

		if (c instanceof HtmlOutputText) {
			return ((Convertable) value).toLocalizedString(FacesContext.getCurrentInstance().getViewRoot().getLocale());
		} else {
			return id;
		}

	}

}
