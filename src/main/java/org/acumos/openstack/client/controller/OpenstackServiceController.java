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
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.ParseJSON;
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

@RestController
public class OpenstackServiceController extends AbstractController {
	
	Logger logger = LoggerFactory.getLogger(OpenstackServiceController.class);
	
	@Autowired
	private Environment env;
	/*@RequestMapping(value ="/getUIDDetails",  method = RequestMethod.GET, produces=APPLICATION_JSON)
	@ResponseBody
	public String getUIDDetails() {
		JSONObject  jsonOutput = new JSONObject();
		System.out.println("=======getUIDDetails=========");
		try{
			jsonOutput.put("Status", "Succeess");
		}catch(Exception e){
			e.printStackTrace();
		}
        return "Hello bbbbbbb!";
    }*/
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_SINGLE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String singleImageOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackDeployBean auth,HttpServletResponse response) throws Exception {
		logger.debug("=======singleImageOpenstackDeployment===Start==============");
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
		JSONObject  jsonOutput = new JSONObject();
		try{
			 flavourName=env.getProperty("docker.openstack.flavourName");
			 securityGropName=env.getProperty("docker.openstack.securityGroupName");
			 endpoint=env.getProperty("docker.openstack.endpoint");
			 userName=env.getProperty("docker.openstack.userName");
			 password=env.getProperty("docker.openstack.password");
			 scopeProject=env.getProperty("docker.openstack.scopeProject");
			 key=env.getProperty("docker.openstack.key");
			 keyName=env.getProperty("docker.openstack.keyName");
			 IdentifierName=env.getProperty("docker.openstack.IdentifierName");
			 vmRegisterNumber=env.getProperty("docker.openstack.vmRegisterNumber");
			 hostOpenStack=env.getProperty("docker.openstack.hostOpenStack");
			 hostUserName=env.getProperty("docker.openstack.hostUserName");
			 vmUserName=env.getProperty("docker.openstack.vmUserName");
			 dockerUserName=env.getProperty("docker.openstack.dockerUserName");
			 dockerPassword=env.getProperty("docker.openstack.dockerPassword");
			 dataSource=env.getProperty("cmndatasvc.cmndatasvcendpoinurl");
			 cmndatasvcuser=env.getProperty("cmndatasvc.cmndatasvcuser");
			 cmndatasvcpwd=env.getProperty("cmndatasvc.cmndatasvcpwd");
			 proxyIP=env.getProperty("docker.openstack.proxyIP");
			 proxyPort=env.getProperty("docker.openstack.proxyPort");
			 openStackIP=env.getProperty("docker.openstack.openStackIP");
			 
			 logger.debug("<-----flavourName------->"+flavourName);
			 logger.debug("<----securityGropName--->"+securityGropName);
			 logger.debug("<----endpoint----------->"+endpoint);
			 logger.debug("<----userName----------->"+userName);
			 logger.debug("<----password----------->"+password);
			 logger.debug("<----scopeProject----------->"+scopeProject);
			 logger.debug("<----key----------->"+key);
			 logger.debug("<----keyName----------->"+keyName);
			 logger.debug("<----IdentifierName----------->"+IdentifierName);
			 logger.debug("<----vnRegisterNumber----------->"+vmRegisterNumber);
			 logger.debug("<----hostOpenStack----------->"+hostOpenStack);
			 logger.debug("<----hostUserName----------->"+hostUserName);
			 logger.debug("<----vmUserName----------->"+vmUserName);
			 logger.debug("<----dockerUserName----------->"+dockerUserName);
			 logger.debug("<----dockerPassword----------->"+dockerPassword);
			 logger.debug("<----dataSource----------->"+dataSource);
			 logger.debug("<----cmndatasvcuser----------->"+cmndatasvcuser);
			 logger.debug("<----cmndatasvcpwd----------->"+cmndatasvcpwd);
			 logger.debug("<----proxyIP----------------->"+proxyIP);
			 logger.debug("<----proxyPort--------------->"+proxyPort);
			 logger.debug("<----openStackIP--------------->"+openStackIP);
			 
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 jsonOutput.put("Status", uidNumStr);
			 OpenstackSimpleSolution opSingleSolution=new OpenstackSimpleSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,password,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPassword,uidNumStr,dataSource,cmndatasvcuser,cmndatasvcpwd,proxyIP,proxyPort,openStackIP);
			 Thread t = new Thread(opSingleSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in---singleImageOpenstackDeployment------>"+e.getMessage());
		}
		//logger.debug("<------start----singleImageOpenstackDeployment------------>");
		logger.debug("=======singleImageOpenstackDeployment=End first thread==jsonOutput.toString()===="+jsonOutput.toString());
		return jsonOutput.toString();
	}
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_COMPOSITE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String compositeOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackCompositeDeployBean auth,HttpServletResponse response) throws Exception {
		logger.debug("=======compositeOpenstackDeployment===Start==============");
		
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
		JSONObject  jsonOutput = new JSONObject();
		try{
			 ParseJSON parseJson=new ParseJSON();
			 CommonUtil commonUtil=new CommonUtil();
			 flavourName=env.getProperty("docker.openstack.flavourName");
			 securityGropName=env.getProperty("docker.openstack.securityGroupName");
			 endpoint=env.getProperty("docker.openstack.endpoint");
			 userName=env.getProperty("docker.openstack.userName");
			 password=env.getProperty("docker.openstack.password");
			 scopeProject=env.getProperty("docker.openstack.scopeProject");
			 key=env.getProperty("docker.openstack.key");
			 keyName=env.getProperty("docker.openstack.keyName");
			 IdentifierName=env.getProperty("docker.openstack.IdentifierName");
			 vmRegisterNumber=env.getProperty("docker.openstack.vmRegisterNumber");
			 hostOpenStack=env.getProperty("docker.openstack.hostOpenStack");
			 hostUserName=env.getProperty("docker.openstack.hostUserName");
			 vmUserName=env.getProperty("docker.openstack.vmUserName");
			 dockerUserName=env.getProperty("docker.openstack.dockerUserName");
			 dockerPassword=env.getProperty("docker.openstack.dockerPassword");
			 bluePrintImage=env.getProperty("docker.openstack.bluePrintImageName");
			 bluePrintName=env.getProperty("docker.openstack.bluePrintName");
			 bluePrintUserName=env.getProperty("docker.openstack.bluePrintUserName");
			 bluePrintPassword=env.getProperty("docker.openstack.bluePrintPassword");
			 dataSource=env.getProperty("cmndatasvc.cmndatasvcendpoinurl");
			 cmndatasvcuser=env.getProperty("cmndatasvc.cmndatasvcuser");
			 cmndatasvcpwd=env.getProperty("cmndatasvc.cmndatasvcpwd");
			 nexusUrl=env.getProperty("nexus.url");
			 nexusUserName=env.getProperty("nexus.username");
			 nexusPassword=env.getProperty("nexus.password");
			 solutionPort=env.getProperty("docker.openstack.solutionPort");
			 Sleeptime=env.getProperty("docker.openstack.Sleeptime");
			 proxyIP=env.getProperty("docker.openstack.proxyIP");
			 proxyPort=env.getProperty("docker.openstack.proxyPort");
			 openStackIP=env.getProperty("docker.openstack.openStackIP");
			 bluePrintPortNumber=env.getProperty("docker.openstack.bluePrintPortNumber");
			 
			 logger.debug("<-----flavourName------->"+flavourName);
			 logger.debug("<----securityGropName--->"+securityGropName);
			 logger.debug("<----endpoint----------->"+endpoint);
			 logger.debug("<----userName----------->"+userName);
			 logger.debug("<----password----------->"+password);
			 logger.debug("<----scopeProject----------->"+scopeProject);
			 logger.debug("<----key----------->"+key);
			 logger.debug("<----keyName----------->"+keyName);
			 logger.debug("<----IdentifierName----------->"+IdentifierName);
			 logger.debug("<----vnRegisterNumber----------->"+vmRegisterNumber);
			 logger.debug("<----hostOpenStack----------->"+hostOpenStack);
			 logger.debug("<----hostUserName----------->"+hostUserName);
			 logger.debug("<----vmUserName----------->"+vmUserName);
			 logger.debug("<----dockerUserName----------->"+dockerUserName);
			 logger.debug("<----dockerPassword----------->"+dockerPassword);
			 logger.debug("<----bluePrintImage----------->"+bluePrintImage);
			 logger.debug("<----bluePrintName----------->"+bluePrintName);
			 logger.debug("<----bluePrintUserName----------->"+bluePrintUserName);
			 logger.debug("<----bluePrintPassword----------->"+bluePrintPassword);
			 logger.debug("<----dataSource----------->"+dataSource);
			 logger.debug("<----cmndatasvcuser----------->"+cmndatasvcuser);
			 logger.debug("<----cmndatasvcpwd----------->"+cmndatasvcpwd);
			 logger.debug("<----nexusUrl----------->"+nexusUrl);
			 logger.debug("<----nexusUserName----------->"+nexusUserName);
			 logger.debug("<----nexusPassword----------->"+nexusPassword);
			 logger.debug("<----solutionPort----------->"+solutionPort);
			 logger.debug("<----Sleeptime----------->"+Sleeptime);
			 logger.debug("<------SolutionId---------->"+auth.getSolutionId());
			 logger.debug("<----proxyIP--------------->"+proxyIP);
			 logger.debug("<----proxyPort------------->"+proxyPort);
			 logger.debug("<----openStackIP----------->"+openStackIP);
			 logger.debug("<----bluePrintPortNumber----------->"+bluePrintPortNumber);
			 logger.debug("<------authObject.getSolutionRevisionId()---------->"+auth.getSolutionRevisionId());
			 
			 
			 String bluePrintStr=commonUtil.getBluePrintNexus(auth.getSolutionId(), auth.getSolutionRevisionId(),dataSource,
					 cmndatasvcuser,cmndatasvcpwd,nexusUrl,nexusUserName,nexusPassword);
			 logger.debug("<------bluePrintStr---------->"+bluePrintStr);
			 
			 
			 Blueprint bluePrint=parseJson.jsonFileToObject();
		 	 HashMap<String,String> imageMap=parseJson.parseJsonFile();
			 ArrayList<String> list=commonUtil.iterateImageMap(imageMap);
			 LinkedList<String> sequenceList=parseJson.getSequenceFromJSON();
			
			 if(bluePrintImage!=null && !"".equals(bluePrintImage)){
				list.add(bluePrintImage);
				imageMap.put(bluePrintImage, "BluePrintContainer");
			 }
			 logger.debug("<----list----------->"+list);
			 logger.debug("<----imageMap----------->"+imageMap);
			 UUID uidNumber = UUID.randomUUID();
			 uidNumStr=uidNumber.toString();
			 logger.debug("<----uidNumStr----------->"+uidNumStr);
			 jsonOutput.put("Status", uidNumStr);
			 OpenstackCompositeSolution compositeSolution=new OpenstackCompositeSolution(flavourName,securityGropName,auth,endpoint
					 ,userName,password,scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,hostUserName,
					 vmUserName,dockerUserName,dockerPassword,bluePrintImage,bluePrintName,bluePrintUserName,bluePrintPassword,dataSource,cmndatasvcuser,
					 cmndatasvcpwd,nexusUrl,nexusUserName,nexusPassword,list,imageMap,sequenceList,bluePrint,uidNumStr,solutionPort,Sleeptime,
					 proxyIP,proxyPort,openStackIP,bluePrintPortNumber);
			 Thread t = new Thread(compositeSolution);
	         t.start();
		 
		 
		}catch(Exception e){
			logger.error("Exception in---compositeOpenstackDeployment------>"+e.getMessage());
		}
		
		logger.debug("=======compositeOpenstackDeployment===End first thread==============");
		return jsonOutput.toString();
	}
}
