package ioffice.br.persistance.dao.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import ioffice.br.persistance.common.DataRequest;
import ioffice.br.persistance.common.DataResponse;
import ioffice.br.persistance.model.administration.User;
import ioffice.br.persistance.model.core.AbstractEntity;
import ioffice.br.persistance.model.core.AuditableEntity;
import ioffice.br.persistance.service.administration.UserService;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.context.SecurityContextHolder;

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
	
	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(type);
	}
	
	protected Criteria createNotDeletedCriteria() {
		return createCriteria().add(RESTRICTION_NOT_DELETED);
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
		return createNotDeletedCriteria().list();
	}

	@SuppressWarnings("unchecked")
	public List<E> load(int firstResult, int maxResults) {
		return createNotDeletedCriteria().setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public Long getCountAll() {
		return ((Number) createCriteria().setProjection(Projections.rowCount()).uniqueResult()).longValue();
	}

	public void delete(E entity) {
		getCurrentSession().delete(entity);
	}

	public DataResponse<E> findByRequest(DataRequest dataRequest) {
		DataResponse<E> result = new DataResponse<E>();

		Criteria criteria = createCriteria();
		if(!dataRequest.isDeleted()){
			criteria.add(RESTRICTION_NOT_DELETED);
		}
		
		addFilteringCriteria(criteria, dataRequest);
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		result.setTotal(count.intValue());
		result.setFirst(dataRequest.getFirst());

		criteria = createCriteria();
		if(!dataRequest.isDeleted()){
			criteria.add(RESTRICTION_NOT_DELETED);
		}
		
		addSortingCriteria(criteria, dataRequest);
		addFilteringCriteria(criteria, dataRequest);

		if (result.getTotal() > 0) {
			int firstResult = dataRequest.getFirst();
			int maxResults = dataRequest.getPageSize();

			@SuppressWarnings("unchecked")
			List<E> entities = criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
			result.setEntities(entities);
		}
		return result;
	}
	
	protected void addSortingCriteria(Criteria criteria, DataRequest dataRequest) {
		String sortField = dataRequest.getSortField();
		SortOrder sortOrder = dataRequest.getSortOrder();

		if (sortField != null) {
			String[] subTypes = sortField.split("\\.");
			if (subTypes.length > 1) {
				criteria.createAlias(subTypes[0], subTypes[0]);
			}

			if (SortOrder.ASCENDING.equals(sortOrder)) {
				criteria.addOrder(Order.asc(sortField));
			} else if (SortOrder.DESCENDING.equals(sortOrder)) {
				criteria.addOrder(Order.desc(sortField));
			}
		}
	}

	protected void addFilteringCriteria(Criteria criteria, DataRequest dataRequest) {
		// Must be implemented separately for each type if necessary.
	}

	protected void addDateRange(Criteria criteria, String field, Date dateFrom, Date dateTo) {
		if (dateFrom != null) {
			criteria.add(Restrictions.ge(field, dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le(field, dateTo));
		}
	}
	
	protected void addBigDecimalRange(Criteria criteria, String field, BigDecimal decimalFrom, BigDecimal decimalTo) {
		if (decimalFrom != null && !BigDecimal.ZERO.equals(decimalFrom)) {
			criteria.add(Restrictions.ge(field, decimalFrom));
		}
		if (decimalTo != null && !BigDecimal.ZERO.equals(decimalTo)) {
			criteria.add(Restrictions.le(field, decimalTo));
		}
	}
	
	public void addAuditInfo(E model, UserService userService) {
		if (model instanceof AuditableEntity) {

			// Should be unnecessary after users are created from script
			// Throws NullPointerException when users are created from BootStrapBean
			User user;
			try {
				user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			}
			catch (NullPointerException e) {
				user = userService.findByUsername("admin@namoprojektas.eu");
			}

			AuditableEntity entity = (AuditableEntity) model;
			
			entity.setDateModified(new Date());
			entity.setModifiedBy(user);

			if (entity.getDateCreated() == null) {
				entity.setDateCreated(entity.getDateModified());
			}

			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(entity.getModifiedBy());
			}

		}
	}		
}
