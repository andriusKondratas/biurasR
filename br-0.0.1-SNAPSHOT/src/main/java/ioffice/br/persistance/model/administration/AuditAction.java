package ioffice.br.persistance.model.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ioffice.br.persistance.enums.AuditActionType;
import ioffice.br.persistance.model.core.AbstractEntity;

@Entity
@Table(name = "ADM_AUDIT_ACTION")
@SequenceGenerator(name = "default_gen", sequenceName = "audit_action_seq", allocationSize = 1)
public class AuditAction extends AbstractEntity {
	
	@Column(unique = true, nullable=false, length=50)
	@Enumerated(EnumType.STRING)
	private AuditActionType code;

	public AuditActionType getCode() {
		return code;
	}

	public void setCode(AuditActionType code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return code.name();
	}
}
