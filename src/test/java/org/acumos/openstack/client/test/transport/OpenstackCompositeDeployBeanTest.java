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

import org.acumos.openstack.client.transport.OpenstackCompositeDeployBean;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenstackCompositeDeployBeanTest {
	Logger logger = LoggerFactory.getLogger(OpenstackCompositeDeployBeanTest.class);
	@Test	
	public void openstackCompositeDeployBeanTestTestparameter(){
		 logger.debug("Start openstackCompositeDeployBeanTestTestparameter");
		 OpenstackCompositeDeployBean obean=new OpenstackCompositeDeployBean();
		 String vmName="Gold24";
		 String solutionId="1566ec6f-cdc7-4ba9-8fb6-3124ab9e17c1";
		 String solutionRevisionId="05cac760-cd53-49a3-85f3-df908f14211d";
		 String userId="7cd47ca4-1c5d-4cdc-909c-f7c17367b4d4";
		 String imagetag="Adder1:1";
		 obean.setVmName("Gold24");
		 obean.setSolutionId("1566ec6f-cdc7-4ba9-8fb6-3124ab9e17c1");
		 obean.setSolutionRevisionId("05cac760-cd53-49a3-85f3-df908f14211d");
		 obean.setUserId("7cd47ca4-1c5d-4cdc-909c-f7c17367b4d4");
		 Assert.assertEquals(vmName, obean.getVmName());
		 Assert.assertEquals(solutionId, obean.getSolutionId());
		 Assert.assertEquals(solutionRevisionId, obean.getSolutionRevisionId());
		 Assert.assertEquals(userId, obean.getUserId());
		 logger.debug("End openstackCompositeDeployBeanTestTestparameter");
		
	}

}
