package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import types.*;

public abstract class DAL {
	protected Connection conn = null;
	protected String url = null;
	protected String username = null;
	protected String password = null;
	protected Boolean connected = false;

	protected final String TB_ACCOUNT = "Account";
	protected final String TB_ACCOUNT_DEVICE = "AccountDevice";
	protected final String TB_DEVICE = "Device";
	protected final String TB_INCENTIVE = "Incentive";
	protected final String TB_NOTIFICATION = "Notification";
	protected final String TB_NOTIFICATION_TYPE = "NotificationType";
	protected final String TB_POWERCOMPANY = "PowerCompany";
	protected final String TB_USAGE = "Usage";
	protected final String TB_ACCOUNT_INCENTIVE = "AccountIncentive";

	protected final String DELETED = "deleted";

	public DAL() {
		this("jdbc:postgresql://localhost/", "SPARC", "teamsparc");
	}

	public DAL(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	// Account

	public abstract Account getAccount(String username, String password);

	public abstract Account getAccount(Integer accountId);

	public abstract Boolean createAccount(Account acc);

	public abstract Boolean updateAccount(Account acc);

	public abstract Boolean deleteAccount(Integer accountId);

	public abstract Boolean toggleIncentiveActive(Integer accountId,
			Integer incid);

	// Device

	public abstract List<Device> getDevices(Integer accountId);

	public abstract Device getDevice(Integer deviceId);

	public abstract Boolean createDevice(Device dev);

	public abstract Boolean updateDevice(Device dev);

	public abstract Boolean deleteDevice(Integer deviceId);

	// Notification

	public abstract List<Notification> getNotifications(Integer accountId);

	public abstract Boolean createNotification(Notification no);

	public abstract Boolean deleteNotification(Integer notificationId,
			Integer accid);

	// Notification Type

	public abstract NotificationType getNotificationType(
			Integer notificationtypeId);

	public abstract Boolean createNotificationType(NotificationType nt);

	public abstract Boolean deleteNotificationType(Integer notificationtypeId);

	// Power Company

	public abstract PowerCompany getPowerCompany(String name);

	public abstract PowerCompany getPowerCompany(Integer powercompanyId);

	public abstract Boolean createPowerCompany(PowerCompany pc);

	public abstract Boolean deletePowerCompany(Integer powercompanyId);

	// Incentive

	public abstract List<Incentive> getPowerCompanyIncentives(
			Integer powercompanyId);

	public abstract List<Incentive> getAccountIncentives(Integer accountId);

	public abstract Incentive getIncentive(Integer incentiveid);

	public abstract Incentive getAccountIncentive(Integer accid, Integer incid);

	public abstract Boolean createIncentive(Incentive inc);

	public abstract Boolean deleteIncentive(Integer incentiveid);

	public abstract Boolean updateAccountIncentive(Integer accountId,
			Integer incentiveId, Boolean result);

	// Usage

	public abstract List<Usage> getUsage(Integer deviceId);

	public abstract Boolean createUsage(Usage us);

	public abstract Boolean deleteUsage(Integer usageId);

	// Generic DB methods

	public abstract List<Map<String, Object>> executeQuery(String sql);

	public abstract Boolean executeSql(String sql);

	public boolean connect() {
		try {
			conn = DriverManager.getConnection(this.url, this.username,
					this.password);
			connected = !conn.isClosed();
		} catch (SQLException ex) {
			System.out.println("Caught SQLException: " + ex.toString());
		} catch (Exception ex) {
			System.out.println("Caught Exception: " + ex.toString());
		}
		return connected;
	}

	public boolean disconnect() {
		try {
			conn.close();
			connected = conn.isClosed();
		} catch (SQLException ex) {
			System.out.println("Caught SQLException: " + ex.toString());
		} catch (Exception ex) {
			System.out.println("Caught Exception: " + ex.toString());
		}
		return !connected;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isConnected() {
		return this.connected;
	}

	public Connection getConnection() {
		return this.conn;
	}

	public static String getDateStr() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	@SuppressWarnings("deprecation")
	public static Date getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		Date d = new Date(dateFormat.format(cal.getTime()));
		return d;
	}
}
