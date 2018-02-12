package org.acumos.openstack.client.test.transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acumos.openstack.client.transport.OpenstackDeployBean;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenstackDeployBeanTest {

	@Test	
	public void testAzureDeployBeanTestparameter(){
		OpenstackDeployBean obean=new OpenstackDeployBean();
		obean.setIdentifierName("http://10.1.0.100/identity/v3");
		obean.setIdentityEndpoint("http://10.1.0.100/identity/v3");
		obean.setKeyName("e6e");
		obean.setPassword("pass");
		obean.setProjectScopeId("7badda19df524dd58c2fe249fd02e7f6");
		obean.setUserName("e6euser");
		obean.setVmName("Gold70");
		
		
		
		
		String identifireName="http://10.1.0.100/identity/v3";
		String identityEndpoint="http://10.1.0.100/identity/v3";
		String userName="e6euser";
		String password="pass";
		String projectScopeId="7badda19df524dd58c2fe249fd02e7f6";
		 String keyName="e6e";
		 String vmName="Gold70";
		
		Assert.assertEquals(identifireName, obean.getIdentifierName());
		Assert.assertEquals(identityEndpoint, obean.getIdentityEndpoint());
		Assert.assertEquals(userName, obean.getUserName());
		Assert.assertEquals(password, obean.getPassword());
		Assert.assertEquals(projectScopeId, obean.getProjectScopeId());
		Assert.assertEquals(keyName, obean.getKeyName());
		Assert.assertEquals(vmName, obean.getVmName());
		
	}
}
