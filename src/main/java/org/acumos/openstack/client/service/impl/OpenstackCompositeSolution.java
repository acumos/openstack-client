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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.acumos.openstack.client.transport.ContainerInfo;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.DockerInfo;
import org.acumos.openstack.client.util.DockerInfoList;
import org.acumos.openstack.client.util.OpenStackConstants;
import org.acumos.openstack.client.util.ProbeIndicator;
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

import com.jcraft.jsch.JSchException;

public class OpenstackCompositeSolution implements Runnable {
	
Logger logger = LoggerFactory.getLogger(OpenstackCompositeSolution.class);
	
	
	private String flavourName;
	private String  securityGropName;
	private OpenstackCompositeDeployBean auth;
	private String endpoint;
	private String userName;
	private String password;
	private String scopeProject;
	private String key;
	private String keyName;
	private String IdentifierName;
	private String vmRegisterNumber;
	private String hostOpenStack;
	private String hostUserName;
	private String vmUserName;
	private String dockerUserName;
	private String dockerPassword;	
	private String bluePrintImage;
	private String bluePrintName;
	private String bluePrintUserName;
	private String bluePrintPassword;
	private String dataSource;
	private String cmndatasvcuser;
	private String cmndatasvcpwd;
	private String nexusUrl;
	private String nexusUserName;
	private String nexusPassword;
	private ArrayList<String> list;
	private HashMap<String,String> imageMap;
	private LinkedList<String> sequenceList;
	private Blueprint bluePrint;
	private String uidNumStr;
	private String solutionPort;
	private String Sleeptime;
	private String proxyIP;
	private String proxyPort;
	private String openStackIP;
	private String bluePrintPortNumber;
	private String probePrintName;
	private String probUser;
	private String probePass;
	private HashMap<String,DeploymentBean> nodeTypeContainerMap;
	private String probeNexusEndPoint;
	private String probeInternalPort;
	private String repositoryNames;
	private DataBrokerBean dataBrokerBean;
	private String exposeDataBrokerPort;
	private String internalDataBrokerPort;
	private String bluePrintStr;
	private String nexusRegistyName;
	private String nexusRegistyUserName;
	private String nexusRegistyPwd;
	private String repositoryDetails;
	
	public OpenstackCompositeSolution(){
		
	}
	
	public OpenstackCompositeSolution(String flavourName,String  securityGropName,OpenstackCompositeDeployBean auth,String endpoint
			 ,String userName,String password,String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,
			 String hostOpenStack,String hostUserName,String vmUserName,String dockerUserName,String dockerPassword,String bluePrintImage,
			 String bluePrintName,String bluePrintUserName,String bluePrintPassword,String dataSource,String cmndatasvcuser,String cmndatasvcpwd,
			 String nexusUrl,String nexusUserName,String nexusPassword,ArrayList<String> list,HashMap<String,String> imageMap,LinkedList<String> sequenceList,
			 Blueprint bluePrint,String uidNumStr,String solutionPort,String Sleeptime,String proxyIP,String proxyPort,
			 String openStackIP,String bluePrintPortNumber,String probePrintName,String probUser,String probePass,
			 HashMap<String,DeploymentBean> nodeTypeContainerMap,String probeNexusEndPoint,String probeInternalPort,String repositoryNames,
			 DataBrokerBean dataBrokerBean,String exposeDataBrokerPort,String internalDataBrokerPort,String bluePrintStr,String nexusRegistyName,
			 String nexusRegistyUserName,String nexusRegistyPwd,String repositoryDetails){
			//this.os = os;
			this.flavourName = flavourName;
			this.securityGropName = securityGropName;
			this.auth = auth;
			this.endpoint = endpoint;
			this.userName = userName;
			this.password = password;
			this.scopeProject = scopeProject;
			this.key = key;
			this.keyName = keyName;
			this.IdentifierName = IdentifierName;
			this.vmRegisterNumber = vmRegisterNumber;
			this.hostOpenStack = hostOpenStack;
			this.hostUserName = hostUserName;
			this.vmUserName = vmUserName;
			this.dockerUserName = dockerUserName;
			this.dockerPassword = dockerPassword;
			this.bluePrintImage = bluePrintImage;
			this.bluePrintName = bluePrintName;
			this.bluePrintUserName = bluePrintUserName;
			this.bluePrintPassword = bluePrintPassword;
			this.dataSource = dataSource;
			this.cmndatasvcuser = cmndatasvcuser;
			this.cmndatasvcpwd = cmndatasvcpwd;
			this.nexusUrl = nexusUrl;
			this.nexusUserName = nexusUserName;
			this.nexusPassword = nexusPassword;
			this.list=list;
			this.imageMap=imageMap;
			this.sequenceList=sequenceList;
			this.bluePrint=bluePrint;
			this.uidNumStr=uidNumStr;
			this.solutionPort=solutionPort;
			this.Sleeptime=Sleeptime;
			this.proxyIP=proxyIP;
			this.proxyPort=proxyPort;
			this.openStackIP=openStackIP;
			this.bluePrintPortNumber=bluePrintPortNumber;
			this.probePrintName=probePrintName;
			this.probUser=probUser;
			this.probePass=probePass;
			this.nodeTypeContainerMap=nodeTypeContainerMap;
			this.probeNexusEndPoint=probeNexusEndPoint;
			this.probeInternalPort=probeInternalPort;
			this.repositoryNames=repositoryNames;
			this.dataBrokerBean=dataBrokerBean;
			this.exposeDataBrokerPort = exposeDataBrokerPort;
			this.internalDataBrokerPort = internalDataBrokerPort;
			this.bluePrintStr = bluePrintStr;
			this.nexusRegistyName = nexusRegistyName;
			this.nexusRegistyUserName = nexusRegistyUserName;
			this.nexusRegistyPwd = nexusRegistyPwd;
			this.repositoryDetails=repositoryDetails;
	}
	
	
	public void run() {
		logger.debug("Start CompositeSolution RUN");
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
		String bluePrintPort="";
		List<OpanStackContainerBean> openStackContainerBeanList=new ArrayList<OpanStackContainerBean>();
		CommonUtil commonUtil=new CommonUtil();
		int sleepTimeInt=0;
		String stackIp="";
		int vmBindCount=0;
		String probeIP="";
		String probePort="";
		HashMap<String,String> portMap=new HashMap<String,String>();
		String containerInstanceBluePrint="BluePrintContainer";
		String containerInstanceProbe="Probe";
		List<ContainerInfo> probeContainerBeanList=new ArrayList<ContainerInfo>();
		List<DeploymentBean> deploymentList=new ArrayList<DeploymentBean>();
		try{
			
			 logger.debug("flavourName "+flavourName);
			 logger.debug("securityGropName "+securityGropName);
			 logger.debug("endpoint "+endpoint);
			 logger.debug("userName "+userName);
			 logger.debug("password "+password);
			 logger.debug("scopeProject "+scopeProject);
			 logger.debug("key "+key);
			 logger.debug("keyName "+keyName);
			 logger.debug("IdentifierName "+IdentifierName);
			 logger.debug("vmRegisterNumber "+vmRegisterNumber);
			 logger.debug("vmUserName "+vmUserName);
			 logger.debug("dockerUserName "+dockerUserName);
			 logger.debug("dockerPassword "+dockerPassword);
			 logger.debug("solutionPort "+solutionPort);
			 logger.debug("Sleeptime "+Sleeptime);
			 logger.debug("proxyIP "+proxyIP);
			 logger.debug("proxyPort "+proxyPort);
			 logger.debug("openStackIP "+openStackIP);
			 logger.debug("bluePrintPortNumber "+bluePrintPortNumber);
			 logger.debug("probePrintName "+probePrintName);
			 logger.debug("probUser "+probUser);
			 logger.debug("probePass "+probePass);
			 logger.debug("nodeTypeContainerMap "+nodeTypeContainerMap);
			 logger.debug("SoulutionId "+auth.getSolutionId());
			 logger.debug("SolutionRevisionId "+auth.getSolutionRevisionId());
			 logger.debug("probeNexusEndPoint "+probeNexusEndPoint);
			 logger.debug("probeInternalPort "+probeInternalPort);
			 logger.debug("nexusRegistyName "+nexusRegistyName);
			 logger.debug("exposeDataBrokerPort "+exposeDataBrokerPort);
			 logger.debug("internalDataBrokerPort "+internalDataBrokerPort);
			 logger.debug("nexusRegistyUserName "+nexusRegistyUserName);
			 logger.debug("nexusRegistyPwd "+nexusRegistyPwd);
			 logger.debug(" JSON FROM DS bluePrintStr "+bluePrintStr);
			 int proxyPortInt=Integer.parseInt(proxyPort);
			 int bluePrintPorInt=Integer.parseInt(bluePrintPortNumber);
			 int listSize=list.size();
			 String portArr[]={"8557","8558","8559","8560","8561","8562","8563","8564","8565"};
			 os = OSFactory.builderV3().endpoint(endpoint)
					.credentials(userName, password, Identifier.byName(IdentifierName))
					.scopeToProject(Identifier.byId(scopeProject))
					.withConfig(Config.newConfig().withProxy(ProxyHost.of("http://"+proxyIP, proxyPortInt)))
					.authenticate();
			logger.debug("byId Authnetication success");
			sleepTimeInt=Integer.parseInt(Sleeptime);
			logger.debug("sleepTimeInt "+sleepTimeInt);
			logger.debug("flavourName "+flavourName);
			List< ? extends Flavor> flavourList= os.compute().flavors().list();
			logger.debug("flavourList "+flavourList.size());
			
			if(flavourList!=null && flavourList.size() >0){
				for(Flavor fl:flavourList){
					logger.debug("Flavour Id "+fl.getId());
					String idVal=fl.getId();
					logger.debug("Flavour Name "+fl.getName());
					if(fl.getName()!=null && fl.getName().equalsIgnoreCase(flavourName)){
						flavourId=idVal;
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
		 
		 logger.debug("Start Image part ");
		 List<? extends Image> images = os.compute().images().list();
		 logger.debug("Images list " + images);
		 Image image = images.get(0);
		 imageId=image.getId();
		 logger.debug("Image id " + imageId);
		 
		 logger.debug("Start Creating vm ");
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
		 logger.debug("Start Adding floating point ip");
		 int limit=5;
		 for(int i=0;i<limit;i++){
			   logger.debug("networking Start i "+i);
				Server server2 = os.compute().servers().get(serverId);
				 logger.debug("Address.server2 "+server2.getAddresses());
				if(server2.getAddresses()!=null && server2.getAddresses().getAddresses()!=null){
				    logger.debug("Address.server "+server2.getAddresses());
					Map<String,List<? extends Address >> addressmap=server2.getAddresses().getAddresses();
					logger.debug("...addressmap...."+addressmap.size());
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
					    logger.debug("Breaking the loop");
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
	 logger.debug("Start for VM register Number"); 
	 logger.debug("wait to create VM");
	 Thread.sleep(180000);
	 HashMap<String,String> singlatonMap=SingletonMapClass.getInstance(); 
	 if(singlatonMap!=null && singlatonMap.get("vmBindNum")!=null){
		 vmBindNumber=singlatonMap.get("vmBindNum"); 
		 logger.debug("vmBindNumber 1 "+vmBindNumber);
	 }else{
		 vmBindNumber=vmRegisterNumber; 
		 logger.debug("vmBindNumber 2 "+vmBindNumber);
	 }
	 if(vmBindNumber!=null){
		 vmBind=Integer.parseInt(vmBindNumber); 
		 vmBind=vmBind+1;
		 vmBindCount=vmBind;
		 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBind));
	 }
	 logger.debug("vmRegisterNumber "+vmRegisterNumber+" vmBind "+vmBind);
	 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
	 
	 
	 byte[] bytesArray=readBytesFromFile(keyName);
	 
	 
	 sshOpenStackCore(vmBind,floatingIp,hostOpenStack,hostUserName,bytesArray,22);
	 for(int i=0;i<listSize;i++){
		 String portTunnel=portArr[i];
		 int portTunnelInt=Integer.parseInt(portTunnel);
		 vmBindCount=vmBindCount+1;
		 portMap.put(portTunnel, String.valueOf(vmBindCount));
		 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
		 sshOpenStackCore(vmBindCount,floatingIp,hostOpenStack,hostUserName,bytesArray,portTunnelInt);
		 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
	 }
	 vmBindCount=vmBindCount+1;
	 logger.debug("vmBindCount "+vmBindCount);
	 sshOpenStackCore(vmBindCount,floatingIp,hostOpenStack,hostUserName,bytesArray,bluePrintPorInt);
	 portMap.put(bluePrintPortNumber, String.valueOf(vmBindCount));
	 logger.debug("portMap "+portMap);
	 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBindCount));
	 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
	 
	 //bluePrintPort.sshOpenStackCore(vmBind,floatingIp,hostOpenStack,hostUserName,bytesArray);
	 installDockerOpenstack(vmBind,hostOpenStack,vmUserName,bytesArray,repositoryDetails);
	 
	 String portNumber="";
	 int count=0;
	 DockerInfoList  dockerList=new DockerInfoList();
	 logger.debug("list "+list);
	 logger.debug("sequenceList "+sequenceList);
	 List<DockerInfo> dockerInfoList=new ArrayList<DockerInfo>();
		 if(list!=null &&  list.size() > 0){
			 if(sequenceList!=null && sequenceList.size() > 0){
				 Iterator seqItr = sequenceList.iterator();
	             while (seqItr.hasNext()) {
	                 String jsonContainerName=(String)seqItr.next(); 
	                 logger.debug("jsonContainerName "+jsonContainerName);
	                 if(jsonContainerName!=null && !"".equals(jsonContainerName)){
	                	 
	                	 Iterator repoContainer=imageMap.entrySet().iterator();
	 		             while(repoContainer.hasNext()){
	 		            	String portNumberString=""; 
	 		            	Map.Entry pair = (Map.Entry)repoContainer.next();
	 		            	String imageNameVal=(String)pair.getKey();
	 		            	String finalContainerName=(String)pair.getValue();
	 		            	logger.debug("finalContainerName "+finalContainerName);
	 		            	logger.debug("jsonContainerName "+jsonContainerName);
	 		            	logger.debug("imageNameVal "+imageNameVal);
	 		            	if(finalContainerName!=null && !finalContainerName.equalsIgnoreCase(jsonContainerName)){
			            		 logger.debug("Continue.............................................");
			            		continue;
			            	}
	 		            	String nodeTypeContainer="";
    		            	String nodeTypeName="";
    		            	if(nodeTypeContainerMap!=null && nodeTypeContainerMap.size() > 0 && nodeTypeContainerMap.get(finalContainerName)!=null){
    		            		DeploymentBean dBean=nodeTypeContainerMap.get(finalContainerName);
    		            		if(dBean!=null){
    		            			nodeTypeContainer=dBean.getNodeType();
    		            			nodeTypeName=dBean.getDataBrokerType();
    		            		}
    		            		
    		            	}
    		            	logger.debug("nodeTypeName "+nodeTypeName);
    		            	logger.debug("nodeTypeContainer "+nodeTypeContainer);
	 		            	logger.debug("finalContainerName "+finalContainerName);
	 		            	logger.debug("containerInstanceBluePrint "+containerInstanceBluePrint);
	 		            	logger.debug("containerInstanceProbe "+containerInstanceProbe);
	 		            	DockerInfo dockerinfo=new DockerInfo();
	 		            	OpanStackContainerBean containerBean=new OpanStackContainerBean();
	 		            	logger.debug("imageNameVal "+imageNameVal);
	 		            	repositaryName=commonUtil.getRepositryName(imageNameVal,repositoryNames);
		            		repositryImageName=commonUtil.getRepositryImageName(imageNameVal,repositoryNames);
		            		logger.debug("repositaryName "+repositaryName+" repositryImageName "+repositryImageName);
		            		boolean nexusRepo=commonUtil.getRepositryStatus(imageNameVal,nexusRegistyName);
		            		logger.debug("nexusRepo "+nexusRepo);
	 		            	if(containerInstanceBluePrint!=null && containerInstanceBluePrint.equalsIgnoreCase(finalContainerName)){
	 		            		portNumber=bluePrintPortNumber;
	 		            		bluePrintPort=portNumber;
	 		            		portNumberString=portNumber+":"+portNumber;
	 		            		logger.debug("imageNameVal "+imageNameVal);
	 		            		logger.debug("portNumberString "+portNumberString);
	 		            		deploymentImageVM(hostOpenStack,vmUserName,repositaryName,bluePrintUserName,bluePrintPassword,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,11,sleepTimeInt,probeNexusEndPoint);
	 		            	}else{
	 		            		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
	 		            			portNumber=probeInternalPort;
	 		            			portNumberString=probeInternalPort+":"+probeInternalPort;
	 		            		}else if(nodeTypeContainer!=null && !"".equals(nodeTypeContainer) && nodeTypeContainer.equalsIgnoreCase(OpenStackConstants.DATABROKER_NAME)
	    		        					&& nodeTypeName!=null && !"".equals(nodeTypeName) && nodeTypeName.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_CSV_FILE)){
	    		        				portNumberString=exposeDataBrokerPort+":"+internalDataBrokerPort;
	    		        				portNumber=exposeDataBrokerPort;
	 		            		}else{
	 		            			portNumber=portArr[count];
	 		            			if(solutionPort!=null && !"".equals(solutionPort)){
				        				portNumberString=portNumber+":"+solutionPort;
				        			}else{
				        				portNumberString=portNumber+":"+portNumber;
				        			}
		 		            		count++;
		 		            		logger.debug("portNumberString "+portNumberString);
	 		            		}
	 		            		
	 		            		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
	 		            			logger.debug("Deploying Probe container finalContainerName "+finalContainerName);
	 		            			deploymentImageVM(hostOpenStack,vmUserName,repositaryName,probUser,probePass,repositryImageName,vmBind,bytesArray,
		 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint);
	 		            		}else if(nexusRepo){
	 		            			logger.debug("Deploying Container else if finalContainerName "+finalContainerName);
	 		            			//dockerinfo.setNodeType("Probe");
	 		            		  deploymentImageVM(hostOpenStack,vmUserName,repositaryName,nexusRegistyUserName,nexusRegistyPwd,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint);
	 		            		}else{
	 		            			logger.debug(" Deploying Container finalContainerName "+finalContainerName);
	 		            			//dockerinfo.setNodeType("Probe");
	 		            		  deploymentImageVM(hostOpenStack,vmUserName,repositaryName,dockerUserName,dockerPassword,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint);
	 		            		}
	 		            	}
	 		            	dockerinfo.setIpAddress(floatingIp);
	    		            dockerinfo.setPort(portNumber);
	    		            dockerinfo.setContainer(finalContainerName);
	    		            dockerInfoList.add(dockerinfo);
	    		            
	    		            containerBean.setContainerName(finalContainerName);
    		        		containerBean.setContainerIp(floatingIp);
    		        		containerBean.setContainerPort(portNumber);
    		        		openStackContainerBeanList.add(containerBean);
    		        		
    		        		DeploymentBean deploymentBean=new DeploymentBean();
    		        		deploymentBean.setVmIP(floatingIp);
    		        		deploymentBean.setVmName("");
    		        		deploymentBean.setContainerName(finalContainerName);
    		        		deploymentBean.setContainerPort(portNumber);
    		        		if(portMap!=null && portMap.get(portNumber)!=null){
    		        			logger.debug("portNumber :" + portNumber+" TunnelNum "+portMap.get(portNumber));
    		        			deploymentBean.setTunnelNumber(portMap.get(portNumber));
    		        		}
    		        		ContainerInfo containerInfo = new ContainerInfo();
    		        		containerInfo.setContainerName(finalContainerName);
    		        		containerInfo.setContainerIp(floatingIp);
    		        		containerInfo.setContainerPort(portNumber);
    		        		
    		        		//containerInfo.setNodeType(nodeTypeContainer);
    		        		logger.debug("Before-Probe-containerInstanceProbe "+containerInstanceProbe+" finalContainerName "+finalContainerName);
    		        		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
    		        			logger.debug("After Probe containerInstanceprobe "+containerInstanceProbe+" finalContainerName "+finalContainerName);
	    		        	   containerInfo.setNodeType("Probe");
	    		        	   probeIP = floatingIp;
	    		        	   probePort = portNumber;
	    		        	   containerInfo.setNodeType(OpenStackConstants.PROBE_NODE_TYPE);
	    		        	   deploymentBean.setNodeType(OpenStackConstants.PROBE_NODE_TYPE);
	    		        	   deploymentBean.setDataBrokerType("");
    		        		}else if(nodeTypeContainer!=null && !"".equals(nodeTypeContainer)){
	    		        			containerInfo.setNodeType(nodeTypeContainer);
	    		        			deploymentBean.setNodeType(nodeTypeContainer);
	    		        			deploymentBean.setDataBrokerType(nodeTypeName);
    		        		}else{
    		        			containerInfo.setNodeType(OpenStackConstants.DEFAULT_NODE_TYPE);
    		        			deploymentBean.setNodeType(OpenStackConstants.DEFAULT_NODE_TYPE);
    		        			deploymentBean.setDataBrokerType("");
    		        	    }
                           probeContainerBeanList.add(containerInfo);
                           deploymentList.add(deploymentBean);
	 		            	
	 		            }	
	                	 
	                 }
	             } 
				 
			}else{
				logger.debug("sequenceList is blank "); 	
			}
		 }else{
			 logger.debug("List is blank"); 
		 }
		 logger.debug(" dockerInfoList " + dockerInfoList);
	     if(dockerInfoList!=null && dockerInfoList.size() > 0){
	       	dockerList.setDockerList(dockerInfoList);
	      }
	     logger.debug(" bluePrintPort " + bluePrintPort);
	     String bluePrintTunnel=portMap.get(bluePrintPort);
	     logger.debug("bluePrintTunnel " + bluePrintTunnel);
	    
	     String urlDockerInfo="http://"+openStackIP+":"+bluePrintTunnel+"/putDockerInfo";  
		 String urlBluePrint="http://"+openStackIP+":"+bluePrintTunnel+"/putBlueprint";
		 logger.debug("urlDockerInfo " + urlDockerInfo);
		 Thread.sleep(2*sleepTimeInt);
		 logger.debug("urlBluePrint " + urlBluePrint);
		 String dataBrokerTunnelNum=commonUtil.getDataBrokerTunnelNumber(deploymentList,"DataBroker");
		 String urlDataBroker="http://"+openStackIP+":"+dataBrokerTunnelNum+"/configDB";
		 logger.debug("urlDataBroker "+urlDataBroker);
		 logger.debug(" dataBrokerTunnelNum "+dataBrokerTunnelNum);
		 
		 String csvDataBrokerTunnel="";
		  String csvDataBrokerUrl="";
		  if(dataBrokerBean!=null){
			  csvDataBrokerTunnel=commonUtil.getDataBrokerTunnelCSV(deploymentList,OpenStackConstants.DATABROKER_NAME);
		  }
		  if(csvDataBrokerTunnel!=null && !"".equalsIgnoreCase(csvDataBrokerTunnel)){
			  csvDataBrokerUrl="http://"+openStackIP+":"+csvDataBrokerTunnel+"/"+OpenStackConstants.CONFIG_DB_URL;
		  }
		  logger.debug("csvDataBrokerUrl "+csvDataBrokerUrl);
		  logger.debug("csvDataBrokerTunnel "+csvDataBrokerTunnel);
		  logger.debug("urlDataBroker "+urlDataBroker);
		  logger.debug("dataBrokerTunnelNum "+dataBrokerTunnelNum);
		  // Added for probe
		  
		  if(csvDataBrokerTunnel!=null && !"".equalsIgnoreCase(csvDataBrokerTunnel)){
			  logger.debug("Inside csv Data Broker ConfigDB  "); 
			  commonUtil.callCsvConfigDB(auth,csvDataBrokerUrl,dataBrokerBean);
			 }
		  if(dataBrokerTunnelNum!=null &&  !"".equals(dataBrokerTunnelNum)){
				 logger.debug("Inside putDataBrokerDetails ");
				 commonUtil.putDataBrokerDetails(auth,urlDataBroker);
			  }
		  if(bluePrint!=null){
				 commonUtil.putBluePrintDetailsJSON(bluePrintStr,urlBluePrint);
			 }
		 if(dockerList!=null){
			 DockerInfoList dockerInfoFinalList=dockerList;
			 commonUtil.putContainerDetailsJSON(dockerInfoFinalList,urlDockerInfo);
			}
		
		 
		 ArrayList<ProbeIndicator> probeIndicatorList = bluePrint.getProbeIndicator();
		 ProbeIndicator prbIndicator = null;
		 if(probeIndicatorList != null && probeIndicatorList.size() >0) {
				prbIndicator = probeIndicatorList.get(0);
		 }
		 
		 if (bluePrint.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True")) {
				 logger.debug("Probe indicator true. Starting generatenotircation auth.getUserId()) "+auth.getUserId());
				 logger.debug("probeIP "+probeIP+"probePort "+probePort);
				 commonUtil.generateNotification(probeIP+":"+probePort,auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpwd);
		 }
		 if(openStackContainerBeanList!=null){
	       	  
  			  logger.debug("Start saving data in database openStackContainerBeanList "+openStackContainerBeanList); 
  			commonUtil.createDeploymentCompositeData(dataSource,cmndatasvcuser,cmndatasvcpwd,openStackContainerBeanList,auth.getSolutionId(),
  					auth.getSolutionRevisionId(),auth.getUserId(),uidNumStr,"DP");
      		  
        }
	  }catch(Exception e){
		  logger.error("Exception in openstackCompositeSolution " +e);
		  try{
			  commonUtil.createDeploymentCompositeData(dataSource,cmndatasvcuser,cmndatasvcpwd,openStackContainerBeanList,auth.getSolutionId(),
	  					auth.getSolutionRevisionId(),auth.getUserId(),uidNumStr,"FA");
				
			}catch(Exception ex){
				logger.error("Exception in saving data openstackCompositeSolution" +ex);
			}
		  
	  }
	 logger.debug("CompositeSolution Run End");	
	}
	
	public void sshOpenStackCore(int vmNumber,String floatingIp,String hostName,String user,byte[] bytesArray,int hostPort)throws Exception{
		logger.debug("Start sshOpenStackCore");
		 SSHShell sshShell = null;
		 final String host=hostName; 
		 final String userName=user;
		try {
			 Thread th = new Thread() {

                 public synchronized void run() {
                         SSHShell sshShell=null;
                      try{
                               logger.debug("host "+host);
                               logger.debug("userName "+userName);
                               logger.debug("vmNumber "+vmNumber);
                               logger.debug("floatingIp "+floatingIp);
                               logger.debug("hostPort "+hostPort);
                               logger.debug("openStackIP "+openStackIP);
                               sshShell = SSHShell.open(host, 22, userName, bytesArray);

                               String regiterVM = "" + "ssh -L "+vmNumber+":"+floatingIp+":"+hostPort+" "+openStackIP+" -g -T -N & \n";
                               logger.debug("start regiterVM check point 2 regiterVM " + regiterVM);

                                      //sshShell = SSHShell.open(host, 2201, userName, bytesArray);
                                      sshShell.upload(new ByteArrayInputStream(regiterVM.getBytes()), "regiterVM_"+vmNumber+".sh",
                                                      ".openstackdocker", true, "4095");
                                      logger.debug("start regiterVM check point 3 ");

                                      String output = sshShell
                                                      .executeCommand("bash -c ~/.openstackdocker/regiterVM_"+vmNumber+".sh", true, true);

                 }catch(Exception e){
                	 logger.error("Exception in sshOpenStackCore in composite solution in new thread "+e);
                 }finally {
                      if (sshShell != null) {
                              sshShell.close();
                              sshShell = null;
                       }
                 }
               }

              };
               th.start();
               Thread.sleep(60000);
               th.stop();

              
		} catch (Exception ex) {
			logger.error("Exception in sshOpenStackCore in composite solution "+ex);
			throw ex;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		logger.debug("sshOpenStackCore End");
	}
	public  void installDockerOpenstack(int vmNum,String host,String userName,byte[] bytesArray,String repositoryDetails)throws Exception{
		logger.debug("installDockerOpenstack Start");
		SSHShell sshShell = null;
		try {
			 logger.debug("vmNum "+vmNum);
			 logger.debug("host "+host);
			 logger.debug("userName "+userName);
			 String repArray[]=repositoryDetails.split(",");
			 String daemon_file="";
			 String INSTALL_DOCKER_FOR_UBUNTU_SERVER_16_04_LTS = ""
						+ "echo Running: \"if [ ! -d ~/.azuredocker/tls ]; then mkdir -p ~/.azuredocker/tls ; fi\" \n"
						+ "if [ ! -d ~/.azuredocker/tls ]; then mkdir -p ~/.azuredocker/tls ; fi \n"
						+ "echo Running: sudo apt-get update \n" + "sudo apt-get update \n"
						+ "echo Running: sudo apt-get install -y --no-install-recommends apt-transport-https ca-certificates curl software-properties-common \n"
						+ "sudo apt-get install -y --no-install-recommends apt-transport-https ca-certificates curl software-properties-common \n"
						+ "echo Running: curl -fsSL https://apt.dockerproject.org/gpg | sudo apt-key add - \n"
						+ "curl -fsSL https://apt.dockerproject.org/gpg | sudo apt-key add - \n"
						+ "echo Running: sudo add-apt-repository \"deb https://apt.dockerproject.org/repo/ ubuntu-$(lsb_release -cs) main\" \n"
						+ "sudo add-apt-repository \"deb https://apt.dockerproject.org/repo/ ubuntu-xenial main\" \n"
						+ "echo Running: sudo apt-get update \n" + "sudo apt-get update \n"
						+ "echo Running: sudo apt-get -y install docker-engine \n" + "sudo apt-get -y install docker-engine \n"
						+ "echo Running: sudo groupadd docker \n" + "sudo groupadd docker \n"
						+ "echo Running: sudo usermod -aG docker $USER \n" + "sudo usermod -aG docker $USER \n"
						+ "sudo usermod -aG docker $USER \n"
						+ "sudo sudo chmod 777 /var/run/docker.sock \n"
						+ "echo Code for nexus repository \n"
						+ "sudo chmod 777 /etc/docker \n"
						+ "sudo cp -f ~/.azuredocker/daemon.json /etc/docker/daemon.json"
						+ "sudo chmod 777 /etc/docker \n"
						+"sudo service docker restart \n"
						+ "echo Daemon restart done \n"
						+ "sudo sudo chmod 777 /var/run/docker.sock \n";
			 
			 
			 String daemonFirstPart=""
					    +	"{ \n"
						+	 " \"insecure-registries\": [ \n";
			String daemonSecondpart="";
			for(int i=0;i<repArray.length;i++ ){
				if(daemonSecondpart!=null && !"".equalsIgnoreCase(daemonSecondpart)){
					daemonSecondpart=daemonSecondpart+","+"\""+repArray[i]+"\"";
				}else{
					daemonSecondpart=daemonSecondpart+"\""+repArray[i]+"\"";
				}
				
			 }
			String daemonThirdPart=	  "], \n"
			+	 " \"disable-legacy-registry\": true \n"
			+	"} \n";
			
			 daemon_file=daemonFirstPart+daemonSecondpart+daemonThirdPart;
			 
			 sshShell = SSHShell.open(host, vmNum, userName, bytesArray);
			 sshShell.upload(new ByteArrayInputStream(INSTALL_DOCKER_FOR_UBUNTU_SERVER_16_04_LTS.getBytes()),
						"INSTALL_DOCKER_FOR_UBUNTU_SERVER_16_04_LTS.sh", ".azuredocker", true, "4095");
			 logger.debug("Upload docker install script check point 1");
			
			 sshShell.upload(new ByteArrayInputStream(daemon_file.getBytes()),
						"daemon.json", ".azuredocker", true, "4095");
			 logger.debug("Upload docker install script check point 2");
			 logger.debug("Start installing docker");
			 String output = sshShell
						.executeCommand("bash -c ~/.azuredocker/INSTALL_DOCKER_FOR_UBUNTU_SERVER_16_04_LTS.sh", true, true);
			 
			 
			 logger.debug("SSH Cmplete output "+output);
		} catch (JSchException jSchException) {
			logger.error("JSchException in installDockerOpenstack "+jSchException);
			throw jSchException;
		} catch (IOException ioException) {
			logger.error("IOException in installDockerOpenstack "+ioException);
			throw ioException;
		} catch (Exception exception) {
			logger.error("Exception in installDockerOpenstack "+exception);
			throw exception;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		logger.debug("installDockerOpenstack End");
	}
	public  byte[] readBytesFromFile(String fileName)throws Exception {
		logger.debug("Start readBytesFromFile ");
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
        	
            File file = new File(fileName);
            bytesArray = new byte[(int) file.length()];

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
            

        } catch (IOException e) {
        	logger.error("Exception in readBytesFromFile CompositeSolution "+e);
        	throw e;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                	logger.error("Exception in readBytesFromFile Finally CompositeSolution "+e);
                	throw e;
                }
            }

        }
        logger.debug("bytesArray.length "+bytesArray.length);
        logger.debug("readBytesFromFile End");
        return bytesArray;

    }
	
	public  String deploymentImageVM(String dockerHostIP, String vmUserName,String registryServerUrl, String username, String password, 
			 String repositoryName,int vmNum,byte[] bytesArray,String finalContainerName,String portNumberString,int count,
			 int sleepTime,String probeNexusEndPoint)throws Exception {
		logger.debug(" dockerHostIP " + dockerHostIP);
		logger.debug("vmUserName " + vmUserName);
		logger.debug("registryServerUrl " + registryServerUrl);
		logger.debug("username " + username);
		logger.debug("password " + password);
		logger.debug("repositoryName " + repositoryName);
		logger.debug("finalContainerName " + finalContainerName);
		logger.debug("portNumberString " + portNumberString);
		logger.debug("vmNum " + vmNum);
		logger.debug("count " + count);
		logger.debug("probeNexusEndPoint " + probeNexusEndPoint);
		logger.debug("start deploymentImageVM CompositeSolution ");
		SSHShell sshShell = null;
		try {
			String PULL_IMAGE = "" + "docker login --username=" + username + " --password=" + password + " "
					+ registryServerUrl + " \n" + "docker pull " + repositoryName + " \n";
			logger.debug("start deploymentImageVM 2 PULL_IMAGE " + PULL_IMAGE);

			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			sshShell.upload(new ByteArrayInputStream(PULL_IMAGE.getBytes()), "PULL_IMAGE_"+count+".sh", ".azuredocker", true,
					"4095");
			logger.debug("start deploymentImageVM 3 File "+"PULL_IMAGE_"+count+".sh");

			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			String output2 = sshShell.executeCommand("bash -c ~/.azuredocker/PULL_IMAGE_"+count+".sh", true, true);
			logger.debug("start deploymentImageVM 4 output2 " + output2);
			Thread.sleep(30000);
			logger.debug(" start deploymentImageVM Container Name "+finalContainerName);
			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			String RUN_IMAGE="";
			if(finalContainerName!=null && finalContainerName.trim().equalsIgnoreCase("Probe")){
				RUN_IMAGE = "" + "docker run --name " + finalContainerName + " -itd -p 0.0.0.0:" + portNumberString
						+ "  -e NEXUSENDPOINTURL='"+probeNexusEndPoint+"' " + repositoryName + " \n";
			}else{
				RUN_IMAGE = "" + "docker run --name " + finalContainerName + " -d -p 0.0.0.0:" + portNumberString
						+ "  " + repositoryName + " \n";
			}
			logger.debug("output Start 4 RUN_IMAGE "+RUN_IMAGE);
			sshShell.upload(new ByteArrayInputStream(RUN_IMAGE.getBytes()), "RUN_DOCKER_IMAGE_"+count+".sh", ".azuredocker", true,
					"4095");
			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			String output3 = sshShell.executeCommand("bash -c ~/.azuredocker/RUN_DOCKER_IMAGE_"+count+".sh", true, true);
			logger.debug("output Start 5 output3 " + output3);
			Thread.sleep(30000);
		} catch (JSchException jSchException) {
			logger.error("JSchException in deploymentImageVM "+jSchException);
			throw jSchException;
		} catch (IOException ioException) {
			logger.error("JSchException in deploymentImageVM  "+ioException);
			throw ioException;
		} catch (Exception exception) {
			logger.error("JSchException in deploymentImageVM "+exception);
			throw exception;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		logger.debug("End deploymentImageVM CompositeSolution");
		return "sucess";
	}

}
