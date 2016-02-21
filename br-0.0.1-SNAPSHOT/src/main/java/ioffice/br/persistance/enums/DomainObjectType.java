package ioffice.br.persistance.enums;


public enum DomainObjectType implements GenericEnum{
	
	//Vidiniai klasifikatoriai
	PERSON(DomainType.CLI_INT, 0, "/pages/administration/classification/internal/person-list.xhtml", ""), 
	PORT(DomainType.CLI_INT, 1, "/pages/administration/classification/internal/port-list.xhtml", ""), 
	COUNTRY(DomainType.CLI_INT, 2, "/pages/administration/classification/internal/country-list.xhtml", ""), 
	ECONOMIC_ZONE(DomainType.CLI_INT, 3, "/pages/administration/classification/internal/economicZone-list.xhtml", ""), 
	EFFORT_ZONE(DomainType.CLI_INT, 4, "/pages/administration/classification/internal/effortZone-list.xhtml", ""), 
	RFMO(DomainType.CLI_INT, 5, "/pages/administration/classification/internal/rfmo-list.xhtml", ""), 
	FAO_ZONE(DomainType.CLI_INT, 6, "/pages/administration/classification/internal/faoZone-list.xhtml", ""), 
	MEASURE_UNIT(DomainType.CLI_INT, 7, "/pages/administration/classification/internal/measureUnit-list.xhtml", ""),
	ICES_STATIC_SQUARE(DomainType.CLI_INT, 8, "/pages/administration/classification/internal/icesStatisticRectangle-list.xhtml", ""),
	SPECIES(DomainType.CLI_INT, 9, "/pages/administration/classification/internal/species-list.xhtml", ""),
	FISH_PRESERVATION_STATE(DomainType.CLI_INT, 10, "/pages/administration/classification/internal/fishPreservationState-list.xhtml", ""),
	FISH_PRESENTATION(DomainType.CLI_INT, 11, "/pages/administration/classification/internal/fishPresentation-list.xhtml", ""),
	FISH_PACKAGING(DomainType.CLI_INT, 12, "/pages/administration/classification/internal/fishPackaging-list.xhtml", ""),
	LITTORAL_FISHING_BAR(DomainType.CLI_INT, 13, "/pages/administration/classification/internal/littoralFishingBar-list.xhtml", ""),
	GEAR(DomainType.CLI_INT, 14, "/pages/administration/classification/internal/gear-list.xhtml", ""),
	CURRENCY(DomainType.CLI_INT, 15, "/pages/administration/classification/internal/currency-list.xhtml", ""),
	
	//Aktualus klasifikatoriai
	ACT_PERSON(DomainType.CLI_ACT, 0, "/pages/administration/classification/actual/actualPerson-list.xhtml", ""), 
	
	//Administravimas
	USER(DomainType.ADM, 0, "/pages/administration/users/user-list.xhtml", "icon-link-outline"), 
	ROLE(DomainType.ADM, 1, "/pages/administration/roles/role-list.xhtml", "icon-link-outline"), 
	AUDITLOG(DomainType.ADM, 2, "", true),
	
	//Laivu rejestras
	VESSEL(DomainType.VES, 0, "/pages/vesselreg/vessel-list.xhtml", "icon-link-outline"),
	VESSEL_CATEGORY(DomainType.VES, 1, "", true),
	
	//Limitai ir kvotos
	LIMIT(DomainType.LIM, 0, "/pages/limits/limit-create.xhtml", "");
	
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