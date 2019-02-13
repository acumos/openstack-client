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

import org.acumos.openstack.client.test.transport.CompositeDeployTest;
import org.acumos.openstack.client.transport.TransportBean;
import org.acumos.openstack.client.util.LoggerUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtilTest {
	
	Logger logger = LoggerFactory.getLogger(CompositeDeployTest.class);
	@Test	
	public void existingVMDeploymentPrintTest(){
		logger.debug("Start existingVMDeploymentPrintTest");
		String result=null;
		LoggerUtil loggerutil=new LoggerUtil();
		TransportBean tbean=new TransportBean();
		result=loggerutil.printExistingVMDeployment(tbean);
		assertNotNull(result);
		logger.debug("end existingVMDeploymentPrintTest");
	}
	
	@Test	
	public void printsingleImageDetailsTest(){
		logger.debug("Start printsingleImageDetailsTest");
		String result=null;
		LoggerUtil loggerutil=new LoggerUtil();
		result=loggerutil.printsingleImageDetails(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ);
		assertNotNull(result);
		logger.debug("end printsingleImageDetailsTest");
	}
	@Test	
	public void printSingleImageImplDetailsTest(){
		logger.debug("Start printSingleImageImplDetails");
		String result=null;
		LoggerUtil loggerutil=new LoggerUtil();
		result=loggerutil.printSingleImageImplDetails(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ);
		assertNotNull(result);
		logger.debug("end printSingleImageImplDetails");
	}
	
	@Test	
	public void printCompositeDetailsTest(){
		logger.debug("Start printCompositeDetailsTest");
		String result=null;
		LoggerUtil loggerutil=new LoggerUtil();
		result=loggerutil.printCompositeDetails(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ);
		assertNotNull(result);
		logger.debug("end printCompositeDetailsTest");
	}
	
	@Test	
	public void printCompositeImplDetailsTest(){
		logger.debug("Start printCompositeDetailsTest");
		String result=null;
		LoggerUtil loggerutil=new LoggerUtil();
		result=loggerutil.printCompositeImplDetails(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ,
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ, 
				OpenStackTestConstants.TEST_OBJ,OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ);
		assertNotNull(result);
		logger.debug("end printCompositeImplDetailsTest");
	}

}
