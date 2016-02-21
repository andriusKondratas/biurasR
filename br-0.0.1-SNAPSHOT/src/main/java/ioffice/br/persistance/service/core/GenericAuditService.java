package ioffice.br.persistance.service.core;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.transaction.Transactional;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.model.core.AuditableEntity;
import ioffice.br.persistance.service.administration.AdministrationObjectCache;
import ioffice.br.persistance.service.administration.AuditLogService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base service for generic method with audit events and business logic.
 * 
 * @param <AuditableEntity>
 */
public class GenericAuditService<T extends AuditableEntity> {

	protected AbstractHibernateDao<T> dao;

	@Autowired
	AuditLogService auditLog;
	
	@Autowired
	AdministrationObjectCache administrationObjectCache;

	public GenericAuditService(AbstractHibernateDao<T> dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void edit(T model) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		String parameterClassName = pt.getActualTypeArguments()[0].toString().split("\\s")[1];
		T newModel = (T) Class.forName(parameterClassName).newInstance();

		BeanUtils.copyProperties(model, newModel);

		model = findById(model.getId());
		model.setDeleted(true);

		Long newObjectId = this.dao.insert(newModel);

		auditLog.logAuditEvent(administrationObjectCache.getDomainObjectByCode(model.getDomainObjectType().name()), administrationObjectCache.getAuditActionByCode(AuditActionType.EDIT.name()), model.getId(), newObjectId, newModel.getComments());
	}

	@Transactional
	public void correct(T model) {
		dao.update(model);
		auditLog.logAuditEvent(administrationObjectCache.getDomainObjectByCode(model.getDomainObjectType().name()), administrationObjectCache.getAuditActionByCode(AuditActionType.CORRECT.name()), model.getId(), model.getId(), model.getComments());
	}

	@Transactional
	public void insert(T model) {
		Long id = this.dao.insert(model);
		model.setId(id);
		auditLog.logAuditEvent(administrationObjectCache.getDomainObjectByCode(model.getDomainObjectType().name()), administrationObjectCache.getAuditActionByCode(AuditActionType.INSERT.name()), model.getId(), model.getId(), model.getComments());
	}

	@Transactional
	public void delete(T model) {
		String comments = model.getComments();
		model = findById(model.getId());
		model.setDeleted(true);
		model.setComments(comments);
		dao.delete(model);
		auditLog.logAuditEvent(administrationObjectCache.getDomainObjectByCode(model.getDomainObjectType().name()), administrationObjectCache.getAuditActionByCode(AuditActionType.DELETE.name()), model.getId(), model.getId(), model.getComments());
	}

	public T findById(Long id) {
		return this.dao.findById(id);
	}

	public List<T> loadAll() {
		return dao.loadAll();
	}

	public Long getCountAll() {
		return dao.getCountAll();
	}
}