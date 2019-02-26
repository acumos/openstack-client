package org.acumos.openstack.client.logging;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.slf4j.MDC;

public class ONAPLogDetails {
	
	public static void setMDCDetails(String requestId,String user) {

		// Extract MDC values from standard HTTP headers.
		final String requestID = requestId;
		final String invocationID = getUUID();
		
		// Get the UserName
		final String userName = user;
		MDC.put(ONAPLogConstants.MDCs.USER,userName);
				

		// Set standard MDCs. 
		MDC.put(ONAPLogConstants.MDCs.INVOKE_TIMESTAMP,
				ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
		MDC.put(ONAPLogConstants.MDCs.REQUEST_ID, requestID);
		MDC.put(ONAPLogConstants.MDCs.INVOCATION_ID, invocationID);
		/*if (!partnerName.isEmpty())
			MDC.put(ONAPLogConstants.MDCs.PARTNER_NAME, partnerName);
		MDC.put(ONAPLogConstants.MDCs.CLIENT_IP_ADDRESS, defaultToEmpty(request.getClientAddress()));
		MDC.put(ONAPLogConstants.MDCs.SERVER_FQDN, defaultToEmpty(request.getServerAddress()));	*/

		
	}
	protected static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	protected static String defaultToEmpty(final Object in) {
		if (in == null) {
			return "";
		}
		return in.toString();
	}
   public static void clearMDCDetails() {
		MDC.clear();
	}

}
