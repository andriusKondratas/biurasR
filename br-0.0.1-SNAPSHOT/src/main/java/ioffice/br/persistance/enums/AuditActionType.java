package ioffice.br.persistance.enums;

public enum AuditActionType implements GenericEnum {
	EDIT, CORRECT, INSERT, DELETE;

	private final String inlLabel;

	AuditActionType() {
		this.inlLabel = "common.enum.auditActionType" + "." + this.name();
	}

	public String getInlLabel() {
		return this.inlLabel;
	}
}
