package sparcservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import bacnetaccess.BACNetConnector;

import types.*;
import dataaccess.*;

@Path("/sparc")
public class SPARCWebService {
	private final String USERNAME = "user";
	private final String PASSWORD = "pass";
	private final String ACCOUNT_ID = "accid";
	private final String POWERCOMPANY_ID = "pcid";
	private final String DEVICE_ID = "devid";
	private final String PRIORITY = "pri";
	private final String LOCATION = "loc";
	private final String PROPERTY = "prop";
	private final String NAME = "name";
	private final String VALUE = "val";
	private final String BACNET_ID = "bacid";
	private final String INCENTIVE_ID = "incid";
	private final String DESCRIPTION = "desc";
	private final String NOTIFICATION_ID = "notid";
	private final String TITLE = "title";
	private final String TEXT = "text";
	private final String TYPE_ID = "typeid";
	private final String THRESHOLD = "thresh";

	DAL dal = new PostgresqlConnector();
	BACNetConnector bac = new BACNetConnector();

	@Path("/auth")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String auth(@QueryParam(USERNAME) String username,
			@QueryParam(PASSWORD) String password) {
		Account account = null;
		if ((account = dal.getAccount(username, password)) != null) {
			return account.toString();
		}
		return null;
	}

	@Path("/getDeviceList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeviceList(@QueryParam(ACCOUNT_ID) Integer accid) {
		String returnvalue = "{ \"deviceList\" : [ ";
		List<Device> devices = dal.getDevices(accid);
		if (devices != null && !devices.isEmpty()) {
			for (int a = 0; a < devices.size(); a++) {
				returnvalue += devices.get(a).toString();
				if (a < (devices.size() - 1)) {
					returnvalue += ",";
				}
			}
			returnvalue += "]}";
			return returnvalue;
		}
		return null;
	}

	@Path("/getIncentive")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIncentive(@QueryParam(INCENTIVE_ID) Integer incid) {
		Incentive incentive = dal.getIncentive(incid);
		if (incentive != null) {
			return incentive.toString();
		}
		return null;
	}

	@Path("/getPowerCompanyIncentives")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPowerCompanyIncentiveList(
			@QueryParam(POWERCOMPANY_ID) Integer pcid) {
		List<Incentive> incentives = dal.getPowerCompanyIncentives(pcid);
		if (incentives != null && !incentives.isEmpty()) {
			String returnvalue = "{ \"incentiveList\" : [ ";
			for (int a = 0; a < incentives.size(); a++) {
				returnvalue += incentives.get(a).toString();
				if (a < (incentives.size() - 1)) {
					returnvalue += ",";
				}
			}
			returnvalue += "]}";
			return returnvalue;
		}
		return null;
	}

	@Path("/getAccountIncentives")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAccountIncentiveList(@QueryParam(ACCOUNT_ID) Integer accid) {
		List<Incentive> incentives = dal.getAccountIncentives(accid);
		if (incentives != null && !incentives.isEmpty()) {
			String returnvalue = "{ \"incentiveList\" : [ ";
			for (int a = 0; a < incentives.size(); a++) {
				returnvalue += incentives.get(a).toString();
				if (a < (incentives.size() - 1)) {
					returnvalue += ",";
				}
			}
			returnvalue += "]}";
			return returnvalue;
		}
		return null;
	}

	@Path("/getNotificationList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotificationList(@QueryParam(ACCOUNT_ID) Integer accid) {
		List<Notification> notifications = dal.getNotifications(accid);
		if (notifications != null && !notifications.isEmpty()) {
			String returnvalue = "{ \"notificationList\" : [ ";
			for (int a = 0; a < notifications.size(); a++) {
				returnvalue += notifications.get(a).toString();
				if (a < (notifications.size() - 1)) {
					returnvalue += ",";
				}
			}
			returnvalue += "]}";
			return returnvalue;
		}
		return null;
	}

	@Path("/getAccountUsageData")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAccountUsageData(@QueryParam(ACCOUNT_ID) Integer accid) {
		List<Device> devices = dal.getDevices(accid);
		if (devices != null && !devices.isEmpty()) {
			String returnvalue = "{ \"usageList\" : [ ";
			for (int a = 0; a < devices.size(); a++) {
				List<Usage> usages = devices.get(a).getUsageList();
				if (usages != null && !usages.isEmpty()) {
					for (int b = 0; b < usages.size(); b++) {
						returnvalue += usages.get(b).toString();
						if (a < (devices.size() - 1)) {
							returnvalue += ",";
						}
					}
				}
			}
			returnvalue += "]}";
			return returnvalue;
		}
		return null;
	}

	@Path("/getDeviceUsageData")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeviceUsageData(@QueryParam(DEVICE_ID) Integer devid) {
		Device d = dal.getDevice(devid);
		List<Usage> usages = d.getUsageList();
		if (usages != null && !usages.isEmpty()) {
			String returnvalue = "";
			for (int a = 0; a < usages.size(); a++) {
				returnvalue += usages.get(a).toString();
				if (a < (usages.size() - 1)) {
					returnvalue += ",";
				}
			}
			return returnvalue;
		}
		return null;
	}

	@Path("/toggleDevice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String toggleDevice(@QueryParam(DEVICE_ID) Integer id) {

		// add code to toggle device

		return "Toggle device id: " + id;
	}

	@Path("/toggleIncentive")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String toggleAccountIncentive(@QueryParam(ACCOUNT_ID) Integer accid,
			@QueryParam(INCENTIVE_ID) Integer incid) {
		dal.toggleIncentiveActive(accid, incid);
		Incentive inc = dal.getAccountIncentive(accid, incid);
		if (inc != null) {
			return inc.toString();
		}
		return null;
	}

	@Path("/updateDevice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDevice(@QueryParam(DEVICE_ID) Integer id,
			@QueryParam(NAME) String name,
			@QueryParam(LOCATION) String location,
			@QueryParam(PRIORITY) Integer priority,
			@QueryParam(DESCRIPTION) String description) {
		Device d = dal.getDevice(id);
		if (d != null) {
			d.setName(name);
			d.setLocation(location);
			d.setPriority(priority);
			d.setDescription(description);
			dal.updateDevice(d);
			return d.toString();
		}
		return null;
	}

	@Path("/deleteNotification")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteNotification(
			@QueryParam(NOTIFICATION_ID) Integer notid,
			@QueryParam(ACCOUNT_ID) int accid) {
		dal.deleteNotification(notid, accid);
		return "Success";
	}

	@Path("/setDeviceProperty")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String setDevicePropertyDevice(@QueryParam(DEVICE_ID) Integer id,
			@QueryParam(PROPERTY) String property,
			@QueryParam(VALUE) String value) {

		// add code to set device property

		return "Setting property: " + property + " of device: " + id + " to "
				+ value;
	}

	@Path("/syncDevice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String syncDevice(@QueryParam(DEVICE_ID) Integer id) {

		// add code to search for bacnet device by id

		return "Sync device: " + id;
	}

	@Path("/createDevice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String addDevice(@QueryParam(BACNET_ID) Integer bacid,
			@QueryParam(NAME) String name,
			@QueryParam(LOCATION) String location,
			@QueryParam(PRIORITY) Integer priority,
			@QueryParam(ACCOUNT_ID) Integer accid,
			@QueryParam(DESCRIPTION) String desc) {
		Device dev = new Device();
		dev.setAccountId(accid);
		dev.setBacnetId(bacid);
		dev.setPriority(priority);
		dev.setName(name);
		dev.setLocation(location);
		dev.setDescription(desc);
		if (dal.createDevice(dev)) {
			return "Success";
		}
		return null;
	}

	@Path("/createIncentive")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String addIncentive(@QueryParam(DESCRIPTION) String desc,
			@QueryParam(POWERCOMPANY_ID) Integer pcid,
			@QueryParam(THRESHOLD) Double threshold,
			@QueryParam(TITLE) String title) {
		Incentive inc = new Incentive();
		inc.setDescription(desc);
		inc.setPowerCompanyId(pcid);
		inc.setThreshold(threshold);
		inc.setTitle(title);
		if (dal.createIncentive(inc)) {
			return "Success";
		}
		return null;
	}

	@Path("/addNotification")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String addNotification(@QueryParam(TEXT) String text,
			@QueryParam(TITLE) String title,
			@QueryParam(ACCOUNT_ID) Integer accid,
			@QueryParam(TYPE_ID) Integer typeid) {
		Notification no = new Notification();
		no.setAccountId(accid);
		no.setText(text);
		no.setTitle(title);
		no.setTypeId(typeid);
		no.setViewed(false);
		if (dal.createNotification(no)) {
			return "Success";
		}
		return null;
	}

	@Path("/getDeviceUsageForToday/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeviceUsageForToday(@PathParam("id") Integer deviceid) {
		Double usage = bac.getPowerUsage(dal.getDevice(deviceid).getBacnetId());
		return usage.toString();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "This is the SPARC service";
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> This is the SPARC service "
				+ "</hello>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "SPARC service" + "</title>"
				+ "<body><h1>" + "This is the SPARC service" + "</body></h1>"
				+ "</html> ";
	}
}