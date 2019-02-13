package org.acumos.openstack.client.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.acumos.openstack.client.transport.ContainerInfo;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.TransportBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.DockerInfo;
import org.acumos.openstack.client.util.DockerInfoList;
import org.acumos.openstack.client.util.OpenStackConstants;
import org.acumos.openstack.client.util.ParseJSON;
import org.acumos.openstack.client.util.ProbeIndicator;
import org.acumos.openstack.client.util.SingletonMapClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExistingVMSolution implements Runnable {
	public TransportBean tbean;
	
	public ExistingVMSolution(TransportBean tbean)
	{
		this.tbean=tbean;
	}
	Logger logger = LoggerFactory.getLogger(ExistingVMSolution.class);
	public void run() {
		CommonUtil commonUtil=new CommonUtil();
		int sshBindNumber=0;
		try {
			String solutionToolKitType=commonUtil.getSolutionCode(tbean.getSolutionId(), 
					tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
			logger.debug("solutionToolKitType "+solutionToolKitType);
			byte[] bytesArray=commonUtil.readBytesFromFile(tbean.getKeyName());
			String sshBindStr=commonUtil.getsshComandoutput(tbean.getVmHostIP(),tbean.getHostUserName(), bytesArray,
					tbean.getOpenStackIP());
			logger.debug("sshBindStr "+sshBindStr);
			if(sshBindStr!=null && !"".equals(sshBindStr)) {
				sshBindNumber=Integer.parseInt(sshBindStr);
				logger.debug("sshBindNumber "+sshBindNumber);
				String checkSoftware=commonUtil.checkPrerequisites(tbean.getOpenStackIP(), sshBindNumber, tbean.getVmUserName(), bytesArray);
				logger.debug("checkSoftware "+checkSoftware);
				if(checkSoftware!=null && "success".equalsIgnoreCase(checkSoftware)) {
					String removeContainer=commonUtil.removeAllContainer(tbean.getVmHostIP(), sshBindNumber, tbean.getVmUserName(), bytesArray);
					if(removeContainer!=null && "success".equalsIgnoreCase(removeContainer) ) {
						if(solutionToolKitType!=null && !"".equals(solutionToolKitType) && "CP".equalsIgnoreCase(solutionToolKitType)){
					   		logger.debug("Composite Solution Details Start");
					   		compositeSolutionDetails(tbean,commonUtil,sshBindNumber,bytesArray);
					   		logger.debug("Composite Solution Details End");
					   	 }else{
					   		logger.debug("Single Solution Details Start");
					   		singleSolutionDetails(tbean,commonUtil,sshBindNumber,bytesArray);
					   		logger.debug("Single Solution Details End");
					   	 }
					}
				}
			}
		}catch(Exception e) {
			logger.error("Exception in ExistingVMSolution RUN " +e);
			
		}
	}
	public void singleSolutionDetails(TransportBean tbean,CommonUtil commonUtil,int sshBindNumber,byte[] bytesArray)throws Exception{
		logger.debug("singleSolutionDetails Start");
		String repositaryName="";
		String repositryImageName="";
		OpanStackContainerBean containerBean=new OpanStackContainerBean();
		try {
			
			String ImageTag=commonUtil.getSingleImageData(tbean.getSolutionId(), tbean.getSolutionRevisionId(), 
					tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
			logger.debug("ImageTag "+ImageTag);
			repositaryName=commonUtil.getRepositryName(ImageTag,tbean.getRepositoryNames());
			repositryImageName=commonUtil.getRepositryImageName(ImageTag,tbean.getRepositoryNames());
			commonUtil.deploymentSingleImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,
					tbean.getDockerUserName(),tbean.getDockerPd(),repositryImageName,sshBindNumber,bytesArray);
			containerBean.setContainerIp(tbean.getVmHostIP());
			containerBean.setContainerPort("8557");
			commonUtil.createDeploymentData(tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd(), containerBean,
					tbean.getSolutionId(), tbean.getSolutionRevisionId(),tbean.getUserId(), tbean.getUidNumStr(), "DP");
		}catch(Exception e) {
			logger.error("Exception in singleSolutionDetails in exing vm deployment RUN " +e);
			try{
				  commonUtil.generateNotification("Error in existing vm Deployment for single solution in rackspace ",tbean.getUserId(),
						  tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
				  commonUtil.createDeploymentData(tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd(), containerBean,
							tbean.getSolutionId(), tbean.getSolutionRevisionId(),tbean.getUserId(), tbean.getUidNumStr(), "FA");  
			  }catch(Exception ex){
				  logger.error("Exception in saving data openstackSimpleSolution " +ex);
				}
			throw e;
		}
		logger.debug("singleSolutionDetails End");
	}
    public void compositeSolutionDetails(TransportBean tbean,CommonUtil commonUtil,int sshBindNumber,byte[] bytesArray)throws Exception{
    	logger.debug("compositeSolutionDetails Start");
    	int portNum=8557;
    	int port=8557;
    	int vmBindCount=0;
		int vmBind=0;
		String probeIP="";
		String probePort="";
		String bluePrintPort="";
		String vmBindNumber="";
		String repositaryName="";
		String repositryImageName="";
    	Blueprint bluePrintProbe=null;
		HashMap<String,String> imageMap=null;
		ArrayList<String> list=null;
		LinkedList<String> sequenceList=null;
		DataBrokerBean dataBrokerBean=null;
		HashMap<String,DeploymentBean> nodeTypeContainerMap=null;
		HashMap<String,String> portMap=new HashMap<String,String>();
		String containerInstanceBluePrint="BluePrintContainer";
		String containerInstanceProbe="Probe";
		List<ContainerInfo> probeContainerBeanList=new ArrayList<ContainerInfo>();
		List<DeploymentBean> deploymentList=new ArrayList<DeploymentBean>();
		List<OpanStackContainerBean> openStackContainerBeanList=new ArrayList<OpanStackContainerBean>();
    	try {
    		ParseJSON parseJson=new ParseJSON();	
    	    String bluePrintStr=commonUtil.getBluePrintNexus(tbean.getSolutionId(), tbean.getSolutionRevisionId(),tbean.getDataSource(),tbean.getCmndatasvcuser(),
    			tbean.getCmndatasvcpd(),tbean.getNexusUrl(),tbean.getNexusUserName(),tbean.getNexusPd());
		    logger.debug("bluePrintStr "+bluePrintStr);
		    boolean probeIndicator=parseJson.checkProbeIndicator(OpenStackConstants.JSON_FILE_NAME);
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
			ArrayList<ProbeIndicator> probeIndicatorList = bluePrintProbe.getProbeIndicator();
			ProbeIndicator prbIndicator = null;
			if(probeIndicatorList != null && probeIndicatorList.size() >0) {
				prbIndicator = probeIndicatorList.get(0);
			}			
		    if (bluePrintProbe.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True") ) {
		    	list.add(tbean.getNginxImageName());
				imageMap.put(tbean.getNginxImageName(), OpenStackConstants.NGINX_CONTAINER);
				sequenceList=commonUtil.addContainerSequence(sequenceList,OpenStackConstants.NGINX_CONTAINER);
				if (tbean.getProbePrintImage() != null && !"".equals(tbean.getProbePrintImage())) {
					list.add(tbean.getProbePrintImage());
					imageMap.put(tbean.getProbePrintImage(), OpenStackConstants.PROBE_CONTAINER_NAME);
					sequenceList=commonUtil.addContainerSequence(sequenceList,OpenStackConstants.PROBE_CONTAINER_NAME);
				}
		    }
		 if(tbean.getBluePrintImage()!=null && !"".equals(tbean.getBluePrintImage())){
			list.add(tbean.getBluePrintImage());
			imageMap.put(tbean.getBluePrintImage(), OpenStackConstants.BLUEPRINT_CONTAINER_NAME);
		 }
		 
		 HashMap<String,String> singlatonMap=SingletonMapClass.getInstance(); 
		 if(singlatonMap!=null && singlatonMap.get("vmBindNum")!=null){
			 vmBindNumber=singlatonMap.get("vmBindNum"); 
			 logger.debug("vmBindNumber 1 "+vmBindNumber);
		 }else{
			 vmBindNumber=tbean.getVmRegisterNumber(); 
			 logger.debug("vmBindNumber 2 "+vmBindNumber);
		 }
		 if(vmBindNumber!=null){
			 vmBind=Integer.parseInt(vmBindNumber); 
			 vmBind=vmBind+1;
			 vmBindCount=vmBind;
			 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBind));
		 }
		 logger.debug("vmRegisterNumber "+tbean.getVmRegisterNumber()+" vmBind "+vmBind);
		 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
		 for(int i=0;i<list.size();i++){
			 String portTunnel=String.valueOf(portNum);
			 int portTunnelInt=Integer.parseInt(portTunnel);
			 vmBindCount=vmBindCount+1;
			 portMap.put(portTunnel, String.valueOf(vmBindCount));
			 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
			 commonUtil.sshOpenStackCore(vmBindCount,tbean.getVmHostIP(),tbean.getHostOpenStack(),tbean.getHostUserName(),
					 bytesArray,portTunnelInt,tbean.getOpenStackIP());
			 logger.debug("portTunnel "+portTunnel+"vmBindCount "+vmBindCount);
			 portNum++;
		 }
		 vmBindCount=vmBindCount+1;
		 logger.debug("vmBindCount "+vmBindCount);
		 commonUtil.sshOpenStackCore(vmBindCount,tbean.getVmHostIP(),tbean.getHostOpenStack(),tbean.getHostUserName(),
				 bytesArray,Integer.parseInt(tbean.getBluePrintPortNumber()),tbean.getOpenStackIP());
		 portMap.put(tbean.getBluePrintPortNumber(), String.valueOf(vmBindCount));
		 logger.debug("portMap "+portMap);
		 SingletonMapClass.getInstance().put("vmBindNum", String.valueOf(vmBindCount));
		 logger.debug("SingletonMapClass.getInstance() "+SingletonMapClass.getInstance());
		 
		//Map Nginx folder
		 commonUtil.protoFileVM(tbean.getHostOpenStack(),tbean.getVmUserName(),bytesArray,tbean,vmBind);
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
		 		            	repositaryName=commonUtil.getRepositryName(imageNameVal,tbean.getRepositoryNames());
			            		repositryImageName=commonUtil.getRepositryImageName(imageNameVal,tbean.getRepositoryNames());
			            		logger.debug("repositaryName "+repositaryName+" repositryImageName "+repositryImageName);
			            		boolean nexusRepo=commonUtil.getRepositryStatus(imageNameVal,tbean.getNexusRegistyName());
			            		logger.debug("nexusRepo "+nexusRepo);
		 		            	if(containerInstanceBluePrint!=null && containerInstanceBluePrint.equalsIgnoreCase(finalContainerName)){
		 		            		portNumber=tbean.getBluePrintPortNumber();
		 		            		bluePrintPort=portNumber;
		 		            		portNumberString=portNumber+":"+portNumber;
		 		            		logger.debug("imageNameVal "+imageNameVal);
		 		            		logger.debug("portNumberString "+portNumberString);
		 		            		commonUtil.deploymentImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,tbean.getBluePrintUserName(),
		 		            				tbean.getBluePrintPd(),repositryImageName,vmBind,bytesArray,finalContainerName,portNumberString,11,
		 		            				Integer.parseInt(tbean.getSleeptime()),tbean.getProbeNexusEndPoint(),tbean);
		 		            	}else{
		 		            		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
		 		            			portNumber=tbean.getProbeInternalPort();
		 		            			portNumberString=tbean.getProbeInternalPort()+":"+tbean.getProbeInternalPort();
		 		            		}else if(nodeTypeContainer!=null && !"".equals(nodeTypeContainer) && nodeTypeContainer.equalsIgnoreCase(OpenStackConstants.DATABROKER_NAME)
		    		        					&& nodeTypeName!=null && !"".equals(nodeTypeName) && nodeTypeName.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_CSV_FILE)){
		    		        				portNumberString=tbean.getExposeDataBrokerPort()+":"+tbean.getInternalDataBrokerPort();
		    		        				portNumber=tbean.getExposeDataBrokerPort();
		 		            		}else if(finalContainerName.equalsIgnoreCase(OpenStackConstants.NGINX_CONTAINER)){
	    		        				portNumber=String.valueOf(port);
	    		        				portNumberString=portNumber+":"+tbean.getNginxInternalPort();
	    		        				tbean.setNginxPort(portNumber);
	    		        				port++;
	    		        				count++;
	    		        			}else{
		 		            			portNumber=String.valueOf(port);
		 		            			if(tbean.getSolutionPort()!=null && !"".equals(tbean.getSolutionPort())){
					        				portNumberString=portNumber+":"+tbean.getSolutionPort();
					        			}else{
					        				portNumberString=portNumber+":"+portNumber;
					        			}
			 		            		count++;
			 		            		port++;
			 		            		logger.debug("portNumberString "+portNumberString);
		 		            		}
		 		            		
		 		            		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
		 		            			logger.debug("Deploying Probe container finalContainerName "+finalContainerName);
		 		            			commonUtil.deploymentImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,tbean.getProbUser(),tbean.getProbePass(),repositryImageName,vmBind,bytesArray,
			 		            				finalContainerName,portNumberString,count,Integer.parseInt(tbean.getSleeptime()),tbean.getProbeNexusEndPoint(),tbean);
		 		            		}else if(nexusRepo){
		 		            			logger.debug("Deploying Container else if finalContainerName "+finalContainerName);
		 		            			//dockerinfo.setNodeType("Probe");
		 		            			commonUtil.deploymentImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,tbean.getNexusRegistyUserName(),
		 		            					tbean.getNexusRegistyPd(),repositryImageName,vmBind,bytesArray,finalContainerName,portNumberString,
		 		            					count,Integer.parseInt(tbean.getSleeptime()),tbean.getProbeNexusEndPoint(),tbean);
		 		            		}else{
		 		            			logger.debug(" Deploying Container finalContainerName "+finalContainerName);
		 		            			//dockerinfo.setNodeType("Probe");
		 		            			commonUtil.deploymentImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,tbean.getDockerUserName(),
		 		            					tbean.getDockerPd(),repositryImageName,vmBind,bytesArray,finalContainerName,portNumberString,
		 		            					count,Integer.parseInt(tbean.getSleeptime()),tbean.getProbeNexusEndPoint(),tbean);
		 		            		}
		 		            	}
		 		            	dockerinfo.setIpAddress(tbean.getVmHostIP());
		    		            dockerinfo.setPort(portNumber);
		    		            dockerinfo.setContainer(finalContainerName);
		    		            dockerInfoList.add(dockerinfo);
		    		            
		    		            containerBean.setContainerName(finalContainerName);
	    		        		containerBean.setContainerIp(tbean.getVmHostIP());
	    		        		containerBean.setContainerPort(portNumber);
	    		        		openStackContainerBeanList.add(containerBean);
	    		        		
	    		        		DeploymentBean deploymentBean=new DeploymentBean();
	    		        		deploymentBean.setVmIP(tbean.getVmHostIP());
	    		        		deploymentBean.setVmName("");
	    		        		deploymentBean.setContainerName(finalContainerName);
	    		        		deploymentBean.setContainerPort(portNumber);
	    		        		if(portMap!=null && portMap.get(portNumber)!=null){
	    		        			logger.debug("portNumber :" + portNumber+" TunnelNum "+portMap.get(portNumber));
	    		        			deploymentBean.setTunnelNumber(portMap.get(portNumber));
	    		        		}
	    		        		ContainerInfo containerInfo = new ContainerInfo();
	    		        		containerInfo.setContainerName(finalContainerName);
	    		        		containerInfo.setContainerIp(tbean.getVmHostIP());
	    		        		containerInfo.setContainerPort(portNumber);
	    		        		
	    		        		//containerInfo.setNodeType(nodeTypeContainer);
	    		        		logger.debug("Before-Probe-containerInstanceProbe "+containerInstanceProbe+" finalContainerName "+finalContainerName);
	    		        		if(containerInstanceProbe!=null && containerInstanceProbe.equalsIgnoreCase(finalContainerName)){
	    		        			logger.debug("After Probe containerInstanceprobe "+containerInstanceProbe+" finalContainerName "+finalContainerName);
		    		        	   containerInfo.setNodeType("Probe");
		    		        	   probeIP = tbean.getVmHostIP();
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
		    
		     String urlDockerInfo="http://"+tbean.getOpenStackIP()+":"+bluePrintTunnel+"/putDockerInfo";  
			 String urlBluePrint="http://"+tbean.getOpenStackIP()+":"+bluePrintTunnel+"/putBlueprint";
			 logger.debug("urlDockerInfo " + urlDockerInfo);
			 Thread.sleep(2*Integer.parseInt(tbean.getSleeptime()));
			 logger.debug("urlBluePrint " + urlBluePrint);
			 String dataBrokerTunnelNum=commonUtil.getDataBrokerTunnelNumber(deploymentList,"DataBroker");
			 String urlDataBroker="http://"+tbean.getOpenStackIP()+":"+dataBrokerTunnelNum+"/configDB";
			 logger.debug("urlDataBroker "+urlDataBroker);
			 logger.debug(" dataBrokerTunnelNum "+dataBrokerTunnelNum);
			 
			 String csvDataBrokerTunnel="";
			  String csvDataBrokerUrl="";
			  if(dataBrokerBean!=null){
				  csvDataBrokerTunnel=commonUtil.getDataBrokerTunnelCSV(deploymentList,OpenStackConstants.DATABROKER_NAME);
			  }
			  if(csvDataBrokerTunnel!=null && !"".equalsIgnoreCase(csvDataBrokerTunnel)){
				  csvDataBrokerUrl="http://"+tbean.getOpenStackIP()+":"+csvDataBrokerTunnel+"/"+OpenStackConstants.CONFIG_DB_URL;
			  }
			  logger.debug("csvDataBrokerUrl "+csvDataBrokerUrl);
			  logger.debug("csvDataBrokerTunnel "+csvDataBrokerTunnel);
			  logger.debug("urlDataBroker "+urlDataBroker);
			  logger.debug("dataBrokerTunnelNum "+dataBrokerTunnelNum);
			  // Added for probe
			  
			  if(csvDataBrokerTunnel!=null && !"".equalsIgnoreCase(csvDataBrokerTunnel)){
				  logger.debug("Inside csv Data Broker ConfigDB  "); 
				  commonUtil.callCsvConfigDB(tbean.getDataBrokerUserName(),tbean.getDataBrokerUserPd(),tbean.getDataBrokerHost(),
						  tbean.getDataBrokerPort(),csvDataBrokerUrl,dataBrokerBean);
				 }
			  if(dataBrokerTunnelNum!=null &&  !"".equals(dataBrokerTunnelNum)){
					 logger.debug("Inside putDataBrokerDetails ");
					 commonUtil.putDataBrokerDetails(tbean.getUrlAttribute(),tbean.getJsonMapping(),tbean.getJsonPosition(),urlDataBroker);
				  }
			  if(bluePrintProbe!=null){
					 commonUtil.putBluePrintDetailsJSON(bluePrintStr,urlBluePrint);
				 }
			 if(dockerList!=null){
				 DockerInfoList dockerInfoFinalList=dockerList;
				 commonUtil.putContainerDetailsJSON(dockerInfoFinalList,urlDockerInfo);
				}
			
			 
			 probeIndicatorList = bluePrintProbe.getProbeIndicator();
			 prbIndicator = null;
			 if(probeIndicatorList != null && probeIndicatorList.size() >0) {
					prbIndicator = probeIndicatorList.get(0);
			 }
			 if(tbean.getVmHostIP()!=null && !"".equals(tbean.getVmHostIP())) {
				 logger.debug("Openstack Notification start floatingIp "+tbean.getVmHostIP());
					commonUtil.generateNotification("RackSpace VM is created for composite solution , IP is: "+tbean.getVmHostIP(),
							tbean.getUserId(),tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
			 }
			 
			 if (bluePrintProbe.getProbeIndicator() != null && prbIndicator != null && prbIndicator.getValue().equalsIgnoreCase("True")) {
					 logger.debug("Probe indicator true. Starting generatenotircation auth.getUserId()) "+tbean.getUserId());
					 logger.debug("probeIP "+probeIP+"probePort "+probePort);
					 commonUtil.generateNotification(probeIP+":"+probePort,tbean.getUserId(),tbean.getDataSource(),
							 tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
			 }
			 if(openStackContainerBeanList!=null){
	  			logger.debug("Start saving data in database openStackContainerBeanList "+openStackContainerBeanList); 
	  			commonUtil.createDeploymentCompositeData(tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd(),
	  					openStackContainerBeanList,tbean.getSolutionId(),tbean.getSolutionRevisionId(),tbean.getUserId(),tbean.getUidNumStr(),"DP");
	      		  
	        }
    	}catch(Exception e) {
    		
    	}
    	logger.debug("compositeSolutionDetails End");
	}

}
