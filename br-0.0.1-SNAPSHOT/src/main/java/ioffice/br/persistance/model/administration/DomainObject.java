package ioffice.br.persistance.model.administration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.core.AbstractEntity;

@Entity
@Table(name = "ADM_DOMAIN_OBJECT")
@SequenceGenerator(name = "default_gen", sequenceName = "domain_object_seq", allocationSize = 1)
public class DomainObject extends AbstractEntity {

	@Column(unique = true, nullable=false, length=50)
	@Enumerated(EnumType.STRING)
	private DomainObjectType code;

	public DomainObjectType getCode() {
		return code;
	}

	public void setCode(DomainObjectType code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code.name();
	}
}
