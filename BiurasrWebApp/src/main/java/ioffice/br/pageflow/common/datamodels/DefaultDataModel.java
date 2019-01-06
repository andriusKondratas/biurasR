package ioffice.br.pageflow.common.datamodels;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class DefaultDataModel<T extends AbstractDataObject> extends ListDataModel<T> implements SelectableDataModel<T> {

	public DefaultDataModel(List<T> data) {
		super(data);
	}

	@Override
	public T getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) getWrappedData();

		for (T obj : list) {
			if (obj.getId().equals(rowKey)) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(T item) {
		return item.getId();
	}
}