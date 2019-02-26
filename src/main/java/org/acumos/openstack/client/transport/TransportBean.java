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
	
	private String uidNumStr;
	private String endpoint;
	private String userName;
	private String scopeProject;
	private String key;
	private String keyName;
	private String IdentifierName;
	private String vmRegisterNumber;
	private String hostOpenStack;
	private String hostUserName;
	private String vmUserName;
	private String dockerUserName;
	private String dockerPd;
	private String bluePrintImage;
	private String bluePrintName;
	private String bluePrintUserName;
	private String bluePrintPd;
	private String dataSource;
	private String cmndatasvcuser;
	private String cmndatasvcpd;
	private String solutionPort;
	private String Sleeptime;
	private String proxyIP;
	private String proxyPort;
	private String openStackIP;
	private String bluePrintPortNumber;
	private String probePrintImage;
	private String probePrintName;
	private String probUser;
	private String probePass;
	private String jsonFileName="blueprint.json";
	private String probeNexusEndPoint;
	private String probeInternalPort;
	private String repositoryNames;
	private String exposeDataBrokerPort;
	private String internalDataBrokerPort;
	private String nexusRegistyName;
	private String nexusRegistyUserName;
	private String nexusRegistyPd;
	private String repositoryDetails;
	private String solutionId;
	private String solutionRevisionId;
	private String vmHostName;
	private String vmHostIP;
	private String userId;
	
	private String urlAttribute;
	private String jsonPosition;
	private String jsonMapping;
	private String dataBrokerUserName;
	private String dataBrokerUserPd;
	private String dataBrokerHost;
	private String dataBrokerPort;
	private String userDetail;
	private String requestId;
	
	
	
	
	public String getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(String userDetail) {
		this.userDetail = userDetail;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getUrlAttribute() {
		return urlAttribute;
	}
	public void setUrlAttribute(String urlAttribute) {
		this.urlAttribute = urlAttribute;
	}
	public String getJsonPosition() {
		return jsonPosition;
	}
	public void setJsonPosition(String jsonPosition) {
		this.jsonPosition = jsonPosition;
	}
	public String getJsonMapping() {
		return jsonMapping;
	}
	public void setJsonMapping(String jsonMapping) {
		this.jsonMapping = jsonMapping;
	}
	public String getDataBrokerUserName() {
		return dataBrokerUserName;
	}
	public void setDataBrokerUserName(String dataBrokerUserName) {
		this.dataBrokerUserName = dataBrokerUserName;
	}
	public String getDataBrokerUserPd() {
		return dataBrokerUserPd;
	}
	public void setDataBrokerUserPd(String dataBrokerUserPd) {
		this.dataBrokerUserPd = dataBrokerUserPd;
	}
	public String getDataBrokerHost() {
		return dataBrokerHost;
	}
	public void setDataBrokerHost(String dataBrokerHost) {
		this.dataBrokerHost = dataBrokerHost;
	}
	public String getDataBrokerPort() {
		return dataBrokerPort;
	}
	public void setDataBrokerPort(String dataBrokerPort) {
		this.dataBrokerPort = dataBrokerPort;
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
	public String getVmHostName() {
		return vmHostName;
	}
	public void setVmHostName(String vmHostName) {
		this.vmHostName = vmHostName;
	}
	public String getVmHostIP() {
		return vmHostIP;
	}
	public void setVmHostIP(String vmHostIP) {
		this.vmHostIP = vmHostIP;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUidNumStr() {
		return uidNumStr;
	}
	public void setUidNumStr(String uidNumStr) {
		this.uidNumStr = uidNumStr;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getScopeProject() {
		return scopeProject;
	}
	public void setScopeProject(String scopeProject) {
		this.scopeProject = scopeProject;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getIdentifierName() {
		return IdentifierName;
	}
	public void setIdentifierName(String identifierName) {
		IdentifierName = identifierName;
	}
	public String getVmRegisterNumber() {
		return vmRegisterNumber;
	}
	public void setVmRegisterNumber(String vmRegisterNumber) {
		this.vmRegisterNumber = vmRegisterNumber;
	}
	public String getHostOpenStack() {
		return hostOpenStack;
	}
	public void setHostOpenStack(String hostOpenStack) {
		this.hostOpenStack = hostOpenStack;
	}
	public String getHostUserName() {
		return hostUserName;
	}
	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}
	public String getVmUserName() {
		return vmUserName;
	}
	public void setVmUserName(String vmUserName) {
		this.vmUserName = vmUserName;
	}
	public String getDockerUserName() {
		return dockerUserName;
	}
	public void setDockerUserName(String dockerUserName) {
		this.dockerUserName = dockerUserName;
	}
	public String getDockerPd() {
		return dockerPd;
	}
	public void setDockerPd(String dockerPd) {
		this.dockerPd = dockerPd;
	}
	public String getBluePrintImage() {
		return bluePrintImage;
	}
	public void setBluePrintImage(String bluePrintImage) {
		this.bluePrintImage = bluePrintImage;
	}
	public String getBluePrintName() {
		return bluePrintName;
	}
	public void setBluePrintName(String bluePrintName) {
		this.bluePrintName = bluePrintName;
	}
	public String getBluePrintUserName() {
		return bluePrintUserName;
	}
	public void setBluePrintUserName(String bluePrintUserName) {
		this.bluePrintUserName = bluePrintUserName;
	}
	public String getBluePrintPd() {
		return bluePrintPd;
	}
	public void setBluePrintPd(String bluePrintPd) {
		this.bluePrintPd = bluePrintPd;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getCmndatasvcuser() {
		return cmndatasvcuser;
	}
	public void setCmndatasvcuser(String cmndatasvcuser) {
		this.cmndatasvcuser = cmndatasvcuser;
	}
	public String getCmndatasvcpd() {
		return cmndatasvcpd;
	}
	public void setCmndatasvcpd(String cmndatasvcpd) {
		this.cmndatasvcpd = cmndatasvcpd;
	}
	public String getSolutionPort() {
		return solutionPort;
	}
	public void setSolutionPort(String solutionPort) {
		this.solutionPort = solutionPort;
	}
	public String getSleeptime() {
		return Sleeptime;
	}
	public void setSleeptime(String sleeptime) {
		Sleeptime = sleeptime;
	}
	public String getProxyIP() {
		return proxyIP;
	}
	public void setProxyIP(String proxyIP) {
		this.proxyIP = proxyIP;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getOpenStackIP() {
		return openStackIP;
	}
	public void setOpenStackIP(String openStackIP) {
		this.openStackIP = openStackIP;
	}
	public String getBluePrintPortNumber() {
		return bluePrintPortNumber;
	}
	public void setBluePrintPortNumber(String bluePrintPortNumber) {
		this.bluePrintPortNumber = bluePrintPortNumber;
	}
	public String getProbePrintImage() {
		return probePrintImage;
	}
	public void setProbePrintImage(String probePrintImage) {
		this.probePrintImage = probePrintImage;
	}
	public String getProbePrintName() {
		return probePrintName;
	}
	public void setProbePrintName(String probePrintName) {
		this.probePrintName = probePrintName;
	}
	public String getProbUser() {
		return probUser;
	}
	public void setProbUser(String probUser) {
		this.probUser = probUser;
	}
	public String getProbePass() {
		return probePass;
	}
	public void setProbePass(String probePass) {
		this.probePass = probePass;
	}
	public String getJsonFileName() {
		return jsonFileName;
	}
	public void setJsonFileName(String jsonFileName) {
		this.jsonFileName = jsonFileName;
	}
	public String getProbeNexusEndPoint() {
		return probeNexusEndPoint;
	}
	public void setProbeNexusEndPoint(String probeNexusEndPoint) {
		this.probeNexusEndPoint = probeNexusEndPoint;
	}
	public String getProbeInternalPort() {
		return probeInternalPort;
	}
	public void setProbeInternalPort(String probeInternalPort) {
		this.probeInternalPort = probeInternalPort;
	}
	public String getRepositoryNames() {
		return repositoryNames;
	}
	public void setRepositoryNames(String repositoryNames) {
		this.repositoryNames = repositoryNames;
	}
	public String getExposeDataBrokerPort() {
		return exposeDataBrokerPort;
	}
	public void setExposeDataBrokerPort(String exposeDataBrokerPort) {
		this.exposeDataBrokerPort = exposeDataBrokerPort;
	}
	public String getInternalDataBrokerPort() {
		return internalDataBrokerPort;
	}
	public void setInternalDataBrokerPort(String internalDataBrokerPort) {
		this.internalDataBrokerPort = internalDataBrokerPort;
	}
	public String getNexusRegistyName() {
		return nexusRegistyName;
	}
	public void setNexusRegistyName(String nexusRegistyName) {
		this.nexusRegistyName = nexusRegistyName;
	}
	public String getNexusRegistyUserName() {
		return nexusRegistyUserName;
	}
	public void setNexusRegistyUserName(String nexusRegistyUserName) {
		this.nexusRegistyUserName = nexusRegistyUserName;
	}
	public String getNexusRegistyPd() {
		return nexusRegistyPd;
	}
	public void setNexusRegistyPd(String nexusRegistyPd) {
		this.nexusRegistyPd = nexusRegistyPd;
	}
	public String getRepositoryDetails() {
		return repositoryDetails;
	}
	public void setRepositoryDetails(String repositoryDetails) {
		this.repositoryDetails = repositoryDetails;
	}
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
