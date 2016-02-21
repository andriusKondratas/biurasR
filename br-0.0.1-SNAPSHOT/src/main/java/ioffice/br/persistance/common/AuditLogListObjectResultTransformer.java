package ioffice.br.persistance.common;

import java.util.Collections;
import java.util.List;

import ioffice.br.pageflow.administration.datamodels.AuditLogListDataObject;
import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.enums.DomainObjectType;

import org.hibernate.transform.ResultTransformer;

public class AuditLogListObjectResultTransformer implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private boolean reverseOrder = false;

	public AuditLogListObjectResultTransformer(boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

	@Override
	public Object transformTuple(Object[] rowData, String[] aliasNames) {
		/* @formatter:off */	  
		 /* .addScalar("id", StandardBasicTypes.STRING)
			.addScalar("auditActionId", StandardBasicTypes.LONG)
			.addScalar("actionCode", StandardBasicTypes.STRING)
			.addScalar("domainObjectId", StandardBasicTypes.LONG)
			.addScalar("objectCode", StandardBasicTypes.STRING)
			.addScalar("comments", StandardBasicTypes.STRING)
			.addScalar("eventDate",StandardBasicTypes.STRING)
			.addScalar("poid", StandardBasicTypes.LONG)
			.addScalar("coid", StandardBasicTypes.LONG)
			.addScalar("eventUser", StandardBasicTypes.STRING)*/  
		  /* @formatter:on */
		AuditLogListDataObject auditLog = new AuditLogListDataObject();
		auditLog.setId((String) rowData[0]);
		auditLog.setActionId((Long) rowData[1]);
		auditLog.setActionType(AuditActionType.valueOf((String) rowData[2]));
		auditLog.setActionCode((String) rowData[2]);
		auditLog.setObjectId((Long) rowData[3]);
		auditLog.setDomainObjectType(DomainObjectType.valueOf((String) rowData[4]));
		auditLog.setDomainObjectCode((String) rowData[4]);
		auditLog.setComments((String) rowData[5]);
		auditLog.setEventDate((String) rowData[6]);
		auditLog.setPoid((Long) rowData[7]);
		auditLog.setCoid((Long) rowData[8]);
		auditLog.setEventUser((String) rowData[9]);

		return auditLog;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List paramList) {
		if (reverseOrder) {
			Collections.reverse(paramList);
		}
		return paramList;
	}
}
