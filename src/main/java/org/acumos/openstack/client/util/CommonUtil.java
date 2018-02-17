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
import org.acumos.cds.client.CommonDataServiceRestClientImpl;
import org.acumos.cds.domain.MLPArtifact;
import org.acumos.cds.domain.MLPSolutionRevision;
import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.nexus.client.RepositoryLocation;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acumos.cds.domain.MLPSolutionDeployment;

public class CommonUtil {
	
Logger logger = LoggerFactory.getLogger(CommonUtil.class);

  public String getRepositryName(String imageName){
	logger.debug("<----Start-getRepositryName------->"+imageName);
	String repositaryName="";
	if(imageName!=null){
		String imageArr[]=imageName.split("/");
		if(imageArr!=null && imageArr[0]!=null){
			if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8000")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8000";
			}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8001")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8001";
			}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8002")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8002";
			}else{
				repositaryName=imageArr[0];
			}
			
		}
		
	}
	logger.debug("<----End-repositaryName------->"+repositaryName);
	
	return repositaryName;
  }
  
  public String getRepositryImageName(String imageName){
		logger.debug("<----Start-getRepositryName------->"+imageName);
		String repositaryName="";
		String repositaryImageName="";
		String ImageName="";
		if(imageName!=null){
			String imageArr[]=imageName.split("/");
			if(imageArr!=null && imageArr[0]!=null){
				if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8000")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8000";
				}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8001")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8001";
				}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8002")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8002";
				}else{
					repositaryName=imageArr[0];
				}
				
			}
			if(imageArr[1]!=null){
				ImageName=imageArr[1];
			}
			logger.debug("<-----repositaryName------->"+repositaryName+"--ImageName----"+ImageName);	
			
		}
		if(repositaryName!=null && ImageName!=null){
			repositaryImageName=repositaryName+"/"+ImageName;
		}
		logger.debug("<--End-getRepositryImageName--repositaryImageName------->"+repositaryImageName);
		
		return repositaryImageName;
	  }
    
   public ArrayList<String> iterateImageMap(HashMap<String,String> imageMap){
		logger.debug("<--Start-------iterateImageMap-------imageMap---->"+imageMap);
		ArrayList<String> list=new ArrayList<String>();
		 Iterator it = imageMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        logger.debug(pair.getKey() + " = " + pair.getValue());
		        if(pair.getKey()!=null){
		        	list.add((String)pair.getKey());
		        }
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		logger.debug("<--End-------iterateImageMap-------list---->"+list);
		return list;
	}
   
   public CommonDataServiceRestClientImpl getClient(String datasource,String userName,String password) {
		logger.debug("<------start----getClient------------>");
		CommonDataServiceRestClientImpl client = new CommonDataServiceRestClientImpl(datasource, userName, password);
		logger.debug("<------End----getClient---------client--->"+client);
		return client;
	}
	public NexusArtifactClient nexusArtifactClient(String nexusUrl, String nexusUserName,String nexusPassword) {
		logger.debug("<------start----nexusArtifactClient------------>");
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("1");
		repositoryLocation.setUrl(nexusUrl);
		repositoryLocation.setUsername(nexusUserName);
		repositoryLocation.setPassword(nexusPassword);
		NexusArtifactClient nexusArtifactClient = new NexusArtifactClient(repositoryLocation);
		logger.debug("<------End----nexusArtifactClient------------>");
		return nexusArtifactClient;
	}
	
	public String getBluePrintNexus(String solutionId, String revisionId,String datasource,String userName,String password,
			String nexusUrl,String nexusUserName,String nexusPassword) throws  Exception{
		  logger.debug("------ Start getBluePrintNexus-----------------");
		  logger.debug("-------solutionId-----------"+solutionId);
		  logger.debug("-------revisionId-----------"+revisionId);
		  logger.debug("-------datasource-----------"+datasource);
		  logger.debug("-------userName-----------"+userName);
		  logger.debug("-------password-----------"+password);
		  logger.debug("-------nexusUrl-----------"+nexusUrl);
		  logger.debug("-------nexusUserName-----------"+nexusUserName);
		  logger.debug("-------nexusPassword-----------"+nexusPassword);
		  List<MLPSolutionRevision> mlpSolutionRevisionList;
		  String solutionRevisionId = revisionId;
		  List<MLPArtifact> mlpArtifactList;
		  String nexusURI = "";
		  String artifactType="BP";
		  String bluePrintStr="";
		  ByteArrayOutputStream byteArrayOutputStream = null;
		  CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,password);
          		 
			if (null != solutionRevisionId) {
				// 3. Get the list of Artifiact for the SolutionId and SolutionRevisionId.
				mlpArtifactList = cmnDataService.getSolutionRevisionArtifacts(solutionId, solutionRevisionId);
				if (null != mlpArtifactList && !mlpArtifactList.isEmpty()) {
					nexusURI = mlpArtifactList.stream()
							.filter(mlpArt -> mlpArt.getArtifactTypeCode().equalsIgnoreCase(artifactType)).findFirst()
							.get().getUri();
					logger.debug("------ Nexus URI : " + nexusURI + " -------");
					if (null != nexusURI) {
						NexusArtifactClient nexusArtifactClient=nexusArtifactClient(nexusUrl,nexusUserName,nexusPassword);
						File f = new File("blueprint.json");
						if(f.exists() && !f.isDirectory()) { 
						    f.delete();
						}
						byteArrayOutputStream = nexusArtifactClient.getArtifact(nexusURI);
						logger.debug("------- byteArrayOutputStream ---blueprint.json-------"+byteArrayOutputStream.toString());
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
			logger.debug("------ End getBluePrintNexus-----------------");	
		return bluePrintStr;	
	  }
	  private List<MLPSolutionRevision> getSolutionRevisionsList(String solutionId,String datasource,String userName,String password)throws  Exception{
			logger.debug("------- getSolutionRevisions() : Start ----------");
			List<MLPSolutionRevision> solRevisionsList = null;
			CommonDataServiceRestClientImpl cmnDataService=getClient(datasource,userName,password);
			solRevisionsList = cmnDataService.getSolutionRevisions(solutionId);
			logger.debug("------- getSolutionRevisions() : End ----------");
			return solRevisionsList;
		}
	  public LinkedList<String> getSequence(HashMap<String,String> hmap){
			LinkedList<String> sequenceList=new LinkedList<String>();
			Iterator itrContainer=hmap.entrySet().iterator();
	        while(itrContainer.hasNext()){
	        	
	        	Map.Entry pair = (Map.Entry)itrContainer.next();
	        	String containerName=(String)pair.getKey();
	        	sequenceList.add((String)pair.getValue());
	        }
	        logger.debug("======sequenceList=============="+sequenceList);
	        return sequenceList;
		}
	  
	  public void putContainerDetailsJSON(DockerInfoList  dockerList,String apiUrl){
			logger.debug("<--------Start---putContainerDetailsJSON------->");
			try {
				logger.debug("<----dockerList---------->"+dockerList.toString()+"======apiUrl==="+apiUrl);
				final String url = apiUrl;
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				ObjectMapper mapper = new ObjectMapper();
				String dockerJson=mapper.writeValueAsString(dockerList);
				logger.debug("<----dockerJson---------->"+dockerJson);
			    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			    	
			    HttpEntity<String> entity = new HttpEntity<String>(dockerJson,headers);
			    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
			   
			  } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("<---------Exception----------->"+e.getMessage());
			 }
			logger.debug("<--------End---putContainerDetailsJSON------->");
		}
		public void putBluePrintDetailsJSON(Blueprint  bluePrint,String apiUrl){
			logger.debug("<--------Start---putBluePrintDetailsJSON------->");
			try {
				logger.debug("<----bluePrint---------->"+bluePrint.toString()+"======apiUrl==="+apiUrl);
				final String url = apiUrl;
				ObjectMapper mapper = new ObjectMapper();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				String blueprintJson=mapper.writeValueAsString(bluePrint); 
				logger.debug("<----blueprintJson---------->"+blueprintJson);
				RestTemplate restTemplate = new RestTemplate();
			    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			    HttpEntity<String> entity = new HttpEntity<String>(blueprintJson,headers);
			    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
			   
			  } catch (Exception e) {
	            logger.error("<---------Exception----------->"+e.getMessage());
	            e.printStackTrace();
			 }
			logger.debug("<--------End---putBluePrintDetailsJSON------->");
		}
		
		public void createDeploymentCompositeData(String dataSource,String dataUserName,String dataPassword,List<OpanStackContainerBean> openStackContainerBeanList,
				String solutionId,String solutionRevisionId,String userId,String uidNumber,String deploymentStatusCode) throws Exception{
			logger.debug("<---------Start createDeploymentCompositeData ------------------------->");
			logger.debug("<---------dataSource-------->"+dataSource);
			logger.debug("<-------dataUserName-------------->"+dataUserName);
			logger.debug("<--------dataPassword------------->"+dataPassword);
			logger.debug("<---------solutionId------------------->"+solutionId);
			logger.debug("<--------solutionRevisionId-------------------->"+solutionRevisionId);
			logger.debug("<------userId--------------->"+userId);
			logger.debug("<------uidNumber--------------->"+uidNumber);
			logger.debug("<------deploymentStatusCode--------------->"+deploymentStatusCode);
			logger.debug("<------openStackContainerBeanList--------------->"+openStackContainerBeanList);
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
				logger.debug("<---------openStackDetails------------------------->"+openStackDetails);
				MLPSolutionDeployment mlpDeployment=client.createSolutionDeployment(mlp);
				logger.debug("<---------mlpDeployment------------------------->"+mlpDeployment);
			}
			logger.debug("<---------End createDeploymentCompositeData ------------------------->");
		}
		
		public MLPSolutionDeployment createDeploymentData(String dataSource, String dataUserName, String dataPassword,
				OpanStackContainerBean containerBean, String solutionId, String solutionRevisionId, String userId,
				String uidNumber, String deploymentStatusCode) throws Exception {
			logger.debug("<---------Start createDeploymentData ------------------------->");
			logger.debug("<---------dataSource-------->" + dataSource);
			logger.debug("<-------dataUserName-------------->" + dataUserName);
			logger.debug("<--------dataPassword------------->" + dataPassword);
			logger.debug("<---------solutionId------------------->" + solutionId);
			logger.debug("<--------solutionRevisionId-------------------->" + solutionRevisionId);
			logger.debug("<------userId--------------->" + userId);
			logger.debug("<------uidNumber--------------->" + uidNumber);
			logger.debug("<------deploymentStatusCode--------------->" + deploymentStatusCode);
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
				logger.debug("<---------openStackDetails------------------------->" + openStackDetails);
				mlpDeployment = client.createSolutionDeployment(mlp);
				logger.debug("<---------mlpDeployment------------------------->" + mlpDeployment);
			}
			logger.debug("<---------End createDeploymentData ------------------------->");
			return mlpDeployment;
		}

}
