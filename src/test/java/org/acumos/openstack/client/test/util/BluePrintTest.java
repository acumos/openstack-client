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

import java.util.ArrayList;
import java.util.List;

import org.acumos.openstack.client.util.Blueprint;
import org.acumos.openstack.client.util.OperationSignature;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.pattern.parser.Node;

public class BluePrintTest {
	
	private static Logger logger = LoggerFactory.getLogger(BluePrintTest.class);
	@Test	
	public void BluePrintTestObjectparameter(){
		   logger.debug("Start BluePrintTestObjectparameter");
			String name = "bluePrint";
			String version = "1";
			
	        List<OperationSignature> inputs=(List)new ArrayList<OperationSignature>();
			List<Node> nodes=(List)new ArrayList<Node>();
			
			Blueprint blueprint=new Blueprint();
			blueprint.setName(name);
			blueprint.setVersion(version);
			
			Assert.assertEquals(name, blueprint.getName());
			Assert.assertEquals(version, blueprint.getVersion());
		    logger.debug("End BluePrintTestObjectparameter");
	}

}
