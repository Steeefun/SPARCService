package dataaccess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import types.*;

public class PostgresqlConnector extends DAL {

	public PostgresqlConnector() {
		super();
	}

	public PostgresqlConnector(String url, String username, String password) {
		super(url, username, password);
	}

	@Override
	public Account getAccount(String username, String password) {
		String sql = "select * from \"" + TB_ACCOUNT + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and lower("
				+ Account.USERNAME + ") = lower('" + username + "') and "
				+ Account.PASSWORD + " = '" + password + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				Account ac = new Account();
				ac.setAttributes(records.get(0));
				ac.mapIdsToObjects(this);
				return ac;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public Boolean createAccount(Account acc) {
		String sql = "insert into \"" + TB_ACCOUNT + "\" ("
				+ Account.CREATE_DATE + "," + Account.PASSWORD + ","
				+ Account.POWERCOMPANY_ID + "," + Account.POWERCOMPANY_LOGIN
				+ "," + Account.POWERCOMPANY_VERIFIED + "," + Account.USERNAME
				+ ") values (" + acc.getCreateDate() + ",'" + acc.getPassword()
				+ "'," + acc.getPowercompanyId() + ",'"
				+ acc.getPowercompanyLogin() + "',"
				+ acc.isPowercompanyVerified() + ",'" + acc.getUsername()
				+ "')";
		return executeSql(sql);
	}

	@Override
	public List<Device> getDevices(Integer accountId) {
		String sql = "select * from \"" + TB_DEVICE + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and  "
				+ Device.ACCOUNT_ID + " = " + accountId;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			List<Device> devices = new ArrayList<Device>();
			for (int a = 0; a < records.size(); a++) {
				Device device = new Device();
				device.setAttributes(records.get(a));
				device.mapIdsToObjects(this);
				devices.add(device);
			}
			return devices;
		}
		return null;
	}

	@Override
	public Boolean createDevice(Device dev) {
		String sql = "insert into \"" + TB_DEVICE + "\" (" + Device.ACCOUNT_ID
				+ "," + Device.BACNET_ID + "," + Device.DESCRIPTION + ","
				+ Device.LOCATION + "," + Device.NAME + "," + Device.PRIORITY
				+ ") values (" + dev.getAccountId() + ",'" + dev.getBacnetId()
				+ "','" + dev.getDescription() + "','" + dev.getLocation()
				+ "','" + dev.getName() + "'," + dev.getPriority() + ")";
		return executeSql(sql);
	}

	@Override
	public List<Notification> getNotifications(Integer accountId) {
		String sql = "select * from \"" + TB_NOTIFICATION + "\" where ("
				+ DELETED + " is null or " + DELETED + " = false) and "
				+ Notification.ACCOUNT_ID + " = " + accountId;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			List<Notification> notifications = new ArrayList<Notification>();
			for (int a = 0; a < records.size(); a++) {
				Notification notification = new Notification();
				notification.setAttributes(records.get(a));
				notification.mapIdsToObjects(this);
				notifications.add(notification);
			}
			return notifications;
		}
		return null;
	}

	@Override
	public Boolean createNotification(Notification no) {
		String sql = "insert into \"" + TB_NOTIFICATION + "\" ("
				+ Notification.ACCOUNT_ID + ","
				+ Notification.NOTIFICATIONTYPE_ID + "," + Notification.TEXT
				+ "," + Notification.TIME + "," + Notification.VIEWED + ","
				+ Notification.TITLE + ") " + "values (" + no.getAccountId()
				+ "," + no.getNotificationTypeId() + ",'" + no.getText() + "',"
				+ no.getTimestamp() + "," + no.isViewed() + ",'"
				+ no.getTitle() + "')";
		return executeSql(sql);
	}

	@Override
	public PowerCompany getPowerCompany(String name) {
		String sql = "select * from \"" + TB_POWERCOMPANY + "\" where ("
				+ DELETED + " is null or " + DELETED + " = false) and lower("
				+ PowerCompany.NAME + ") = lower('" + name + "')";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				PowerCompany pc = new PowerCompany();
				pc.setAttributes(records.get(0));
				pc.mapIdsToObjects(this);
				return pc;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public List<Incentive> getPowerCompanyIncentives(Integer powercompanyId) {
		String sql = "select * from \"" + TB_INCENTIVE + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and "
				+ Incentive.POWERCOMPANY_ID + " = '" + powercompanyId + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			List<Incentive> incentives = new ArrayList<Incentive>();
			for (int a = 0; a < records.size(); a++) {
				Incentive incentive = new Incentive();
				incentive.setAttributes(records.get(a));
				incentive.mapIdsToObjects(this);
				incentives.add(incentive);
			}
			return incentives;
		}
		return null;
	}

	@Override
	public List<Incentive> getAccountIncentives(Integer accountId) {
		String sql = "select * from \"" + TB_ACCOUNT_INCENTIVE + "\" a , \""
				+ TB_INCENTIVE + "\" b where " + Account.ACCOUNT_ID + " = '"
				+ accountId + "' and a." + Incentive.INCENTIVE_ID + " = b."
				+ Incentive.INCENTIVE_ID;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			List<Incentive> incentives = new ArrayList<Incentive>();
			for (int a = 0; a < records.size(); a++) {
				Incentive incentive = new Incentive();
				incentive.setAttributes(records.get(a));
				incentive.mapIdsToObjects(this);
				incentives.add(incentive);
			}
			return incentives;
		}

		return null;
	}

	@Override
	public List<Usage> getUsage(Integer deviceId) {
		String sql = "select * from \"" + TB_USAGE + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and " + Usage.DEVICE_ID
				+ " = " + deviceId + "";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			List<Usage> usages = new ArrayList<Usage>();
			for (int a = 0; a < records.size(); a++) {
				Usage usage = new Usage();
				usage.setAttributes(records.get(a));
				usage.mapIdsToObjects(this);
				usages.add(usage);
			}
			return usages;
		}
		return null;
	}

	@Override
	public Boolean createUsage(Usage us) {
		String sql = "insert into \"" + TB_USAGE + "\" (" + Usage.DEVICE_ID
				+ "," + Usage.RECORD_DATE + "," + Usage.TOTAL_KW + ") values ("
				+ us.getDeviceId() + "," + us.getRecordDate() + ","
				+ us.getTotalKW() + ")";
		return executeSql(sql);
	}

	@Override
	public PowerCompany getPowerCompany(Integer powercompanyId) {
		String sql = "select * from \"" + TB_POWERCOMPANY + "\" where ("
				+ DELETED + " is null or " + DELETED + " = false) and "
				+ PowerCompany.POWERCOMPANY_ID + " = '" + powercompanyId + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				PowerCompany pc = new PowerCompany();
				pc.setAttributes(records.get(0));
				pc.mapIdsToObjects(this);
				return pc;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public Boolean createPowerCompany(PowerCompany pc) {
		String sql = "insert into \"" + TB_POWERCOMPANY + "\" ("
				+ PowerCompany.ADDRESS + "," + PowerCompany.NAME + ","
				+ PowerCompany.RATE + "," + PowerCompany.TELEPHONE
				+ ") values (" + pc.getAddress() + "," + pc.getName() + ","
				+ pc.getRate() + "," + pc.getTelephone() + ")";
		return executeSql(sql);
	}

	@Override
	public Incentive getIncentive(Integer incentiveid) {
		String sql = "select * from \"" + TB_INCENTIVE + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and "
				+ Incentive.INCENTIVE_ID + " = '" + incentiveid + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				Incentive inc = new Incentive();
				inc.setAttributes(records.get(0));
				inc.mapIdsToObjects(this);
				return inc;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public Boolean createIncentive(Incentive inc) {
		String sql = "insert into \"" + TB_INCENTIVE + "\" ("
				+ Incentive.DESCRIPTION + "," + Incentive.POWERCOMPANY_ID + ","
				+ Incentive.THRESHOLD + "," + Incentive.TITLE + ") values ("
				+ inc.getDescription() + "," + inc.getPowerCompanyId() + ","
				+ inc.getThreshold() + "," + inc.getTitle() + ")";
		return executeSql(sql);
	}

	@Override
	public NotificationType getNotificationType(Integer notificationtypeId) {
		String sql = "select * from \"" + TB_NOTIFICATION_TYPE + "\" where ("
				+ DELETED + " is null or " + DELETED + " = false) and "
				+ NotificationType.NOTIFICATIONTYPE_ID + " = '"
				+ notificationtypeId + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				NotificationType nt = new NotificationType();
				nt.setAttributes(records.get(0));
				nt.mapIdsToObjects(this);
				return nt;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public Boolean createNotificationType(NotificationType nt) {
		String sql = "insert into " + TB_NOTIFICATION_TYPE + " ("
				+ NotificationType.DESCRIPTION + "," + NotificationType.NAME
				+ ") values (" + nt.getDescription() + "," + nt.getName() + ")";
		return executeSql(sql);
	}

	@Override
	public Boolean executeSql(String sql) {
		Statement st = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.out.println("Could not load class");
		}
		try {
			conn = DriverManager.getConnection(this.url, this.username,
					this.password);
			st = conn.createStatement();
			System.out.println("Statement to execute: " + sql);
			st.execute(sql);
			return true;
		} catch (SQLException ex) {
			System.out.println("Caught exception when executing: "
					+ ex.toString());
			ex.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out
						.println("Caught exception when closing connections: "
								+ ex.toString());
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> executeQuery(String sql) {
		ResultSet rs = null;
		Statement st = null;
		List<Map<String, Object>> records = null;
		Integer numcols = null;
		List<String> column_names = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.out.println("Could not load class");
		}
		try {
			conn = DriverManager.getConnection(this.url, this.username,
					this.password);
			st = conn.createStatement();
			System.out.println("Query to execute: " + sql);
			rs = st.executeQuery(sql);
			ResultSetMetaData metadata = rs.getMetaData();
			numcols = metadata.getColumnCount();
			records = new ArrayList<Map<String, Object>>();
			column_names = new ArrayList<String>();
			for (int b = 0; b < numcols; b++) {
				column_names.add(b, metadata.getColumnName(b + 1));
			}
			while (rs.next()) {
				Map<String, Object> record = new HashMap<String, Object>();
				for (int a = 0; a < numcols; a++) {
					record.put(column_names.get(a), rs.getObject(a + 1));
				}
				records.add(record);
			}
			System.out.println("Number of results returned: " + records.size());
		} catch (SQLException ex) {
			System.out.println("Caught exception when querying: "
					+ ex.toString());
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out
						.println("Caught exception when closing connections: "
								+ ex.toString());
			}
		}
		return records;
	}

	@Override
	public Account getAccount(Integer accountId) {
		String sql = "select * from \"" + TB_ACCOUNT + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and "
				+ Account.ACCOUNT_ID + " = " + accountId;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records != null && records.size() > 0) {
			if (records.size() == 1) {
				Account ac = new Account();
				ac.setAttributes(records.get(0));
				ac.mapIdsToObjects(this);
				return ac;
			} else {
				System.out.println("Query returned " + records.size()
						+ " results, expecting 1.");
			}
		}
		return null;
	}

	@Override
	public Device getDevice(Integer deviceId) {
		String sql = "select * from \"" + TB_DEVICE + "\" where (" + DELETED
				+ " is null or " + DELETED + " = false) and "
				+ Device.DEVICE_ID + " = '" + deviceId + "'";
		List<Map<String, Object>> records = executeQuery(sql);
		if (records.size() == 1) {
			Device d = new Device();
			d.setAttributes(records.get(0));
			d.mapIdsToObjects(this);
			return d;
		} else {
			System.out.println("Query returned " + records.size()
					+ " results, expecting 1.");
		}
		return null;
	}

	@Override
	public Boolean updateAccount(Account acc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateDevice(Device dev) {
		String sql = "update \"" + TB_DEVICE + "\" set (" + Device.DESCRIPTION
				+ ", " + Device.LOCATION + ", " + Device.NAME + ", "
				+ Device.PRIORITY + ") = (" + dev.getDescription() + ", "
				+ dev.getLocation() + ", " + dev.getName() + ", "
				+ dev.getPriority() + ") where " + Device.DEVICE_ID + " = "
				+ dev.getDeviceId();
		return executeSql(sql);
	}

	@Override
	public Boolean deleteAccount(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteDevice(Integer deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteNotification(Integer notificationId, Integer accountId) {
		String sql = "update \"" + TB_NOTIFICATION + "\" set " + DELETED
				+ " = " + "TRUE where " + Notification.NOTIFICATION_ID + " = "
				+ notificationId + " and " + Notification.ACCOUNT_ID + " = "
				+ accountId;
		return executeSql(sql);
	}

	@Override
	public Boolean deleteNotificationType(Integer notificationtypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deletePowerCompany(Integer powercompanyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteIncentive(Integer incentiveid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteUsage(Integer usageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean toggleIncentiveActive(Integer accountId, Integer incentiveId) {
		String sql = "select * from \"" + TB_ACCOUNT_INCENTIVE + "\" where "
				+ Account.ACCOUNT_ID + " = '" + accountId + "' and "
				+ Incentive.INCENTIVE_ID + " = " + incentiveId;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records.size() == 1) {
			Map<String, Object> map = records.get(0);
			Object b = map.get(Incentive.INCENTIVE_ACTIVE);
			if (b != null) {
				Boolean result = Boolean.parseBoolean(b.toString());
				result = !result;
				updateAccountIncentive(accountId, incentiveId, result);
				return result;
			}
		} else {
			System.out.println("Query returned " + records.size()
					+ " results, expecting 1.");
		}
		return null;
	}

	@Override
	public Boolean updateAccountIncentive(Integer accountId,
			Integer incentiveId, Boolean result) {
		String sql = "update \"" + TB_ACCOUNT_INCENTIVE + "\" set "
				+ Incentive.INCENTIVE_ACTIVE + " = '" + result + "' where "
				+ Incentive.INCENTIVE_ID + " = " + incentiveId + " and "
				+ Account.ACCOUNT_ID + " = " + accountId;
		Boolean ret = executeSql(sql);
		return ret;
	}

	@Override
	public Incentive getAccountIncentive(Integer accid, Integer incid) {
		String sql = "select * from \"" + TB_ACCOUNT_INCENTIVE + "\" a , \""
				+ TB_INCENTIVE + "\" b where " + Account.ACCOUNT_ID + " = '"
				+ accid + "' and a." + Incentive.INCENTIVE_ID + " = b."
				+ Incentive.INCENTIVE_ID + " and b." + Incentive.INCENTIVE_ID
				+ " = " + incid;
		List<Map<String, Object>> records = executeQuery(sql);
		if (records.size() == 1) {
			Incentive incentive = new Incentive();
			incentive.setAttributes(records.get(0));
			incentive.mapIdsToObjects(this);
			return incentive;
		} else {
			System.out.println("Query returned " + records.size()
					+ " results, expecting 1.");
		}
		return null;
	}
}
