package types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bacnetaccess.BACNetConnector;

import dataaccess.DAL;

public class Device extends BaseType {

	// Attribute mapping to db column
	public static final String DEVICE_ID = "device_id";
	public static final String LOCATION = "location";
	public static final String PRIORITY = "priority";
	public static final String NAME = "name";
	public static final String BACNET_ID = "bacnet_id";
	public static final String DESCRIPTION = "description";
	public static final String ACCOUNT_ID = "account_id";

	private static final String USAGES = "usages";

	public Device() {
		super();
		attributes.put(DEVICE_ID, null);
		attributes.put(LOCATION, null);
		attributes.put(PRIORITY, null);
		attributes.put(NAME, null);
		attributes.put(BACNET_ID, null);
		attributes.put(DESCRIPTION, null);
		attributes.put(ACCOUNT_ID, null);

		attributes.put(USAGES, null);
	}

	public void setAccountId(Integer accountId) {
		setAttribute(ACCOUNT_ID, accountId);
	}

	public Integer getAccountId() {
		try {
			return (Integer) attributes.get(ACCOUNT_ID);
		} catch (Exception e) {
			return null;
		}
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

	public String getLocation() {
		try {
			return (String) attributes.get(LOCATION);
		} catch (Exception e) {
			return null;
		}
	}

	public void setLocation(String location) {
		setAttribute(LOCATION, location);
	}

	public Integer getPriority() {
		try {
			return (Integer) attributes.get(PRIORITY);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPriority(Integer priority) {
		setAttribute(PRIORITY, priority);
	}

	public String getName() {
		try {
			return (String) attributes.get(NAME);
		} catch (Exception e) {
			return null;
		}
	}

	public void setName(String name) {
		setAttribute(NAME, name);
	}

	public Integer getBacnetId() {
		try {
			return (Integer) attributes.get(BACNET_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setBacnetId(Integer bacnetId) {
		setAttribute(BACNET_ID, bacnetId);
	}

	public String getDescription() {
		try {
			return (String) attributes.get(DESCRIPTION);
		} catch (Exception e) {
			return null;
		}
	}

	public void setDescription(String description) {
		setAttribute(DESCRIPTION, description);
	}

	public void addUsage(Usage usage) {
		List<Usage> usages = null;
		if ((usages = getUsageList()) != null) {
			usages.add(usage);
			setUsageList(usages);
		} else {
			usages = new ArrayList<Usage>();
			usages.add(usage);
		}
	}

	public Usage getUsageForToday(){
		Usage u = new Usage();
		u.setDeviceId(getDeviceId());
		u.setRecordDate(DAL.getDate());
		
		BACNetConnector connector = new BACNetConnector();
		Double usage = connector.getPowerUsage(this.getBacnetId());
		u.setTotalKW(usage);
		return u;
	}
	
	
	public Usage getUsage(Date date) {
		List<Usage> usages = getUsageList();
		if (usages != null) {
			for (int a = 0; a < usages.size(); a++) {
				if (usages.get(a).getRecordDate().equals(date)) {
					return usages.get(a);
				}
			}
		}
		return null;
	}

	public void setUsageList(List<Usage> usages) {
		setAttribute(USAGES, usages);
	}

	@SuppressWarnings("unchecked")
	public List<Usage> getUsageList() {
		try {
			return (List<Usage>) attributes.get(USAGES);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void mapIdsToObjects(DAL dal) {
		Integer devid = null;
		if ((devid = getDeviceId()) != null) {
			List<Usage> usages = null;
			if ((usages = dal.getUsage(devid)) != null) {
				setUsageList(usages);
			}
		}
	}
}
