package types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dataaccess.DAL;

public abstract class BaseType {
	protected Map<String, Object> attributes = null;

	public BaseType() {
		attributes = new HashMap<String, Object>();
	}

	public void setAttribute(String attributename, Object value) {
		if (attributes.containsKey(attributename)) {
			attributes.put(attributename, value);
		}
	}

	public Object getDBValue(String attributename) {
		if (attributes.containsKey(attributename)) {
			Object obj = null;
			if ((obj = attributes.get(attributename)) != null) {
				return obj;
			}
		}
		return Types.NULL;
	}

	public abstract void mapIdsToObjects(DAL dal);

	public void setAttributes(Map<String, Object> att) {
		Map<String, Object> record = att;
		Set<String> keys = record.keySet();
		Iterator<String> keyiterator = keys.iterator();
		while (keyiterator.hasNext()) {
			String key = keyiterator.next();
			setAttribute(key, record.get(key));
		}
	}

	@Override
	public String toString() {
		String string = "";
		Set<Entry<String, Object>> entries = attributes.entrySet();
		Iterator<Entry<String, Object>> iterator = entries.iterator();
		string += "{";
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				value = "null";
			}
			if (!(value instanceof List) && !(value instanceof BaseType)) {
				string += "\"" + key + "\"" + ": " + "\"" + value.toString()
						+ "\"";
				if (iterator.hasNext()) {
					string += ",";
				} else {
					string += "}";
				}
				string += "\n";
			}
		}
		return string;
	}
}
