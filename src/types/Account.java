package types;

import java.util.Date;
import java.util.List;

import dataaccess.DAL;

public class Account extends BaseType {

	// Attribute mapping to db column
	public static final String ACCOUNT_ID = "account_id";
	public static final String CREATE_DATE = "create_date";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String POWERCOMPANY_LOGIN = "powercompany_login";
	public static final String POWERCOMPANY_VERIFIED = "powercompany_verified";
	public static final String POWERCOMPANY_ID = "powercompany_id";

	private static final String INCENTIVES = "incentives";
	private static final String DEVICES = "devices";
	private static final String NOTIFICATIONS = "notifications";
	private static final String POWERCOMPANY = "powercompany";

	public Account() {
		super();

		// These attributes must match the attribute names in the database
		attributes.put(ACCOUNT_ID, null);
		attributes.put(CREATE_DATE, null);
		attributes.put(USERNAME, null);
		attributes.put(PASSWORD, null);
		attributes.put(POWERCOMPANY_LOGIN, null);
		attributes.put(POWERCOMPANY_VERIFIED, null);
		attributes.put(POWERCOMPANY_ID, null);

		// These are local attributes
		attributes.put(INCENTIVES, null);
		attributes.put(DEVICES, null);
		attributes.put(NOTIFICATIONS, null);
		attributes.put(POWERCOMPANY, null);
	}

	public Integer getAccountId() {
		try {
			return (Integer) attributes.get(ACCOUNT_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setAccountId(Integer accountId) {
		setAttribute(ACCOUNT_ID, accountId);
	}

	public Date getCreateDate() {
		try {
			return (Date) attributes.get(CREATE_DATE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setCreateDate(Date createDate) {
		setAttribute(CREATE_DATE, createDate);
	}

	public String getUsername() {
		try {
			return (String) attributes.get(USERNAME);
		} catch (Exception e) {
			return null;
		}
	}

	public void setUsername(String username) {
		setAttribute(USERNAME, username);
	}

	public String getPassword() {
		try {
			return (String) attributes.get(PASSWORD);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPassword(String password) {
		setAttribute(PASSWORD, password);
	}

	public String getPowercompanyLogin() {
		try {
			return (String) attributes.get(POWERCOMPANY_LOGIN);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPowercompanyLogin(String powercompanyLogin) {
		setAttribute(POWERCOMPANY_LOGIN, powercompanyLogin);
	}

	public Boolean isPowercompanyVerified() {
		try {
			return (Boolean) attributes.get(POWERCOMPANY_VERIFIED);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPowercompanyVerified(Boolean powercompanyVerified) {
		setAttribute(POWERCOMPANY_VERIFIED, powercompanyVerified);
	}

	public Integer getPowercompanyId() {
		try {
			return (Integer) attributes.get(POWERCOMPANY_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPowercompanyId(Integer powercompanyId) {
		setAttribute(POWERCOMPANY_ID, powercompanyId);
	}

	@SuppressWarnings("unchecked")
	public List<Device> getDevices() {
		try {
			return (List<Device>) attributes.get(DEVICES);
		} catch (Exception e) {
			return null;
		}
	}

	public void addDevice(Device device) {
		List<Device> devices = null;
		if ((devices = getDevices()) != null) {
			if (!devices.contains(device)) {
				devices.add(device);
				setDeviceList(devices);
			}
		}
	}

	public void setDeviceList(List<Device> list) {
		setAttribute(DEVICES, list);
	}

	public void removeDevice(Device device) {
		List<Device> devices = null;
		if ((devices = getDevices()) != null) {
			if (devices.contains(device)) {
				devices.remove(device);
				setDeviceList(devices);
			}
		}
	}

	public void setPowerCompany(PowerCompany powercompany) {
		setAttribute(POWERCOMPANY, powercompany);
	}

	public PowerCompany getPowerCompany() {
		try {
			return (PowerCompany) attributes.get(POWERCOMPANY);
		} catch (Exception e) {
			return null;
		}
	}

	public void setIncentiveList(List<Incentive> incentiveList) {
		setAttribute(INCENTIVES, incentiveList);
	}

	@SuppressWarnings("unchecked")
	public List<Incentive> getIncentiveList() {
		try {
			return (List<Incentive>) attributes.get(INCENTIVES);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Notification> getNotificationList() {
		try {
			return (List<Notification>) attributes.get(NOTIFICATIONS);
		} catch (Exception e) {
			return null;
		}
	}

	public void addNotification(Notification notification) {
		List<Notification> notifications = null;
		if ((notifications = getNotificationList()) != null) {
			if (!notifications.contains(notification)) {
				notifications.add(notification);
				setNotificationList(notifications);
			}
		}
	}

	public void setNotificationList(List<Notification> notifications) {
		setAttribute(NOTIFICATIONS, notifications);
	}
	
	@Override
	public void mapIdsToObjects(DAL dal) {
		Integer pcid = null;
		if ((pcid = getPowercompanyId()) != null) {
			PowerCompany pc = null;
			if ((pc = dal.getPowerCompany(pcid)) != null) {
				setPowerCompany(pc);
			}
		}
		Integer accid = null;
		if ((accid = getAccountId()) != null) {
			List<Device> devices = null;
			if ((devices = dal.getDevices(accid)) != null) {
				setDeviceList(devices);
			}
			List<Notification> notifications = null;
			if ((notifications = dal.getNotifications(accid)) != null) {
				setNotificationList(notifications);
			}
			List<Incentive> incentives = null;
			if ((incentives = dal.getAccountIncentives(accid)) != null) {
				setIncentiveList(incentives);
			}
		}
	}
}
