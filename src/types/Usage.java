package types;

import java.util.Date;

import dataaccess.DAL;

public class Usage extends BaseType {

	// Attribute mapping to db column
	public static final String TOTAL_KW = "total_kw";
	public static final String RECORD_DATE = "record_date";
	public static final String DEVICE_ID = "device_id";

	public Usage() {
		super();
		attributes.put(TOTAL_KW, null);
		attributes.put(RECORD_DATE, null);
		attributes.put(DEVICE_ID, null);
	}

	public Integer getDeviceId() {
		try {
			return (Integer) attributes.get(DEVICE_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setDeviceId(Integer deviceId) {
		setAttribute(DEVICE_ID, deviceId);
	}

	public Double getTotalKW() {
		try {
			return (Double) attributes.get(TOTAL_KW);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTotalKW(Double totalKW) {
		setAttribute(TOTAL_KW, totalKW);
	}

	public Date getRecordDate() {
		try {
			return (Date) attributes.get(RECORD_DATE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setRecordDate(Date recordDate) {
		setAttribute(RECORD_DATE, recordDate);
	}

	@Override
	public void mapIdsToObjects(DAL dal) {
	}

}
