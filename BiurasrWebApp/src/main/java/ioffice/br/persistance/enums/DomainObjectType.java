package ioffice.br.persistance.enums;


public enum DomainObjectType implements GenericEnum{
	
	//Vidiniai klasifikatoriai
	PERSON(DomainType.CLI_INT, 0, "/pages/administration/classification/internal/person-list.xhtml", ""), 
	
	//Administravimas
	USER(DomainType.ADM, 0, "/pages/administration/users/user-list.xhtml", "icon-link-outline"), 
	ROLE(DomainType.ADM, 1, "/pages/administration/roles/role-list.xhtml", "icon-link-outline"), 
	
	//Projektai
	PROJECT(DomainType.PRO, 0, "/pages/vesselreg/vessel-list.xhtml", "icon-link-outline");
	
	private final Integer seqNo;
	private final boolean hidden;
	private final String inlLabel;
	private final String url;
	private final String icon;
	private final DomainType domain;
	

	DomainObjectType(DomainType domain, Integer seqNo, String url, String icon) {
		this.inlLabel = "common.enum.domainObjectType" + "." + this.name();
		this.domain = domain;
		this.seqNo = seqNo;
		this.hidden = false;
		this.url = url;
		this.icon = icon;
	}
	
	DomainObjectType(DomainType domain, Integer seqNo, String url, boolean hidden) {
		this.inlLabel = "common.enum.domainObjectType" + "." + this.name();
		this.domain = domain;
		this.seqNo = seqNo;
		this.hidden = hidden;
		this.url = url;
		this.icon = "";
	}

	public String getInlLabel() {
		return this.inlLabel;
	}

	public DomainType getDomain() {
		return domain;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public boolean isHidden() {
		return hidden;
	}
	
	

	public String getIcon() {
		return icon;
	}

	public String getUrl() {
		return url;
	}
}