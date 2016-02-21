package ioffice.br.persistance.enums;


public enum PersonState implements GenericEnum{
	ACTIVE, NOT_ACTIVE, STOPPED;

	private final String inlLabel;

	PersonState() {
		this.inlLabel = "common.enum.personState" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
