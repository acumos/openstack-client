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
package org.acumos.openstack.client.service.impl;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.acumos.openstack.client.logging.LogConfig;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.LoggerUtil;
import org.acumos.openstack.client.util.SSHShell;
import org.acumos.openstack.client.util.SingletonMapClass;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.core.transport.ProxyHost;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.SecGroupExtension;
import org.openstack4j.model.compute.Server;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.jcraft.jsch.JSchException;

public class OpenstackSimpleSolution implements Runnable{
	Logger logger = LoggerFactory.getLogger(OpenstackSimpleSolution.class);
	
	//private OSClientV3 os;
	private String flavourName;
	private String  securityGropName;
	private OpenstackDeployBean auth;
	private String endpoint;
	private String userName;
	private String userPd;
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
	private String uidNumStr;
	private String dataSource;
	private String cmndatasvcuser;
	private String cmndatasvcpd;
	private String proxyIP;
	private String proxyPort;
	private String openStackIP;
	private String repositoryNames;
	private String repositoryDetails;
	private String requestId;
	
	public OpenstackSimpleSolution(){
		
	}
	
	public OpenstackSimpleSolution(String flavourName,String  securityGropName,OpenstackDeployBean auth,String endpoint
			 ,String userName,String userPd,String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,
			 String hostOpenStack,String hostUserName,String vmUserName,String dockerUserName,String dockerPd,
			 String uidNumStr,String dataSource,String cmndatasvcuser,String cmndatasvcpd,String proxyIP,String proxyPort,
			 String openStackIP,String repositoryNames,String repositoryDetails,String requestId){
		this.flavourName = flavourName;
		this.securityGropName = securityGropName;
		this.auth = auth;
		this.endpoint = endpoint;
		this.userName = userName;
		this.userPd = userPd;
		this.scopeProject = scopeProject;
		this.key = key;
		this.keyName = keyName;
		this.IdentifierName = IdentifierName;
		this.vmRegisterNumber = vmRegisterNumber;
		this.hostOpenStack = hostOpenStack;
		this.hostUserName = hostUserName;
		this.vmUserName = vmUserName;
		this.dockerUserName = dockerUserName;
		this.dockerPd = dockerPd;
		this.uidNumStr = uidNumStr;
		this.dataSource = dataSource;
		this.cmndatasvcuser = cmndatasvcuser;
		this.cmndatasvcpd = cmndatasvcpd;
		this.proxyIP=proxyIP;
		this.proxyPort=proxyPort;
		this.openStackIP=openStackIP;
		this.repositoryNames=repositoryNames;
		this.repositoryDetails=repositoryDetails;
		this.requestId=requestId;
	}
	
	public void run() {
		String serverId="";
		String flavourId="";
		String securityGropId="";
		String imageId="";
		String fixedAdd="";
		String floatingIp="";
		String vmBindNumber="";
		int vmBind=0;
		OSClientV3 os=null;
		String repositaryName="";
		String repositryImageName="";
		OpanStackContainerBean containerBean=new OpanStackContainerBean();
		CommonUtil commonUtil=new CommonUtil();
		LoggerUtil loggerUtil=new LoggerUtil();
		try{
			LogConfig.setEnteringMDCs("acumos-openstack-client","singleImageOpenstackDeployment",requestId);
			loggerUtil.printSingleImageImplDetails(flavourName,securityGropName,endpoint,userName,userPd,
					scopeProject,key,keyName,IdentifierName,vmRegisterNumber,hostOpenStack,
					hostUserName,vmUserName,dockerUserName,dockerPd,dataSource,
					cmndatasvcuser,cmndatasvcpd,proxyIP,proxyPort,openStackIP,repositoryNames,
					auth.getSolutionId(),auth.getSolutionRevisionId(),auth.getImagetag(),uidNumStr,repositoryDetails);
			 if(proxyPort==null){
				 proxyPort="3128"; 
			 }
			 int proxyPortInt=Integer.parseInt(proxyPort);
			 os = OSFactory.builderV3().endpoint(endpoint)
						.credentials(userName, userPd, Identifier.byName(IdentifierName))
						.scopeToProject(Identifier.byId(scopeProject))
						.withConfig(Config.newConfig().withProxy(ProxyHost.of("http://"+proxyIP, proxyPortInt)))
						.authenticate();
			logger.debug("byId Authnetication success");
			
			logger.debug("flavourName "+flavourName);
			List< ? extends Flavor> flavourList= os.compute().flavors().list();
			logger.debug(" flavourList "+flavourList.size());
			
			if(flavourList!=null && flavourList.size() >0){
				for(Flavor fl:flavourList){
					logger.debug("Flavour Id "+fl.getId());
					String idVal=fl.getId();
					logger.debug("Flavour Name "+fl.getName());
					if(fl.getName()!=null && fl.getName().equalsIgnoreCase(flavourName)){
						flavourId=idVal;
						logger.debug("Flavour Name "+fl.getName()+" flavourId "+flavourId);	
					}
				}
			}
		  logger.debug("flavourId "+flavourId);
		  logger.debug("Start Security group part securityGropName "+securityGropName);
		  List<? extends SecGroupExtension> sg = os.compute().securityGroups().list();
			for(SecGroupExtension sgt: sg){
				logger.debug("security.getName() "+sgt.getName());
				logger.debug("security.ID "+sgt.getId());
				logger.debug("security.Rules "+sgt.getRules());
				if(sgt.getName()!=null && sgt.getName().equalsIgnoreCase(securityGropName)){
					securityGropId=sgt.getId();
				}
			}
		 logger.debug("securityGropId "+securityGropId);	
		 
		 logger.debug("Start Image part");
		 List<? extends Image> images = os.compute().images().list();
		 logger.debug("Images list " + images);
		 Image image = images.get(0);
		 imageId=image.getId();
		 logger.debug("Image id " + imageId);
		 
		 logger.debug("Start Creating VM");
		 Server server = os.compute().servers()
					.boot(Builders.server()
							.name(auth.getVmName())
							.flavor(flavourId)
							.image(image.getId())
							.keypairName(key)
							.addSecurityGroup(securityGropId)
							.build());
		 serverId=server.getId();
		 logger.debug("End Creating VM "+serverId);
		 logger.debug("Start Adding floating point ip ");
		 int limit=5;
		 for(int i=0;i<limit;i++){
			   logger.debug("networking Start "+i);
				Server server2 = os.compute().servers().get(serverId);
				 logger.debug("Address.server2 "+server2.getAddresses());
				if(server2.getAddresses()!=null && server2.getAddresses().getAddresses()!=null){
				    logger.debug("Address.server "+server2.getAddresses());
					Map<String,List<? extends Address >> addressmap=server2.getAddresses().getAddresses();
					logger.debug("addressmap "+addressmap.size());
					 if(!addressmap.isEmpty()){
					    Iterator it = addressmap.entrySet().iterator();
					    while (it.hasNext()) {
					        Map.Entry pair = (Map.Entry)it.next();
					        logger.debug("key pair "+pair.getKey());
					        logger.debug("value pair"+ pair.getValue()); 
					        List<? extends Address> listAddress=(List<? extends Address>)pair.getValue();
					        logger.debug("listAddress "+listAddress);
					        if(listAddress.size() >0){
					        	Address add=(Address)listAddress.get(0);
					        	logger.debug("listAddress.get(0) "+listAddress.get(0));
					        	logger.debug("Address "+add.getAddr());
					        	logger.debug("MacAddr "+add.getMacAddr());
					        	logger.debug("Version "+add.getVersion());
					        	fixedAdd=add.getAddr();
					        }
					    }
					    logger.debug(" Breaking the loop");
					    break;
					 }else{
						 logger.debug("I am in first Sleep");
						 Thread.sleep(30000);
					 }
				}else{
					logger.debug("I am in Second Sleep");
				Thread.sleep(30000);
				}
			}
		 logger.debug("fixedAdd "+fixedAdd);
		 if(fixedAdd!=null && !"".equals(fixedAdd)){
			  Server server3 = os.compute().servers().get(serverId);
			  FloatingIP ip = os.compute().floatingIps().allocateIP("public");
			  String fIp=ip.getFloatingIpAddress();
			  floatingIp=fIp;
			  logger.debug("ip.getFloatingIpAddress() "+fIp);
			  ActionResponse r = os.compute().floatingIps().addFloatingIP(server3, fixedAdd, fIp);
			  logger.debug("ActionResponser "+r.isSuccess());
			  
		}
		
	 Thread.sleep(60000); 
	 logger.debug("Start for VM register Number wait to create vm ");
	 Thread.sleep(180000);
	 HashMap<String,String> singlatonMap=SingletonMapClass.getInstance(); 
	 if(singlatonMap!=null && singlatonMap.get("vmBindNum")!=null){
		 vmBindNumber=singlatonMap.get("vmBindNum"); 
	 }else{
		 vmBindNumber=vmRegisterNumber; 
	 }
	 if(vmBindNumber!=null){
		 vmBind=Integer.parseInt(vmBindNumber); 
		 vmBind=vmBind+1;
		 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBind));
	 }
	 logger.debug("vmRegisterNumber "+vmRegisterNumber+" vmBind "+vmBind);
	 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
	 
	 repositaryName=commonUtil.getRepositryName(auth.getImagetag(),repositoryNames);
	 repositryImageName=commonUtil.getRepositryImageName(auth.getImagetag(),repositoryNames);
	 byte[] bytesArray=commonUtil.readBytesFromFile(keyName);
	 logger.debug("repositaryName "+repositaryName+" repositryImageName "+repositryImageName);
	 commonUtil.sshOpenStackCore(vmBind,floatingIp,hostOpenStack,hostUserName,bytesArray,22,openStackIP);
	 commonUtil.installDockerOpenstack(vmBind,hostOpenStack,vmUserName,bytesArray,repositoryDetails);
	 deploymentImageVM(hostOpenStack,vmUserName,repositaryName,dockerUserName,dockerPd,repositryImageName,vmBind,bytesArray);
	 
	 if(floatingIp!=null && !"".equals(floatingIp)) {
		 logger.debug("Openstack Notification start floatingIp "+floatingIp);
			commonUtil.generateNotification("RackSpace VM is created for Single solution , IP is: "+floatingIp,auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpd);
	 }
	 commonUtil.createDeploymentData(dataSource, cmndatasvcuser, cmndatasvcpd, containerBean,auth.getSolutionId(), 
			 auth.getSolutionRevisionId(),auth.getUserId(), uidNumStr, "DP");
	 
	 logger.debug("End Simple Image deployment");
	  }catch(Exception e){
		  logger.error("Exception in openstackSimpleSolution RUN " +e);
		  LogConfig.clearMDCDetails();
		  try{
			  commonUtil.generateNotification("Error in vm for single solution in rackspace ",auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpd);
			  commonUtil.createDeploymentData(dataSource, cmndatasvcuser, cmndatasvcpd, containerBean,auth.getSolutionId(), 
						 auth.getSolutionRevisionId(),auth.getUserId(), uidNumStr, "FA");  
		  }catch(Exception ex){
			  logger.error("Exception in saving data openstackSimpleSolution " +ex);
			}
		  
	  }
		LogConfig.clearMDCDetails();
	}
	public  String deploymentImageVM(String dockerHostIP, String vmUserName, 
			String registryServerUrl, String username, String userPd, String repositoryName,int vmNum,byte[] bytesArray)throws Exception {
		logger.debug("dockerHostIP " + dockerHostIP);
		logger.debug("vmUserName " + vmUserName);
		logger.debug("registryServerUrl " + registryServerUrl);
		logger.debug("username " + username);
		logger.debug("userPd " + userPd);
		logger.debug("repositoryName " + repositoryName);
		logger.debug("start deploymentImageVM SimpleSolution ");
		SSHShell sshShell = null;
		try {
			String PULL_IMAGE = "" + "docker login --username=" + username + " --password=" + userPd + " "
					+ registryServerUrl + " \n" + "docker pull " + repositoryName + " \n";
			logger.debug("start deploymentImageVM 2 PULL_IMAGE " + PULL_IMAGE);

			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			sshShell.upload(new ByteArrayInputStream(PULL_IMAGE.getBytes()), "PULL_IMAGE.sh", ".azuredocker", true,
					"4095");
			logger.debug(" start deploymentImageVM 3 ");

			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			String output2 = sshShell.executeCommand("bash -c ~/.azuredocker/PULL_IMAGE.sh", true, true);
			logger.debug("start deploymentImageVM 3 output2 " + output2);
			Thread.sleep(30000);
			logger.debug(" start deploymentImageVM check point 1");
			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			String RUN_IMAGE = "" + "docker run -d -p 0.0.0.0:8557:8336  " + repositoryName + " \n";
			logger.debug("output Start check point 4");

			sshShell.upload(new ByteArrayInputStream(RUN_IMAGE.getBytes()), "RUN_DOCKER_IMAGE.sh", ".azuredocker", true,
					"4095");
			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);

			String output3 = sshShell.executeCommand("bash -c ~/.azuredocker/RUN_DOCKER_IMAGE.sh", true, true);
			logger.debug("output Start check point 5 output3 " + output3);

		} catch (JSchException jSchException) {
			logger.error("JSchException in deploymentImageVM "+jSchException);
			throw jSchException;
		} catch (IOException ioException) {
			logger.error("JSchException in deploymentImageVM "+ioException);
			throw ioException;
		} catch (Exception exception) {
			logger.error("JSchException in deploymentImageVM  "+exception);
			throw  exception;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		logger.debug("End deploymentImageVM SimpleSolution");
		return "sucess";
	}

}
