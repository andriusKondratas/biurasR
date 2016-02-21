package ioffice.br.config;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;

public class PreRenderComponentListener implements SystemEventListener {

	public boolean isListenerForSource(Object source) {
		return true;
	}

	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event.getSource() instanceof InputText) {
			InputText source = (InputText) event.getSource();
			if (source.isRequired()) {
				source.getAttributes().put("styleClass", "input-required");
			}else{
				source.getAttributes().put("styleClass", "");
			}
		} else if (event.getSource() instanceof Password) {
			Password source = (Password) event.getSource();
			if (source.isRequired()) {
				source.getAttributes().put("styleClass", "input-required");
			}else{
				source.getAttributes().put("styleClass", "");
			}
		} else if (event.getSource() instanceof InputTextarea) {
			InputTextarea source = (InputTextarea) event.getSource();
			if (source.isRequired()) {
				source.getAttributes().put("styleClass", "input-required");
			}else{
				source.getAttributes().put("styleClass", "");
			}
		} else if (event.getSource() instanceof SelectOneMenu) {
			SelectOneMenu source = (SelectOneMenu) event.getSource();
			if (source.isRequired()) {
				source.getAttributes().put("panelStyleClass", "panel-required");
				source.getAttributes().put("styleClass", "menu-required");
			}else{
				source.getAttributes().put("styleClass", "");
			}
		}
	}
}
