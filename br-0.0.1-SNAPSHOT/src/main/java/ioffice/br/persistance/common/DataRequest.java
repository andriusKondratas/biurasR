package ioffice.br.persistance.common;

import java.util.Map;

public class DataRequest {
	/**
	 * Page number. Starts from 1.
	 */
	private int current;
	/**
	 * Maximum number of results.
	 */
	private int rowCount;
	/**
	 * sorting attributes map.
	 */
	private Map<String, String> sort;

	/**
	 * @return the {@link #current}
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the {@link #current} to set
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 * @return the {@link #rowCount}
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the {@link #rowCount} to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the {@link #sort}
	 */
	public Map<String, String> getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the {@link #sort} to set
	 */
	public void setSort(Map<String, String> sort) {
		this.sort = sort;
	}

}
