package ioffice.br.persistance.enums;

public enum VesselCategoryType {
	FISHING_VESSEL, NON_FISHING_VESSEL;

	private final String inlLabel;

	VesselCategoryType() {
		this.inlLabel = "common.enum.vesselCategoryType" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
