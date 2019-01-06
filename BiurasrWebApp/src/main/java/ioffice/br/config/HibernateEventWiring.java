package ioffice.br.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ioffice.br.persistance.common.SaveOrUpdateDateListener;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class HibernateEventWiring {
	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private SaveOrUpdateDateListener listener;

	@PostConstruct
	public void registerListeners() {
		EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.SAVE_UPDATE).prependListener(listener);
		registry.getEventListenerGroup(EventType.SAVE).prependListener(listener);
		registry.getEventListenerGroup(EventType.UPDATE).prependListener(listener);
	}
}
