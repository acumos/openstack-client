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

import java.util.HashMap;
import java.util.LinkedList;

import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.ParseJSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseJsonTest {
	
	private static Logger logger = LoggerFactory.getLogger(ParseJsonTest.class);
	@Test	
	public void parseJsonFileTest(){
		logger.info("<---------Start-------parseJsonFileTest-------------->");
		try{
			HashMap<String,String> imageMap=null;
			ParseJSON parse=new ParseJSON();
			imageMap=parse.parseJsonFile();
			assertNotNull(imageMap);
		}catch(Exception e){
			logger.error("Exception in parseJsonFileTest-->"+e.getMessage());
		}
		logger.info("<---------End-------parseJsonFileTest-------------->");
	}
   
	@Test	
	public void jsonFileToObjectTest(){
		logger.info("<---------Start-------jsonFileToObjectTest-------------->");
		try{
			HashMap<String,String> imageMap=null;
			ParseJSON parse=new ParseJSON();
			Blueprint blueprint=null;
			blueprint=parse.jsonFileToObject();
			assertNotNull(blueprint);
		}catch(Exception e){
			logger.error("Exception in jsonFileToObjectTest--->"+e.getMessage());
		}
		logger.info("<---------End-------jsonFileToObjectTest-------------->");
	}
	
	@Test	
	public void getSequenceFromJSONTest(){
		logger.info("<---------Start-------getSequenceFromJSONTest-------------->");
		try{
			HashMap<String,String> imageMap=null;
			ParseJSON parse=new ParseJSON();
			LinkedList<String> linkedList=null;
			linkedList=parse.getSequenceFromJSON();
			assertNotNull(linkedList);
		}catch(Exception e){
			logger.debug("Exception in jsonFileToObjectTest"+e.getMessage());
		}
		logger.info("<---------End-------getSequenceFromJSONTest-------------->");
	}
	
	
}