package org.acumos.openstack.client.service.impl;

import java.io.ByteArrayInputStream;


import org.acumos.openstack.client.transport.TransportBean;
import org.acumos.openstack.client.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExistingVMSolution implements Runnable {
	public TransportBean tbean;
	
	public ExistingVMSolution(TransportBean tbean)
	{
		this.tbean=tbean;
	}
	Logger logger = LoggerFactory.getLogger(ExistingVMSolution.class);
	public void run() {
		CommonUtil commonUtil=new CommonUtil();
		int sshBindNumber=0;
		try {
			String solutionToolKitType=commonUtil.getSolutionCode(tbean.getSolutionId(), 
					tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
			logger.debug("solutionToolKitType "+solutionToolKitType);
			byte[] bytesArray=commonUtil.readBytesFromFile(tbean.getKeyName());
			String sshBindStr=commonUtil.getsshComandoutput(tbean.getVmHostIP(),tbean.getHostUserName(), bytesArray,
					tbean.getOpenStackIP());
			logger.debug("sshBindStr "+sshBindStr);
			if(sshBindStr!=null && !"".equals(sshBindStr)) {
				sshBindNumber=Integer.parseInt(sshBindStr);
				logger.debug("sshBindNumber "+sshBindNumber);
				String checkSoftware=commonUtil.checkPrerequisites(tbean.getOpenStackIP(), sshBindNumber, tbean.getVmUserName(), bytesArray);
				logger.debug("checkSoftware "+checkSoftware);
				if(checkSoftware!=null && "success".equalsIgnoreCase(checkSoftware)) {
					String removeContainer=commonUtil.removeAllContainer(tbean.getVmHostIP(), sshBindNumber, tbean.getVmUserName(), bytesArray);
					if(removeContainer!=null && "success".equalsIgnoreCase(removeContainer) ) {
						if(solutionToolKitType!=null && !"".equals(solutionToolKitType) && "CP".equalsIgnoreCase(solutionToolKitType)){
					   		logger.debug("Composite Solution Details Start");
					   		compositeSolutionDetails(tbean,commonUtil);
					   		logger.debug("Composite Solution Details End");
					   	 }else{
					   		logger.debug("Single Solution Details Start");
					   		singleSolutionDetails(tbean,commonUtil,sshBindNumber,bytesArray);
					   		logger.debug("Single Solution Details End");
					   	 }
					}
				}
			}
		}catch(Exception e) {
			
		}
	}
	public void singleSolutionDetails(TransportBean tbean,CommonUtil commonUtil,int sshBindNumber,byte[] bytesArray)throws Exception{
		logger.debug("singleSolutionDetails Start");
		String repositaryName="";
		String repositryImageName="";
		String deploymentImageVM="";
		String ImageTag=commonUtil.getSingleImageData(tbean.getSolutionId(), tbean.getSolutionRevisionId(), 
				tbean.getDataSource(),tbean.getCmndatasvcuser(),tbean.getCmndatasvcpd());
		logger.debug("ImageTag "+ImageTag);
		repositaryName=commonUtil.getRepositryName(ImageTag,tbean.getRepositoryNames());
		repositryImageName=commonUtil.getRepositryImageName(ImageTag,tbean.getRepositoryNames());
		commonUtil.deploymentSingleImageVM(tbean.getHostOpenStack(),tbean.getVmUserName(),repositaryName,
				tbean.getDockerUserName(),tbean.getDockerPd(),repositryImageName,sshBindNumber,bytesArray);
		logger.debug("singleSolutionDetails End");
	}
    public void compositeSolutionDetails(TransportBean tbean,CommonUtil commonUtil)throws Exception{
    	logger.debug("compositeSolutionDetails Start");
    	logger.debug("compositeSolutionDetails End");
	}

}
