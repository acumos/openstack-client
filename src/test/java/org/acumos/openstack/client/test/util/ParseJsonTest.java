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

import static org.junit.Assert.assertNotNull;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.acumos.openstack.client.transport.DeploymentBean;
import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.DataBrokerBean;
import org.acumos.openstack.client.util.ParseJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJsonTest {
	
	private static Logger logger = LoggerFactory.getLogger(ParseJsonTest.class);
	@Test	
	public void jsonFileToObjectTest()throws Exception{
		logger.info("jsonFileToObjectTest Start");
		HashMap<String,String> imageMap=null;
		ParseJSON parse=new ParseJSON();
		Blueprint blueprint=null;
		ObjectMapper mapper = new ObjectMapper();
		DataBrokerBean dataBrokerbean=parse.getDataBrokerContainer(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		blueprint=parse.jsonFileToObject(OpenStackTestConstants.TEST_BLUEPRINT_FILE,dataBrokerbean);
		String blueprintJson=mapper.writeValueAsString(blueprint); 
		logger.debug("blueprintJson "+blueprintJson);
		assertNotNull(blueprint);
		logger.info("jsonFileToObjectTest End");
	}
	@Test	
	public void getSequenceFromJSONTest()throws Exception{
		logger.info("getSequenceFromJSONTest Start");
		ParseJSON parse=new ParseJSON();
		LinkedList<String> linkedList=null;
		linkedList=parse.getSequenceFromJSON(OpenStackTestConstants.TEST_BLUEPRINT_OLD_FILE);
		logger.info("getSequenceFromJSONTest linkedList "+linkedList);
		assertNotNull(linkedList);
		logger.info("getSequenceFromJSONTest End");
	}
	
	@Test	
	public void jsonFileToObjectProbeTest()throws Exception{
		logger.info("jsonFileToObjectProbeTest Start");
		HashMap<String,String> imageMap=null;
		ParseJSON parse=new ParseJSON();
		ObjectMapper mapper = new ObjectMapper();
		Blueprint blueprint=null;
		DataBrokerBean dataBrokerbean=parse.getDataBrokerContainer(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		blueprint=parse.jsonFileToObjectProbe(OpenStackTestConstants.TEST_BLUEPRINT_FILE,dataBrokerbean);
		String blueprintJson=mapper.writeValueAsString(blueprint); 
		logger.debug("blueprintJson "+blueprintJson);
		assertNotNull(blueprint);
		logger.info("jsonFileToObjectProbeTest End");
	}
	
	@Test
	public void parseJsonFileImageMapTest()throws Exception{
		logger.info("parseJsonFileImageMapTest Start");
		HashMap<String,String> imageMap=null;
		ParseJSON parse=new ParseJSON();
		imageMap=parse.parseJsonFileImageMap(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		assertNotNull(imageMap);
		logger.info("parseJsonFileImageMapTest End");
	}
	@Test	
	public void getSequenceListFromJSONTest()throws Exception{
		logger.info("getSequenceListFromJSONTest Start");
		HashMap<String,String> imageMap=null;
		ParseJSON parse=new ParseJSON();
		LinkedList<String> linkedList=null;
		linkedList=parse.getSequenceListFromJSON(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		logger.info("getSequenceListFromJSONTest linkedList "+linkedList);
		assertNotNull(linkedList);
		logger.info("getSequenceListFromJSONTest End");
	}
	
	@Test
	public void nodeTypeContainerMapTest()throws Exception{
		logger.info("nodeTypeContainerMapTest Start");
		HashMap<String,DeploymentBean> nodeTypeContainerMap=null;
		ParseJSON parse=new ParseJSON();
		nodeTypeContainerMap=parse.getNodeTypeContainerMap(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		assertNotNull(nodeTypeContainerMap);
		logger.info("nodeTypeContainerMapTest End");
	}
	
	@Test
	public void getDataBrokerContainerTest()throws Exception{
		logger.info("nodeTypeContainerMapTest Start");
		DataBrokerBean	dataBrokerbean=null;	
		ParseJSON parse=new ParseJSON();
		ObjectMapper mapper = new ObjectMapper();
		dataBrokerbean=parse.getDataBrokerContainer(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		String dataBrokerbeanjson=mapper.writeValueAsString(dataBrokerbean); 
		logger.info("dataBrokerbeanjson "+dataBrokerbeanjson);
		assertNotNull(dataBrokerbean);
		logger.info("nodeTypeContainerMapTest End");
	}
	@Test
	public void checkProbeIndicatorTest()throws Exception{
		logger.info("nodeTypeContainerMapTest Start");
		boolean probeIdicator=false;
		ParseJSON parse=new ParseJSON();
		 probeIdicator=parse.checkProbeIndicator(OpenStackTestConstants.TEST_BLUEPRINT_FILE);
		assertNotNull(probeIdicator);
		logger.info("nodeTypeContainerMapTest End");
	}
}
