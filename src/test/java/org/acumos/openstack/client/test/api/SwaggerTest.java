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
package org.acumos.openstack.client.test.api;

import static org.junit.Assert.assertNotNull;

import org.acumos.openstack.client.SwaggerConfiguration;
import org.acumos.openstack.client.service.impl.OpenstackSimpleSolution;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwaggerTest {
	Logger logger = LoggerFactory.getLogger(SwaggerTest.class);
	@Test	
	public void swaggerDeploymentAPITest(){
		logger.debug("Start swaggerDeploymentAPITest");
		SwaggerConfiguration sw=new SwaggerConfiguration(); 
		assertNotNull(sw.api());
		logger.debug(" swaggerDeploymentAPITest End");
	}
	@Test	
	public void swaggerDeploymentApiInfoTest(){
		logger.debug("swaggerDeploymentApiInfoTest");
		SwaggerConfiguration sw=new SwaggerConfiguration(); 
		assertNotNull(sw.apiInfo());
		logger.debug("End swaggerDeploymentApiInfoTest ");
	}

}
