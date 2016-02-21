package ioffice.br.persistance.service.core;

import java.util.List;

import ioffice.br.persistance.dao.core.AbstractHibernateDao;
import ioffice.br.persistance.model.core.AbstractEntity;

/**
 * Base service for generic methods.
 */
public class GenericService<T extends AbstractEntity> {
	protected AbstractHibernateDao<T> dao;

	public GenericService(AbstractHibernateDao<T> dao) {
		this.dao = dao;
	}

	public void saveOrUpdate(T model) {
		if (model.getId() == null) {
			Long id = this.dao.insert(model);
			model.setId(id);
		} else {
			dao.update(model);
		}
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

	public void delete(T entity) {
		dao.delete(entity);
	}
}