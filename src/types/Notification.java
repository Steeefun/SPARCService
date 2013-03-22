package types;

import java.sql.Timestamp;

import dataaccess.DAL;

public class Notification extends BaseType {

	// Attribute mapping to db column
	public static final String NOTIFICATION_ID = "notification_id";
	public static final String NOTIFICATIONTYPE_ID = "notificationtype_id";
	public static final String TIME = "time";
	public static final String TEXT = "text";
	public static final String VIEWED = "viewed";
	public static final String ACCOUNT_ID = "account_id";
	public static final String TITLE = "title";

	private static final String NOTIFICATIONTYPE = "notificationtype";

	public Notification() {
		super();
		attributes.put(NOTIFICATION_ID, null);
		attributes.put(NOTIFICATIONTYPE_ID, null);
		attributes.put(TIME, null);
		attributes.put(TEXT, null);
		attributes.put(VIEWED, null);
		attributes.put(ACCOUNT_ID, null);
		attributes.put(TITLE, null);

		attributes.put(NOTIFICATIONTYPE, null);
	}

	public Integer getNotificationId() {
		try {
			return (Integer) attributes.get(NOTIFICATION_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setNotificationId(Integer notificationId) {
		setAttribute(NOTIFICATION_ID, notificationId);
	}

	public Integer getNotificationTypeId() {
		try {
			return (Integer) attributes.get(NOTIFICATIONTYPE_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTypeId(Integer notificationTypeId) {
		setAttribute(NOTIFICATIONTYPE_ID, notificationTypeId);
	}

	public Timestamp getTimestamp() {
		try {
			return (Timestamp) attributes.get(TIME);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTimestamp(Timestamp timestamp) {
		setAttribute(TIME, timestamp);
	}

	public String getText() {
		try {
			return (String) attributes.get(TEXT);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTitle(String title) {
		setAttribute(TITLE, title);
	}

	public String getTitle() {
		try {
			return (String) attributes.get(TITLE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setText(String text) {
		setAttribute(TEXT, text);
	}

	public Boolean isViewed() {
		try {
			return (Boolean) attributes.get(VIEWED);
		} catch (Exception e) {
			return null;
		}
	}

	public void setViewed(Boolean viewed) {
		setAttribute(VIEWED, viewed);
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

	public NotificationType getNotificationType() {
		try {
			return (NotificationType) attributes.get(NOTIFICATIONTYPE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setNotificationType(NotificationType notificationtype) {
		setAttribute(NOTIFICATIONTYPE, notificationtype);
	}

	@Override
	public void mapIdsToObjects(DAL dal) {
		Integer ntid = null;
		if ((ntid = getNotificationTypeId()) != null) {
			NotificationType nt = null;
			if ((nt = dal.getNotificationType(ntid)) != null) {
				setNotificationType(nt);
			}
		}
	}
}
