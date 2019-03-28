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
package org.acumos.openstack.client.controller;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acumos.openstack.client.api.APINames;
import org.acumos.openstack.client.logging.ACUMOSLogConstants.MDCs;
import org.acumos.openstack.client.logging.LogConfig;
import org.acumos.openstack.client.service.impl.ExistingVMSolution;
import org.acumos.openstack.client.service.impl.OpenstackCompositeSolution;
import org.acumos.openstack.client.service.impl.OpenstackSimpleSolution;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.acumos.openstack.client.transport.TransportBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.LoggerUtil;
import org.acumos.openstack.client.util.OpenStackConstants;
import org.acumos.openstack.client.util.ParseJSON;
import org.acumos.openstack.client.util.ProbeIndicator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenstackServiceController extends AbstractController {
	
	Logger logger = LoggerFactory.getLogger(OpenstackServiceController.class);
	
	@Autowired
	private Environment env;
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_SINGLE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String singleImageOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackDeployBean auth,HttpServletResponse response) throws Exception {
		LogConfig.setEnteringMDCs("acumos-openstack-client","singleImageOpenstackDeployment","");
		logger.debug(" singleImageOpenstackDeployment Start");
		String uidNumStr="";
		JSONObject  jsonOutput = new JSONObject();
		LoggerUtil loggerUtil=new LoggerUtil();
		try{
			 String flavourName=env.getProperty(OpenStackConstants.OPENSTACK_FLAVOURNAME);
			 String securityGropName=env.getProperty(OpenStackConstants.OPENSTACK_SECURITYGROUPNAME);
			 String endpoint=env.getProperty(OpenStackConstants.OPENSTACK_ENDPOINT);
			 String userName=env.getProperty(OpenStackConstants.OPENSTACK_USERNAME);
			 String userPd=env.getProperty(OpenStackConstants.OPENSTACK_PD);
			 String scopeProject=env.getProperty(OpenStackConstants.OPENSTACK_SCOPEPROJECT);
			 String key=env.getProperty(OpenStackConstants.OPENSTACK_KEY);
			 String keyName=env.getProperty(OpenStackConstants.OPENSTACK_KEYNAME);
			 String IdentifierName=env.getProperty(OpenStackConstants.OPENSTACK_IDENTIFIERNAME);
			 String vmRegisterNumber=env.getProperty(OpenStackConstants.OPENSTACK_VMREGISTERNUMBER);
			 String hostOpenStack=env.getProperty(OpenStackConstants.OPENSTACK_HOSTOPENSTACK);
			 String hostUserName=env.getProperty(OpenStackConstants.OPENSTACK_HOSTUSERNAME);
			 String vmUserName=env.getProperty(OpenStackConstants.OPENSTACK_VMUSERNAME);
			 String dockerUserName=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERUSERNAME);
			 String dockerPd=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERPD);
			 
			 String proxyIP=env.getProperty(OpenStackConstants.OPENSTACK_PROXYIP);
			 String proxyPort=env.getProperty(OpenStackConstants.OPENSTACK_PROXYPORT);
			 String openStackIP=env.getProperty(OpenStackConstants.OPENSTACK_OPENSTACKIP);
			 String repositoryNames=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYNAMES);
			 String repositoryDetails=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYDETAILS);
			 
			 String dataSource=env.getProperty(OpenStackConstants.CMNDATASVC_ENDPOINURL);
			 String cmndatasvcuser=env.getProperty(OpenStackConstants.CMNDATASVC_USER);
			 String cmndatasvcpd=env.getProperty(OpenStackConstants.CMNDATASVC_PD);
			 String requestId=MDC.get(MDCs.REQUEST_ID); 
			 
			 logger.debug("requestId "+requestId);
			 
			 loggerUtil.printsingleImageDetails(flavourName, securityGropName, endpoint, userName, userPd, scopeProject,
					 key, keyName, IdentifierName, vmRegisterNumber, hostOpenStack, hostUserName, vmUserName, 
					 dockerUserName, dockerPd, dataSource, cmndatasvcuser, cmndatasvcpd, proxyIP, proxyPort, 
					 openStackIP, repositoryNames);
			 
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 jsonOutput.put("status", APINames.SUCCESS_RESPONSE);
			 OpenstackSimpleSolution opSingleSolution=new OpenstackSimpleSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,userPd,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPd,uidNumStr,dataSource,cmndatasvcuser,cmndatasvcpd,proxyIP,proxyPort,
					 openStackIP,repositoryNames,repositoryDetails,requestId);
			 Thread t = new Thread(opSingleSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in singleImageOpenstackDeployment "+e);
			LogConfig.clearMDCDetails();
			response.setStatus(401);
			jsonOutput.put("status", APINames.FAILED);
		}
		jsonOutput.put("UIDNumber", uidNumStr);
		logger.debug("jsonOutput.toString() "+jsonOutput.toString());
		logger.debug("singleImageOpenstackDeployment End");
		LogConfig.clearMDCDetails();
		return jsonOutput.toString();
	}
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_COMPOSITE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String compositeOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackCompositeDeployBean auth,HttpServletResponse response) throws Exception {
		LogConfig.setEnteringMDCs("acumos-openstack-client","compositeSolutionOpenstackDeployment","");
		logger.debug("compositeOpenstackDeployment Start");
		
		String uidNumStr="";
		
		TransportBean tbean=new TransportBean();
		JSONObject  jsonOutput = new JSONObject();
		LoggerUtil loggerUtil=new LoggerUtil();
		try{
			 ParseJSON parseJson=new ParseJSON();
			 CommonUtil commonUtil=new CommonUtil();
			 String flavourName=env.getProperty(OpenStackConstants.OPENSTACK_FLAVOURNAME);
			 String securityGropName=env.getProperty(OpenStackConstants.OPENSTACK_SECURITYGROUPNAME);
			 String endpoint=env.getProperty(OpenStackConstants.OPENSTACK_ENDPOINT);
			 String userName=env.getProperty(OpenStackConstants.OPENSTACK_USERNAME);
			 String userPd=env.getProperty(OpenStackConstants.OPENSTACK_PD);
			 String scopeProject=env.getProperty(OpenStackConstants.OPENSTACK_SCOPEPROJECT);
			 String key=env.getProperty(OpenStackConstants.OPENSTACK_KEY);
			 String keyName=env.getProperty(OpenStackConstants.OPENSTACK_KEYNAME);
			 String IdentifierName=env.getProperty(OpenStackConstants.OPENSTACK_IDENTIFIERNAME);
			 String vmRegisterNumber=env.getProperty(OpenStackConstants.OPENSTACK_VMREGISTERNUMBER);
			 String hostOpenStack=env.getProperty(OpenStackConstants.OPENSTACK_HOSTOPENSTACK);
			 String hostUserName=env.getProperty(OpenStackConstants.OPENSTACK_HOSTUSERNAME);
			 String vmUserName=env.getProperty(OpenStackConstants.OPENSTACK_VMUSERNAME);
			 String dockerUserName=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERUSERNAME);
			 String dockerPd=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERPD);
			 String solutionPort=env.getProperty(OpenStackConstants.OPENSTACK_SOLUTIONPORT);
			 String Sleeptime=env.getProperty(OpenStackConstants.OPENSTACK_SLEEPTIME);
			 String proxyIP=env.getProperty(OpenStackConstants.OPENSTACK_PROXYIP);
			 String proxyPort=env.getProperty(OpenStackConstants.OPENSTACK_PROXYPORT);
			 String openStackIP=env.getProperty(OpenStackConstants.OPENSTACK_OPENSTACKIP);
			 String repositoryNames=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYNAMES);
			 String exposeDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_EXPOSEDATABROKERPORT);
			 String internalDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_INTERNALDATABROKERPORT);
			 String nexusRegistyName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYNAME);
			 String nexusRegistyUserName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYUSERNAME);
			 String nexusRegistyPd=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYPD);
			 String repositoryDetails=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYDETAILS);
			 String nginxMapFolder=env.getProperty(OpenStackConstants.NGINX_MAPFOLDER);
			 String nginxWebFolder=env.getProperty(OpenStackConstants.NGINX_WEBFOLDER);
			 String nginxImageName=env.getProperty(OpenStackConstants.NGINX_IMAGENAME);
			 String nginxInternalPort=env.getProperty(OpenStackConstants.NGINX_INTERNALPORT);
			 String azureDataFiles=env.getProperty(OpenStackConstants.DATAFILE_FOLDER);
			 
			 String bluePrintImage=env.getProperty(OpenStackConstants.BLUEPRINT_IMAGENAME);
			 String bluePrintName=env.getProperty(OpenStackConstants.BLUEPRINT_NAME);
			 String bluePrintUserName=env.getProperty(OpenStackConstants.BLUEPRINT_USERNAME);
			 String bluePrintPd=env.getProperty(OpenStackConstants.BLUEPRINT_PD);
			 String bluePrintPortNumber=env.getProperty(OpenStackConstants.BLUEPRINT_PORTNUMBER);
			 
			 String dataSource=env.getProperty(OpenStackConstants.CMNDATASVC_ENDPOINURL);
			 String cmndatasvcuser=env.getProperty(OpenStackConstants.CMNDATASVC_USER);
			 String cmndatasvcpd=env.getProperty(OpenStackConstants.CMNDATASVC_PD);
			 
			 String nexusUrl=env.getProperty(OpenStackConstants.NEXUS_URL);
			 String nexusUserName=env.getProperty(OpenStackConstants.NEXUS_USERNAME);
			 String nexusPd=env.getProperty(OpenStackConstants.NEXUS_PD);
			 //probe
			 String probePrintImage=env.getProperty(OpenStackConstants.PROBE_IMAGENAME);
			 String probePrintName=env.getProperty(OpenStackConstants.PROBE_NAME);
			 String probUser=env.getProperty(OpenStackConstants.PROBE_USERNAME);
			 String probePass=env.getProperty(OpenStackConstants.PROBE_PD);
			 String probeNexusEndPoint=env.getProperty(OpenStackConstants.PROBE_PROBENEXUSENDPOINT);
			 String probeInternalPort=env.getProperty(OpenStackConstants.PROBE_INTERNALPORT);
			 String requestId=MDC.get(MDCs.REQUEST_ID); 
			 
			 logger.debug("requestId "+requestId);
			 
			 loggerUtil.printCompositeDetails(nginxMapFolder, nginxWebFolder, nginxImageName, nginxInternalPort, azureDataFiles,
					 repositoryDetails, exposeDataBrokerPort, internalDataBrokerPort, nexusRegistyName, probePrintImage, 
					 probePrintName, probUser, probePass, probeNexusEndPoint, probeInternalPort, flavourName, securityGropName, 
					 probeNexusEndPoint, userName, userPd, scopeProject, key, keyName, IdentifierName, vmRegisterNumber, 
					 hostOpenStack, hostUserName, vmUserName, dockerUserName, dockerPd, bluePrintImage, bluePrintName, 
					 bluePrintUserName, bluePrintPd, dataSource, cmndatasvcuser, cmndatasvcpd, nexusUrl, nexusUserName, 
					 nexusPd, solutionPort, Sleeptime, auth.getSolutionId(), proxyIP, proxyPort, openStackIP, bluePrintPortNumber, 
					 auth.getSolutionRevisionId(), repositoryNames, nexusRegistyUserName, nexusRegistyPd);
			 String bluePrintStr=commonUtil.getBluePrintNexus(auth.getSolutionId(), auth.getSolutionRevisionId(),dataSource,
					 cmndatasvcuser,cmndatasvcpd,nexusUrl,nexusUserName,nexusPd);
			 logger.debug("bluePrintStr "+bluePrintStr);
			 
			    boolean probeIndicator=parseJson.checkProbeIndicator(OpenStackConstants.JSON_FILE_NAME);
				Blueprint bluePrintProbe=null;
				HashMap<String,String> imageMap=null;
				HashMap<String,DeploymentBean> nodeTypeContainerMap=null;
				ArrayList<String> list=null;
				LinkedList<String> sequenceList=null;
				DataBrokerBean dataBrokerBean=null;
				logger.debug("probeIndicator "+probeIndicator);
				if(probeIndicator){
					 bluePrintProbe =parseJson.jsonFileToObjectProbe(OpenStackConstants.JSON_FILE_NAME,dataBrokerBean);
				 }else{
					bluePrintProbe=parseJson.jsonFileToObject(OpenStackConstants.JSON_FILE_NAME,dataBrokerBean);
				}
			    imageMap=parseJson.parseJsonFileImageMap(OpenStackConstants.JSON_FILE_NAME);
				nodeTypeContainerMap=parseJson.getNodeTypeContainerMap(OpenStackConstants.JSON_FILE_NAME);
				list=commonUtil.iterateImageMap(imageMap);
				sequenceList=parseJson.getSequenceListFromJSON(OpenStackConstants.JSON_FILE_NAME);
				dataBrokerBean=parseJson.getDataBrokerContainer(OpenStackConstants.JSON_FILE_NAME);
				if(dataBrokerBean!=null){
					if(dataBrokerBean!=null){
						ByteArrayOutputStream byteArrayOutputStream=commonUtil.getNexusUrlFile(tbean.getNexusUrl(),tbean.getNexusUserName(),tbean.getNexusPd(),dataBrokerBean.getProtobufFile());
						logger.debug("byteArrayOutputStream "+byteArrayOutputStream);
						if(byteArrayOutputStream!=null){
							dataBrokerBean.setProtobufFile(byteArrayOutputStream.toString());
						}else{
							dataBrokerBean.setProtobufFile("");
						}
						
					 }
				}
			
				logger.debug("bluePrintProbe.getProbeIndocator() "+bluePrintProbe.getProbeIndicator());
				
				ArrayList<ProbeIndicator> probeIndicatorList = bluePrintProbe.getProbeIndicator();
				ProbeIndicator prbIndicator = null;
				if(probeIndicatorList != null && probeIndicatorList.size() >0) {
					prbIndicator = probeIndicatorList.get(0);
				}			
			    if (bluePrintProbe.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True") ) {
			    	list.add(nginxImageName);
					imageMap.put(nginxImageName, OpenStackConstants.NGINX_CONTAINER);
					sequenceList=commonUtil.addContainerSequence(sequenceList,OpenStackConstants.NGINX_CONTAINER);
					if (probePrintImage != null && !"".equals(probePrintImage)) {
						list.add(probePrintImage);
						imageMap.put(probePrintImage, OpenStackConstants.PROBE_CONTAINER_NAME);
						sequenceList=commonUtil.addContainerSequence(sequenceList,OpenStackConstants.PROBE_CONTAINER_NAME);
					}
			    }
			 if(bluePrintImage!=null && !"".equals(bluePrintImage)){
				list.add(bluePrintImage);
				imageMap.put(bluePrintImage, OpenStackConstants.BLUEPRINT_CONTAINER_NAME);
			 }
			 logger.debug("list "+list);
			 logger.debug("imageMap "+imageMap);
			 logger.debug("sequenceList "+sequenceList);
			 tbean.setNexusUrl(nexusUrl);
			 tbean.setNexusUserName(nexusUserName);	
			 tbean.setNexusPd(nexusPd);
			 tbean.setNginxMapFolder(nginxMapFolder);
			 tbean.setNginxWebFolder(nginxWebFolder);
			 tbean.setNginxImageName(nginxImageName);
			 tbean.setNginxInternalPort(nginxInternalPort);
			 tbean.setAzureDataFiles(azureDataFiles);
			 tbean.setRequestId(requestId);
			 
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 logger.debug("uidNumStr "+uidNumStr);
			 jsonOutput.put("status", APINames.SUCCESS_RESPONSE);
			 OpenstackCompositeSolution compositeSolution=new OpenstackCompositeSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,userPd,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPd,bluePrintImage,bluePrintName,bluePrintUserName,bluePrintPd,dataSource,cmndatasvcuser,
					 cmndatasvcpd,nexusUrl,nexusUserName,nexusPd,list,imageMap,sequenceList,bluePrintProbe,uidNumStr,solutionPort,Sleeptime,
					 proxyIP,proxyPort,openStackIP,bluePrintPortNumber,probePrintName,probUser,probePass,nodeTypeContainerMap,probeNexusEndPoint
					 ,probeInternalPort,repositoryNames,dataBrokerBean,exposeDataBrokerPort,internalDataBrokerPort,bluePrintStr,nexusRegistyName,
					 nexusRegistyUserName,nexusRegistyPd,repositoryDetails,tbean);
			 Thread t = new Thread(compositeSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in compositeOpenstackDeployment "+e);
			LogConfig.clearMDCDetails();
			response.setStatus(401);
			MDC.remove("ClassName");
			jsonOutput.put("status", APINames.FAILED);
		}
		jsonOutput.put("UIDNumber", uidNumStr);
		logger.debug("jsonOutput.toString() "+jsonOutput.toString());
		logger.debug("compositeOpenstackDeployment End");
		LogConfig.clearMDCDetails();
		return jsonOutput.toString();
	}
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_EXISTING_VM, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String openstackExistingVMDeployment(@RequestParam("solutionId")String solutionId,@RequestParam("solutionRevisionId")String solutionRevisionId,
			@RequestParam("vmHostIP")String vmHostIP,@RequestParam("vmHostName")String vmHostName,
			@RequestParam("userId")String userId,@RequestParam("urlAttribute")String urlAttribute,@RequestParam("jsonPosition")String jsonPosition,
			@RequestParam("jsonMapping")String jsonMapping,@RequestParam("dataBrokerUserName")String dataBrokerUserName,@RequestParam("dataBrokerUserPd")String dataBrokerUserPd,
			@RequestParam("dataBrokerHost")String dataBrokerHost,@RequestParam("dataBrokerPort")String dataBrokerPort,HttpServletRequest request,HttpServletResponse response) throws Exception {
			String uidNumStr="";
			LogConfig.setEnteringMDCs("acumos-openstack-client","openstackExistingVMDeployment","");
			logger.debug("openstackExistingVMDeployment Start");
			logger.debug("solutionId "+solutionId);
			logger.debug("solutionRevisionId "+solutionRevisionId);
			logger.debug("vmHostIP "+vmHostIP);
			logger.debug("vmHostName "+vmHostName);
			logger.debug("userId "+userId);
			logger.debug("urlAttribute "+urlAttribute);
			logger.debug("jsonPosition "+jsonPosition);
			logger.debug("jsonMapping "+jsonMapping);
			logger.debug("dataBrokerUserName "+dataBrokerUserName);
			logger.debug("dataBrokerUserPd "+dataBrokerUserPd);
			logger.debug("dataBrokerHost "+dataBrokerHost);
			logger.debug("dataBrokerPort "+dataBrokerPort);
			
			UUID uidNumber = UUID.randomUUID();
			JSONObject  jsonOutput = new JSONObject();
			TransportBean tbean=new TransportBean();
			LoggerUtil loggerUtil=new LoggerUtil();
			try{
				String endpoint=env.getProperty(OpenStackConstants.OPENSTACK_ENDPOINT);
				String userName=env.getProperty(OpenStackConstants.OPENSTACK_USERNAME);
				String userPd=env.getProperty(OpenStackConstants.OPENSTACK_PD);
				String key=env.getProperty(OpenStackConstants.OPENSTACK_KEY);
				String keyName=env.getProperty(OpenStackConstants.OPENSTACK_KEYNAME);
				String vmRegisterNumber=env.getProperty(OpenStackConstants.OPENSTACK_VMREGISTERNUMBER);
				String hostOpenStack=env.getProperty(OpenStackConstants.OPENSTACK_HOSTOPENSTACK);
				String hostUserName=env.getProperty(OpenStackConstants.OPENSTACK_HOSTUSERNAME);
				String vmUserName=env.getProperty(OpenStackConstants.OPENSTACK_VMUSERNAME);
				String dockerUserName=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERUSERNAME);
				String dockerPd=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERPD);
				String solutionPort=env.getProperty(OpenStackConstants.OPENSTACK_SOLUTIONPORT);
				String Sleeptime=env.getProperty(OpenStackConstants.OPENSTACK_SLEEPTIME);
				String proxyIP=env.getProperty(OpenStackConstants.OPENSTACK_PROXYIP);
				String proxyPort=env.getProperty(OpenStackConstants.OPENSTACK_PROXYPORT);
				String openStackIP=env.getProperty(OpenStackConstants.OPENSTACK_OPENSTACKIP);
				String repositoryNames=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYNAMES);
				String exposeDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_EXPOSEDATABROKERPORT);
				String internalDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_INTERNALDATABROKERPORT);
				String nexusRegistyName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYNAME);
				String nexusRegistyUserName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYUSERNAME);
				String nexusRegistyPd=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYPD);
				String repositoryDetails=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYDETAILS);
				String nginxMapFolder=env.getProperty(OpenStackConstants.NGINX_MAPFOLDER);
				String nginxWebFolder=env.getProperty(OpenStackConstants.NGINX_WEBFOLDER);
				String nginxImageName=env.getProperty(OpenStackConstants.NGINX_IMAGENAME);
				String nginxInternalPort=env.getProperty(OpenStackConstants.NGINX_INTERNALPORT);
				String azureDataFiles=env.getProperty(OpenStackConstants.DATAFILE_FOLDER);
				 
				String bluePrintImage=env.getProperty(OpenStackConstants.BLUEPRINT_IMAGENAME);
				String bluePrintName=env.getProperty(OpenStackConstants.BLUEPRINT_NAME);
				String bluePrintUserName=env.getProperty(OpenStackConstants.BLUEPRINT_USERNAME);
				String bluePrintPd=env.getProperty(OpenStackConstants.BLUEPRINT_PD);
				String bluePrintPortNumber=env.getProperty(OpenStackConstants.BLUEPRINT_PORTNUMBER);
				 
				String dataSource=env.getProperty(OpenStackConstants.CMNDATASVC_ENDPOINURL);
				String cmndatasvcuser=env.getProperty(OpenStackConstants.CMNDATASVC_USER);
				String cmndatasvcpd=env.getProperty(OpenStackConstants.CMNDATASVC_PD);
				 
				String nexusUrl=env.getProperty(OpenStackConstants.NEXUS_URL);
				String nexusUserName=env.getProperty(OpenStackConstants.NEXUS_USERNAME);
				String nexusPd=env.getProperty(OpenStackConstants.NEXUS_PD);
				 //probe
				String probePrintImage=env.getProperty(OpenStackConstants.PROBE_IMAGENAME);
				String probePrintName=env.getProperty(OpenStackConstants.PROBE_NAME);
				String probUser=env.getProperty(OpenStackConstants.PROBE_USERNAME);
				String probePass=env.getProperty(OpenStackConstants.PROBE_PD);
				String probeNexusEndPoint=env.getProperty(OpenStackConstants.PROBE_PROBENEXUSENDPOINT);
				String probeInternalPort=env.getProperty(OpenStackConstants.PROBE_INTERNALPORT);
				String requestId=MDC.get(MDCs.REQUEST_ID); 
				 
				 tbean.setUidNumStr(uidNumStr);
				 tbean.setEndpoint(endpoint);
				 tbean.setUserName(userName);
				 tbean.setKey(key);
				 tbean.setKeyName(keyName);
				 tbean.setVmRegisterNumber(vmRegisterNumber);
				 tbean.setHostOpenStack(hostOpenStack);
				 tbean.setHostUserName(hostUserName);
				 tbean.setVmUserName(vmUserName);
				 tbean.setDockerUserName(dockerUserName);
				 tbean.setDockerPd(dockerPd);
				 tbean.setBluePrintImage(bluePrintImage);
				 tbean.setBluePrintName(bluePrintName);
				 tbean.setDataSource(dataSource);
				 tbean.setCmndatasvcuser(cmndatasvcuser);
				 tbean.setCmndatasvcpd(cmndatasvcpd);
				 tbean.setNexusUrl(nexusUrl);
				 tbean.setNexusUserName(nexusUserName);	
				 tbean.setNexusPd(nexusPd);
				 tbean.setSolutionPort(solutionPort);
				 tbean.setSleeptime(Sleeptime);
				 tbean.setProxyIP(proxyIP);
				 tbean.setProxyPort(proxyPort);
				 tbean.setOpenStackIP(openStackIP);
				 tbean.setBluePrintPortNumber(bluePrintPortNumber);
				 tbean.setProbePrintImage(probePrintImage);
				 tbean.setProbePrintName(probePrintName);
				 tbean.setProbUser(probUser);
				 tbean.setProbePass(probePass);
				 tbean.setProbeNexusEndPoint(probeNexusEndPoint);
				 tbean.setProbeInternalPort(probeInternalPort);
				 tbean.setRepositoryNames(repositoryNames);
				 tbean.setExposeDataBrokerPort(exposeDataBrokerPort);
				 tbean.setInternalDataBrokerPort(internalDataBrokerPort);
				 tbean.setNexusRegistyName(nexusRegistyName);
				 tbean.setNexusRegistyUserName(nexusRegistyUserName);
				 tbean.setNexusRegistyPd(nexusRegistyPd);
				 tbean.setRepositoryDetails(repositoryDetails);
				 tbean.setNginxMapFolder(nginxMapFolder);
				 tbean.setNginxWebFolder(nginxWebFolder);
				 tbean.setNginxImageName(nginxImageName);
				 tbean.setNginxInternalPort(nginxInternalPort);
				 tbean.setAzureDataFiles(azureDataFiles);
				 tbean.setSolutionId(solutionId);
				 tbean.setSolutionRevisionId(solutionRevisionId);
				 tbean.setVmHostName(vmHostName);
				 tbean.setVmHostIP(vmHostIP);
				 tbean.setUserId(userId);
				 
				 tbean.setUrlAttribute(urlAttribute);
				 tbean.setJsonMapping(jsonMapping);
				 tbean.setJsonPosition(jsonPosition);
				 tbean.setDataBrokerHost(dataBrokerHost);
				 tbean.setDataBrokerHost(dataBrokerHost);
				 tbean.setDataBrokerUserName(dataBrokerUserName);
				 tbean.setDataBrokerUserPd(dataBrokerUserPd);
				 tbean.setRequestId(requestId);
				 loggerUtil.printExistingVMDeployment(tbean);
				 ExistingVMSolution solutionObj=new ExistingVMSolution(tbean);
				 Thread t = new Thread(solutionObj);
		         t.start();
				jsonOutput.put("status", APINames.SUCCESS_RESPONSE);
			}catch(Exception e){
				logger.error("Exception in openstackExistingVMDeployment "+e);
				LogConfig.clearMDCDetails();
				response.setStatus(401);
				jsonOutput.put("status", APINames.FAILED);
			}
			jsonOutput.put("UIDNumber", uidNumStr);
			logger.debug("jsonOutput.toString() "+jsonOutput.toString());
			logger.debug("openstackExistingVMDeployment end");
			LogConfig.clearMDCDetails();
			return jsonOutput.toString();
	}
}
