package ioffice.br.persistance.enums;

public enum PriviledgeType implements GenericEnum {
	VIEW, ADM;

	private final String inlLabel;

	PriviledgeType() {
		this.inlLabel = "common.enum.priviledgeType" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
