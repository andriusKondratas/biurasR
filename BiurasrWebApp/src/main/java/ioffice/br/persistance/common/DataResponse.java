package ioffice.br.persistance.common;

import java.util.List;

import ioffice.br.persistance.model.core.AbstractEntity;

public class DataResponse<T extends AbstractEntity> {
	List<T> entities;
	int first;
	int total;

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
