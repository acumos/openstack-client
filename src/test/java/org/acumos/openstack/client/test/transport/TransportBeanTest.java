package org.acumos.openstack.client.test.transport;

import java.util.HashMap;
import java.util.Map;

import org.acumos.openstack.client.test.util.OpenStackTestConstants;
import org.acumos.openstack.client.transport.TransportBean;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportBeanTest {

	Logger logger = LoggerFactory.getLogger(CompositeDeployTest.class);
	@Test	
	public void transportBeanTestTestparameter(){
		logger.debug("Start transportBeanTestTestparameter");
		TransportBean tbean=new TransportBean();
		
		 tbean.setUidNumStr(OpenStackTestConstants.TEST_OBJ);
		 tbean.setEndpoint(OpenStackTestConstants.TEST_OBJ);
		 tbean.setUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setKey(OpenStackTestConstants.TEST_OBJ);
		 tbean.setKeyName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setVmRegisterNumber(OpenStackTestConstants.TEST_OBJ);
		 tbean.setHostOpenStack(OpenStackTestConstants.TEST_OBJ);
		 tbean.setHostUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setVmUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDockerUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDockerPd(OpenStackTestConstants.TEST_OBJ);
		 tbean.setBluePrintImage(OpenStackTestConstants.TEST_OBJ);
		 tbean.setBluePrintName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataSource(OpenStackTestConstants.TEST_OBJ);
		 tbean.setCmndatasvcuser(OpenStackTestConstants.TEST_OBJ);
		 tbean.setCmndatasvcpd(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNexusUrl(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNexusUserName(OpenStackTestConstants.TEST_OBJ);	
		 tbean.setNexusPd(OpenStackTestConstants.TEST_OBJ);
		 tbean.setSolutionPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setSleeptime(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProxyIP(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProxyPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setOpenStackIP(OpenStackTestConstants.TEST_OBJ);
		 tbean.setBluePrintPortNumber(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbePrintImage(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbePrintName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbUser(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbePass(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbeNexusEndPoint(OpenStackTestConstants.TEST_OBJ);
		 tbean.setProbeInternalPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setRepositoryNames(OpenStackTestConstants.TEST_OBJ);
		 tbean.setExposeDataBrokerPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setInternalDataBrokerPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNexusRegistyName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNexusRegistyUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNexusRegistyPd(OpenStackTestConstants.TEST_OBJ);
		 tbean.setRepositoryDetails(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNginxMapFolder(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNginxWebFolder(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNginxImageName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setNginxInternalPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setAzureDataFiles(OpenStackTestConstants.TEST_OBJ);
		 tbean.setSolutionId(OpenStackTestConstants.TEST_OBJ);
		 tbean.setSolutionRevisionId(OpenStackTestConstants.TEST_OBJ);
		 tbean.setVmHostName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setVmHostIP(OpenStackTestConstants.TEST_OBJ);
		 tbean.setUserId(OpenStackTestConstants.TEST_OBJ);
		 tbean.setUrlAttribute(OpenStackTestConstants.TEST_OBJ);
		 tbean.setJsonMapping(OpenStackTestConstants.TEST_OBJ);
		 tbean.setJsonPosition(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerHost(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerHost(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerUserPd(OpenStackTestConstants.TEST_OBJ);
		 tbean.setJsonFileName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerHost(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setDataBrokerUserPd(OpenStackTestConstants.TEST_OBJ);
		 
		 tbean.setDataBrokerPort(OpenStackTestConstants.TEST_OBJ);
		 tbean.setVmHostIP(OpenStackTestConstants.TEST_OBJ);
		 tbean.setScopeProject(OpenStackTestConstants.TEST_OBJ);
		 tbean.setIdentifierName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setBluePrintUserName(OpenStackTestConstants.TEST_OBJ);
		 tbean.setBluePrintPd(OpenStackTestConstants.TEST_OBJ);
		 Map<String,String> protoContainerMap=new HashMap<String,String>();
		 protoContainerMap.put(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ);
		 Map<String,String> protoMap=new HashMap<String,String>();
		 protoMap.put(OpenStackTestConstants.TEST_OBJ, OpenStackTestConstants.TEST_OBJ);
		 tbean.setProtoContainerMap(protoContainerMap);
		 tbean.setProtoMap(protoMap);
		 
		 
		 
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getUidNumStr());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getEndpoint());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getKey());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getKeyName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getVmRegisterNumber());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getHostOpenStack());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getHostUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getVmUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDockerUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDockerPd());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getBluePrintImage());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getBluePrintName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDataSource());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getCmndatasvcuser());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getCmndatasvcpd());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusUrl());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusPd());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getSolutionPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getSleeptime());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProxyIP());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProxyPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getOpenStackIP());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getBluePrintPortNumber());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProbePrintImage());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProbUser());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProbePass());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProbeNexusEndPoint());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getProbeInternalPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getRepositoryNames());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getExposeDataBrokerPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getInternalDataBrokerPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusRegistyName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusRegistyUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNexusRegistyPd());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getRepositoryDetails());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNginxMapFolder());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNginxWebFolder());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNginxImageName());
		 
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getNginxInternalPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getAzureDataFiles());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getSolutionId());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getSolutionRevisionId());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getVmHostName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getUserId());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getUrlAttribute());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getJsonMapping());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getJsonPosition());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getJsonFileName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDataBrokerHost());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDataBrokerUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDataBrokerUserPd());
		 
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getDataBrokerPort());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getVmHostIP());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getScopeProject());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getIdentifierName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getBluePrintUserName());
		 Assert.assertEquals(OpenStackTestConstants.TEST_OBJ, tbean.getBluePrintPd());
		 Assert.assertEquals(protoContainerMap, tbean.getProtoContainerMap());
		 Assert.assertEquals(protoMap, protoMap);
		 
		 
		 
		
		logger.debug("end transportBeanTestTestparameter");
		
	}
}
