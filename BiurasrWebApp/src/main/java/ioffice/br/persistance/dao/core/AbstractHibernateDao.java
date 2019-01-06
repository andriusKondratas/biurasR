package ioffice.br.persistance.dao.core;

import java.util.List;

import ioffice.br.persistance.common.DataRequest;
import ioffice.br.persistance.common.DataResponse;
import ioffice.br.persistance.model.core.AbstractEntity;

public interface AbstractHibernateDao<E extends AbstractEntity> {

	E findById(Long id);

	DataResponse<E> findByRequest(DataRequest dataRequest);

	Long insert(E entity);

	void update(E entity);

	List<E> loadAll();

	Long getCountAll();

	void delete(E entity);
}
