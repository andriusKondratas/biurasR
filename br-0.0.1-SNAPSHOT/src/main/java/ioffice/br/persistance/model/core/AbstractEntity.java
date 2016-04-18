package ioffice.br.persistance.model.core;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.model.administration.User;

@MappedSuperclass
public abstract class AbstractEntity implements Convertable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
	protected Long id;

	@Column(columnDefinition="number(1,0) default 0 not null")
	private boolean deleted;
	
	@Transient
	protected DomainObjectType domainObjectType;
	
	@Transient
	public DomainObjectType getDomainObjectType() {
		return domainObjectType;
	}
	
	@Override
	public String toString() {
		return getDomainObjectType().name() + " [id=" + id + "]";
	}

	@Override
	public String toConverterString() {
		return id.toString() + getDomainObjectType().name();
	}
	
	@Override
	public String toLocalizedString(Locale locale) {
		return "Must be Overrided";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AbstractEntity) {
			AbstractEntity ae = (AbstractEntity) o;
			if (this.getId() != null && ae.getId() != null) {
				return this.getId().equals(ae.getId());
			}
		}
		return this == o;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}
}
