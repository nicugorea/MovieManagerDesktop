package mmd.common.types;

public class GenericData {
	private Tuple<Object, Class<?>> data = null;

	public GenericData(Object obj, Class<?> cls) {
		data = new Tuple<Object, Class<?>>(obj, cls);
	}

	public GenericData() {

	}

	public void setDataValue(Object value) {
		if (data != null)
			data.setFirst(value);
		else
			throw new NullPointerException();
	}

	public void setDataType(Class<?> cls) {

		if (data != null)
			data.setSecond(cls);
		else
			throw new NullPointerException();
	}

	public Object getDataValue() {

		if (data != null)
			return data.getFirst();
		else
			throw new NullPointerException();
	}

	public Class<?> getDataType() {
		if (data != null)
			return data.getSecond();
		else
			throw new NullPointerException();
	}
}
