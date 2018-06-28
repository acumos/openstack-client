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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.acumos.openstack.client.api.APINames;
import org.acumos.openstack.client.service.impl.OpenstackCompositeSolution;
import org.acumos.openstack.client.service.impl.OpenstackSimpleSolution;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.OpenStackConstants;
import org.acumos.openstack.client.util.ParseJSON;
import org.acumos.openstack.client.util.ProbeIndicator;
import org.json.JSONObject;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Keypair;
import org.openstack4j.model.compute.SecGroupExtension;
import org.openstack4j.model.compute.SecurityGroup;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.network.NetFloatingIP;
import org.openstack4j.openstack.OSFactory;
import com.jcraft.jsch.JSchException;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.identity.v3.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;

@RestController
public class OpenstackServiceController extends AbstractController {
	
	Logger logger = LoggerFactory.getLogger(OpenstackServiceController.class);
	
	@Autowired
	private Environment env;
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_SINGLE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String singleImageOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackDeployBean auth,HttpServletResponse response) throws Exception {
		logger.debug(" singleImageOpenstackDeployment Start");
		String uidNumStr="";
		String flavourName="";
		String securityGropName="";
		String endpoint="";
		String userName="";
		String password="";
		String scopeProject="";
		String key="";
		String keyName="";
		String IdentifierName="";
		String vmRegisterNumber="";
		String hostOpenStack="";
		String hostUserName="";
		String vmUserName="";
		String dockerUserName="";
		String dockerPassword="";
		String dataSource="";
		String cmndatasvcuser="";
		String cmndatasvcpwd="";
		String proxyIP="";
		String proxyPort="";
		String openStackIP="";
		String repositoryNames="";
		String repositoryDetails="";
		JSONObject  jsonOutput = new JSONObject();
		try{
			 flavourName=env.getProperty(OpenStackConstants.OPENSTACK_FLAVOURNAME);
			 securityGropName=env.getProperty(OpenStackConstants.OPENSTACK_SECURITYGROUPNAME);
			 endpoint=env.getProperty(OpenStackConstants.OPENSTACK_ENDPOINT);
			 userName=env.getProperty(OpenStackConstants.OPENSTACK_USERNAME);
			 password=env.getProperty(OpenStackConstants.OPENSTACK_PASSWORD);
			 scopeProject=env.getProperty(OpenStackConstants.OPENSTACK_SCOPEPROJECT);
			 key=env.getProperty(OpenStackConstants.OPENSTACK_KEY);
			 keyName=env.getProperty(OpenStackConstants.OPENSTACK_KEYNAME);
			 IdentifierName=env.getProperty(OpenStackConstants.OPENSTACK_IDENTIFIERNAME);
			 vmRegisterNumber=env.getProperty(OpenStackConstants.OPENSTACK_VMREGISTERNUMBER);
			 hostOpenStack=env.getProperty(OpenStackConstants.OPENSTACK_HOSTOPENSTACK);
			 hostUserName=env.getProperty(OpenStackConstants.OPENSTACK_HOSTUSERNAME);
			 vmUserName=env.getProperty(OpenStackConstants.OPENSTACK_VMUSERNAME);
			 dockerUserName=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERUSERNAME);
			 dockerPassword=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERPASSWORD);
			 
			 proxyIP=env.getProperty(OpenStackConstants.OPENSTACK_PROXYIP);
			 proxyPort=env.getProperty(OpenStackConstants.OPENSTACK_PROXYPORT);
			 openStackIP=env.getProperty(OpenStackConstants.OPENSTACK_OPENSTACKIP);
			 repositoryNames=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYNAMES);
			 repositoryDetails=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYDETAILS);
			 
			 dataSource=env.getProperty(OpenStackConstants.CMNDATASVC_ENDPOINURL);
			 cmndatasvcuser=env.getProperty(OpenStackConstants.CMNDATASVC_USER);
			 cmndatasvcpwd=env.getProperty(OpenStackConstants.CMNDATASVC_PWD);
			 
			 logger.debug("flavourName "+flavourName);
			 logger.debug("securityGropName "+securityGropName);
			 logger.debug("endpoint "+endpoint);
			 logger.debug("userName "+userName);
			 logger.debug("password "+password);
			 logger.debug("scopeProject "+scopeProject);
			 logger.debug("key "+key);
			 logger.debug("keyName "+keyName);
			 logger.debug("IdentifierName "+IdentifierName);
			 logger.debug("vnRegisterNumber "+vmRegisterNumber);
			 logger.debug("hostOpenStack "+hostOpenStack);
			 logger.debug("hostUserName "+hostUserName);
			 logger.debug("vmUserName "+vmUserName);
			 logger.debug("dockerUserName "+dockerUserName);
			 logger.debug("dockerPassword "+dockerPassword);
			 logger.debug("dataSource "+dataSource);
			 logger.debug("cmndatasvcuser "+cmndatasvcuser);
			 logger.debug("cmndatasvcpwd "+cmndatasvcpwd);
			 logger.debug("proxyIP "+proxyIP);
			 logger.debug("proxyPort "+proxyPort);
			 logger.debug("openStackIP "+openStackIP);
			 logger.debug("repositoryNames "+repositoryNames);
			 logger.debug("repositoryDetails "+repositoryDetails);
			 
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 jsonOutput.put("status", APINames.SUCCESS_RESPONSE);
			 OpenstackSimpleSolution opSingleSolution=new OpenstackSimpleSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,password,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPassword,uidNumStr,dataSource,cmndatasvcuser,cmndatasvcpwd,proxyIP,proxyPort,openStackIP,repositoryNames,repositoryDetails);
			 Thread t = new Thread(opSingleSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in singleImageOpenstackDeployment "+e);
			response.setStatus(401);
			jsonOutput.put("status", APINames.FAILED);
		}
		jsonOutput.put("UIDNumber", uidNumStr);
		logger.debug("jsonOutput.toString() "+jsonOutput.toString());
		logger.debug("singleImageOpenstackDeployment End");
		return jsonOutput.toString();
	}
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_COMPOSITE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String compositeOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackCompositeDeployBean auth,HttpServletResponse response) throws Exception {
		logger.debug("compositeOpenstackDeployment Start");
		
		String uidNumStr="";
		String flavourName="";
		String securityGropName="";
		String endpoint="";
		String userName="";
		String password="";
		String scopeProject="";
		String key="";
		String keyName="";
		String IdentifierName="";
		String vmRegisterNumber="";
		String hostOpenStack="";
		String hostUserName="";
		String vmUserName="";
		String dockerUserName="";
		String dockerPassword="";
		String bluePrintImage="";
		String bluePrintName="";
		String bluePrintUserName="";
		String bluePrintPassword="";
		String dataSource="";
		String cmndatasvcuser="";
		String cmndatasvcpwd="";
		String nexusUrl="";
		String nexusUserName="";
		String nexusPassword="";
		String solutionPort="";
		String Sleeptime="";
		String proxyIP="";
		String proxyPort="";
		String openStackIP="";
		String bluePrintPortNumber="";
		String probePrintImage="";
		String probePrintName="";
		String probUser="";
		String probePass="";
		String jsonFileName="blueprint.json";
		String probeNexusEndPoint="";
		String probeInternalPort="";
		String repositoryNames="";
		String exposeDataBrokerPort="";
		String internalDataBrokerPort="";
		String nexusRegistyName="";
		String nexusRegistyUserName="";
		String nexusRegistyPwd="";
		String repositoryDetails="";
		JSONObject  jsonOutput = new JSONObject();
		try{
			 ParseJSON parseJson=new ParseJSON();
			 CommonUtil commonUtil=new CommonUtil();
			 flavourName=env.getProperty(OpenStackConstants.OPENSTACK_FLAVOURNAME);
			 securityGropName=env.getProperty(OpenStackConstants.OPENSTACK_SECURITYGROUPNAME);
			 endpoint=env.getProperty(OpenStackConstants.OPENSTACK_ENDPOINT);
			 userName=env.getProperty(OpenStackConstants.OPENSTACK_USERNAME);
			 password=env.getProperty(OpenStackConstants.OPENSTACK_PASSWORD);
			 scopeProject=env.getProperty(OpenStackConstants.OPENSTACK_SCOPEPROJECT);
			 key=env.getProperty(OpenStackConstants.OPENSTACK_KEY);
			 keyName=env.getProperty(OpenStackConstants.OPENSTACK_KEYNAME);
			 IdentifierName=env.getProperty(OpenStackConstants.OPENSTACK_IDENTIFIERNAME);
			 vmRegisterNumber=env.getProperty(OpenStackConstants.OPENSTACK_VMREGISTERNUMBER);
			 hostOpenStack=env.getProperty(OpenStackConstants.OPENSTACK_HOSTOPENSTACK);
			 hostUserName=env.getProperty(OpenStackConstants.OPENSTACK_HOSTUSERNAME);
			 vmUserName=env.getProperty(OpenStackConstants.OPENSTACK_VMUSERNAME);
			 dockerUserName=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERUSERNAME);
			 dockerPassword=env.getProperty(OpenStackConstants.OPENSTACK_DOCKERPASSWORD);
			 solutionPort=env.getProperty(OpenStackConstants.OPENSTACK_SOLUTIONPORT);
			 Sleeptime=env.getProperty(OpenStackConstants.OPENSTACK_SLEEPTIME);
			 proxyIP=env.getProperty(OpenStackConstants.OPENSTACK_PROXYIP);
			 proxyPort=env.getProperty(OpenStackConstants.OPENSTACK_PROXYPORT);
			 openStackIP=env.getProperty(OpenStackConstants.OPENSTACK_OPENSTACKIP);
			 repositoryNames=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYNAMES);
			 exposeDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_EXPOSEDATABROKERPORT);
			 internalDataBrokerPort=env.getProperty(OpenStackConstants.OPENSTACK_INTERNALDATABROKERPORT);
			 nexusRegistyName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYNAME);
			 nexusRegistyUserName=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYUSERNAME);
			 nexusRegistyPwd=env.getProperty(OpenStackConstants.OPENSTACK_NEXUSREGISTYPWD);
			 repositoryDetails=env.getProperty(OpenStackConstants.OPENSTACK_REPOSITYDETAILS);
			 
			 
			 bluePrintImage=env.getProperty(OpenStackConstants.BLUEPRINT_IMAGENAME);
			 bluePrintName=env.getProperty(OpenStackConstants.BLUEPRINT_NAME);
			 bluePrintUserName=env.getProperty(OpenStackConstants.BLUEPRINT_USERNAME);
			 bluePrintPassword=env.getProperty(OpenStackConstants.BLUEPRINT_PASSWORD);
			 bluePrintPortNumber=env.getProperty(OpenStackConstants.BLUEPRINT_PORTNUMBER);
			 
			 dataSource=env.getProperty(OpenStackConstants.CMNDATASVC_ENDPOINURL);
			 cmndatasvcuser=env.getProperty(OpenStackConstants.CMNDATASVC_USER);
			 cmndatasvcpwd=env.getProperty(OpenStackConstants.CMNDATASVC_PWD);
			 
			 nexusUrl=env.getProperty(OpenStackConstants.NEXUS_URL);
			 nexusUserName=env.getProperty(OpenStackConstants.NEXUS_USERNAME);
			 nexusPassword=env.getProperty(OpenStackConstants.NEXUS_PASSWORD);
			 //probe
			 probePrintImage=env.getProperty(OpenStackConstants.PROBE_IMAGENAME);
			 probePrintName=env.getProperty(OpenStackConstants.PROBE_NAME);
			 probUser=env.getProperty(OpenStackConstants.PROBE_USERNAME);
			 probePass=env.getProperty(OpenStackConstants.PROBE_PASSWORD);
			 probeNexusEndPoint=env.getProperty(OpenStackConstants.PROBE_PROBENEXUSENDPOINT);
			 probeInternalPort=env.getProperty(OpenStackConstants.PROBE_INTERNALPORT);
			
			
			 logger.debug("repositoryDetails "+repositoryDetails);
			 logger.debug("exposeDataBrokerPort "+exposeDataBrokerPort);
			 logger.debug("internalDataBrokerPort "+internalDataBrokerPort);
			 logger.debug("nexusRegistyName "+nexusRegistyName);
			 logger.debug("probePrintImage "+probePrintImage);
			 logger.debug("probePrintName "+probePrintName);
			 logger.debug("probUser "+probUser);
			 logger.debug("probePass "+probePass);
			 logger.debug("probeNexusEndPoint "+probeNexusEndPoint);
			 logger.debug("probeInternalPort "+probeInternalPort);
			 logger.debug("flavourName "+flavourName);
			 logger.debug("securityGropName "+securityGropName);
			 logger.debug("endpoint "+endpoint);
			 logger.debug("userName "+userName);
			 logger.debug("password "+password);
			 logger.debug("scopeProject "+scopeProject);
			 logger.debug("key "+key);
			 logger.debug("keyName "+keyName);
			 logger.debug("IdentifierName "+IdentifierName);
			 logger.debug("vnRegisterNumber "+vmRegisterNumber);
			 logger.debug("hostOpenStack "+hostOpenStack);
			 logger.debug("hostUserName "+hostUserName);
			 logger.debug("vmUserName "+vmUserName);
			 logger.debug("dockerUserName "+dockerUserName);
			 logger.debug("dockerPassword "+dockerPassword);
			 logger.debug("bluePrintImage "+bluePrintImage);
			 logger.debug("bluePrintName "+bluePrintName);
			 logger.debug("bluePrintUserName "+bluePrintUserName);
			 logger.debug("bluePrintPassword "+bluePrintPassword);
			 logger.debug("dataSource "+dataSource);
			 logger.debug("cmndatasvcuser "+cmndatasvcuser);
			 logger.debug("cmndatasvcpwd "+cmndatasvcpwd);
			 logger.debug("nexusUrl "+nexusUrl);
			 logger.debug("nexusUserName "+nexusUserName);
			 logger.debug("nexusPassword "+nexusPassword);
			 logger.debug("solutionPort "+solutionPort);
			 logger.debug("Sleeptime "+Sleeptime);
			 logger.debug("SolutionId "+auth.getSolutionId());
			 logger.debug("proxyIP "+proxyIP);
			 logger.debug("proxyPort "+proxyPort);
			 logger.debug("openStackIP "+openStackIP);
			 logger.debug("bluePrintPortNumber "+bluePrintPortNumber);
			 logger.debug("authObject.getSolutionRevisionId() "+auth.getSolutionRevisionId());
			 logger.debug("repositoryNames "+repositoryNames);
			 logger.debug("nexusRegistyUserName "+nexusRegistyUserName);
			 logger.debug("nexusRegistyPwd "+nexusRegistyPwd);
			 
			 
			 
			 String bluePrintStr=commonUtil.getBluePrintNexus(auth.getSolutionId(), auth.getSolutionRevisionId(),dataSource,
					 cmndatasvcuser,cmndatasvcpwd,nexusUrl,nexusUserName,nexusPassword);
			 logger.debug("bluePrintStr "+bluePrintStr);
			 
			    boolean probeIndicator=parseJson.checkProbeIndicator(jsonFileName);
				Blueprint bluePrintProbe=null;
				HashMap<String,String> imageMap=null;
				HashMap<String,DeploymentBean> nodeTypeContainerMap=null;
				ArrayList<String> list=null;
				LinkedList<String> sequenceList=null;
				DataBrokerBean dataBrokerBean=null;
				logger.debug("probeIndicator "+probeIndicator);
                if(probeIndicator){
					
					imageMap=parseJson.parseJsonFileImageMap(OpenStackConstants.JSON_FILE_NAME);
					//Node Type and container Name in nodes
					nodeTypeContainerMap=parseJson.getNodeTypeContainerMap(OpenStackConstants.JSON_FILE_NAME);
					// images list
					list=commonUtil.iterateImageMap(imageMap);
					dataBrokerBean=parseJson.getDataBrokerContainer(OpenStackConstants.JSON_FILE_NAME);
					if(dataBrokerBean!=null){
						if(dataBrokerBean!=null){
							ByteArrayOutputStream byteArrayOutputStream=commonUtil.getNexusUrlFile(nexusUrl, nexusUserName, nexusPassword, dataBrokerBean.getProtobufFile());
							logger.debug("byteArrayOutputStream "+byteArrayOutputStream);
							if(byteArrayOutputStream!=null){
								dataBrokerBean.setProtobufFile(byteArrayOutputStream.toString());
							}else{
								dataBrokerBean.setProtobufFile("");
								
							}
							
						 }
					}
					//For new blueprint.json
					 bluePrintProbe =parseJson.jsonFileToObjectProbe(OpenStackConstants.JSON_FILE_NAME,dataBrokerBean);
					//sequence
					sequenceList=parseJson.getSequenceListFromJSON(OpenStackConstants.JSON_FILE_NAME);
				}else{
					//old code 
					imageMap=parseJson.parseJsonFileImageMap(OpenStackConstants.JSON_FILE_NAME);
					//Node Type and container Name in nodes
					nodeTypeContainerMap=parseJson.getNodeTypeContainerMap(OpenStackConstants.JSON_FILE_NAME);
					list=commonUtil.iterateImageMap(imageMap);
					sequenceList=parseJson.getSequenceListFromJSON(OpenStackConstants.JSON_FILE_NAME);
					dataBrokerBean=parseJson.getDataBrokerContainer(OpenStackConstants.JSON_FILE_NAME);
					if(dataBrokerBean!=null){
						if(dataBrokerBean!=null){
							ByteArrayOutputStream byteArrayOutputStream=commonUtil.getNexusUrlFile(nexusUrl, nexusUserName, nexusPassword, dataBrokerBean.getProtobufFile());
							logger.debug("byteArrayOutputStream "+byteArrayOutputStream);
							if(byteArrayOutputStream!=null){
								dataBrokerBean.setProtobufFile(byteArrayOutputStream.toString());
							}else{
								dataBrokerBean.setProtobufFile("");
								
							}
							
						 }
					}
					bluePrintProbe=parseJson.jsonFileToObject(OpenStackConstants.JSON_FILE_NAME,dataBrokerBean);
				}
			
				logger.debug("bluePrintProbe.getProbeIndocator() "+bluePrintProbe.getProbeIndicator());
				
				ArrayList<ProbeIndicator> probeIndicatorList = bluePrintProbe.getProbeIndicator();
				ProbeIndicator prbIndicator = null;
				if(probeIndicatorList != null && probeIndicatorList.size() >0) {
					prbIndicator = probeIndicatorList.get(0);
				}			
			    if (bluePrintProbe.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True") ) {

					if (probePrintImage != null && !"".equals(probePrintImage)) {
						list.add(probePrintImage);
						imageMap.put(probePrintImage, OpenStackConstants.PROBE_CONTAINER_NAME);
						sequenceList=commonUtil.addProbeSequence(sequenceList,OpenStackConstants.PROBE_CONTAINER_NAME);
					}
			    }
			 if(bluePrintImage!=null && !"".equals(bluePrintImage)){
				list.add(bluePrintImage);
				imageMap.put(bluePrintImage, OpenStackConstants.BLUEPRINT_CONTAINER_NAME);
			 }
			 logger.debug("list "+list);
			 logger.debug("imageMap "+imageMap);
			 logger.debug("sequenceList "+sequenceList);
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 logger.debug("uidNumStr "+uidNumStr);
			 jsonOutput.put("status", APINames.SUCCESS_RESPONSE);
			 OpenstackCompositeSolution compositeSolution=new OpenstackCompositeSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,password,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPassword,bluePrintImage,bluePrintName,bluePrintUserName,bluePrintPassword,dataSource,cmndatasvcuser,
					 cmndatasvcpwd,nexusUrl,nexusUserName,nexusPassword,list,imageMap,sequenceList,bluePrintProbe,uidNumStr,solutionPort,Sleeptime,
					 proxyIP,proxyPort,openStackIP,bluePrintPortNumber,probePrintName,probUser,probePass,nodeTypeContainerMap,probeNexusEndPoint
					 ,probeInternalPort,repositoryNames,dataBrokerBean,exposeDataBrokerPort,internalDataBrokerPort,bluePrintStr,nexusRegistyName,
					 nexusRegistyUserName,nexusRegistyPwd,repositoryDetails);
			 Thread t = new Thread(compositeSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in compositeOpenstackDeployment "+e);
			response.setStatus(401);
			jsonOutput.put("status", APINames.FAILED);
		}
		jsonOutput.put("UIDNumber", uidNumStr);
		logger.debug("jsonOutput.toString() "+jsonOutput.toString());
		logger.debug("compositeOpenstackDeployment End");
		return jsonOutput.toString();
	}
}
