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
package org.acumos.openstack.client.util;

import org.acumos.openstack.client.controller.OpenstackServiceController;
import org.acumos.openstack.client.transport.TransportBean;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	Logger log = LoggerFactory.getLogger(OpenstackServiceController.class);
	
	public String printExistingVMDeployment(TransportBean tbean) {
		 log.debug("openstackExistingVMDeployment start");
		 log.debug("UidNumStr "+tbean.getUidNumStr());
		 log.debug("Endpoin "+tbean.getEndpoint());
		 log.debug("UserName "+tbean.getUserName());
		 log.debug("Key "+tbean.getKey());
		 log.debug("KeyName "+tbean.getKeyName());
		 log.debug("VmRegisterNumber "+tbean.getVmRegisterNumber());
		 log.debug("HostOpenStack "+tbean.getHostOpenStack());
		 log.debug("HostUserName "+tbean.getHostUserName());
		 log.debug("VmUserName "+tbean.getVmUserName());
		 log.debug("DockerUserName "+tbean.getDockerUserName());
		 log.debug("DockerPd "+tbean.getDockerPd());
		 log.debug("BluePrintImag "+tbean.getBluePrintImage());
		 log.debug("BluePrintName "+tbean.getBluePrintName());
		 log.debug("DataSource "+tbean.getDataSource());
		 log.debug("Cmndatasvcuser "+tbean.getCmndatasvcuser());
		 log.debug("Cmndatasvcpd "+tbean.getCmndatasvcpd());
		 log.debug("NexusUrl "+tbean.getNexusUrl());
		 log.debug("NexusUserName "+tbean.getNexusUserName());
		 log.debug("NexusPd "+tbean.getNexusPd());
		 log.debug("SolutionPort "+tbean.getSolutionPort());
		 log.debug("Sleeptime "+tbean.getSleeptime());
		 log.debug("ProxyIP "+tbean.getProxyIP());
		 log.debug("ProxyPort "+tbean.getProxyPort());
		 log.debug("OpenStackIP "+tbean.getOpenStackIP());
		 log.debug("BluePrintPortNumber "+tbean.getBluePrintPortNumber());
		 log.debug("ProbePrintImage "+tbean.getProbePrintImage());
		 log.debug("ProbUser "+tbean.getProbUser());
		 log.debug("ProbePass "+tbean.getProbePass());
		 log.debug("ProbeNexusEndPoint "+tbean.getProbeNexusEndPoint());
		 log.debug("ProbeInternalPort "+tbean.getProbeInternalPort());
		 log.debug("RepositoryNames "+tbean.getRepositoryNames());
		 log.debug("ExposeDataBrokerPort "+tbean.getExposeDataBrokerPort());
		 log.debug("InternalDataBrokerPort "+tbean.getInternalDataBrokerPort());
		 log.debug("NexusRegistyName "+tbean.getNexusRegistyName());
		 log.debug("NexusRegistyUserName "+tbean.getNexusRegistyUserName());
		 log.debug("NexusRegistyPd "+tbean.getNexusRegistyPd());
		 log.debug("RepositoryDetails "+tbean.getRepositoryDetails());
		 log.debug("NginxMapFolder "+tbean.getNginxMapFolder());
		 log.debug("NginxWebFolder "+tbean.getNginxWebFolder());
		 log.debug("NginxImageName "+tbean.getNginxImageName());
		 
		 log.debug("NginxInternalPort "+tbean.getNginxInternalPort());
		 log.debug("AzureDataFiles "+tbean.getAzureDataFiles());
		 log.debug("SolutionId "+tbean.getSolutionId());
		 log.debug("SolutionRevisionId "+tbean.getSolutionRevisionId());
		 log.debug("VmHostName "+tbean.getVmHostName());
		 log.debug("UserId "+tbean.getUserId());
		 log.debug("UrlAttribute "+tbean.getUrlAttribute());
		 log.debug("JsonMapping "+tbean.getJsonMapping());
		 log.debug("JsonPosition "+tbean.getJsonPosition());
		 log.debug("JsonFileName "+tbean.getJsonFileName());
		 log.debug("DataBrokerHost "+tbean.getDataBrokerHost());
		 log.debug("DataBrokerUserName "+tbean.getDataBrokerUserName());
		 log.debug("DataBrokerUserPd "+tbean.getDataBrokerUserPd());
		 
		 log.debug("DataBrokerPort "+tbean.getDataBrokerPort());
		 log.debug("VmHostIP "+tbean.getVmHostIP());
		 log.debug("ScopeProject "+tbean.getScopeProject());
		 log.debug("IdentifierName "+tbean.getIdentifierName());
		 log.debug("BluePrintUserName "+tbean.getBluePrintUserName());
		 log.debug("BluePrintPd "+tbean.getBluePrintPd());
		
		log.debug("openstackExistingVMDeployment end");
		return "success";
	}
	
	public String printsingleImageDetails(String flavourName,String securityGropName,String endpoint,String userName,String userPd,
			String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,String hostOpenStack,
			String hostUserName,String vmUserName,String dockerUserName,String dockerPd,String dataSource,
			String cmndatasvcuser,String cmndatasvcpd,String proxyIP,String proxyPort,String openStackIP,String repositoryNames) {
		 log.debug("singleImagePrint start");
		 log.debug("flavourName "+flavourName);
		 log.debug("securityGropName "+securityGropName);
		 log.debug("endpoint "+endpoint);
		 log.debug("userName "+userName);
		 log.debug("userPd "+userPd);
		 log.debug("scopeProject "+scopeProject);
		 log.debug("key "+key);
		 log.debug("keyName "+keyName);
		 log.debug("IdentifierName "+IdentifierName);
		 log.debug("vnRegisterNumber "+vmRegisterNumber);
		 log.debug("hostOpenStack "+hostOpenStack);
		 log.debug("hostUserName "+hostUserName);
		 log.debug("vmUserName "+vmUserName);
		 log.debug("dockerUserName "+dockerUserName);
		 log.debug("dockerPd "+dockerPd);
		 log.debug("dataSource "+dataSource);
		 log.debug("cmndatasvcuser "+cmndatasvcuser);
		 log.debug("cmndatasvcpd "+cmndatasvcpd);
		 log.debug("proxyIP "+proxyIP);
		 log.debug("proxyPort "+proxyPort);
		 log.debug("openStackIP "+openStackIP);
		 log.debug("repositoryNames "+repositoryNames);
		 log.debug("singleImagePrint end");
		return "success";
	}
	
	public String printSingleImageImplDetails(String flavourName,String securityGropName,String endpoint,String userName,String userPd,
			String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,String hostOpenStack,
			String hostUserName,String vmUserName,String dockerUserName,String dockerPd,String dataSource,
			String cmndatasvcuser,String cmndatasvcpd,String proxyIP,String proxyPort,String openStackIP,String repositoryNames,
			String solutionId,String solutionRevisionId,String imagetag,String uidNumStr,String repositoryDetails) {
		
		 log.debug("flavourName "+flavourName);
		 log.debug("securityGropName "+securityGropName);
		 log.debug("endpoint "+endpoint);
		 log.debug("userName "+userName);
		 log.debug("userPd "+userPd);
		 log.debug("scopeProject "+scopeProject);
		 log.debug("key "+key);
		 log.debug("keyName "+keyName);
		 log.debug("IdentifierName "+IdentifierName);
		 log.debug("vmRegisterNumber "+vmRegisterNumber);
		 log.debug("vmUserName "+vmUserName);
		 log.debug("dockerUserName "+dockerUserName);
		 log.debug("dockerPd "+dockerPd);
		 log.debug("SoulutionId "+solutionId);
		 log.debug("SolutionRevisionId "+solutionRevisionId);
		 log.debug("getImagetag() "+imagetag);
		 log.debug("uidNumStr "+uidNumStr);
		 log.debug("dataSource "+dataSource);
		 log.debug("cmndatasvcuser "+cmndatasvcuser);
		 log.debug(" cmndatasvcpd "+cmndatasvcpd);
		 log.debug("proxyIP "+proxyIP);
		 log.debug("proxyPort "+proxyPort);
		 log.debug("openStackIP "+openStackIP);
		 log.debug("repositoryNames "+repositoryNames);
		 log.debug("SimpleSolution repositoryDetails "+repositoryDetails);
		return "success";
	}
	
	public String printCompositeDetails(String nginxMapFolder,String nginxWebFolder,String nginxImageName,String nginxInternalPort,
			String azureDataFiles,String  repositoryDetails,String exposeDataBrokerPort,String internalDataBrokerPort,
			String nexusRegistyName,String probePrintImage,String probePrintName,String probUser,String probePass,
			String probeNexusEndPoint,String probeInternalPort,String flavourName,String securityGropName,String endpoint,String userName,
			String userPd,String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,
			String hostOpenStack,String hostUserName,String vmUserName,String dockerUserName,String dockerPd,String bluePrintImage,
			String bluePrintName,String bluePrintUserName,String bluePrintPd,String dataSource,String cmndatasvcuser,
			String cmndatasvcpd,String nexusUrl,String nexusUserName,String nexusPd,String solutionPort,String Sleeptime,
			String solutionId,String proxyIP,String proxyPort,String openStackIP,String bluePrintPortNumber,String solutionRevisionId,
			String repositoryNames,String nexusRegistyUserName,String nexusRegistyPd) {
			 log.debug("nginxMapFolder "+nginxMapFolder);
			 log.debug("nginxWebFolder "+nginxWebFolder);
			 log.debug("nginxImageName "+nginxImageName);
			 log.debug("nginxInternalPort "+nginxInternalPort);
			 log.debug("azureDataFiles "+azureDataFiles);
			 log.debug("repositoryDetails "+repositoryDetails);
			 log.debug("exposeDataBrokerPort "+exposeDataBrokerPort);
			 log.debug("internalDataBrokerPort "+internalDataBrokerPort);
			 log.debug("nexusRegistyName "+nexusRegistyName);
			 log.debug("probePrintImage "+probePrintImage);
			 log.debug("probePrintName "+probePrintName);
			 log.debug("probUser "+probUser);
			 log.debug("probePass "+probePass);
			 log.debug("probeNexusEndPoint "+probeNexusEndPoint);
			 log.debug("probeInternalPort "+probeInternalPort);
			 log.debug("flavourName "+flavourName);
			 log.debug("securityGropName "+securityGropName);
			 log.debug("endpoint "+endpoint);
			 log.debug("userName "+userName);
			 log.debug("userPd "+userPd);
			 log.debug("scopeProject "+scopeProject);
			 log.debug("key "+key);
			 log.debug("keyName "+keyName);
			 log.debug("IdentifierName "+IdentifierName);
			 log.debug("vnRegisterNumber "+vmRegisterNumber);
			 log.debug("hostOpenStack "+hostOpenStack);
			 log.debug("hostUserName "+hostUserName);
			 log.debug("vmUserName "+vmUserName);
			 log.debug("dockerUserName "+dockerUserName);
			 log.debug("dockerPd "+dockerPd);
			 log.debug("bluePrintImage "+bluePrintImage);
			 log.debug("bluePrintName "+bluePrintName);
			 log.debug("bluePrintUserName "+bluePrintUserName);
			 log.debug("bluePrintPd "+bluePrintPd);
			 log.debug("dataSource "+dataSource);
			 log.debug("cmndatasvcuser "+cmndatasvcuser);
			 log.debug("cmndatasvcpd "+cmndatasvcpd);
			 log.debug("nexusUrl "+nexusUrl);
			 log.debug("nexusUserName "+nexusUserName);
			 log.debug("nexusPd "+nexusPd);
			 log.debug("solutionPort "+solutionPort);
			 log.debug("Sleeptime "+Sleeptime);
			 log.debug("SolutionId "+solutionId);
			 log.debug("proxyIP "+proxyIP);
			 log.debug("proxyPort "+proxyPort);
			 log.debug("openStackIP "+openStackIP);
			 log.debug("bluePrintPortNumber "+bluePrintPortNumber);
			 log.debug("authObject.getSolutionRevisionId() "+solutionRevisionId);
			 log.debug("repositoryNames "+repositoryNames);
			 log.debug("nexusRegistyUserName "+nexusRegistyUserName);
			 log.debug("nexusRegistyPd "+nexusRegistyPd);
			
		return "success";
	}
	
	public String printCompositeImplDetails(String flavourName,String securityGropName,String endpoint,String userName,String userPd,
			String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,String vmUserName,
			String dockerUserName,String dockerPd,String solutionPort,String Sleeptime,String proxyIP,String proxyPort,
			String openStackIP,String bluePrintPortNumber,String probePrintName,String probUser,String probePass,
			String solutionId,String solutionRevisionId,String probeNexusEndPoint,String probeInternalPort,String nexusRegistyName,
			String exposeDataBrokerPort,String internalDataBrokerPort,String nexusRegistyUserName,String nexusRegistyPd,String bluePrintStr) {
		
			 log.debug("flavourName "+flavourName);
			 log.debug("securityGropName "+securityGropName);
			 log.debug("endpoint "+endpoint);
			 log.debug("userName "+userName);
			 log.debug("userPd "+userPd);
			 log.debug("scopeProject "+scopeProject);
			 log.debug("key "+key);
			 log.debug("keyName "+keyName);
			 log.debug("IdentifierName "+IdentifierName);
			 log.debug("vmRegisterNumber "+vmRegisterNumber);
			 log.debug("vmUserName "+vmUserName);
			 log.debug("dockerUserName "+dockerUserName);
			 log.debug("dockerPd "+dockerPd);
			 log.debug("solutionPort "+solutionPort);
			 log.debug("Sleeptime "+Sleeptime);
			 log.debug("proxyIP "+proxyIP);
			 log.debug("proxyPort "+proxyPort);
			 log.debug("openStackIP "+openStackIP);
			 log.debug("bluePrintPortNumber "+bluePrintPortNumber);
			 log.debug("probePrintName "+probePrintName);
			 log.debug("probUser "+probUser);
			 log.debug("probePass "+probePass);
			 log.debug("SoulutionId "+solutionId);
			 log.debug("SolutionRevisionId "+solutionRevisionId);
			 log.debug("probeNexusEndPoint "+probeNexusEndPoint);
			 log.debug("probeInternalPort "+probeInternalPort);
			 log.debug("nexusRegistyName "+nexusRegistyName);
			 log.debug("exposeDataBrokerPort "+exposeDataBrokerPort);
			 log.debug("internalDataBrokerPort "+internalDataBrokerPort);
			 log.debug("nexusRegistyUserName "+nexusRegistyUserName);
			 log.debug("nexusRegistyPd "+nexusRegistyPd);
			 log.debug(" JSON FROM DS bluePrintStr "+bluePrintStr);
		
		return "success";
	}

}
