package ioffice.br.pageflow.administration.datamodels;

import ioffice.br.pageflow.common.datamodels.AbstractDataObject;
import ioffice.br.persistance.enums.DomainObjectType;
import ioffice.br.persistance.enums.PriviledgeType;

public class PriviledgeTreeDataObject extends AbstractDataObject {

	private String name;
	private DomainObjectType domainObjectType;
	private PriviledgeType priviledgeType;

	
	public PriviledgeTreeDataObject(String name) {
		this.name = name;
	}
	
	public PriviledgeTreeDataObject(DomainObjectType domainObjectType, PriviledgeType priviledgeType, String id, boolean selected) {
		this.domainObjectType = domainObjectType;
		this.priviledgeType = priviledgeType;
		this.id = id;
		this.selected = selected;
	}

	public DomainObjectType getDomainObjectType() {
		return domainObjectType;
	}

	public void setDomainObjectType(DomainObjectType domainObjectType) {
		this.domainObjectType = domainObjectType;
	}

	public PriviledgeType getPriviledgeType() {
		return priviledgeType;
	}

	public void setPriviledgeType(PriviledgeType priviledgeType) {
		this.priviledgeType = priviledgeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
