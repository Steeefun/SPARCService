package types;

import dataaccess.DAL;

public class Incentive extends BaseType {

	// Attribute mapping to db column
	public static final String INCENTIVE_ID = "incentive_id";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String THRESHOLD = "threshold";
	public static final String POWERCOMPANY_ID = "powercompany_id";
	public static final String INCENTIVE_ACTIVE = "incentive_active";

	public Incentive() {
		super();
		attributes.put(INCENTIVE_ID, null);
		attributes.put(DESCRIPTION, null);
		attributes.put(TITLE, null);
		attributes.put(THRESHOLD, null);
		attributes.put(POWERCOMPANY_ID, null);
		attributes.put(INCENTIVE_ACTIVE, null);
	}

	public Integer getIncentiveId() {
		try {
			return (Integer) attributes.get(INCENTIVE_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setIncentiveId(Integer incentiveId) {
		setAttribute(INCENTIVE_ID, incentiveId);
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

	public String getTitle() {
		try {
			return (String) attributes.get(TITLE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTitle(String title) {
		setAttribute(TITLE, title);
	}

	public Double getThreshold() {
		try {
			return (Double) attributes.get(THRESHOLD);
		} catch (Exception e) {
			return null;
		}
	}

	public void setThreshold(Double threshold) {
		setAttribute(THRESHOLD, threshold);
	}

	public Integer getPowerCompanyId() {
		try {
			return (Integer) attributes.get(POWERCOMPANY_ID);
		} catch (Exception e) {
			return null;
		}
	}

	public void setPowerCompanyId(Integer powercompanyId) {
		setAttribute(POWERCOMPANY_ID, powercompanyId);
	}

	public void setIncentiveActive(Boolean active) {
		setAttribute(INCENTIVE_ACTIVE, active);
	}

	public Boolean getIncentiveActive() {
		try {
			return (Boolean) attributes.get(INCENTIVE_ACTIVE);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void mapIdsToObjects(DAL dal) {
	}

}
