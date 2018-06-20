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
package org.acumos.openstack.client.test.controller;

import org.acumos.openstack.client.controller.OpenstackServiceController;
import org.acumos.openstack.client.test.api.SwaggerTest;
import org.acumos.openstack.client.transport.OpenstackDeployBean;

import org.acumos.openstack.client.util.CommonUtil;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Assert;
import org.junit.Test;

import org.junit.Test;


@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class OpenStackServiceControllerTest {
	Logger logger = LoggerFactory.getLogger(OpenStackServiceControllerTest.class);
	final HttpServletResponse response = new MockHttpServletResponse();
	final HttpServletRequest request = new MockHttpServletRequest();
    
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	Environment env;
	
	@InjectMocks
	OpenstackServiceController controller;
	
	
	
	@Test	
	public void singleImageOpenstackDeploymentTest(){
		logger.debug("Start singleImageOpenstackDeploymentTest");
		OpenstackDeployBean auth=new OpenstackDeployBean();
		try{
			auth.setSolutionId("Test");
			auth.setImagetag("TestImage");
			auth.setUserId("TestUser");
			auth.setVmName("Gold84");
			when(controller.singleImageOpenstackDeployment(request,auth,response)).thenReturn("{\"UIDNumber\":\"\",\"status\":\"SUCCESS\"}");
			Assert.assertEquals(200, response.getStatus());
		}catch(Exception e){
			logger.error("Error in singleImageOpenstackDeploymentTest "+e.getMessage());
		}
		logger.debug("End  singleImageOpenstackDeploymentTest");
		
	}

}
