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

import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.acumos.cds.client.CommonDataServiceRestClientImpl;
import org.acumos.cds.domain.MLPNotification;
import org.acumos.cds.domain.MLPSolutionRevision;
import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.transport.OpanStackContainerBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.CommonUtil;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.DockerInfoList;
import org.acumos.openstack.client.util.OpenStackConstants;
import org.acumos.openstack.client.util.ParseJSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonUtilTest {
	Logger logger = LoggerFactory.getLogger(CommonUtilTest.class);
	
	@Test	
	public void getRepositryNameTest()throws Exception{
		logger.info("getRepositryNameTest Start");
		CommonUtil cutil=new CommonUtil();
		String repo=cutil.getRepositryName("repoTest/test:1", "repoTest#repoTest1");
		assertNotNull(repo);
		logger.info("getRepositryNameTest End"+repo);
	}
	
	@Test	
	public void getRepositryImageNameTest()throws Exception{
		logger.info("getRepositryNameTest Start");
		CommonUtil cutil=new CommonUtil();
		String repo=cutil.getRepositryImageName("repoTest/test:1", "repoTest#repoTest1");
		assertNotNull(repo);
		logger.info("getRepositryNameTest End"+repo);
	}
	@Test	
	public void iterateImageMapTest()throws Exception{
		logger.info("iterateImageMapTest Start");
		CommonUtil cutil=new CommonUtil();
		ArrayList<String> list=null;
		HashMap<String,String> imageMap=new HashMap<String,String>();
		imageMap.put("test", "test");
		imageMap.put("test1", "test1");
		list=cutil.iterateImageMap(imageMap);
		assertNotNull(list);
		logger.info("iterateImageMapTest End"+list);
	}
	@Test	
	public void getSequenceTest()throws Exception{
		logger.info("iterateImageMapTest Start");
		CommonUtil cutil=new CommonUtil();
		LinkedList<String> list=null;
		HashMap<String,String> imageMap=new HashMap<String,String>();
		imageMap.put("test", "test");
		imageMap.put("test1", "test1");
		list=cutil.getSequence(imageMap);
		assertNotNull(list);
		logger.info("getSequenceTest End"+list);
	}
	
	@Test	
	public void addContainerSequenceTest()throws Exception{
		logger.info("addContainerSequenceTest Start");
		CommonUtil cutil=new CommonUtil();
		LinkedList<String> list=new LinkedList<String>();
		list.add("test");
		list.add("test1");
		LinkedList<String> listSequence=cutil.addContainerSequence(list, "test3");
		assertNotNull(listSequence);
		logger.info("addContainerSequenceTest End"+list);
	}
	
	@Test	
	public void getDataBrokerTunnelNumberTest()throws Exception{
		logger.info("getDataBrokerTunnelNumberTest Start");
		String tunnelNumber=null;
		CommonUtil cutil=new CommonUtil();
		DeploymentBean db=new DeploymentBean();
		db.setNodeType("DataBroker");
		db.setDataBrokerType(OpenStackConstants.DATA_BROKER_CSV_FILE);
		db.setTunnelNumber("8000");
		List<DeploymentBean> deploymentList=new ArrayList<DeploymentBean>();
		deploymentList.add(db);
		tunnelNumber=cutil.getDataBrokerTunnelNumber(deploymentList, "DataBroker");
		assertNotNull(tunnelNumber);
		logger.info("getDataBrokerTunnelNumberTest End"+tunnelNumber);
	}
	
	@Test	
	public void getDataBrokerTunnelCSVTest()throws Exception{
		logger.info("getDataBrokerTunnelCSVTest Start");
		String tunnelNumber=null;
		CommonUtil cutil=new CommonUtil();
		DeploymentBean db=new DeploymentBean();
		db.setNodeType("csv");
		db.setDataBrokerType(OpenStackConstants.DATA_BROKER_CSV_FILE);
		db.setTunnelNumber("8000");
		List<DeploymentBean> deploymentList=new ArrayList<DeploymentBean>();
		deploymentList.add(db);
		tunnelNumber=cutil.getDataBrokerTunnelCSV(deploymentList, "csv");
		assertNotNull(tunnelNumber);
		logger.info("getDataBrokerTunnelCSVTest End"+tunnelNumber);
	}
	
	@Test	
	public void getRepositryStatusTest()throws Exception{
		logger.info("getRepositryStatusTest Start");
		CommonUtil cutil=new CommonUtil();
		boolean check=false;
		check=cutil.getRepositryStatus("repoTest/test:1", "repoTest");
	    Assert.assertEquals(check, true);
		logger.info("getRepositryStatusTest End");
	}
	
	@Test	
	public void getFileDetailsTest()throws Exception{
		logger.info("readBytesFromFileTest Start");
		CommonUtil cutil=new CommonUtil();
		String fileOutput=null;
		fileOutput=cutil.getFileDetails(OpenStackConstants.JSON_FILE_NAME);
		assertNotNull(fileOutput);
		logger.info("getFileDetailsTest End"+fileOutput);
	}
	
	@Test	
	public void readBytesFromFileTest()throws Exception{
		logger.info("readBytesFromFileTest Start");
		byte[] bytesArray = null;
		CommonUtil cutil=new CommonUtil();
		bytesArray=cutil.readBytesFromFile(OpenStackConstants.JSON_FILE_NAME);
		assertNotNull(bytesArray);
		logger.info("readBytesFromFileTest End"+bytesArray);
	}
	
	@Test	
	public void createNotificationTest()throws Exception{
		logger.info("createNotificationTest Start");
		Instant startDate = Instant.now();
		Instant endDate = startDate.plus(Period.ofDays(365));
		CommonUtil cutil=new CommonUtil();
		MLPNotification mlpNotification=new MLPNotification();
		mlpNotification.setNotificationId(OpenStackTestConstants.TEST_OBJ);
		mlpNotification.setTitle(OpenStackTestConstants.TEST_OBJ);
		mlpNotification.setMessage(OpenStackTestConstants.TEST_OBJ);
		mlpNotification.setUrl(OpenStackTestConstants.TEST_OBJ);
		mlpNotification.setStart(startDate);
		mlpNotification.setEnd(endDate);
		org.acumos.openstack.client.transport.MLNotification ml=cutil.convertToMLNotification(mlpNotification);
		assertNotNull(ml);
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, mlpNotification.getNotificationId());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, mlpNotification.getTitle());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, mlpNotification.getMessage());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, mlpNotification.getUrl());
		 Assert.assertEquals(startDate, mlpNotification.getStart());
		 Assert.assertEquals(endDate, mlpNotification.getEnd());
		logger.info("createNotificationTest End"+ml);
	}
	
	
	
	
}
