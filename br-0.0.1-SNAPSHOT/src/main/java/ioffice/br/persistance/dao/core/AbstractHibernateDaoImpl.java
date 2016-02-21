package ioffice.br.persistance.dao.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import ioffice.br.persistance.common.DataRequest;
import ioffice.br.persistance.common.GridResult;
import ioffice.br.persistance.model.core.AbstractEntity;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

@Transactional
public abstract class AbstractHibernateDaoImpl<E extends AbstractEntity> implements AbstractHibernateDao<E> {

	public static final SimpleExpression RESTRICTION_NOT_DELETED = Restrictions.eq("deleted", false);
	protected Class<E> type;
	Logger logger = Logger.getLogger(AbstractHibernateDaoImpl.class);
	@Inject
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public AbstractHibernateDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		if (!(t instanceof ParameterizedType)) {
			logger.error("Class " + getClass().getName() + " must be properly defined.");
		}
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<E>) pt.getActualTypeArguments()[0];
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public E findById(Long id) {
		return (E) getCurrentSession().get(type, id);
	}

	public Long insert(E entity) {
		return (Long) getCurrentSession().save(entity);
	}

	public void update(E entity) {
		getCurrentSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public List<E> loadAll() {
		return getCurrentSession().createCriteria(type).add(RESTRICTION_NOT_DELETED).list();
	}

	@SuppressWarnings("unchecked")
	public List<E> load(int firstResult, int maxResults) {
		return getCurrentSession().createCriteria(type).add(RESTRICTION_NOT_DELETED).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public Long getCountAll() {
		return ((Number) getCurrentSession().createCriteria(type).setProjection(Projections.rowCount()).uniqueResult()).longValue();
	}

	public void delete(E entity) {
		getCurrentSession().delete(entity);
	}

	public GridResult<E> findByRequest(DataRequest dataRequest) {

		Criteria criteria = getCurrentSession().createCriteria(type);

		addSortingCriteria(criteria, dataRequest);
		addFilteringCriteria(criteria, dataRequest);

		GridResult<E> result = new GridResult<E>();

		int firstResult = (dataRequest.getCurrent() - 1) * dataRequest.getRowCount();
		int maxResults = dataRequest.getRowCount();

		@SuppressWarnings("unchecked")
		List<E> entities = criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();

		result.setObjects(entities);
		result.setCurrent(dataRequest.getCurrent());

		criteria = getCurrentSession().createCriteria(type);
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

		result.setTotal(count.intValue());

		return result;
	}

	protected void addSortingCriteria(Criteria criteria, DataRequest dataRequest) {
		// Must be implemented separately for each type if necessary.
	}

	protected void addFilteringCriteria(Criteria criteria, DataRequest dataRequest) {
		// Must be implemented separately for each type if necessary.
	}

	protected void addDateRange(Criteria criteria, String field, LocalDate dateFrom, LocalDate dateTo) {
		if (dateFrom != null) {
			criteria.add(Restrictions.ge(field, Date.from(dateFrom.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le(field, Date.from(dateTo.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant())));
		}
	}
}
