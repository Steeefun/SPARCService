package types;

import dataaccess.DAL;

public class NotificationType extends BaseType {

	// Attribute mapping to db column
	public static final String NOTIFICATIONTYPE_ID = "notificationtype_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	public NotificationType() {
		super();
		attributes.put(NOTIFICATIONTYPE_ID, null);
		attributes.put(NAME, null);
		attributes.put(DESCRIPTION, null);
	}

	public Integer getNotificationTypeId() {
		try {
			return (Integer) attributes.get(NOTIFICATIONTYPE_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setNotificationTypeId(Integer notificationTypeId) {
		setAttribute(NOTIFICATIONTYPE_ID, notificationTypeId);
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

	@Override
	public void mapIdsToObjects(DAL dal) {
	}
}
