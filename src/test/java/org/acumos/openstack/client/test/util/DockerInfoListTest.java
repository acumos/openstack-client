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

import org.acumos.openstack.client.util.DockerInfo;
import org.acumos.openstack.client.util.DockerInfoList;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerInfoListTest {
	
	private static Logger logger = LoggerFactory.getLogger(DockerInfoListTest.class);
	
	@Test	
	public void dockerinfoListTestparameter(){
		    logger.info("Start dockerinfoListTestparameter ");
			String container = "Adder1";
	        String ipAddress ="10.21.13.63";
	        String port = "8557";
	        DockerInfoList infolist=new DockerInfoList();
	        DockerInfo dockerInfo=new DockerInfo();
	        dockerInfo.setContainer(container);
	        dockerInfo.setIpAddress(ipAddress);
	        dockerInfo.setPort(port);
	        List<DockerInfo> list=new ArrayList<DockerInfo>();
	        list.add(dockerInfo);
	        infolist.setDockerList(list);
	        logger.info("infolist "+infolist);
	        Assert.assertEquals(list, infolist.getDockerList());
	        Assert.assertEquals(dockerInfo, infolist.findDockerInfoByContainer("Adder1"));
		    logger.debug("End dockerinfoListTestparameter ");

	}

	
}
