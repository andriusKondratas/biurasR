package ioffice.br.persistance.enums;

public enum RegionType {
	ATLANTIC, BALTIC, COAST;

	private final String inlLabel;

	RegionType() {
		this.inlLabel = "common.enum.regionType" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
