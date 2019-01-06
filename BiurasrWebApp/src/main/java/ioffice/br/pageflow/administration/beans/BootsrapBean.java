package ioffice.br.pageflow.administration.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;

import ioffice.br.persistance.service.core.BootstrapService;

@Scope("singleton")
public class BootsrapBean {
	private final Log log = LogFactory.getLog(BootsrapBean.class);
	
	@Inject
	BootstrapService bootstrapService;

	@PostConstruct
	public void init() {
		log.info("Br application start booting");
		bootstrapService.boot();
		log.info("Br application has booted successfully");
	}

	@PreDestroy
	public void destroy() {
		log.info("Br application cleaning");
	}
}
