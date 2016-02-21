package ioffice.br.persistance.dao.administration;

import java.util.List;

import ioffice.br.pageflow.administration.datamodels.AuditLogListDataObject;
import ioffice.br.persistance.common.AuditLogListObjectResultTransformer;
import ioffice.br.persistance.dao.core.AbstractHibernateDaoImpl;
import ioffice.br.persistance.model.administration.AuditLog;

import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

@Repository
public class AuditLogDaoImpl extends AbstractHibernateDaoImpl<AuditLog> implements AuditLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditLogListDataObject> findByObjectIdAndObjectType(Long objectId, Long objTypeId) {
		/* @formatter:off */
		String queryStr = "select "+
							"distinct a.id \"id\",  "+
							"a.audit_action_id \"auditActionId\", "+
							"ac.code \"actionCode\", "+
							"a.domain_object_id \"domainObjectId\", "+
							"ob.code \"objectCode\", "+
						    "a.comments \"comments\", "+
						    "to_char(a.eventdate,'yyyy.MM.dd HH24:mi:ss') \"eventDate\", "+
						    "a.poid \"poid\", "+
						    "a.coid \"coid\", "+
						    "u.name||', '||u.lastname \"eventUser\" "+
						    "from adm_auditlog a  "+
						    " inner join adm_user u on u.id = a.user_id "+
						    " inner join adm_audit_action ac on ac.id = a.audit_action_id "+
						    " inner join adm_domain_object ob on ob.id = a.domain_object_id "+
						    " inner join ( "+
						    "   select  "+
						    "     distinct id "+
						    "     from adm_auditlog "+
						    "     where adm_domain_object_id = :obid "+
						    "     start with coid = :coid "+
						    "     connect by prior poid = coid and prior id > id "+
						    "   ) b on b.id = a.id ";
		
		
		SQLQuery query = (SQLQuery) getCurrentSession()
				.createSQLQuery(queryStr)
				.addScalar("id", StandardBasicTypes.STRING)
				.addScalar("auditActionId", StandardBasicTypes.LONG)
				.addScalar("actionCode", StandardBasicTypes.STRING)
				.addScalar("domainObjectId", StandardBasicTypes.LONG)
				.addScalar("objectCode", StandardBasicTypes.STRING)
				.addScalar("comments", StandardBasicTypes.STRING)
				.addScalar("eventDate",StandardBasicTypes.STRING)
				.addScalar("poid", StandardBasicTypes.LONG)
				.addScalar("coid", StandardBasicTypes.LONG)
				.addScalar("eventUser", StandardBasicTypes.STRING)
				.setParameter("coid", objectId)
				.setParameter("obid", objTypeId);
	  /* @formatter:on */
		query.setResultTransformer(new AuditLogListObjectResultTransformer(false));
		return (List<AuditLogListDataObject>) query.list();
	}
}
