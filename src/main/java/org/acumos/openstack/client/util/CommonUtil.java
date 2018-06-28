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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.acumos.cds.MessageSeverityCode;
import org.acumos.cds.client.CommonDataServiceRestClientImpl;
import org.acumos.cds.domain.MLPArtifact;
import org.acumos.cds.domain.MLPNotification;
import org.acumos.cds.domain.MLPSolutionRevision;
import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.nexus.client.RepositoryLocation;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.MLNotification;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acumos.cds.domain.MLPSolutionDeployment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CommonUtil {
	
Logger logger = LoggerFactory.getLogger(CommonUtil.class);

  public String getRepositryName(String imageName,String repositoryNames){
	logger.debug("Start-getRepositryName "+imageName);
	String repositaryName="";
	if(imageName!=null){
		String imageArr[]=imageName.split("/");
		if(repositoryNames!=null){
			String repositoryNamesArr[]=repositoryNames.split(",");
			if(repositoryNamesArr!=null && repositoryNamesArr.length >0){
				for(String repository: repositoryNamesArr){
					if(repository!=null){
						String repositoryArr[]=repository.split("#");
						if(repositoryArr!=null && repositoryArr.length > 1){
							if(imageArr[0].equalsIgnoreCase(repositoryArr[0])){
								repositaryName=	repositoryArr[1];
								break;
							}
							
						}else{
							logger.debug(" Array size is not 2  "+repositoryNamesArr.length);
						}
					}
				}
			}
		}
	}
	
	logger.debug(" End repositaryName "+repositaryName);
	return repositaryName;
  }
  
  public String getRepositryImageName(String imageName,String repositoryNames){
		logger.debug(" Start-getRepositryName "+imageName);
		String repositaryName="";
		String repositaryImageName="";
		String ImageName="";
		if(imageName!=null){
			String imageArr[]=imageName.split("/");
			//String imageArr[]=imageName.split("/");
			if(repositoryNames!=null){
				String repositoryNamesArr[]=repositoryNames.split(",");
				if(repositoryNamesArr!=null && repositoryNamesArr.length >0){
					for(String repository: repositoryNamesArr){
						if(repository!=null){
							String repositoryArr[]=repository.split("#");
							if(repositoryArr!=null && repositoryArr.length > 1){
								if(imageArr[0].equalsIgnoreCase(repositoryArr[0])){
									repositaryName=	repositoryArr[1];
									break;
								}
								
							}else{
								logger.debug("Array size is not 2  "+repositoryNamesArr.length);
							}
						}
					}
				}
			}
			
			if(imageArr[1]!=null){
				ImageName=imageArr[1];
			}
			logger.debug("repositaryName "+repositaryName+" ImageName "+ImageName);	
			
		}
		if(repositaryName!=null && ImageName!=null){
			repositaryImageName=repositaryName+"/"+ImageName;
		}
		logger.debug(" End getRepositryImageName repositaryImageName "+repositaryImageName);
		
		return repositaryImageName;
	  }
    
   public ArrayList<String> iterateImageMap(HashMap<String,String> imageMap){
		logger.debug("Start iterateImageMap imageMap "+imageMap);
		ArrayList<String> list=new ArrayList<String>();
		 Iterator it = imageMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        logger.debug(pair.getKey() + " = " + pair.getValue());
		        if(pair.getKey()!=null){
		        	list.add((String)pair.getKey());
		        }
		    }
		logger.debug(" End iterateImageMap list "+list);
		return list;
	}
   
   public CommonDataServiceRestClientImpl getClient(String datasource,String userName,String password) {
		logger.debug("start getClient");
		CommonDataServiceRestClientImpl client = new CommonDataServiceRestClientImpl(datasource, userName, password);
		logger.debug("End getClient client "+client);
		return client;
	}
	public NexusArtifactClient nexusArtifactClient(String nexusUrl, String nexusUserName,String nexusPassword) {
		logger.debug("start nexusArtifactClient ");
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("1");
		repositoryLocation.setUrl(nexusUrl);
		repositoryLocation.setUsername(nexusUserName);
		repositoryLocation.setPassword(nexusPassword);
		NexusArtifactClient nexusArtifactClient = new NexusArtifactClient(repositoryLocation);
		logger.debug("End nexusArtifactClient ");
		return nexusArtifactClient;
	}
	
	public String getBluePrintNexus(String solutionId, String revisionId,String datasource,String userName,String password,
			String nexusUrl,String nexusUserName,String nexusPassword) throws  Exception{
		  logger.debug("Start getBluePrintNexus ");
		  logger.debug("solutionId "+solutionId);
		  logger.debug("revisionId "+revisionId);
		  logger.debug("datasource "+datasource);
		  logger.debug("userName "+userName);
		  logger.debug("password "+password);
		  logger.debug("nexusUrl "+nexusUrl);
		  logger.debug("nexusUserName "+nexusUserName);
		  logger.debug("nexusPassword "+nexusPassword);
		  List<MLPSolutionRevision> mlpSolutionRevisionList;
		  String solutionRevisionId = revisionId;
		  List<MLPArtifact> mlpArtifactList;
		  String nexusURI = "";
		  String artifactType="BP";
		  String bluePrintStr="";
		  ByteArrayOutputStream byteArrayOutputStream = null;
		  CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,password);
          		 
			if (null != solutionRevisionId) {
				mlpArtifactList = cmnDataService.getSolutionRevisionArtifacts(solutionId, solutionRevisionId);
				if (null != mlpArtifactList && !mlpArtifactList.isEmpty()) {
					nexusURI = mlpArtifactList.stream()
							.filter(mlpArt -> mlpArt.getArtifactTypeCode().equalsIgnoreCase(artifactType)).findFirst()
							.get().getUri();
					logger.debug("Nexus URI " + nexusURI );
					if (null != nexusURI) {
						NexusArtifactClient nexusArtifactClient=nexusArtifactClient(nexusUrl,nexusUserName,nexusPassword);
						File f = new File("blueprint.json");
						if(f.exists() && !f.isDirectory()) { 
						    f.delete();
						}
						byteArrayOutputStream = nexusArtifactClient.getArtifact(nexusURI);
						logger.debug("byteArrayOutputStream blueprint.json "+byteArrayOutputStream.toString());
						OutputStream outputStream = new FileOutputStream("blueprint.json"); 
						byteArrayOutputStream.writeTo(outputStream);
						bluePrintStr=byteArrayOutputStream.toString();
					}
				}
			}	
			File file = new File("blueprint.json");
			if(!file.exists()){
				 throw  new Exception("blueprint.json file is not exist");
			}
			logger.debug("End getBluePrintNexus");	
		return bluePrintStr;	
	  }
	  public List<MLPSolutionRevision> getSolutionRevisionsList(String solutionId,String datasource,String userName,String password)throws  Exception{
			logger.debug("getSolutionRevisions Start ");
			List<MLPSolutionRevision> solRevisionsList = null;
			CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,password);
			solRevisionsList = cmnDataService.getSolutionRevisions(solutionId);
			logger.debug(" getSolutionRevisions End ");
			return solRevisionsList;
		}
	  public LinkedList<String> getSequence(HashMap<String,String> hmap){
		    logger.debug(" getSequence Start ");
			LinkedList<String> sequenceList=new LinkedList<String>();
			Iterator itrContainer=hmap.entrySet().iterator();
	        while(itrContainer.hasNext()){
	        	
	        	Map.Entry pair = (Map.Entry)itrContainer.next();
	        	String containerName=(String)pair.getKey();
	        	sequenceList.add((String)pair.getValue());
	        }
	        logger.debug(" getSequence End "+sequenceList);
	        return sequenceList;
		}
	  
	  public void putContainerDetailsJSON(DockerInfoList  dockerList,String apiUrl) throws Exception{
			logger.debug("Start putContainerDetailsJSON");
			try {
				logger.debug("dockerList "+dockerList.toString()+" apiUrl "+apiUrl);
				final String url = apiUrl;
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				ObjectMapper mapper = new ObjectMapper();
				String dockerJson=mapper.writeValueAsString(dockerList);
				logger.debug("dockerJson "+dockerJson);
			    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			    	
			    HttpEntity<String> entity = new HttpEntity<String>(dockerJson,headers);
			    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
			   
			  } catch (Exception e) {
				  logger.error("Exception in openstackCompositeSolution putContainerDetailsJSON " +e);
				  throw e;
			 }
			logger.debug("End putContainerDetailsJSON");
		}
		public void putBluePrintDetailsJSON(String  bluePrintStr,String apiUrl)throws Exception{
			logger.debug("Start putBluePrintDetailsJSON ");
			try {
				logger.debug("bluePrintStr "+bluePrintStr);
				logger.debug("apiUrl "+apiUrl);
				final String url = apiUrl;
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				RestTemplate restTemplate = new RestTemplate();
			    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			    HttpEntity<String> entity = new HttpEntity<String>(bluePrintStr,headers);
			    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
			   
			  } catch (Exception e) {
				 logger.error("Exception in openstackCompositeSolution putBluePrintDetailsJSON " +e);
				 throw e;
			 }
			logger.debug("End putBluePrintDetailsJSON ");
		}
		
		public void createDeploymentCompositeData(String dataSource,String dataUserName,String dataPassword,List<OpanStackContainerBean> openStackContainerBeanList,
				String solutionId,String solutionRevisionId,String userId,String uidNumber,String deploymentStatusCode) throws Exception{
			logger.debug("Start createDeploymentCompositeData ");
			logger.debug("dataSource "+dataSource);
			logger.debug("dataUserName "+dataUserName);
			logger.debug("dataPassword "+dataPassword);
			logger.debug("solutionId "+solutionId);
			logger.debug("solutionRevisionId "+solutionRevisionId);
			logger.debug("userId "+userId);
			logger.debug("uidNumber "+uidNumber);
			logger.debug("deploymentStatusCode "+deploymentStatusCode);
			logger.debug("openStackContainerBeanList "+openStackContainerBeanList);
			ObjectMapper mapper = new ObjectMapper();
			CommonDataServiceRestClientImpl client=getClient(dataSource,dataUserName,dataPassword);
			if(solutionId!=null && solutionRevisionId!=null && userId!=null && uidNumber!=null){
				MLPSolutionDeployment mlp=new MLPSolutionDeployment();
				mlp.setSolutionId(solutionId);
				mlp.setUserId(userId);
				mlp.setRevisionId(solutionRevisionId);
				mlp.setDeploymentId(uidNumber);
				mlp.setDeploymentStatusCode(deploymentStatusCode);
				String openStackDetails=mapper.writeValueAsString(openStackContainerBeanList);
				mlp.setDetail(openStackDetails);
				logger.debug("openStackDetails "+openStackDetails);
				MLPSolutionDeployment mlpDeployment=client.createSolutionDeployment(mlp);
				logger.debug("mlpDeployment "+mlpDeployment);
			}
			logger.debug("End createDeploymentCompositeData ");
		}
		
		public MLPSolutionDeployment createDeploymentData(String dataSource, String dataUserName, String dataPassword,
				OpanStackContainerBean containerBean, String solutionId, String solutionRevisionId, String userId,
				String uidNumber, String deploymentStatusCode) throws Exception {
			logger.debug("Start createDeploymentData ");
			logger.debug("dataSource " + dataSource);
			logger.debug("dataUserName " + dataUserName);
			logger.debug("dataPassword " + dataPassword);
			logger.debug("solutionId " + solutionId);
			logger.debug("solutionRevisionId " + solutionRevisionId);
			logger.debug("userId " + userId);
			logger.debug("uidNumber" + uidNumber);
			logger.debug("deploymentStatusCode " + deploymentStatusCode);
			MLPSolutionDeployment mlpDeployment=null;
			ObjectMapper mapper = new ObjectMapper();
			CommonDataServiceRestClientImpl client = getClient(dataSource, dataUserName, dataPassword);
			if (solutionId != null && solutionRevisionId != null && userId != null && uidNumber != null) {
				MLPSolutionDeployment mlp = new MLPSolutionDeployment();
				mlp.setSolutionId(solutionId);
				mlp.setUserId(userId);
				mlp.setRevisionId(solutionRevisionId);
				mlp.setDeploymentId(uidNumber);
				mlp.setDeploymentStatusCode(deploymentStatusCode);
				String openStackDetails = mapper.writeValueAsString(containerBean);
				mlp.setDetail(openStackDetails);
				logger.debug("openStackDetails " + openStackDetails);
				mlpDeployment = client.createSolutionDeployment(mlp);
				logger.debug("mlpDeployment " + mlpDeployment);
			}
			logger.debug(" End createDeploymentData ");
			return mlpDeployment;
		}
		public LinkedList<String> addProbeSequence(LinkedList<String> sequenceList,String probeContainerName){
			  logger.debug("Start addProbeSequence");
			  logger.debug("probeContainerName "+probeContainerName+" sequenceList"+sequenceList);
			  if(sequenceList!=null && sequenceList.size() > 0 && probeContainerName!=null && !"".equals(probeContainerName)){
				  int length=sequenceList.size();
				  logger.debug("length "+length);
				  sequenceList.add((length-1), probeContainerName); 
				}
			  logger.debug("End addProbeSequence "+sequenceList);
			  return sequenceList;
		  }	
	  public void generateNotification(String msg, String userId,String dataSource,String dataUserName,String dataPassword)throws Exception {
			 logger.debug("Start generateNotification");
			 logger.debug("userId "+userId+" msg "+msg);
	         MLPNotification notification = new MLPNotification();
	         try {
	                 if (msg != null) {
	                     notification.setTitle(msg);
	                     notification.setMessage(msg);
	                     Date startDate = new Date();
	                     Date endDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
	                     notification.setStart(startDate);
	                     notification.setEnd(endDate);
	                     CommonDataServiceRestClientImpl client=getClient(dataSource, dataUserName, dataPassword);
	                     notification.setMsgSeverityCode(MessageSeverityCode.ME.toString());
	                     MLNotification mLNotification = createNotification(notification,dataSource,dataUserName,dataPassword);
	                     logger.debug(" mLNotification.getNotificationId() "+mLNotification.getNotificationId());
	                     client.addUserToNotification(mLNotification.getNotificationId(),userId);
	             }
	         } catch (Exception e) {
	              logger.error("Exception Occurred while getNotifications", e);
	              throw e;
	         }
	         logger.debug("End generateNotification"); 
		 }
		
		public org.acumos.openstack.client.transport.MLNotification createNotification(MLPNotification mlpNotification,
				String dataSource,String dataUserName,String dataPassword) {
			 logger.debug("Start createNotification ");
	         CommonDataServiceRestClientImpl client=getClient(dataSource,dataUserName,dataPassword);
	         MLNotification mlNotification = convertToMLNotification(client.createNotification(mlpNotification));
	         logger.debug("End createNotification");
	         return mlNotification;
		 }	
		public  MLNotification convertToMLNotification(MLPNotification mlpNotification) {
			MLNotification mlNotification = new MLNotification();
			if (!isEmptyOrNullString(mlpNotification.getNotificationId())) {
				mlNotification.setNotificationId(mlpNotification.getNotificationId());
			}
			if (!isEmptyOrNullString(mlpNotification.getTitle())) {
				mlNotification.setTitle(mlpNotification.getTitle());
			}
			if (!isEmptyOrNullString(mlpNotification.getMessage())) {
				mlNotification.setMessage(mlpNotification.getMessage());
			}
			if (!isEmptyOrNullString(mlpNotification.getUrl())) {
				mlNotification.setUrl(mlpNotification.getUrl());
			}
			if (mlpNotification.getStart() != null) {
				mlNotification.setStart(mlpNotification.getStart());
			}
			if (mlpNotification.getEnd() != null) {
				mlNotification.setEnd(mlpNotification.getEnd());
			}
			return mlNotification;
		}
		public static boolean isEmptyOrNullString(String input) {
			boolean isEmpty = false;
			if (null == input || 0 == input.trim().length()) {
				isEmpty = true;
			}
			return isEmpty;
		}
		
		public String getDataBrokerTunnelNumber(List<DeploymentBean> deploymentList, String dataBrokerName){
			logger.debug(" Start getDataBrokerIP ");
			String dataBrokerTunnelNum="";
			logger.debug("deploymentList "+deploymentList);
			logger.debug("dataBrokerName "+dataBrokerName);
			if(deploymentList!=null && deploymentList.size() > 0  && dataBrokerName!=null && !"".equals(dataBrokerName)){
				for(DeploymentBean bean:deploymentList){
					if(bean!=null && bean.getNodeType()!=null && bean.getNodeType().equalsIgnoreCase(dataBrokerName)
							&& bean.getDataBrokerType()!=null && !bean.getDataBrokerType().equalsIgnoreCase(OpenStackConstants.DATA_BROKER_CSV_FILE)){
						dataBrokerTunnelNum=bean.getTunnelNumber();
					}
				}
			}
			logger.debug("End getDataBrokerIP dataBrokerTunnelNum "+dataBrokerTunnelNum);
			return dataBrokerTunnelNum;
		}
		public String getDataBrokerTunnelCSV(List<DeploymentBean> deploymentList, String dataBrokerName){
			logger.debug("getDataBrokerTunnelCSV Start");
			String dataBrokerTunnel="";
			logger.debug("deploymentList "+deploymentList);
			logger.debug("dataBrokerName"+dataBrokerName);
			if(deploymentList!=null && deploymentList.size() > 0  && dataBrokerName!=null && !"".equals(dataBrokerName)){
				for(DeploymentBean bean:deploymentList){
					logger.debug("bean.NodeType() "+bean.getNodeType());
					logger.debug("bean.DataBrokerType() "+bean.getDataBrokerType());
					if(bean!=null && bean.getNodeType()!=null && bean.getNodeType().equalsIgnoreCase(dataBrokerName)
							&& bean.getDataBrokerType()!=null && bean.getDataBrokerType().equalsIgnoreCase(OpenStackConstants.DATA_BROKER_CSV_FILE)){
						dataBrokerTunnel=bean.getTunnelNumber();
					}
				}
			}
			logger.debug("dataBrokerTunnel "+dataBrokerTunnel);
			logger.debug("getDataBrokerTunnelCSV End");
			return dataBrokerTunnel;
		}
		public void putDataBrokerDetails(OpenstackCompositeDeployBean deployDataObject,String apiUrl)throws Exception{
			logger.debug("Start putDataBrokerDetails ");
			try {
				logger.debug("apiUrl "+apiUrl);
				logger.debug("UrlAttribute "+deployDataObject.getUrlAttribute());
				logger.debug("JsonMapping "+deployDataObject.getJsonMapping());
				logger.debug("JsonPosition "+deployDataObject.getJsonPosition());
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				RestTemplate restTemplate = new RestTemplate();
				MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
				map.add("jsonUrl", deployDataObject.getUrlAttribute());
				map.add("jsonMapping", deployDataObject.getJsonMapping());
				map.add("jsonPosition", deployDataObject.getJsonPosition());
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
				restTemplate.exchange(apiUrl, HttpMethod.PUT, request, String.class);
			    
			  } catch (Exception e) {
	            logger.error("Exception in putDataBrokerDetails"+e);
	            throw e;
			 }
			logger.debug(" End putDataBrokerDetails");
		}
		
		public NexusArtifactClient nexusArtifactClientDetails(String nexusUrl, String nexusUserName,String nexusPassword) {
			logger.debug("nexusArtifactClientDetails start");
			RepositoryLocation repositoryLocation = new RepositoryLocation();
			repositoryLocation.setId("1");
			repositoryLocation.setUrl(nexusUrl);
			repositoryLocation.setUsername(nexusUserName);
			repositoryLocation.setPassword(nexusPassword);
			NexusArtifactClient nexusArtifactClient = new NexusArtifactClient(repositoryLocation);
			logger.debug("nexusArtifactClientDetails End");
			return nexusArtifactClient;
	}
	 
	public ByteArrayOutputStream getNexusUrlFile(String nexusUrl, String nexusUserName,String nexusPassword,String nexusURI)throws Exception {
		logger.debug("getNexusUrlFile start");
		ByteArrayOutputStream byteArrayOutputStream=null;
		try
		{
			NexusArtifactClient nexusArtifactClient=nexusArtifactClientDetails(nexusUrl, 
					nexusUserName, nexusPassword);
			 byteArrayOutputStream = nexusArtifactClient.getArtifact(nexusURI);
			 logger.debug("byteArrayOutputStream "+byteArrayOutputStream);
		}catch (Exception e) {
			 logger.error("getNexusUrlFile failed", e);
			 throw e;
         }
		logger.debug("getNexusUrlFile ");
		return byteArrayOutputStream;
   }
	public void callCsvConfigDB(OpenstackCompositeDeployBean deployDataObject,String apiUrl,DataBrokerBean dataBrokerBean)throws Exception{
		logger.debug("callCsvConfigDB Start");
		try {
			logger.debug("apiUrl "+apiUrl);
			final String url = apiUrl;
			if(deployDataObject!=null){
				dataBrokerBean.setUserName(deployDataObject.getUsername());
				dataBrokerBean.setPassword(deployDataObject.getPassword());
				dataBrokerBean.setHost(deployDataObject.getHost());
				dataBrokerBean.setPort(deployDataObject.getPort());
			}
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ObjectMapper mapper = new ObjectMapper();
			String dataBrokerBeanJson=mapper.writeValueAsString(dataBrokerBean);
			logger.debug("dataBrokerBeanJson "+dataBrokerBeanJson);
		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		    	
		    HttpEntity<String> entity = new HttpEntity<String>(dataBrokerBeanJson,headers);
		    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
		   
		  } catch (Exception e) {
			  logger.error("callCsvConfigDB failed", e);
			  throw e;
		 }
		logger.debug("callCsvConfigDB End");
	}
	
	public boolean getRepositryStatus(String imageName,String repositoryName){
		logger.debug("getRepositryName Start");
		logger.debug("imageName "+imageName+" repositoryName "+repositoryName);
		boolean checkRepositoryName=false;
		if(imageName!=null){
			String imageArr[]=imageName.split("/");
			if(repositoryName!=null){
				if(imageArr[0].contains(repositoryName)){
					checkRepositoryName=true;
				}
			}
		}
		logger.debug("checkRepositoryName"+checkRepositoryName);
		logger.debug("repositaryName End");
		return checkRepositoryName;
	  }

}
