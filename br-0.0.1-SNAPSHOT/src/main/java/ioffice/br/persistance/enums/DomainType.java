package ioffice.br.persistance.enums;

import java.util.ArrayList;
import java.util.List;

public enum DomainType {
	FIL(null, 0, "", "fa fa-group"), // Žvejybos žurnalai
	INT(null, 1, "", ""), // Integracinis modulis
	SAL(null, 2, "", ""), // Pirminiai pardavmai
	LIM(null, 3, "", "fa fa-institution"), // Limitai ir kvotos
	VES(null, 4, "", "fa fa-ship"), // Laivu rejestras
	REP(null, 5, "", ""), // Ataskaitos
	TRA(null, 6, "", ""), // Atsekamumo modulis
	CLI(null, 7, "", "fa fa-cubes"), // Klasifikatoriai
	CLI_INT(CLI, 8, "/pages/administration/classification/classification.xhtml", "fa fa-cube"), // Klasifikatoriai - > Vidiniai
	CLI_MDR(CLI, 9, "fa fa-magnet"), // Klasifikatoriai - > MDR
	CLI_ACT(CLI, 10, "/pages/administration/classification/actualClassification.xhtml", "fa fa-drupal"), // Klasifikatoriai - > Aktualus irasai
	ADM(null, 11, "", "fa fa-cogs"), // Administravimas
	OLD(null, 12, "", "");// Indegracija su IZDIS

	private final Integer seqNo;
	private final String inlLabel;
	private final String url;
	private final String icon;
	private final DomainType parentDomain;

	DomainType(DomainType parentDomain, Integer seqNo, String icon) {
		this.seqNo = seqNo;
		this.inlLabel = "common.enum.domainType" + "." + this.name();
		this.parentDomain = parentDomain;
		this.url = "";
		this.icon = icon;
	}

	DomainType(DomainType parentDomain, Integer seqNo, String url, String icon) {
		this.seqNo = seqNo;
		this.inlLabel = "common.enum.domainType" + "." + this.name();
		this.parentDomain = parentDomain;
		this.url = url;
		this.icon = icon;
	}

	public String getInlLabel() {
		return this.inlLabel;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public String getUrl() {
		return url;
	}

	public String getIcon() {
		return icon;
	}

	public DomainType getParentDomain() {
		return parentDomain;
	}

	public List<DomainType> getChildDomains() {
		List<DomainType> childDomains = new ArrayList<DomainType>();

		// is leaf itself
		if (this.getParentDomain() != null) {
			return childDomains;
		}

		for (DomainType domainTypeChild : DomainType.values()) {
			if (domainTypeChild.getParentDomain() != null && domainTypeChild.getParentDomain().equals(this)) {
				childDomains.add(domainTypeChild);
			}
		}

		return childDomains;
	}
}
