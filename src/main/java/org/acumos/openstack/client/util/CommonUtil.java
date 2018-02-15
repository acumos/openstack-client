package org.acumos.openstack.client.util;

import org.acumos.openstack.client.service.impl.OpenstackSimpleSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {
	
Logger logger = LoggerFactory.getLogger(CommonUtil.class);

  public String getRepositryName(String imageName){
	logger.debug("<----Start-getRepositryName------->"+imageName);
	String repositaryName="";
	if(imageName!=null){
		String imageArr[]=imageName.split("/");
		if(imageArr!=null && imageArr[0]!=null){
			if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8000")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8000";
			}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8001")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8001";
			}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8002")){
				repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8002";
			}else{
				repositaryName=imageArr[0];
			}
			
		}
		
	}
	logger.debug("<----End-repositaryName------->"+repositaryName);
	
	return repositaryName;
  }
  
  public String getRepositryImageName(String imageName){
		logger.debug("<----Start-getRepositryName------->"+imageName);
		String repositaryName="";
		String repositaryImageName="";
		String ImageName="";
		if(imageName!=null){
			String imageArr[]=imageName.split("/");
			if(imageArr!=null && imageArr[0]!=null){
				if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8000")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8000";
				}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8001")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8001";
				}else if(imageArr[0].equalsIgnoreCase("cognita-nexus01:8002")){
					repositaryName="cognita-nexus01.eastus.cloudapp.azure.com:8002";
				}else{
					repositaryName=imageArr[0];
				}
				
			}
			if(imageArr[1]!=null){
				ImageName=imageArr[1];
			}
			logger.debug("<-----repositaryName------->"+repositaryName+"--ImageName----"+ImageName);	
			
		}
		if(repositaryName!=null && ImageName!=null){
			repositaryImageName=repositaryName+"/"+ImageName;
		}
		logger.debug("<--End-getRepositryImageName--repositaryImageName------->"+repositaryImageName);
		
		return repositaryImageName;
	  }

}
