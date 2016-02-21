package ioffice.br.persistance.service.testdata;

import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.AuditAction;
import ioffice.br.persistance.model.administration.DomainObject;
import ioffice.br.persistance.service.administration.AuditActionService;
import ioffice.br.persistance.service.administration.DomainObjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ActionTestDataService")
@Transactional(readOnly = false)
@Scope("singleton")
public class ActionTestDataService{
	
	@Autowired
	private AuditActionService actionService;

	@Autowired
	private DomainObjectService domainObjectService;
	
	public String createDomainObjects() {
		try {
			for (DomainObjectType objectType : DomainObjectType.values()) {
				createDomainObject(objectType);
			}

			return "Actions test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createDomainObject(DomainObjectType objectType) throws Exception {
		DomainObject domainObject = new DomainObject();
		domainObject.setCode(objectType);
		try {
			domainObjectService.saveOrUpdate(domainObject);
		} catch (Exception e) {
			throw new Exception(String.format("Unable to create domainObject with code: %s", objectType.name()), e);
		}
	}
	
	
	public String createActions() {
		try {
			for (AuditActionType actionCode : AuditActionType.values()) {
				createAction(actionCode);
			}

			return "Actions test data created";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void createAction(AuditActionType actionCode) throws Exception {
		AuditAction action = new AuditAction();
		action.setCode(actionCode);
		try {
			actionService.saveOrUpdate(action);
		} catch (Exception e) {
			throw new Exception(String.format("Unable to create action with code: %s", actionCode.name()), e);
		}
	}
}
