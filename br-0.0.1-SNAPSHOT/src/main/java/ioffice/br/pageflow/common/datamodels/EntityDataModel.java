package ioffice.br.pageflow.common.datamodels;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import ioffice.br.persistance.model.core.AbstractEntity;

public class EntityDataModel <T extends AbstractEntity> extends ListDataModel<T> implements SelectableDataModel<T>{
	
	public EntityDataModel(List<T> data) {
		super(data);
	}

	@Override
	public T getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) getWrappedData();

		for (T obj : list) {
			if (obj.getId() == Long.getLong(rowKey)) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(T item) {
		return item.getId().toString();
	}
}
