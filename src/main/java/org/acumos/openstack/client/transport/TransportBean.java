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

import java.util.Map;

public class TransportBean {
	private String nexusUrl;
	private String nexusUserName;
	private String nexusPd;
	private Map<String,String> protoContainerMap;
	private Map<String,String> protoMap;
	private String nginxWebFolder;
	private String nginxMapFolder;
	private String nginxImageName;
	private String nginxInternalPort;
	private String azureDataFiles;
	private String nginxPort;
	private String vmIP;
	
	
	
	public String getNexusUrl() {
		return nexusUrl;
	}
	public void setNexusUrl(String nexusUrl) {
		this.nexusUrl = nexusUrl;
	}
	public String getNexusUserName() {
		return nexusUserName;
	}
	public void setNexusUserName(String nexusUserName) {
		this.nexusUserName = nexusUserName;
	}
	
	public String getNexusPd() {
		return nexusPd;
	}
	public void setNexusPd(String nexusPd) {
		this.nexusPd = nexusPd;
	}
	public Map<String, String> getProtoContainerMap() {
		return protoContainerMap;
	}
	public void setProtoContainerMap(Map<String, String> protoContainerMap) {
		this.protoContainerMap = protoContainerMap;
	}
	public Map<String, String> getProtoMap() {
		return protoMap;
	}
	public void setProtoMap(Map<String, String> protoMap) {
		this.protoMap = protoMap;
	}
	public String getNginxWebFolder() {
		return nginxWebFolder;
	}
	public void setNginxWebFolder(String nginxWebFolder) {
		this.nginxWebFolder = nginxWebFolder;
	}
	public String getNginxMapFolder() {
		return nginxMapFolder;
	}
	public void setNginxMapFolder(String nginxMapFolder) {
		this.nginxMapFolder = nginxMapFolder;
	}
	public String getNginxImageName() {
		return nginxImageName;
	}
	public void setNginxImageName(String nginxImageName) {
		this.nginxImageName = nginxImageName;
	}
	public String getNginxInternalPort() {
		return nginxInternalPort;
	}
	public void setNginxInternalPort(String nginxInternalPort) {
		this.nginxInternalPort = nginxInternalPort;
	}
	public String getAzureDataFiles() {
		return azureDataFiles;
	}
	public void setAzureDataFiles(String azureDataFiles) {
		this.azureDataFiles = azureDataFiles;
	}
	public String getNginxPort() {
		return nginxPort;
	}
	public void setNginxPort(String nginxPort) {
		this.nginxPort = nginxPort;
	}
	public String getVmIP() {
		return vmIP;
	}
	public void setVmIP(String vmIP) {
		this.vmIP = vmIP;
	}

	 
}
