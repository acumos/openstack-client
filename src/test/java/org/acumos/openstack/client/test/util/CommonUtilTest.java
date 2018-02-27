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
package org.acumos.openstack.client.test.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.acumos.cds.client.CommonDataServiceRestClientImpl;
import org.acumos.cds.domain.MLPSolutionRevision;
import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DockerInfoList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtilTest {
	Logger logger = LoggerFactory.getLogger(CommonUtilTest.class);
	@Test
	public void getRepositryNameTest(){
		logger.debug("<-Start ----getRepositryNameTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			String imageName="cognita-nexus01:8000/Adder1:1";
			String imageName2="cognita-nexus01:8001/Adder2:1";
			String imageName3="cognita-nexus01:8002/Adder3:1";
			String repositoryName=cmUtil.getRepositryName(imageName);
			String repositoryName2=cmUtil.getRepositryName(imageName2);
			String repositoryName3=cmUtil.getRepositryName(imageName3);
			assertNotNull(repositoryName);
			assertNotNull(repositoryName2);
			assertNotNull(repositoryName3);
		}catch(Exception ex){
			   logger.error("Error in getRepositryNameTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----getRepositryNameTest------->");
	}
	@Test
	public void getRepositryImageNameTest(){
		logger.debug("<-Start ----getRepositryImageNameTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			String imageName="cognita-nexus01:8000/Adder1:1";
			String imageName2="cognita-nexus01:8001/Adder2:1";
			String imageName3="cognita-nexus01:8002/Adder3:1";
			String repositoryName=cmUtil.getRepositryImageName(imageName);
			String repositoryName2=cmUtil.getRepositryImageName(imageName2);
			String repositoryName3=cmUtil.getRepositryImageName(imageName3);
			assertNotNull(repositoryName);
			assertNotNull(repositoryName2);
			assertNotNull(repositoryName3);
		}catch(Exception ex){
			   logger.error("Error in getRepositryImageNameTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----getRepositryImageNameTest------->");
	}
	@Test
	public void iterateImageMapTest(){
		logger.debug("<-Start ----iterateImageMapTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			HashMap<String,String> imageMap=new HashMap<String,String>();
			imageMap.put("cognita-nexus01:8000/Adder1:1", "Adder1");
			imageMap.put("cognita-nexus01:8000/Adder2:1", "Adder2");
			ArrayList<String> list =cmUtil.iterateImageMap(imageMap);
			assertNotNull(list);
		}catch(Exception ex){
			   logger.error("Error in iterateImageMapTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----iterateImageMapTest------->");
	}
	
	@Test
	public void getClientTest(){
		logger.debug("<-Start ----getClientTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			String cmndatasvcendpoinurl="http://cognita-dev1-vm01-core:8002/ccds";
			String cmndatasvcuser="ccds_client";
			String cmndatasvcpwd="ccds_client";
			CommonDataServiceRestClientImpl impl=cmUtil.getClient(cmndatasvcendpoinurl, cmndatasvcuser, cmndatasvcpwd);
			assertNotNull(impl);
		}catch(Exception ex){
			   logger.error("Error in getClientTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----getClientTest------->");
		
	}
	@Test
	public void nexusArtifactClientTest(){
		logger.debug("<-Start ----nexusArtifactClientTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			NexusArtifactClient nexusArtifactClient=cmUtil.nexusArtifactClient("http://cognita-nexus01:8081/repository/repo_cognita_model_maven/", "cognita_model_rw", "not4you");
			assertNotNull(nexusArtifactClient);
		}catch(Exception ex){
			   logger.error("Error in nexusArtifactClientTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----nexusArtifactClientTest------->");
	}
	@Test
	public void getSolutionRevisionsListTest(){
		logger.debug("<-Start ----getSolutionRevisionsListTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			String cmndatasvcendpoinurl="http://cognita-dev1-vm01-core:8001/ccds";
			String cmndatasvcuser="ccds_client";
			String cmndatasvcpwd="ccds_client";
			List<MLPSolutionRevision> list=cmUtil.getSolutionRevisionsList("1566ec6f-cdc7-4ba9-8fb6-3124ab9e17c1",
					cmndatasvcendpoinurl, cmndatasvcuser, cmndatasvcpwd);
			assertNotNull(list);
		}catch(Exception ex){
			   logger.error("Error in getSolutionRevisionsListTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----getSolutionRevisionsListTest------->");
	}
	
	@Test
	public void getSequenceTest(){
		logger.debug("<-Start ----getSequenceTest------->");
		try{
			CommonUtil cmUtil=new CommonUtil();
			HashMap<String,String> imageMap=new HashMap<String,String>();
			imageMap.put("cognita-nexus01:8000/Adder1:1", "Adder1");
			imageMap.put("cognita-nexus01:8000/Adder2:1", "Adder2");
			LinkedList<String> list =cmUtil.getSequence(imageMap);
			assertNotNull(list);
		}catch(Exception ex){
			   logger.error("Error in getSequenceTest---> "+ex.getMessage()); 
		}
		logger.debug("<-End ----getSequenceTest------->");
	}
	/*@Test
	public void putContainerDetailsJSONTest(){
		CommonUtil cmUtil=new CommonUtil();
		DockerInfoList  dockerList=new DockerInfoList();
		String apiUrl="http://10.1.0.100:2289/putDockerInfo";
		String returnString=cmUtil.putContainerDetailsJSON(dockerList, apiUrl);
		System.out.println("============"+returnString);
		
	}
	@Test
	public void putBluePrintDetailsJSONTest(){
		CommonUtil cmUtil=new CommonUtil();
		Blueprint  bluePrint=new Blueprint();
		String apiUrl="http://10.1.0.100:2289/putBlueprint";
		String returnString=cmUtil.putBluePrintDetailsJSON(bluePrint, apiUrl);
		System.out.println("============"+returnString);
		
	}*/
	/*@Test
	public void createDeploymentCompositeDataTest(){
		CommonUtil cmUtil=new CommonUtil();
		UUID uidNumber = UUID.randomUUID();
		try{
		String uidNumStr=uidNumber.toString();
		List<OpanStackContainerBean> openStackContainerBeanList=new ArrayList<OpanStackContainerBean>();
		String cmndatasvcendpoinurl="http://cognita-dev1-vm01-core.eastus.cloudapp.azure.com:8002/ccds";
		String cmndatasvcuser="ccds_client";
		String cmndatasvcpwd="ccds_client";
		String solutionId="1566ec6f-cdc7-4ba9-8fb6-3124ab9e17c1";
		String solutionRevisionId="05cac760-cd53-49a3-85f3-df908f14211d";
		String userId="7cd47ca4-1c5d-4cdc-909c-f7c17367b4d4";
		OpanStackContainerBean bean=new OpanStackContainerBean();
		bean.setContainerName("Test");
		bean.setContainerIp("Test");
		bean.setContainerPort("Test");
		openStackContainerBeanList.add(bean);
		cmUtil.createDeploymentCompositeData(cmndatasvcendpoinurl, cmndatasvcuser, cmndatasvcpwd,
				openStackContainerBeanList, solutionId, solutionRevisionId, 
				userId, uidNumStr, "DP");
		}catch(Exception e){
			
		}
	}*/
	/*@Test
	public void createDeploymentDataTest(){
		CommonUtil cmUtil=new CommonUtil();
		UUID uidNumber = UUID.randomUUID();
		try{
		String uidNumStr=uidNumber.toString();
		//List<OpanStackContainerBean> openStackContainerBeanList=new ArrayList<OpanStackContainerBean>();
		String cmndatasvcendpoinurl="http://cognita-dev1-vm01-core.eastus.cloudapp.azure.com:8002/ccds";
		String cmndatasvcuser="ccds_client";
		String cmndatasvcpwd="ccds_client";
		String solutionId="1566ec6f-cdc7-4ba9-8fb6-3124ab9e17c1";
		String solutionRevisionId="05cac760-cd53-49a3-85f3-df908f14211d";
		String userId="7cd47ca4-1c5d-4cdc-909c-f7c17367b4d4";
		OpanStackContainerBean bean=new OpanStackContainerBean();
		bean.setContainerName("Test");
		bean.setContainerIp("Test");
		bean.setContainerPort("Test");
		cmUtil.createDeploymentData(cmndatasvcendpoinurl, cmndatasvcuser, cmndatasvcpwd,
				bean, solutionId, solutionRevisionId, 
				userId, uidNumStr, "DP");
		}catch(Exception e){
			
		}
	}*/
	
	
}
