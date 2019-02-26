package org.acumos.openstack.client.logging;

/**
 * Constants for standard headers, MDCs, etc.
*/
public final class ONAPLogConstants {

	/**
	 * Hide and forbid construction.
	 */
	private ONAPLogConstants() {
		throw new UnsupportedOperationException();
	}	

	/**
	 * MDC name constants.
	 */
	public static final class MDCs {

		/** MDC correlating messages for an invocation. */
		public static final String INVOCATION_ID = "InvocationID";

		/** MDC correlating messages for a logical transaction. */
		public static final String REQUEST_ID = "X-ACUMOS-Request-Id";

		/** MDC recording calling service. */
		public static final String PARTNER_NAME = "PartnerName";

		/** MDC recording current service. */
		public static final String SERVICE_NAME = "ServiceName";

		/** MDC recording target service. */
		public static final String TARGET_SERVICE_NAME = "TargetServiceName";

		/** MDC recording current service instance. */
		public static final String INSTANCE_UUID = "InstanceUUID";

		/** MDC recording caller address. */
		public static final String CLIENT_IP_ADDRESS = "ClientIPAddress";

		/** MDC recording server address. */
		public static final String SERVER_FQDN = "ServerFQDN";

		/**
		 * MDC recording timestamp at the start of the current request, with the same
		 * scope as {@link #REQUEST_ID}.
	    */
		public static final String ENTRY_TIMESTAMP = "EntryTimestamp";

		/** MDC recording timestamp at the start of the current invocation. */
		public static final String INVOKE_TIMESTAMP = "InvokeTimestamp";

		/** MDC reporting outcome code. */
		public static final String RESPONSE_CODE = "ResponseCode";

		/** MDC reporting outcome description. */
		public static final String RESPONSE_DESCRIPTION = "ResponseDescription";

		/** MDC reporting outcome error level. */
		public static final String RESPONSE_SEVERITY = "Severity";

		/** MDC reporting outcome error level. */
		public static final String RESPONSE_STATUS_CODE = "StatusCode";
		
		/** MDC correlating messages for User. */
		public static final String USER = "User";

		/**
		 * Hide and forbid construction.
		 */
		private MDCs() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Header name constants.
	 */
	public static final class Headers {

		public static final String REQUEST_ID = "X-ACUMOS-Request-Id";

		public static final String INVOCATION_ID = "Invocation-ID";

		public static final String PARTNER_NAME = "PartnerName";

		/**
		 * Hide and forbid construction.
		 */
		private Headers() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Response success or not, for setting <tt>StatusCode</tt>.
	 */
	public enum ResponseStatus {

		/** Success. */
		COMPLETED,

		/** Not. */
		ERROR,
	}
}