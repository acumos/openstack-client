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

package org.acumos.openstack.client.util;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.acumos.openstack.client.transport.DeploymentBean;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

//import org.yaml.snakeyaml.Yaml;

public class ParseJSON {
	
	Logger log =LoggerFactory.getLogger(ParseJSON.class);

	public  Blueprint jsonFileToObject(String jsonFileName,DataBrokerBean dataBrokerBean)throws  Exception{
		log.debug(" jsonFileToObject Start");
		log.debug("jsonFileName "+jsonFileName);
		ArrayList<String> list=new ArrayList<String>();	
		Blueprint blueprint=new Blueprint();
		ObjectMapper mapper = new ObjectMapper();
		try
		{
		Object obj = new JSONParser().parse(new FileReader(jsonFileName));
		
        JSONObject jo = (JSONObject) obj;
        String prettyJSONString = jo.toString();
        String name = (String) jo.get(OpenStackConstants.NAME);
        String version = (String) jo.get(OpenStackConstants.VERSION);
        blueprint.setName(name);
        blueprint.setVersion(version);
        Iterator<Map.Entry> itr1=null;
        
        ArrayList<ProbeIndicator> list_of_pb_indicators = new ArrayList<ProbeIndicator>();
		ProbeIndicator prbIndicator = new ProbeIndicator();
		prbIndicator.setValue("false");
		list_of_pb_indicators.add(prbIndicator);
		blueprint.setProbeIndicator(list_of_pb_indicators);
       
        JSONArray inputPorts = (JSONArray) jo.get(OpenStackConstants.INPUT_PORTS);
		ArrayList<OperationSignature> operationList = new ArrayList<OperationSignature>();
		List<InputPort> inputPortList=null;
		if (inputPorts != null) {
			inputPortList=new ArrayList<InputPort>();
			log.debug("input ports");
			
			Iterator itr2 = inputPorts.iterator();
			while (itr2.hasNext()) {
				InputPort inputPortObj=new InputPort();
				OperationSignature operationSignature = new OperationSignature();
				itr1 = ((Map) itr2.next()).entrySet().iterator();
				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();
					String key = (String) pair.getKey();
					log.debug("pair key " + pair.getKey() + " : " + pair.getValue());
					if (key != null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE)) {
						JSONObject jsonObject = (JSONObject) pair.getValue();
						String operationName =(String) jsonObject.get(OpenStackConstants.OPERATION_NAME);
						operationSignature.setOperationName(operationName);
						inputPortObj.setOperationSignature(operationSignature);
					}
					if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
						inputPortObj.setContainerName((String) pair.getValue());
					}
					
				}
				log.debug("inputPortObj "+inputPortObj);
				inputPortList.add(inputPortObj);
			}
			
		}
		if(inputPorts!=null && inputPorts.size() > 0){
			blueprint.setInputPorts(inputPortList);
		}
        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
        ArrayList<Node> nodeList=new ArrayList<Node>();
        if (nodes != null) {
			Iterator itr3 = nodes.iterator();
			int nodeCount = 0;
			while (itr3.hasNext()) {
				Node node = new Node();
				ArrayList<OperationSignatureList> operSigList = new ArrayList<OperationSignatureList>();
				OperationSignatureList obpListObject=new OperationSignatureList();
				operSigList.add(obpListObject);
				itr1 = ((Map) itr3.next()).entrySet().iterator();
				log.debug("Nodes " + ++nodeCount);
				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();
				   if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
						String key = (String) pair.getKey();
						if(key != null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE_LIST)) {
							
							if (pair.getValue() != null) {
								operSigList = jsonArrayParseObjectProb(pair.getValue(),operSigList);
								
								log.debug("operSigList " + operSigList);
							}
							
						
					  }else {
							log.debug(" key " + pair.getKey() + " : " + pair.getValue());
							if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
								node.setContainerName((String) pair.getValue());
							}
							if (key != null && key.equalsIgnoreCase(OpenStackConstants.IMAGE)) {
								node.setImage((String) pair.getValue());
							}
							
							if(key != null && key.equalsIgnoreCase(OpenStackConstants.NODE_TYPE)) {
								node.setNodeType((String)pair.getValue());
							}
							if(key != null && key.equalsIgnoreCase(OpenStackConstants.PROTO_URI)) {
								node.setProtoUri((String)pair.getValue());
							}
							JSONObject dataBrokerObject=null;
							if(key != null && key.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_MAP)){
		                    	 dataBrokerObject = (JSONObject)pair.getValue();
		                    	 if(dataBrokerObject!=null  ){
		                    		 if(dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE)!=null){
		                    			 String dataBrokerType=(String)dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE);
		                    			 log.debug("dataBrokerType "+dataBrokerType);
		                    			 if(dataBrokerType!=null && dataBrokerType.equalsIgnoreCase(OpenStackConstants.CSV_FILE_NAME)){
		                    				 if(dataBrokerBean!=null){
		                    					 node.setDataBrokerMap(dataBrokerBean.getDataBrokerMap()); 
		                    				 }
		                    			 }
		                    			 
		                    		 }
		                    	 } 
							}
							
						}
				 }	
				}
				node.setOperationSignatureList(operSigList);
				nodeList.add(node);
			}

		}
        blueprint.setNodes(nodeList);
        log.debug("blueprint "+ mapper.writeValueAsString(blueprint)+" "+blueprint.toString());
		}catch(Exception e){
			 log.error("jsonFileToObject failed", e);
			throw e;
       }
		log.debug("list "+list);
		log.debug("jsonFileToObject End");
		return blueprint;	
	}

	/**
	 * This method returns probe indicator
	 * @param jo
	 * @return
	 */
	private String getProbeIndicator(JSONObject jo) {
		log.debug("getProbeIndicator Start");
		String value = null;
		JSONArray probeIndicator = (JSONArray) jo.get(OpenStackConstants.PROBE_INDICATOR);
		if(probeIndicator == null){
			probeIndicator = (JSONArray) jo.get(OpenStackConstants.PROBE_INDOCATOR);
		}
		if(probeIndicator!=null){
			Iterator itr = probeIndicator.iterator();
			Iterator<Map.Entry> itr1=null;
			
			 while (itr.hasNext()) {
				 itr1 = ((Map) itr.next()).entrySet().iterator();
		            while (itr1.hasNext()) {
		                Map.Entry pair = itr1.next();
		                value = (String)pair.getValue();
		            }
			 }
		} 
	   log.debug("value "+value);	 
	   log.debug("getProbeIndicator End");
	  return value;	
	}


	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public ArrayList<OperationSignatureList>  jsonArrayParseObjectProb(Object obj, ArrayList<OperationSignatureList> listComponent) {
		log.debug("jsonArrayParseObjectProb Start");
		JSONArray jsonArr = (JSONArray) obj;
		Iterator itr = jsonArr.iterator();
		OperationSignatureList oprListObj=listComponent.get(0);
		ArrayList<ConnectedTo> connectedList=new ArrayList<ConnectedTo>();
		Iterator<Map.Entry> itr1 = null;
		Iterator<Map.Entry> itr3 = null;
		
		while (itr.hasNext()) {
			itr1 = ((Map) itr.next()).entrySet().iterator();
			
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				String key = (String) pair.getKey();
				log.debug("Key " + key);
				ConnectedTo connectedTo=null;
				if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONNECTED_TO)) {
					JSONArray connArr = (JSONArray) pair.getValue();
					Iterator conItr = connArr.iterator();
					connectedTo=new ConnectedTo();
					while (conItr.hasNext()) {

						itr3 = ((Map) conItr.next()).entrySet().iterator();
						while (itr3.hasNext()) {
							Map.Entry pair1 = itr3.next();
							String connectedKey = (String) pair1.getKey();
							if (connectedKey != null && connectedKey.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
								String containerKey = (String) pair1.getKey();
								String containerName = (String) pair1.getValue();
								connectedTo.setContainerName(containerName);
							}

							if (connectedKey != null && connectedKey.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE)) {
								JSONObject objVar = (JSONObject) pair1.getValue();
								OperationSignature opr = new OperationSignature();
								if (objVar != null) {
									String operation = (String) objVar.get(OpenStackConstants.OPERATION_NAME);
									opr.setOperationName(operation);
									log.debug("operation " + operation);
									connectedTo.setOperationSignature(opr);
								}
							}
						}
						
					}
					if(connectedTo!=null){
						connectedList.add(connectedTo);
					}
					
				}
                if(connectedList!=null && connectedList.size() > 0){
                	oprListObj.setConnectedTo(connectedList);
                }
                if (key != null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE)) {
                	JSONObject objVar = (JSONObject) pair.getValue();
					OperationSignature oprSignature = new OperationSignature();
					if (objVar != null) {
						String oprName = (String) objVar.get(OpenStackConstants.OPERATION_NAME);
						if(oprName!=null && !"".equals(oprName)){
							oprSignature.setOperationName(oprName);	
						}
						
						String inputMessage = (String) objVar.get(OpenStackConstants.INPUT_MESSAGE_NAME);
						if(inputMessage!=null && !"".equals(inputMessage)){
							oprSignature.setInputMessageName(inputMessage);
						}
						
						String outputMessage = (String) objVar.get(OpenStackConstants.OUTPUT_MESSAGE_NAME);
						if(outputMessage!=null && !"".equals(outputMessage)){
							oprSignature.setOutputMessageName(outputMessage);
						}
					}
					oprListObj.setOperationSignature(oprSignature);
                }
				

			}
			
			
			
		}
		
		
		log.debug("listComponent "+listComponent);
		log.debug("jsonArrayParseObjectProb End");
		return listComponent;
	}

	
public LinkedList<String> getSequenceFromJSON(String jsonFileName)throws  Exception{
	    log.debug(" getSequenceFromJSON Start");
	    log.debug("jsonFileName "+jsonFileName);
		String contentString="";
		HashMap<String,String> imageMap=new HashMap<String,String>();
		ArrayList<String> list=new ArrayList<String>();	
		LinkedList<String> linkedList=new LinkedList<String>();
		try
		{
			NodeTree<String> root = new NodeTree<String>(OpenStackConstants.BLUEPRINT_CONTAINER);
        Object obj = new JSONParser().parse(new FileReader(jsonFileName));
        JSONObject jo = (JSONObject) obj;
        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
        if(nodes!=null && !nodes.isEmpty()){
        	Iterator itr3 = nodes.iterator();
	        int nodeCount=0; 
	        while (itr3.hasNext()) 
	        {
	        	Iterator<Map.Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
	        	Iterator<Map.Entry> itr5 = itr4;
	        	String containerName="";
	        	String imageName="";
	        	
	        	String contName="test";
	        	NodeTree<String> testNode=new NodeTree<String>(contName);
	        	log.debug("Second while");
	            while (itr4.hasNext()) {
	                Map.Entry pair = itr4.next();
	                String key=(String)pair.getKey();
	                String val=(String)pair.getValue().toString();
	                if(key!=null && key.equalsIgnoreCase(OpenStackConstants.DEPENDS_ON)){
	                	sequenceJsonParse(pair.getValue(),testNode,root);
	                }if(key!=null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)){
	                	containerName=val;
	                	contName=val;
	                }else{
	                	log.debug("key "+pair.getKey() );
	                }
	                if(key!=null && key.equalsIgnoreCase(OpenStackConstants.IMAGE)){
	                	imageName=val;
	                	list.add(val);
	                 }
	                if(containerName!=null && imageName!=null && !"".equals(containerName) && !"".equals(imageName)){
	                	imageMap.put(imageName, containerName);
	                }
	                
	            }
	            testNode.setData(contName);
	            NodeTree<String> searchNode=findDataInTree(root, contName);
	            if(searchNode!=null){
	            	NodeTree<String> parent=searchNode.getParent();
	            	String parentData=parent.getData();
	            	if (parent != null) {
	    				int index = parent.getChildren().indexOf(searchNode);
	    				parent.getChildren().remove(searchNode);
	    			 }
	            	NodeTree<String> parrentNode=findDataInTree(root, parentData);
	            	if(parrentNode!=null){
	            		parrentNode.addChild(testNode);
	            	}
	            }else{
	            	root.addChild(testNode);
	            }
	        }
         }
        printTree(root, " ",linkedList);
        Collections.reverse(linkedList);
        
        
        }catch(Exception e){
        	log.error("getSequenceFromJSON failed", e);
    	    throw e;
       }
		log.debug("linkedList "+linkedList);
		log.debug("getSequenceFromJSON End");
		return linkedList;	
	}


 public  void sequenceJsonParse(Object obj,NodeTree<String> newNode,NodeTree<String> rootNode){
	log.debug(" sequenceJsonParse Start"); 
	JSONArray jsonArr = (JSONArray) obj;
	Iterator itr = jsonArr.iterator();
	Iterator<Map.Entry> itr1=null;
	 while (itr.hasNext()) {
		 itr1 = ((Map) itr.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                log.debug("key value "+pair.getKey() + " : " + pair.getValue());
                if(pair.getKey() !=null && pair.getKey().equals(OpenStackConstants.NAME) && pair.getValue()!=null){
                	String data=String.valueOf(pair.getValue());
                	NodeTree<String> subNode=new NodeTree<String>(data);
                	 NodeTree<String> searchNode=findDataInTree(rootNode, data);
                	 if(searchNode!=null){
     	            	NodeTree<String> parent=searchNode.getParent();
     	            	String parentData=parent.getData();
     	            	if (parent != null) {
     	    				parent.getChildren().remove(searchNode);
     	    				newNode.addChild(searchNode);
     	    			 }
     	            	
     	            }else{
     	            	newNode.addChild(subNode);
     	            }
                }
                log.debug("key value"+pair.getKey() + " : " + pair.getValue());
            }
     }
	 log.debug("End sequenceJsonParse "); 	 
}


	public void sequenceJsonParseProbe(Object obj, NodeTree<String> newNode, NodeTree<String> rootNode) {
		log.debug("sequenceJsonParseProbe Start"); 
		JSONArray jsonArr = (JSONArray) obj;
		Iterator itr = jsonArr.iterator();
		Iterator<Map.Entry> itr1 = null;
		while (itr.hasNext()) {
			itr1 = ((Map) itr.next()).entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				log.debug("key value" + pair.getKey() + " : " + pair.getValue());
				
				if (pair.getKey() != null && pair.getKey().equals(OpenStackConstants.CONNECTED_TO) && pair.getValue() != null) {
					
						JSONArray connArr = (JSONArray) pair.getValue();
						Iterator conItr = connArr.iterator();
						while (conItr.hasNext()) {
							String data = null;
							Iterator<Map.Entry> contrItr = ((Map) conItr.next()).entrySet().iterator();
							while (contrItr.hasNext()) {
								Map.Entry cntPair = contrItr.next();
								String connectedKey = (String) cntPair.getKey();
								if (connectedKey != null && connectedKey.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
									String containerKey = (String) cntPair.getKey();
									data = (String) cntPair.getValue();
									NodeTree<String> subNode = new NodeTree<String>(data);
									NodeTree<String> searchNode = findDataInTree(rootNode, data);
									if (searchNode != null) {
										NodeTree<String> parent = searchNode.getParent();
										String parentData = parent.getData();
										if (parent != null) {
											parent.getChildren().remove(searchNode);
											newNode.addChild(searchNode);
										}
										
									} else {
										newNode.addChild(subNode);
									}
								}
							}
					}
				}
					log.debug("key value " + pair.getKey() + " : " + pair.getValue());
			}
		}
			
		log.debug("sequenceJsonParseProbe End");					
	}

public  NodeTree<String> findDataInTree(NodeTree node, String searchQuery) {
	log.debug("findDataInTree Start");
	NodeTree<String> ss=null;
	 if(node.getData().equals(searchQuery)) {
		 log.debug("node.Data() "+node.getData());
	    return node;
	 }
	 List<NodeTree<String>> children=node.getChildren(); 
	 int count=children.size();
	 for(NodeTree each : children) {
		 NodeTree<String> findDataInTree = findDataInTree(each, searchQuery);
		 log.debug("node.Data "+node.getData());
		 if(findDataInTree!=null){
			 return findDataInTree; 
		 }
		 
	    
	 }
	log.debug(" findDataInTree End");	 
	return ss; 
}
 public  <T> void printTree(NodeTree<T> node, String appender,LinkedList<String> linkedList) {
	  log.debug(appender + node.getData());
	  linkedList.add(String.valueOf(node.getData()));
	  node.getChildren().forEach(each ->  printTree(each, (appender + appender),linkedList));
	  
 }	
   public boolean checkProbeIndicator(String jsonFileName)  throws  Exception {
	   log.debug("checkProbeIndicator Start");
	   boolean probeIndicatorValue=false;
	   String value=null;
	   try {
			Object obj = new JSONParser().parse(new FileReader(jsonFileName));
			JSONObject jo = (JSONObject) obj;
			JSONArray probeIndicator = (JSONArray) jo.get(OpenStackConstants.PROBE_INDICATOR);
			if(probeIndicator == null){
				probeIndicator = (JSONArray) jo.get(OpenStackConstants.PROBE_INDOCATOR);
			}
			if(probeIndicator!=null){
				Iterator itr = probeIndicator.iterator();
				Iterator<Map.Entry> itr1=null;
				
				 while (itr.hasNext()) {
					 itr1 = ((Map) itr.next()).entrySet().iterator();
			            while (itr1.hasNext()) {
			                Map.Entry pair = itr1.next();
			                value = (String)pair.getValue();
			            }
				 }
			} 
			if(value!=null && value.equalsIgnoreCase("True")) {
				probeIndicatorValue=true;
			   } else {				
				   probeIndicatorValue=false;
			  }	
	   } catch (Exception e) {
		    log.error("checkProbeIndicator failed", e);
			throw e;
		}
	   log.debug("probeIndicator "+probeIndicatorValue);
	   log.debug("checkProbeIndicator End");
	   return probeIndicatorValue;
			
   }

	public Blueprint jsonFileToObjectProbe(String jsonFileName,DataBrokerBean dataBrokerBean)  throws  Exception {
		 log.debug("jsonFileToObjectProbe Start");
		 log.debug("jsonFileName "+jsonFileName);
		ArrayList<String> list = new ArrayList<String>();
		Blueprint blueprint = new Blueprint();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object obj = new JSONParser().parse(new FileReader(jsonFileName));

			JSONObject jo = (JSONObject) obj;
			String prettyJSONString = jo.toString();
			String name = (String) jo.get(OpenStackConstants.NAME);
			String version = (String) jo.get(OpenStackConstants.VERSION);

			String probeIndicator = getProbeIndicator(jo);
			JSONArray trainingClients = (JSONArray) jo.get(OpenStackConstants.TRAINING_CLIENTS);
			
			ArrayList<ProbeIndicator> list_of_pb_indicators = new ArrayList<ProbeIndicator>();
			ProbeIndicator prbIndicator = new ProbeIndicator();
			if(probeIndicator!=null){
				prbIndicator.setValue(probeIndicator);
			}else{
				prbIndicator.setValue("");
			}
			list_of_pb_indicators.add(prbIndicator);
			
			blueprint.setProbeIndicator(list_of_pb_indicators);
			blueprint.setName(name);
			blueprint.setVersion(version);
			Iterator<Map.Entry> itr1 = null;
			
			
			JSONArray inputPorts = (JSONArray) jo.get(OpenStackConstants.INPUT_PORTS);
			ArrayList<OperationSignature> operationList = new ArrayList<OperationSignature>();
			List<InputPort> inputPortList=null;
			if (inputPorts != null) {
				inputPortList=new ArrayList<InputPort>();
				log.debug("input ports");
				
				Iterator itr2 = inputPorts.iterator();
				while (itr2.hasNext()) {
					InputPort inputPortObj=new InputPort();
					OperationSignature operationSignature = new OperationSignature();
					itr1 = ((Map) itr2.next()).entrySet().iterator();
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						String key = (String) pair.getKey();
						log.debug("pair key " + pair.getKey() + " : " + pair.getValue());
						if (key != null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE)) {
							JSONObject jsonObject = (JSONObject) pair.getValue();
							String operationName =(String) jsonObject.get(OpenStackConstants.OPERATION_NAME);
							operationSignature.setOperationName(operationName);
							inputPortObj.setOperationSignature(operationSignature);
						}
						if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
							inputPortObj.setContainerName((String) pair.getValue());
						}
						
					}
					log.debug("inputPortObj "+inputPortObj);
					inputPortList.add(inputPortObj);
				}
				
			}
			if(inputPorts!=null && inputPorts.size() > 0){
				blueprint.setInputPorts(inputPortList);
			}
			
			JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
			ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					ArrayList<OperationSignatureList> operSigList = new ArrayList<OperationSignatureList>();
					OperationSignatureList obpListObject=new OperationSignatureList();
					operSigList.add(obpListObject);
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes " + ++nodeCount);
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
					   if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
							String key = (String) pair.getKey();
							if(key != null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE_LIST)) {
								
								if (pair.getValue() != null) {
									operSigList = jsonArrayParseObjectProb(pair.getValue(),operSigList);
									
									log.debug("operSigList " + operSigList);
								}
								
							
						  }else {
								log.debug("pair key val " + pair.getKey() + "  " + pair.getValue());
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
									node.setContainerName((String) pair.getValue());
								}
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.IMAGE)) {
									node.setImage((String) pair.getValue());
								}
								
								if(key != null && key.equalsIgnoreCase(OpenStackConstants.NODE_TYPE)) {
									node.setNodeType((String)pair.getValue());
								}
								if(key != null && key.equalsIgnoreCase(OpenStackConstants.PROTO_URI)) {
									node.setProtoUri((String)pair.getValue());
								}
								JSONObject dataBrokerObject=null;
								if(key != null && key.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_MAP)){
			                    	 dataBrokerObject = (JSONObject)pair.getValue();
			                    	 if(dataBrokerObject!=null  ){
			                    		 if(dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE)!=null){
			                    			 String dataBrokerType=(String)dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE);
			                    			 log.debug("dataBrokerType "+dataBrokerType);
			                    			 if(dataBrokerType!=null && dataBrokerType.equalsIgnoreCase(OpenStackConstants.CSV_FILE_NAME)){
			                    				 if(dataBrokerBean!=null){
			                    					 node.setDataBrokerMap(dataBrokerBean.getDataBrokerMap()); 
			                    				 }
			                    			 }
			                    			 
			                    		 }
			                    	 } 
								}
								
							}
					 }	
					}
					node.setOperationSignatureList(operSigList);
					nodeList.add(node);
				}

			}
			blueprint.setNodes(nodeList);
			log.debug("blueprint " + mapper.writeValueAsString(blueprint) + " " + blueprint.toString());
		} catch (Exception e) {
			log.error("jsonFileToObjectProbe failed", e);
			throw e;
		}
		log.debug("blueprint "+blueprint);
		log.debug("jsonFileToObjectProbe End ");
		return blueprint;
	}

	public HashMap<String, String> parseJsonFileImageMap(String jsonFileName) throws Exception {
		log.debug("parseJsonFileProbe Start");
		log.debug("jsonFileName "+jsonFileName);
		HashMap<String,String> imageMap=new HashMap<String,String>();
		ArrayList<String> list=new ArrayList<String>();	
		try
		{
			Object obj = new JSONParser().parse(new FileReader(jsonFileName));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
	        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
	        ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					String containerName = null,imageName  = null;
					while (itr1.hasNext()) {
						
						Map.Entry pair = itr1.next();
						if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
							String key = (String) pair.getKey();
							if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
									containerName =(String)pair.getValue();
								}
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.IMAGE)) {
									imageName =(String)pair.getValue();
								}
								
							if(containerName!=null && imageName!=null && !"".equals(containerName) && !"".equals(imageName)){
			                	imageMap.put(imageName, containerName);
			                }
					  }	
					}
			
				}
			}
			
		}catch(Exception e){
			log.error("parseJsonFileProbe failed", e);
    	    throw e;
       }
		log.debug("imageMap "+imageMap);
		log.debug(" parseJsonFileProbe End");
		return imageMap;	
	}
	
	public HashMap<String, DeploymentBean> getNodeTypeContainerMap(String jsonFileName) throws Exception {
		log.debug("getNodeTypeContainerMap Start");
		log.debug("jsonFileName "+jsonFileName);
		HashMap<String,DeploymentBean> imageMap=new HashMap<String,DeploymentBean>();
		ArrayList<String> list=new ArrayList<String>();	
		try
		{
		
			Object obj = new JSONParser().parse(new FileReader(jsonFileName));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
	        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
	        ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					String containerName = null,nodeType  = null,script=null;
					DeploymentBean bean=new DeploymentBean();
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
							String key = (String) pair.getKey();
							if (key != null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)) {
									containerName =(String)pair.getValue();
									bean.setContainerName(containerName);
								}
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.NODE_TYPE)) {
									nodeType =(String)pair.getValue();
									bean.setNodeType(nodeType);
								}
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.SCRIPT)) {
									script =(String)pair.getValue();
									bean.setScript(script);
								}
								if(key!=null && key.equalsIgnoreCase(OpenStackConstants.DATA_BROKER_MAP)){
									JSONObject dataBrokerObject = (JSONObject) pair.getValue();
			                    	 if(dataBrokerObject!=null  ){
			                    		 if(dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE)!=null){
			                    			 String dataBrokerType=(String)dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE);
			                    			 log.debug("dataBrokerType "+dataBrokerType);
			                    			 bean.setDataBrokerType(dataBrokerType);
			                    		 }
			                    	 } 
								}
								
								
							if(containerName!=null && nodeType!=null && !"".equals(containerName) && !"".equals(nodeType)){
								if(bean.getDataBrokerType() == null){
									bean.setDataBrokerType(OpenStackConstants.DEFAULT);
								}
			                	imageMap.put(containerName,bean);
			                }
					  }	
					}
					log.debug("container_name "+bean.getContainerName()+" node_type "+bean.getNodeType()+
							" Script"+bean.getScript()+" DataBrokerType "+bean.getDataBrokerType());
			
				}
			}
			
		}catch(Exception e){
			log.error("getNodeTypeContainerMap failed", e);
    	    throw e;
       }
		log.debug("imageMap "+imageMap);
		log.debug(" getNodeTypeContainerMap End");
		return imageMap;	
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	public LinkedList<String> getSequenceListFromJSON(String jsonFileName)  throws  Exception {
		log.debug("getSequenceFromJSONProbe Start");
		log.debug("jsonFileName "+jsonFileName);
		String contentString="";
		HashMap<String,String> imageMap=new HashMap<String,String>();
		ArrayList<String> list=new ArrayList<String>();	
		LinkedList<String> linkedList=new LinkedList<String>();
		try
		{
		NodeTree<String> root = new NodeTree<String>(OpenStackConstants.BLUEPRINT_CONTAINER);
        Object obj = new JSONParser().parse(new FileReader(jsonFileName));
        JSONObject jo = (JSONObject) obj;
        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
        if(nodes!=null && !nodes.isEmpty()){
        	Iterator itr3 = nodes.iterator();
	        int nodeCount=0; 
	        while (itr3.hasNext()) 
	        {
	        	Iterator<Map.Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
	        	Iterator<Map.Entry> itr5 = itr4;
	        	String containerName="";
	        	String imageName="";
	        	
	        	String contName="test";
	        	NodeTree<String> testNode=new NodeTree<String>(contName);
	        	log.debug("Second while");
	            while (itr4.hasNext()) {
	                Map.Entry pair = itr4.next();
	                if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
	                	
		                String key=(String)pair.getKey();
		                String val=(String)pair.getValue().toString();
		                if(key!=null && key.equalsIgnoreCase(OpenStackConstants.OPERATION_SIGNATURE_LIST)){
		                	sequenceJsonParseProbe(pair.getValue(),testNode,root);
		                }if(key!=null && key.equalsIgnoreCase(OpenStackConstants.CONTAINER_NAME)){
		                	containerName=val;
		                	contName=val;
		                }else{
		                	log.debug("key "+pair.getKey() );
		                }
		                if(key!=null && key.equalsIgnoreCase(OpenStackConstants.IMAGE)){
		                	imageName=val;
		                	list.add(val);
		                 }
		                if(containerName!=null && imageName!=null && !"".equals(containerName) && !"".equals(imageName)){
		                	imageMap.put(imageName, containerName);
		                }
		             } 
	            }
	            testNode.setData(contName);
	            NodeTree<String> searchNode=findDataInTree(root, contName);
	            if(searchNode!=null){
	            	NodeTree<String> parent=searchNode.getParent();
	            	String parentData=parent.getData();
	            	if (parent != null) {
	    				int index = parent.getChildren().indexOf(searchNode);
	    				parent.getChildren().remove(searchNode);
	    			 }
	            	NodeTree<String> parrentNode=findDataInTree(root, parentData);
	            	if(parrentNode!=null){
	            		parrentNode.addChild(testNode);
	            	}
	            }else{
	            	root.addChild(testNode);
	            }
	        }
         }
        printTree(root, " ",linkedList);
        Collections.reverse(linkedList);
        log.debug("linkedList "+linkedList);
        
        }catch(Exception e){
        	log.error("getSequenceFromJSONProbe failed", e);
    	    throw e;
       }
		log.debug(" getSequenceFromJSONProbe End");
		return linkedList;	
	}
	public DataBrokerBean getDataBrokerContainer(String jsonFileName) throws Exception {
		log.debug(" getDataBrokerContainer Start");
		log.debug("jsonFileName "+jsonFileName);
		ArrayList<String> list=new ArrayList<String>();	
		DataBrokerBean dataBrokerBean=null;
		try
		{
		
			Object obj = new JSONParser().parse(new FileReader(jsonFileName));
			Iterator<Map.Entry> itr1 = null;
			JSONObject jo = (JSONObject) obj;
	        JSONArray nodes = (JSONArray) jo.get(OpenStackConstants.NODES);
	        int nodeLength=-1;
	        int dataBrokerVal=-1;
	        ArrayList<Integer>dataBrokerList=new ArrayList<Integer>();
	        ArrayList<Node> nodeList = new ArrayList<Node>();
			if (nodes != null) {
				Iterator itr3 = nodes.iterator();
				int nodeCount = 0;
				while (itr3.hasNext()) {
					Node node = new Node();
					itr1 = ((Map) itr3.next()).entrySet().iterator();
					log.debug("Nodes" + ++nodeCount);
					nodeLength++;
					String containerName = null,nodeType  = null,script=null;
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						if(pair!=null && pair.getKey()!=null && pair.getValue()!=null){
							String key = (String) pair.getKey();
							
								if (key != null && key.equalsIgnoreCase(OpenStackConstants.NODE_TYPE)) {
									nodeType =(String)pair.getValue();
									if(nodeType!=null && nodeType.equalsIgnoreCase(OpenStackConstants.DATA_BROKER)){
										dataBrokerList.add(nodeLength);
									}
								}
							
					  }	
					}
				}
				log.debug("dataBrokerList Nodes "+dataBrokerList);
				if(dataBrokerList!=null && dataBrokerList.size() > 0){
					for(Integer num: dataBrokerList){
						dataBrokerVal=num;
						log.debug("dataBrokerVal "+dataBrokerVal);
						dataBrokerBean=new DataBrokerBean();
						DataBrokerMap dataBrokerMap=new DataBrokerMap();
						ArrayList<MapInputs> mapInputList=new ArrayList<MapInputs>();
						ArrayList<MapOutputs> mapOutputList=new ArrayList<MapOutputs>();
						
						
						
						log.debug("DataBroker Nodes "+nodes.get(dataBrokerVal));
						JSONObject jsonObject =(JSONObject)nodes.get(dataBrokerVal);
						String containerName="";
						String nodeType="";
						String image="";
						String protoUri="";
						JSONArray mapInputArray=null;
						JSONArray mapoutputArray=null;
						JSONObject dataBrokerObject=null;
						if(jsonObject!=null){
							if(jsonObject.get(OpenStackConstants.CONTAINER_NAME)!=null){
								containerName=(String)jsonObject.get(OpenStackConstants.CONTAINER_NAME);
								
							}
		                    if(jsonObject.get(OpenStackConstants.NODE_TYPE)!=null){
		                    	nodeType=(String)jsonObject.get(OpenStackConstants.NODE_TYPE);
							}
		                    if(jsonObject.get(OpenStackConstants.IMAGE)!=null){
		                    	image=(String)jsonObject.get(OpenStackConstants.IMAGE);
							}
		                    if(jsonObject.get(OpenStackConstants.PROTO_URI)!=null){
		                    	protoUri=(String)jsonObject.get(OpenStackConstants.PROTO_URI);
		                    	dataBrokerBean.setProtobufFile(protoUri);
							}
		                    if(jsonObject.get(OpenStackConstants.DATA_BROKER_MAP)!=null){
		                    	 dataBrokerObject = (JSONObject) jsonObject.get(OpenStackConstants.DATA_BROKER_MAP);
		                    	 if(dataBrokerObject!=null  ){
		                    		 if(dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE)!=null){
		                    			 String dataBrokerType=(String)dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE);
		                    			 log.debug("dataBrokerType "+dataBrokerType);
		                    			 if(dataBrokerType!=null && dataBrokerType.equalsIgnoreCase(OpenStackConstants.CSV_FILE_NAME)){
		                    				 dataBrokerMap.setDataBrokerType((String)dataBrokerObject.get(OpenStackConstants.DATA_BROKER_TYPE)) ;
		                    			 }else{
		                    				 dataBrokerBean=null;
		                    				 break;
		                    			 }
		                    			 
		                    		 }
		                             if(dataBrokerObject.get(OpenStackConstants.SCRIPT)!=null){
		                            	 dataBrokerMap.setScript((String)dataBrokerObject.get(OpenStackConstants.SCRIPT));
		                    		 }
									 if(dataBrokerObject.get(OpenStackConstants.TARGET_SYSTEM_URL)!=null){
										dataBrokerMap.setTargetSystemUrl((String)dataBrokerObject.get(OpenStackConstants.TARGET_SYSTEM_URL));
									 }
									 if(dataBrokerObject.get(OpenStackConstants.LOCAL_SYSTEM_DATA_FILE_PATH)!=null){
										dataBrokerMap.setLocalSystemDataFilePath((String)dataBrokerObject.get(OpenStackConstants.LOCAL_SYSTEM_DATA_FILE_PATH));
									 }
									 if(dataBrokerObject.get(OpenStackConstants.FIRST_ROW)!=null){
										dataBrokerMap.setFirstRow((String)dataBrokerObject.get(OpenStackConstants.FIRST_ROW));
									 }
									 if(dataBrokerObject.get(OpenStackConstants.CSV_FILE_FIELD_SEPARATOR)!=null){
										dataBrokerMap.setCsvFileFieldSeparator((String)dataBrokerObject.get(OpenStackConstants.CSV_FILE_FIELD_SEPARATOR));
									 }
		                             if(dataBrokerObject.get(OpenStackConstants.MAP_INPUTS)!=null){
		                            	MapInputs mapInputBean=new MapInputs();
		                            	mapInputArray = (JSONArray) dataBrokerObject.get(OpenStackConstants.MAP_INPUTS);
		                            	log.debug("mapInputArray " + mapInputArray);
		                            	Iterator itr5 = null;
		                        		Iterator<Map.Entry> itr6 = null;
		                        		itr5=mapInputArray.iterator();
		                            	while (itr5.hasNext()) {
		                        			itr6 = ((Map) itr5.next()).entrySet().iterator();
		                        			InputField inputFieldBean=new InputField();
		                        			while (itr6.hasNext()) {
		                        				Map.Entry pair = itr6.next();
		                        				String key = (String) pair.getKey();
		                        				log.debug("Key " + key);
		                        				log.debug("value " + pair.getValue());
		                        				if(key!=null && key.equalsIgnoreCase(OpenStackConstants.INPUT_FIELD)){
		                        					JSONObject inputFieldJsonObject=(JSONObject)pair.getValue();
		                        					if(inputFieldJsonObject!=null){
		                        						if(inputFieldJsonObject.get(OpenStackConstants.NAME)!=null){
		                        							inputFieldBean.setName((String)inputFieldJsonObject.get(OpenStackConstants.NAME));
		                        						}
		                                                if(inputFieldJsonObject.get(OpenStackConstants.TYPE)!=null){
		                                                	inputFieldBean.setType((String)inputFieldJsonObject.get(OpenStackConstants.TYPE));
		                        						}
		                                                if(inputFieldJsonObject.get(OpenStackConstants.CHECKED)!=null){
		                                                	inputFieldBean.setChecked((String)inputFieldJsonObject.get(OpenStackConstants.CHECKED));
		                        						}
		                                                if(inputFieldJsonObject.get(OpenStackConstants.MAPPED_TO_FIELD)!=null){
		                                                	inputFieldBean.setMappedToField((String)inputFieldJsonObject.get(OpenStackConstants.MAPPED_TO_FIELD));
		                        						}
		                        					}
		                        				}
		                        			}
		                        			mapInputBean.setInputField(inputFieldBean);
		                    		    }
		                            	mapInputList.add(mapInputBean);
		                            }
		                            dataBrokerMap.setMapInputs(mapInputList);
		                            if(dataBrokerObject.get(OpenStackConstants.MAP_OUTPUTS)!=null){
		                            	MapOutputs mapOutputBean=new MapOutputs();
		                            	mapoutputArray = (JSONArray) dataBrokerObject.get(OpenStackConstants.MAP_OUTPUTS);
		                            	log.debug("mapoutputArray " + mapoutputArray);
		                            	Iterator itr7 = null;
		                        		Iterator<Map.Entry> itr8 = null;
		                        		itr7=mapoutputArray.iterator();
		                            	while (itr7.hasNext()) {
		                        			itr8 = ((Map) itr7.next()).entrySet().iterator();
		                        			OutputField outputFieldBean=new OutputField();
		                        			while (itr8.hasNext()) {
		                        				Map.Entry pair = itr8.next();
		                        				String key = (String) pair.getKey();
		                        				log.debug("Key  "+ key);
		                        				log.debug("value " + pair.getValue());
		                        				if(key!=null && key.equalsIgnoreCase(OpenStackConstants.OUTPUT_FIELD)){
		                        					JSONObject outputFieldJsonObject=(JSONObject)pair.getValue();
		                        					if(outputFieldJsonObject!=null){
		                        						if(outputFieldJsonObject.get(OpenStackConstants.TAG)!=null){
		                        							outputFieldBean.setTag((String)outputFieldJsonObject.get(OpenStackConstants.TAG));
		                        						}
		                                                if(outputFieldJsonObject.get(OpenStackConstants.NAME)!=null){
		                                                	outputFieldBean.setName((String)outputFieldJsonObject.get(OpenStackConstants.NAME));
		                        						}
		                                                if(outputFieldJsonObject.get(OpenStackConstants.TYPE_AND_ROLE_HIERARCHY_LIST)!=null){
		                                                	ArrayList<TypeAndRoleHierarchyList> roleHirerachryList=new ArrayList<TypeAndRoleHierarchyList>();
		                                                	JSONArray hierarchyListArray = (JSONArray) outputFieldJsonObject.get(OpenStackConstants.TYPE_AND_ROLE_HIERARCHY_LIST);
		                                                	if(hierarchyListArray!=null){
		                                                		Iterator itr9 = null;
		                                                		Iterator<Map.Entry> itr10 = null;
		                                                		itr9=hierarchyListArray.iterator();
		                                                		while (itr9.hasNext()) {
		                                                			itr10 = ((Map) itr9.next()).entrySet().iterator();
		                                                			TypeAndRoleHierarchyList typeAndRoleHierarchyListBean=new TypeAndRoleHierarchyList();
		                                                			while (itr10.hasNext()) {
		                                                				Map.Entry mapPair = itr10.next();
		                                                				String keyVal = (String) mapPair.getKey();
		                                                				log.debug("Key " + keyVal);
		                                                				log.debug("value " + mapPair.getValue());
		                                                				if(keyVal!=null){
		                                                					if(keyVal.equalsIgnoreCase(OpenStackConstants.NAME)){
		                                                						typeAndRoleHierarchyListBean.setName((String)mapPair.getValue());
		                                                					}
		                                                                    if(keyVal.equalsIgnoreCase(OpenStackConstants.ROLE)){
		                                                                    	typeAndRoleHierarchyListBean.setRole((String)mapPair.getValue());
		                                                					}
		                                                				}
		                                                			}
		                                                			roleHirerachryList.add(typeAndRoleHierarchyListBean);
		                                                		}		
		                                                	}
		                                                	outputFieldBean.setTypeAndRoleHierarchyList(roleHirerachryList);
		                        						}
		                                                
		                        					}
		                        				}
		                        			}
		                        			mapOutputBean.setOutputField(outputFieldBean);
		                    		    }
		                            	
		                            	mapOutputList.add(mapOutputBean);
		                    		 }
		                            dataBrokerMap.setMapOutputs(mapOutputList);
		                    	 }
							}
		                    dataBrokerBean.setDataBrokerMap(dataBrokerMap);
		
						}
						log.debug("dataBrokerMap "+dataBrokerMap);
				 }	
			   }	
			}
			
		}catch(Exception e){
			log.error("parseJsonFile failed", e);
    	    throw e;
       }
		log.debug("dataBrokerBean "+dataBrokerBean);
		log.debug(" getDataBrokerContainer End");
		return dataBrokerBean;	
	}
	
}
