package ioffice.br.persistance.common;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class DataRequest {
	private int first;
	private int pageSize;
	private List<SortMeta> multiSortMeta;
	private String sortField; 
	private SortOrder sortOrder; 
	private Map<String,Object> filters;
	private boolean deleted;

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<SortMeta> getMultiSortMeta() {
		return multiSortMeta;
	}

	public void setMultiSortMeta(List<SortMeta> multiSortMeta) {
		this.multiSortMeta = multiSortMeta;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
