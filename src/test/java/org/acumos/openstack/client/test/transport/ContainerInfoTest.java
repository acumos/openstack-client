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
package org.acumos.openstack.client.test.transport;

import org.acumos.openstack.client.test.util.OpenStackTestConstants;
import org.acumos.openstack.client.transport.ContainerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerInfoTest {
	
	Logger logger = LoggerFactory.getLogger(ContainerInfoTest.class);
	@Test	
	public void ContainerInfoTestTestparameter(){
		
		ContainerInfo info=new ContainerInfo();
		info.setContainerIp(OpenStackTestConstants.TEST_OBJ);
		info.setContainerName(OpenStackTestConstants.TEST_OBJ);
		info.setContainerPort(OpenStackTestConstants.TEST_OBJ);
		info.setNodeType(OpenStackTestConstants.TEST_OBJ);
		
		Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, info.getContainerIp());
		Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, info.getContainerName());
		Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, info.getContainerPort());
		Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, info.getNodeType());
		
	}

}
