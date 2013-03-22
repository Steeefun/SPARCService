package types;

import java.util.List;

import dataaccess.DAL;

public class PowerCompany extends BaseType {

	// Attribute mapping to db column
	public static final String POWERCOMPANY_ID = "powercompany_id";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TELEPHONE = "telephone";
	public static final String RATE = "rate";

	private static final String INCENTIVES = "incentives";

	public PowerCompany() {
		super();
		attributes.put(POWERCOMPANY_ID, null);
		attributes.put(NAME, null);
		attributes.put(ADDRESS, null);
		attributes.put(TELEPHONE, null);
		attributes.put(RATE, null);

		attributes.put(INCENTIVES, null);
	}

	@SuppressWarnings("unchecked")
	public List<Incentive> getIncentiveList() {
		try {
			return (List<Incentive>) attributes.get(INCENTIVES);
		} catch (Exception e) {
			return null;
		}
	}

	public void addIncentive(Incentive incentive) {
		List<Incentive> incentives = null;
		if ((incentives = getIncentiveList()) != null) {
			if (!incentives.contains(incentive)) {
				incentives.add(incentive);
				setIncentiveList(incentives);
			}
		}
	}

	public void removeIncentive(Incentive incentive) {
		List<Incentive> incentives = null;
		if ((incentives = getIncentiveList()) != null) {
			if (incentives.contains(incentive)) {
				incentives.remove(incentive);
				setIncentiveList(incentives);
			}
		}
	}

	public void setIncentiveList(List<Incentive> incentives) {
		setAttribute(INCENTIVES, incentives);
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

	public String getAddress() {
		try {
			return (String) attributes.get(ADDRESS);
		} catch (Exception e) {
			return null;
		}
	}

	public void setAddress(String address) {
		setAttribute(ADDRESS, address);
	}

	public Integer getTelephone() {
		try {
			return (Integer) attributes.get(TELEPHONE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setTelephone(Integer telephone) {
		setAttribute(TELEPHONE, telephone);
	}

	public Double getRate() {
		try {
			return (Double) attributes.get(RATE);
		} catch (Exception e) {
			return null;
		}
	}

	public void setRate(Double rate) {
		setAttribute(RATE, rate);
	}

	@Override
	public void mapIdsToObjects(DAL dal) {
		Integer pcid = null;
		if ((pcid = getPowercompanyId()) != null) {
			List<Incentive> incentives = null;
			if ((incentives = dal.getPowerCompanyIncentives(pcid)) != null) {
				setIncentiveList(incentives);
			}
		}
	}
}
