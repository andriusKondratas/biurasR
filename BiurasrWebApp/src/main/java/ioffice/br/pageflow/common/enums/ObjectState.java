package ioffice.br.pageflow.common.enums;

public enum ObjectState {
	NEW, OLD, UNKNOWN;

	public boolean anyOf(ObjectState... states) {
		for (ObjectState s : states) {
			if (this.equals(s)) {
				return true;
			}
		}
		return false;
	}
}
