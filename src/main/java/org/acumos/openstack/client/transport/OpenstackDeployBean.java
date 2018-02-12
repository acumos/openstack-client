/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * ===================================================================================
 * This Acumos software file is distributed by AT&T and Tech Mahindra
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============LICENSE_END=========================================================
 */
package org.acumos.openstack.client.transport;

public class OpenstackDeployBean {
	
	private String identityEndpoint;
	private String userName;
	private String password;
	private String identifierName;
	private String projectScopeId;
	private String keyName;
	private String vmName;
	
	
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getIdentityEndpoint() {
		return identityEndpoint;
	}
	public void setIdentityEndpoint(String identityEndpoint) {
		this.identityEndpoint = identityEndpoint;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentifierName() {
		return identifierName;
	}
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}
	public String getProjectScopeId() {
		return projectScopeId;
	}
	public void setProjectScopeId(String projectScopeId) {
		this.projectScopeId = projectScopeId;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	

}
