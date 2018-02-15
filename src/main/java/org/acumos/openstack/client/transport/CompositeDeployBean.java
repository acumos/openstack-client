package org.acumos.openstack.client.transport;

public class CompositeDeployBean {
	
	private String vmName;
	private String solutionId;
	private String solutionRevisionId;
	private String userId;
	
	
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(String solutionId) {
		this.solutionId = solutionId;
	}
	public String getSolutionRevisionId() {
		return solutionRevisionId;
	}
	public void setSolutionRevisionId(String solutionRevisionId) {
		this.solutionRevisionId = solutionRevisionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
