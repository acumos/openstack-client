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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.acumos.cds.client.CommonDataServiceRestClientImpl;
import org.acumos.cds.domain.MLPArtifact;
import org.acumos.cds.domain.MLPNotification;
import org.acumos.cds.domain.MLPSolution;
import org.acumos.cds.domain.MLPSolutionDeployment;
import org.acumos.cds.domain.MLPSolutionRevision;
import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.nexus.client.RepositoryLocation;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.MLNotification;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.transport.TransportBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;

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
   
   public CommonDataServiceRestClientImpl getClient(String datasource,String userName,String userPd) {
		logger.debug("start getClient");
		CommonDataServiceRestClientImpl client = new CommonDataServiceRestClientImpl(datasource, userName, userPd,null);
		logger.debug("End getClient client "+client);
		return client;
	}
	public NexusArtifactClient nexusArtifactClient(String nexusUrl, String nexusUserName,String nexusPd) {
		logger.debug("start nexusArtifactClient ");
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("1");
		repositoryLocation.setUrl(nexusUrl);
		repositoryLocation.setUsername(nexusUserName);
		repositoryLocation.setPassword(nexusPd);
		NexusArtifactClient nexusArtifactClient = new NexusArtifactClient(repositoryLocation);
		logger.debug("End nexusArtifactClient ");
		return nexusArtifactClient;
	}
	
	public String getBluePrintNexus(String solutionId, String revisionId,String datasource,String userName,String userPd,
			String nexusUrl,String nexusUserName,String nexusPd) throws  Exception{
		  logger.debug("Start getBluePrintNexus ");
		  logger.debug("solutionId "+solutionId);
		  logger.debug("revisionId "+revisionId);
		  logger.debug("datasource "+datasource);
		  logger.debug("userName "+userName);
		  logger.debug("userPd "+userPd);
		  logger.debug("nexusUrl "+nexusUrl);
		  logger.debug("nexusUserName "+nexusUserName);
		  logger.debug("nexusPd "+nexusPd);
		  List<MLPSolutionRevision> mlpSolutionRevisionList;
		  String solutionRevisionId = revisionId;
		  List<MLPArtifact> mlpArtifactList;
		  String nexusURI = "";
		  String artifactType="BP";
		  String bluePrintStr="";
		  ByteArrayOutputStream byteArrayOutputStream = null;
		  CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,userPd);
          		 
			if (null != solutionRevisionId) {
				mlpArtifactList = cmnDataService.getSolutionRevisionArtifacts(solutionId, solutionRevisionId);
				if (null != mlpArtifactList && !mlpArtifactList.isEmpty()) {
					nexusURI = mlpArtifactList.stream()
							.filter(mlpArt -> mlpArt.getArtifactTypeCode().equalsIgnoreCase(artifactType)).findFirst()
							.get().getUri();
					logger.debug("Nexus URI " + nexusURI );
					if (null != nexusURI) {
						NexusArtifactClient nexusArtifactClient=nexusArtifactClient(nexusUrl,nexusUserName,nexusPd);
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
	  public List<MLPSolutionRevision> getSolutionRevisionsList(String solutionId,String datasource,String userName,String userPd)throws  Exception{
			logger.debug("getSolutionRevisions Start ");
			List<MLPSolutionRevision> solRevisionsList = null;
			CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,userPd);
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
		
		public void createDeploymentCompositeData(String dataSource,String dataUserName,String dataPd,List<OpanStackContainerBean> openStackContainerBeanList,
				String solutionId,String solutionRevisionId,String userId,String uidNumber,String deploymentStatusCode) throws Exception{
			logger.debug("Start createDeploymentCompositeData ");
			logger.debug("dataSource "+dataSource);
			logger.debug("dataUserName "+dataUserName);
			logger.debug("dataPd "+dataPd);
			logger.debug("solutionId "+solutionId);
			logger.debug("solutionRevisionId "+solutionRevisionId);
			logger.debug("userId "+userId);
			logger.debug("uidNumber "+uidNumber);
			logger.debug("deploymentStatusCode "+deploymentStatusCode);
			logger.debug("openStackContainerBeanList "+openStackContainerBeanList);
			ObjectMapper mapper = new ObjectMapper();
			CommonDataServiceRestClientImpl client=getClient(dataSource,dataUserName,dataPd);
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
		
		public MLPSolutionDeployment createDeploymentData(String dataSource, String dataUserName, String dataPd,
				OpanStackContainerBean containerBean, String solutionId, String solutionRevisionId, String userId,
				String uidNumber, String deploymentStatusCode) throws Exception {
			logger.debug("Start createDeploymentData ");
			logger.debug("dataSource " + dataSource);
			logger.debug("dataUserName " + dataUserName);
			logger.debug("dataPd " + dataPd);
			logger.debug("solutionId " + solutionId);
			logger.debug("solutionRevisionId " + solutionRevisionId);
			logger.debug("userId " + userId);
			logger.debug("uidNumber" + uidNumber);
			logger.debug("deploymentStatusCode " + deploymentStatusCode);
			MLPSolutionDeployment mlpDeployment=null;
			ObjectMapper mapper = new ObjectMapper();
			CommonDataServiceRestClientImpl client = getClient(dataSource, dataUserName, dataPd);
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
		public LinkedList<String> addContainerSequence(LinkedList<String> sequenceList,String ContainerName){
			  logger.debug("Start addProbeSequence");
			  logger.debug("ContainerName "+ContainerName+" sequenceList"+sequenceList);
			  if(sequenceList!=null && sequenceList.size() > 0 && ContainerName!=null && !"".equals(ContainerName)){
				  int length=sequenceList.size();
				  logger.debug("length "+length);
				  sequenceList.add((length-1), ContainerName); 
				}
			  logger.debug("End addSequence "+sequenceList);
			  return sequenceList;
		  }	
	  public void generateNotification(String msg, String userId,String dataSource,String dataUserName,String dataPd)throws Exception {
			 logger.debug("Start generateNotification");
			 logger.debug("userId "+userId+" msg "+msg);
	         MLPNotification notification = new MLPNotification();
	         try {
	                 if (msg != null) {
	                     notification.setTitle(msg);
	                     notification.setMessage(msg);
	                     //Date startDate = new Date();
	                     //Date endDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
	                     Instant startDate = Instant.now();
	     				 Instant endDate = startDate.plus(Period.ofDays(365));
	                     notification.setStart(startDate);
	                     notification.setEnd(endDate);
	                     notification.setCreated(startDate);
	                     notification.setMsgSeverityCode(OpenStackConstants.MSG_SEVERITY_ME);
                         CommonDataServiceRestClientImpl client=getClient(dataSource, dataUserName, dataPd);
	                     MLNotification mLNotification = createNotification(notification,dataSource,dataUserName,dataPd);
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
				String dataSource,String dataUserName,String dataPd) {
			 logger.debug("Start createNotification ");
	         CommonDataServiceRestClientImpl client=getClient(dataSource,dataUserName,dataPd);
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
		public void putDataBrokerDetails(String urlAttribute,String jsonMapping,String jsonPosition,String apiUrl)throws Exception{
			logger.debug("Start putDataBrokerDetails ");
			try {
				logger.debug("apiUrl "+apiUrl);
				logger.debug("UrlAttribute "+urlAttribute);
				logger.debug("JsonMapping "+jsonMapping);
				logger.debug("JsonPosition "+jsonPosition);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				RestTemplate restTemplate = new RestTemplate();
				MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
				map.add("jsonUrl", urlAttribute);
				map.add("jsonMapping", jsonMapping);
				map.add("jsonPosition", jsonPosition);
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
				restTemplate.exchange(apiUrl, HttpMethod.PUT, request, String.class);
			    
			  } catch (Exception e) {
	            logger.error("Exception in putDataBrokerDetails"+e);
	            throw e;
			 }
			logger.debug(" End putDataBrokerDetails");
		}
		
		public NexusArtifactClient nexusArtifactClientDetails(String nexusUrl, String nexusUserName,String nexusPd) {
			logger.debug("nexusArtifactClientDetails start");
			RepositoryLocation repositoryLocation = new RepositoryLocation();
			repositoryLocation.setId("1");
			repositoryLocation.setUrl(nexusUrl);
			repositoryLocation.setUsername(nexusUserName);
			repositoryLocation.setPassword(nexusPd);
			NexusArtifactClient nexusArtifactClient = new NexusArtifactClient(repositoryLocation);
			logger.debug("nexusArtifactClientDetails End");
			return nexusArtifactClient;
	}
	 
	public ByteArrayOutputStream getNexusUrlFile(String nexusUrl, String nexusUserName,String nexusPd,String nexusURI)throws Exception {
		logger.debug("getNexusUrlFile start");
		ByteArrayOutputStream byteArrayOutputStream=null;
		try
		{
			NexusArtifactClient nexusArtifactClient=nexusArtifactClientDetails(nexusUrl, 
					nexusUserName, nexusPd);
			 byteArrayOutputStream = nexusArtifactClient.getArtifact(nexusURI);
			 logger.debug("byteArrayOutputStream "+byteArrayOutputStream);
		}catch (Exception e) {
			 logger.error("getNexusUrlFile failed", e);
			 throw e;
         }
		logger.debug("getNexusUrlFile ");
		return byteArrayOutputStream;
   }
	public void callCsvConfigDB(String userName,String userPd,String host,String port,String apiUrl,DataBrokerBean dataBrokerBean)throws Exception{
		logger.debug("callCsvConfigDB Start");
		try {
			logger.debug("apiUrl "+apiUrl);
			final String url = apiUrl;
			dataBrokerBean.setUserName(userName);
			dataBrokerBean.setUserPd(userPd);
			dataBrokerBean.setHost(host);
			dataBrokerBean.setPort(port);
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
	
	public void getProtoDetails(TransportBean tbean)throws Exception{
		logger.debug("getProtoDetails Start");
		Map<String,String> protoMap=new HashMap<String,String>();
		try {
			
	        if(tbean!=null && tbean.getProtoContainerMap()!=null && tbean.getProtoContainerMap().size() > 0){
	        	Iterator protoItr = tbean.getProtoContainerMap().entrySet().iterator();
			    while (protoItr.hasNext()) {
			        Map.Entry protoPair = (Map.Entry)protoItr.next();
			        if(protoPair!=null && protoPair.getKey()!=null && protoPair.getValue()!=null){
			        	logger.debug(protoPair.getKey() + " = " + protoPair.getValue());
			        	String containerName=(String)protoPair.getKey();
			        	String protoPath=(String)protoPair.getValue();
			        	ByteArrayOutputStream byteArrayOutputStream=getNexusUrlFile(tbean.getNexusUrl(), tbean.getNexusUserName(),
			        			tbean.getNexusPd(), protoPath);
						logger.debug(protoPair.getKey() +"byteArrayOutputStream "+byteArrayOutputStream);
						protoMap.put(protoPath, byteArrayOutputStream.toString());
						logger.debug("protoPath "+protoPath);
						logger.debug("proto file Details "+byteArrayOutputStream.toString());
			        }
			    }
	        }
		} catch (Exception e) {
			  logger.error("getProtoDetails failed", e);
			  throw e;
		 }
        logger.debug("protoMap "+protoMap);
        tbean.setProtoMap(protoMap);
        logger.debug("getProtoDetails End");
	}
	
	public  String deploymentImageVM(String dockerHostIP, String vmUserName,String registryServerUrl, String username, String userPd, 
			 String repositoryName,int vmNum,byte[] bytesArray,String finalContainerName,String portNumberString,int count,
			 int sleepTime,String probeNexusEndPoint,TransportBean tbean)throws Exception {
		logger.debug(" dockerHostIP " + dockerHostIP);
		logger.debug("vmUserName " + vmUserName);
		logger.debug("registryServerUrl " + registryServerUrl);
		logger.debug("username " + username);
		logger.debug("userPd " + userPd);
		logger.debug("repositoryName " + repositoryName);
		logger.debug("finalContainerName " + finalContainerName);
		logger.debug("portNumberString " + portNumberString);
		logger.debug("vmNum " + vmNum);
		logger.debug("count " + count);
		logger.debug("probeNexusEndPoint " + probeNexusEndPoint);
		logger.debug("start deploymentImageVM CompositeSolution ");
		SSHShell sshShell = null;
		try {
			String PULL_IMAGE = "" + "docker login --username=" + username + " --password=" + userPd + " "
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
			}else if(finalContainerName!=null && finalContainerName.equalsIgnoreCase(OpenStackConstants.NGINX_CONTAINER)){
				logger.debug("nginx Condition");
				RUN_IMAGE = "" + "docker run --name "+finalContainerName+" -v "+tbean.getNginxMapFolder()+":"+tbean.getNginxWebFolder()+":ro  -d -p 0.0.0.0:" + portNumberString
						+ "  " + repositoryName + " \n";
				
			}else{
				RUN_IMAGE = "" + "docker run --name " + finalContainerName + " -d -p 0.0.0.0:" + portNumberString
						+ "  " + repositoryName + " \n";
			}
			logger.debug("output Start  RUN_IMAGE "+RUN_IMAGE);
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
	

	public void sshOpenStackCore(int vmNumber,String floatingIp,String hostName,String user,byte[] bytesArray,
			int hostPort,String openStackIP)throws Exception{
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
        	logger.error("Exception in readBytesFromFile "+e);
        	throw e;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                	logger.error("Exception in readBytesFromFile Finally "+e);
                	throw e;
                }
            }

        }
        logger.debug("bytesArray.length "+bytesArray.length);
        logger.debug("readBytesFromFile End");
        return bytesArray;

    }
	public void protoFileVM(String dockerHostIP, String vmUserName, byte[] bytesArray,TransportBean tbean,int vmBind)
			throws Exception{
		SSHShell sshShell = null;
		logger.debug("protoFileVM Start");
		try{
		sshShell = SSHShell.open(dockerHostIP, vmBind, vmUserName, bytesArray);
		String createFolderScript = sshShell.executeCommand("sudo mkdir -p "+tbean.getNginxMapFolder()+" ", true,true);
		logger.debug("createFolderScript  " + createFolderScript);
		Iterator protoItr = tbean.getProtoMap().entrySet().iterator();
	    while (protoItr.hasNext()) {
	        Map.Entry protoPair = (Map.Entry)protoItr.next();
	        if(protoPair!=null && protoPair.getKey()!=null && protoPair.getValue()!=null){
	        	logger.debug(protoPair.getKey() + " keyAndValue " + protoPair.getValue());
	        	String protoFilePathName=(String)protoPair.getKey();
	        	String protoDetails=(String)protoPair.getValue();
	        	int index = protoFilePathName.lastIndexOf("/");
	        	String protoFileName=protoFilePathName.substring(index+1);
	        	String protoUriFolder= protoFilePathName.substring(0,index);
	        	String copyFolderName=tbean.getNginxMapFolder()+"/"+protoUriFolder;
	        	logger.debug("protoFileName "+protoFileName);
	        	logger.debug("protoUriFolder "+protoUriFolder);
	        	logger.debug("copyFolderName "+copyFolderName);
	        	createFolderScript = sshShell.executeCommand("sudo mkdir -p "+copyFolderName+" ", true,true);
	        	logger.debug("createFolderScript folder " + createFolderScript);
	    		
	    		sshShell.upload(new ByteArrayInputStream(protoDetails.getBytes()), protoFileName,
	        			"OpenStackDataFiles", true, "4095");
	    		logger.debug("File uploaded in AzureDataFiles folder " );
	        	String copyScript = sshShell.executeCommand("sudo cp -R "+tbean.getAzureDataFiles()+"/"+protoFileName+" "+ copyFolderName, true,true);
	        	logger.debug("copy file in folder finish"+copyScript);
	        }
	    }
		}catch(Exception e){
			logger.error("protoFileVM failed", e);
			throw e;
		}
		logger.debug("protoFileVM End");
	}
	public String getSolutionCode(String solutionId,String datasource,String userName,String dataPd){
		logger.debug("getSolution start");
		String toolKitTypeCode="";
		try{
		CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,dataPd);
		MLPSolution mlpSolution = cmnDataService.getSolution(solutionId);
			if (mlpSolution != null) {
				logger.debug("mlpSolution.getToolkitTypeCode() "+mlpSolution.getToolkitTypeCode());
				toolKitTypeCode=mlpSolution.getToolkitTypeCode();
			}
		}catch(Exception e){
			logger.error("Error in get solution "+e.getMessage());
			toolKitTypeCode="";
		}
		logger.debug("getSolution End toolKitTypeCode " +toolKitTypeCode);	
	  return toolKitTypeCode;
	 }
	
	public String removeAllContainer(String dockerHostIP, int vmNum, String vmUserName, byte[] bytesArray)throws Exception{
		logger.debug("removeAllContainer Start ");
		SSHShell sshShell = null;
		String output="";
		String returnStr="success";
		 logger.debug("dockerHostIP "+dockerHostIP);
		 logger.debug("vmNum "+vmNum);
		 logger.debug("vmUserName "+vmUserName);
		try {
			String removeDockerScript=""
					                 + "docker stop $(docker ps -a -q) \n"
						             +"docker rm $(docker ps -a -q) ";
			sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			logger.debug("Upload docker install script  ");
			
			 sshShell.upload(new ByteArrayInputStream(removeDockerScript.getBytes()),
						"removeDockerScript.sh", "azuredockerscript", true, "4095");
			 logger.debug("Start executing script ");
			 output = sshShell.executeCommand("bash -c ~/azuredockerscript/removeDockerScript.sh", true, true);
			 logger.debug("output "+output);
			
		} catch (Exception exception) {
			logger.error("Exception in checkInstallDocker "+exception);
			returnStr="fail";
			throw exception;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		
		logger.debug("removeAllContainer End returnStr "+returnStr);
		return returnStr;
	}
	
	public String checkPrerequisites(String dockerHostIP, int vmNum, String vmUserName, byte[] bytesArray)throws Exception{
		 logger.debug("checkPrerequisites Start");
		 SSHShell sshShell = null;
		 String returnStr="success";
		 String scriptOutput="";
		 logger.debug("dockerHostIP "+dockerHostIP);
		 logger.debug("vmNum "+vmNum);
		 logger.debug("vmUserName "+vmUserName);
		 try {
			 String installScript=getFileDetails(OpenStackConstants.SETUP_SCRIPT_NAME);
			 logger.debug("installScript "+installScript);
			 sshShell = SSHShell.open(dockerHostIP, vmNum, vmUserName, bytesArray);
			 sshShell.upload(new ByteArrayInputStream(installScript.getBytes()),
						"setup-dockert.sh", "dockerscript", true, "4095");
			 logger.debug("Start executing script ");
			 scriptOutput = sshShell.executeCommand("bash -c ~/dockerscript/setup-dockert.sh", true, true);
			 logger.debug("scriptOutput "+scriptOutput);
		 }catch (Exception exception) {
				logger.error("Exception in checkPrerequisites "+exception);
				returnStr="fail";
				throw exception;
			} finally {
				if (sshShell != null) {
					sshShell.close();
					sshShell = null;
				}
			}
		 logger.debug("checkPrerequisites End returnStr"+returnStr);
		return returnStr;
	}
	
	public String getsshComandoutput(String vmHostIP,String user,byte[] bytesArray,String openStackIP)throws Exception{
		logger.debug("Start getsshComandoutput");
		 SSHShell sshShell = null;
		 String sshBindNumber=null;
		try {
            logger.debug("user "+user);
            logger.debug("openStackIP "+openStackIP);
            String vmHostIPPort=vmHostIP+":22";
            logger.debug("vmHostIPPort "+vmHostIPPort);
            sshShell = SSHShell.open(openStackIP, 22, user, bytesArray);
            sshBindNumber = sshShell.executeCommand("ps -ef | grep -oP '(?<=ssh -L ).*?(?=:"+vmHostIPPort+")' | sed -n '2p' ", true,true);
            logger.debug("sshBindNumber "+sshBindNumber);
		} catch (Exception ex) {
			logger.error("Exception in sshOpenStackCore in composite solution "+ex);
			throw ex;
		} finally {
			if (sshShell != null) {
				sshShell.close();
				sshShell = null;
			}
		}
		
		logger.debug("getsshComandoutput End");
		return sshBindNumber;
	}
	public String getFileDetails(String fileDetails) throws Exception{
		String content="";
		logger.debug("fileDetails "+fileDetails);
		BufferedReader reader = new BufferedReader(new FileReader(fileDetails));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		content = stringBuilder.toString();
		return content;
	}
	public String getSingleImageData(String solutionId,String revisionId,String datasource,String userName,String dataPd)throws Exception{
		logger.debug("Start getSingleImageData");
		String imageTag="";
		CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,dataPd);
		List<MLPArtifact> mlpSolutionRevisions = null;
		mlpSolutionRevisions = cmnDataService.getSolutionRevisionArtifacts(solutionId, revisionId);
		if(mlpSolutionRevisions != null) {
			for (MLPArtifact artifact : mlpSolutionRevisions) {
				String[] st = artifact.getUri().split("/");
				String name = st[st.length-1];
				artifact.setName(name);
				logger.debug("ArtifactTypeCode" +artifact.getArtifactTypeCode());
				logger.debug("URI" +artifact.getUri());
				if(artifact.getArtifactTypeCode()!=null && artifact.getArtifactTypeCode().equalsIgnoreCase("DI")){
					imageTag=artifact.getUri();
				}
			}
		}
		 
		logger.debug("End getSingleImageData imageTag"+imageTag);
		return imageTag;
	}
	
	public  String deploymentSingleImageVM(String dockerHostIP, String vmUserName, 
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
