package ioffice.br.persistance.model.core;

import java.util.Locale;

public interface Convertable {
	
	public String toConverterString();
	public String toLocalizedString(Locale locale);
	
}
