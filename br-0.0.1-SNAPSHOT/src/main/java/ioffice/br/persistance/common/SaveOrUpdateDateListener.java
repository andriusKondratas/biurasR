package ioffice.br.persistance.common;

import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ioffice.br.persistance.model.core.AbstractEntity;
import ioffice.br.persistance.service.administration.UserService;

@Component
@SuppressWarnings("serial")
public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

	@Autowired
	UserService userService;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		if (event.getObject() instanceof AbstractEntity) {
			// Do nothing for now, if decided not to use, detach this listener
			// AuditableEntity record = (AuditableEntity) event.getObject();
		}
	}
}
