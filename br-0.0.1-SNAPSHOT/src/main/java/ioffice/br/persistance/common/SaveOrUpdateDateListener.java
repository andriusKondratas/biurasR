package ioffice.br.persistance.common;

import ioffice.br.persistance.model.core.AuditableEntity;
import ioffice.br.persistance.service.administration.AuditLogService;
import ioffice.br.persistance.service.administration.UserService;

import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("serial")
public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

	@Autowired
	UserService userService;

	@Autowired
	AuditLogService auditLogService;

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		if (event.getObject() instanceof AuditableEntity) {
			// Do nothing for now, if decided not to use, detach this listener
			// AuditableEntity record = (AuditableEntity) event.getObject();
		}
	}
}
