package ioffice.br.persistance.enums;

public enum ActivityType {
	CAPTAIN, VESSEL_OWNER, INSPECTOR, OPERATOR;

	private final String inlLabel;

	ActivityType() {
		this.inlLabel = "common.enum.activityType" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
