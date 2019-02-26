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

import org.acumos.openstack.client.logging.ONAPLogDetails;
import org.acumos.openstack.client.transport.ContainerInfo;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.transport.TransportBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.DockerInfo;
import org.acumos.openstack.client.util.DockerInfoList;
import org.acumos.openstack.client.util.LoggerUtil;
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
import org.slf4j.MDC;

import com.jcraft.jsch.JSchException;

public class OpenstackCompositeSolution implements Runnable {
	
Logger logger = LoggerFactory.getLogger(OpenstackCompositeSolution.class);
	
	
	private String flavourName;
	private String  securityGropName;
	private OpenstackCompositeDeployBean auth;
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
	private String bluePrintImage;
	private String bluePrintName;
	private String bluePrintUserName;
	private String bluePrintPd;
	private String dataSource;
	private String cmndatasvcuser;
	private String cmndatasvcpd;
	private String nexusUrl;
	private String nexusUserName;
	private String nexusPd;
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
	private String nexusRegistyPd;
	private String repositoryDetails;
	private TransportBean tbean;
	public OpenstackCompositeSolution(){
		
	}
	
	public OpenstackCompositeSolution(String flavourName,String  securityGropName,OpenstackCompositeDeployBean auth,String endpoint
			 ,String userName,String userPd,String scopeProject,String key,String keyName,String IdentifierName,String vmRegisterNumber,
			 String hostOpenStack,String hostUserName,String vmUserName,String dockerUserName,String dockerPd,String bluePrintImage,
			 String bluePrintName,String bluePrintUserName,String bluePrintPd,String dataSource,String cmndatasvcuser,String cmndatasvcpd,
			 String nexusUrl,String nexusUserName,String nexusPd,ArrayList<String> list,HashMap<String,String> imageMap,LinkedList<String> sequenceList,
			 Blueprint bluePrint,String uidNumStr,String solutionPort,String Sleeptime,String proxyIP,String proxyPort,
			 String openStackIP,String bluePrintPortNumber,String probePrintName,String probUser,String probePass,
			 HashMap<String,DeploymentBean> nodeTypeContainerMap,String probeNexusEndPoint,String probeInternalPort,String repositoryNames,
			 DataBrokerBean dataBrokerBean,String exposeDataBrokerPort,String internalDataBrokerPort,String bluePrintStr,String nexusRegistyName,
			 String nexusRegistyUserName,String nexusRegistyPd,String repositoryDetails,TransportBean tbean){
			//this.os = os;
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
			this.bluePrintImage = bluePrintImage;
			this.bluePrintName = bluePrintName;
			this.bluePrintUserName = bluePrintUserName;
			this.bluePrintPd = bluePrintPd;
			this.dataSource = dataSource;
			this.cmndatasvcuser = cmndatasvcuser;
			this.cmndatasvcpd = cmndatasvcpd;
			this.nexusUrl = nexusUrl;
			this.nexusUserName = nexusUserName;
			this.nexusPd = nexusPd;
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
			this.nexusRegistyPd = nexusRegistyPd;
			this.repositoryDetails=repositoryDetails;
			this.tbean=tbean;
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
		LoggerUtil loggerUtil=new LoggerUtil();
		try{
			ONAPLogDetails.setMDCDetails(tbean.getRequestId(), tbean.getUserDetail());
			loggerUtil.printCompositeImplDetails(flavourName,securityGropName,endpoint,userName,userPd,
					scopeProject,key,keyName,IdentifierName,vmRegisterNumber,vmUserName,
					dockerUserName,dockerPd,solutionPort,Sleeptime,proxyIP,proxyPort,
					openStackIP,bluePrintPortNumber,probePrintName,probUser,probePass,
					auth.getSolutionId(),auth.getSolutionRevisionId(),probeNexusEndPoint,probeInternalPort,nexusRegistyName,
					exposeDataBrokerPort,internalDataBrokerPort,nexusRegistyUserName,nexusRegistyPd,bluePrintStr);
			
			logger.debug("nodeTypeContainerMap "+nodeTypeContainerMap);
			 int proxyPortInt=Integer.parseInt(proxyPort);
			 int bluePrintPorInt=Integer.parseInt(bluePrintPortNumber);
			 int listSize=list.size();
			 String portArr[]={"8557","8558","8559","8560","8561","8562","8563","8564","8565"};
			 os = OSFactory.builderV3().endpoint(endpoint)
					.credentials(userName, userPd, Identifier.byName(IdentifierName))
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
	 
	 
	 byte[] bytesArray=commonUtil.readBytesFromFile(keyName);
	 commonUtil.getProtoDetails(tbean);
	 logger.debug("Protomap "+tbean.getProtoMap());
	 commonUtil.sshOpenStackCore(vmBind,floatingIp,hostOpenStack,hostUserName,bytesArray,22,openStackIP);
	 for(int i=0;i<listSize;i++){
		 String portTunnel=portArr[i];
		 int portTunnelInt=Integer.parseInt(portTunnel);
		 vmBindCount=vmBindCount+1;
		 portMap.put(portTunnel, String.valueOf(vmBindCount));
		 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
		 commonUtil.sshOpenStackCore(vmBindCount,floatingIp,hostOpenStack,hostUserName,bytesArray,portTunnelInt,openStackIP);
		 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
	 }
	 vmBindCount=vmBindCount+1;
	 logger.debug("vmBindCount "+vmBindCount);
	 commonUtil.sshOpenStackCore(vmBindCount,floatingIp,hostOpenStack,hostUserName,bytesArray,bluePrintPorInt,openStackIP);
	 portMap.put(bluePrintPortNumber, String.valueOf(vmBindCount));
	 logger.debug("portMap "+portMap);
	 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBindCount));
	 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
	 
	 //bluePrintPort.sshOpenStackCore(vmBind,floatingIp,hostOpenStack,hostUserName,bytesArray);
	 commonUtil.installDockerOpenstack(vmBind,hostOpenStack,vmUserName,bytesArray,repositoryDetails);
	 //Map Nginx folder
	 commonUtil.protoFileVM(hostOpenStack,vmUserName,bytesArray,tbean,vmBind);
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
	 		            		commonUtil.deploymentImageVM(hostOpenStack,vmUserName,repositaryName,bluePrintUserName,bluePrintPd,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,11,sleepTimeInt,probeNexusEndPoint,tbean);
	 		            	}else{
	 		            		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
	 		            			portNumber=probeInternalPort;
	 		            			portNumberString=probeInternalPort+":"+probeInternalPort;
	 		            		}else if(nodeTypeContainer!=null && !"".equals(nodeTypeContainer) && nodeTypeContainer.equalsIgnoreCase(OpenStackConstants.DATABROKER_NAME)
	    		        					&& nodeTypeName!=null && !"".equals(nodeTypeName) && nodeTypeName.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_CSV_FILE)){
	    		        				portNumberString=exposeDataBrokerPort+":"+internalDataBrokerPort;
	    		        				portNumber=exposeDataBrokerPort;
	 		            		}else if(finalContainerName.equalsIgnoreCase(OpenStackConstants.NGINX_CONTAINER)){
    		        				portNumber=portArr[count];
    		        				portNumberString=portNumber+":"+tbean.getNginxInternalPort();
    		        				tbean.setNginxPort(portNumber);
    		        				count++;
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
	 		            			commonUtil.deploymentImageVM(hostOpenStack,vmUserName,repositaryName,probUser,probePass,repositryImageName,vmBind,bytesArray,
		 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint,tbean);
	 		            		}else if(nexusRepo){
	 		            			logger.debug("Deploying Container else if finalContainerName "+finalContainerName);
	 		            			//dockerinfo.setNodeType("Probe");
	 		            			commonUtil.deploymentImageVM(hostOpenStack,vmUserName,repositaryName,nexusRegistyUserName,nexusRegistyPd,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint,tbean);
	 		            		}else{
	 		            			logger.debug(" Deploying Container finalContainerName "+finalContainerName);
	 		            			//dockerinfo.setNodeType("Probe");
	 		            			commonUtil.deploymentImageVM(hostOpenStack,vmUserName,repositaryName,dockerUserName,dockerPd,repositryImageName,vmBind,bytesArray,
	 		            				finalContainerName,portNumberString,count,sleepTimeInt,probeNexusEndPoint,tbean);
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
			  commonUtil.callCsvConfigDB(auth.getUsername(),auth.getUserPd(),auth.getHost(),auth.getPort(),csvDataBrokerUrl,dataBrokerBean);
			 }
		  if(dataBrokerTunnelNum!=null &&  !"".equals(dataBrokerTunnelNum)){
				 logger.debug("Inside putDataBrokerDetails ");
				 commonUtil.putDataBrokerDetails(auth.getUrlAttribute(),auth.getJsonMapping(),auth.getJsonPosition(),urlDataBroker);
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
		 if(floatingIp!=null && !"".equals(floatingIp)) {
			 logger.debug("Openstack Notification start floatingIp "+floatingIp);
				commonUtil.generateNotification("RackSpace VM is created for composite solution , IP is: "+floatingIp,auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpd);
		 }
		 
		 if (bluePrint.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True")) {
				 logger.debug("Probe indicator true. Starting generatenotircation auth.getUserId()) "+auth.getUserId());
				 logger.debug("probeIP "+probeIP+"probePort "+probePort);
				 commonUtil.generateNotification(probeIP+":"+probePort,auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpd);
		 }
		 if(openStackContainerBeanList!=null){
  			logger.debug("Start saving data in database openStackContainerBeanList "+openStackContainerBeanList); 
  			commonUtil.createDeploymentCompositeData(dataSource,cmndatasvcuser,cmndatasvcpd,openStackContainerBeanList,auth.getSolutionId(),
  					auth.getSolutionRevisionId(),auth.getUserId(),uidNumStr,"DP");
      		  
        }
	  }catch(Exception e){
		  MDC.put("ClassName", "OpenstackSimpleSolution");
		  logger.error("Exception in openstackCompositeSolution " +e);
		  MDC.remove("ClassName");
		  try{
			  commonUtil.generateNotification("Error in vm for composite solution in rackspace ",auth.getUserId(),dataSource,cmndatasvcuser,cmndatasvcpd);
			  commonUtil.createDeploymentCompositeData(dataSource,cmndatasvcuser,cmndatasvcpd,openStackContainerBeanList,auth.getSolutionId(),
	  					auth.getSolutionRevisionId(),auth.getUserId(),uidNumStr,"FA");
				
			}catch(Exception ex){
				logger.error("Exception in saving data openstackCompositeSolution" +ex);
			}
		  
	  }
	 ONAPLogDetails.clearMDCDetails();
	 logger.debug("CompositeSolution Run End");	
	}
	
}
