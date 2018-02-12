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
package org.acumos.openstack.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acumos.openstack.client.api.APINames;
import org.acumos.openstack.client.service.impl.OpenstackSimpleSolution;
import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.json.JSONObject;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Keypair;
import org.openstack4j.model.compute.SecGroupExtension;
import org.openstack4j.model.compute.SecurityGroup;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.network.NetFloatingIP;
import org.openstack4j.openstack.OSFactory;
import com.jcraft.jsch.JSchException;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.identity.v3.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OpenstackServiceController extends AbstractController {
	
	Logger logger = LoggerFactory.getLogger(OpenstackServiceController.class);
	/*@RequestMapping(value ="/getUIDDetails",  method = RequestMethod.GET, produces=APPLICATION_JSON)
	@ResponseBody
	public String getUIDDetails() {
		JSONObject  jsonOutput = new JSONObject();
		System.out.println("=======getUIDDetails=========");
		try{
			jsonOutput.put("Status", "Succeess");
		}catch(Exception e){
			e.printStackTrace();
		}
        return "Hello bbbbbbb!";
    }*/
	
	@RequestMapping(value = APINames.OPENSTACK_AUTH_PUSH_SINGLE_IMAGE, method = RequestMethod.POST, produces = APPLICATION_JSON)
	@ResponseBody
	public String singleImageOpenstackDeployment(HttpServletRequest request,@RequestBody OpenstackDeployBean auth,HttpServletResponse response) throws Exception {
		//System.out.println("=======singleImageOpenstackDeployment=========");
		//logger.debug("=======singleImageOpenstackDeployment===1======");
		//logger.info("=======singleImageOpenstackDeployment===2======");
		String uidNumStr="";
		OSClientV3 os = null;
		String flavourName="";
		String securityGropName="";
		JSONObject  jsonOutput = new JSONObject();
		try{
			flavourName="e6esmall";
			securityGropName="E6E-Access";
			
			UUID uidNumber = UUID.randomUUID();
			//System.out.println("=======singleImageOpenstackDeployment===2======"+uidNumber);
			os = OSFactory.builderV3().endpoint(auth.getIdentityEndpoint())
					.credentials(auth.getUserName(), auth.getPassword(), Identifier.byName(auth.getIdentifierName()))
					.scopeToProject(Identifier.byId(auth.getProjectScopeId())).authenticate();
			//logger.debug("<--------os is created in openstack----------->");
		
		 uidNumStr=uidNumber.toString();
		 jsonOutput.put("Status", uidNumStr);
		 OpenstackSimpleSolution opSingleSolution=new OpenstackSimpleSolution(os,flavourName,securityGropName,auth);
		 Thread t = new Thread(opSingleSolution);
         t.start();
		 
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		//logger.debug("<------start----singleImageOpenstackDeployment------------>");
		System.out.println("=======singleImageOpenstackDeployment===jsonOutput.toString()===="+jsonOutput.toString());
		return jsonOutput.toString();
	}
}
