package ioffice.br.persistance.model.administration;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ioffice.br.persistance.model.core.AbstractEntity;

@Entity
@Table(name = "ADM_IGNOREOBJECT", indexes = { @Index(columnList = "user_id", name = "ignoreobject_user_id_fk_idx"),
		@Index(columnList = "object_id", name = "ignoreobject_object_id_idx") })
@SequenceGenerator(name = "default_gen", sequenceName = "ido_seq", allocationSize = 1)
public class IgnoreDomainObject extends AbstractEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OBJECT_ID")
	private DomainObject object;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	@NotNull
	private Long ignore_id;

	public DomainObject getObject() {
		return object;
	}

	public void setObject(DomainObject object) {
		this.object = object;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getIgnore_id() {
		return ignore_id;
	}

	public void setIgnore_id(Long ignore_id) {
		this.ignore_id = ignore_id;
	}
}
