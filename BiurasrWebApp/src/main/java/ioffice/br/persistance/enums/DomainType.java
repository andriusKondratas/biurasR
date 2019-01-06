package ioffice.br.persistance.enums;

import java.util.ArrayList;
import java.util.List;

public enum DomainType {
	PRO(null, 0, "", "fa fa-institution"), // Projektai
	CLI(null, 1, "", "fa fa-cubes"), // Klasifikatoriai
	CLI_INT(CLI, 2, "/pages/administration/classification/classification.xhtml", "fa fa-cube"), // Klasifikatoriai
																								// -
																								// >
																								// Vidiniai
	ADM(null, 3, "", "fa fa-cogs"); // Administravimas

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
