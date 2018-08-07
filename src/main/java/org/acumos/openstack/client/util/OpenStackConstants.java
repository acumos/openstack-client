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

public final class OpenStackConstants {
	
	public static final String APPLICATION_JSON="application/json";
	public static final String BLUEPRINT_CONTAINER_NAME= "BluePrintContainer";
	public static final String PROBE_CONTAINER_NAME= "Probe";
	public static final String JSON_FILE_NAME= "blueprint.json";
	public static final String AZURE_AUTH_LOCATION= "AZURE_AUTH_LOCATION";
	public static final String AZURE_AUTH_LOCATION_NEXT= "AZURE_AUTH_LOCATION_2";
	public static final String SSH_ACS= "ACS";
	public static final String IMAGE_TAG_LATEST= "latest";
	public static final String PRIVATE_REPO_PREFIX= "/samples/";
	public static final String CONTAINER_NAME_PREFIX= "-private_";
	public static final String PROBE_NODE_TYPE= "Probe";
	public static final String DEFAULT_NODE_TYPE= "Default";
	public static final String PUT_DOCKER_INFO_URL= "putDockerInfo";
	public static final String PUT_BLUEPRINT_INFO_URL= "putBlueprint";
	public static final String CONFIG_DB_URL= "configDB";
	public static final String DATABROKER_NAME= "DataBroker";
	public static final String DEPLOYMENT_PROCESS= "DP";
	public static final String DEPLOYMENT_FAILED= "FA";
	public static final String DATA_BROKER_CSV_FILE= "csv";
	public static final String ARTIFACT_TYPE_BLUEPRINT= "BP";
	public static final String HTTP_PROPERTY= "https.protocols";
	public static final String HTTP_PROPERTY_VALUE= "TLSv1";
	public static final String SSL_DOCKER= "docker";
	public static final String DOCKER_CERT_PATH="DOCKER_CERT_PATH";
	public static final String CA_PEM="ca.pem";
	public static final String KEY_PEM="key.pem";
	public static final String CERT_PEM="cert.pem";
	public static final String VNET_NAME="vnet";
	public static final String FRONT_END_NSG_NAME="fensg";
	public static final String NETWORK_INTERFACE_NAME="nic1";
	public static final String PUBLIC_IP_ADDRESS_LEAF="pip1";
	public static final int SSH_PORT=22;
	public static final int DOCKER_HOST_PORT=80;
	public static final String NODES="nodes";
	public static final String DEPENDS_ON="depends_on";
	public static final String CONTAINER_NAME="container_name";
	public static final String IMAGE="image";
	public static final String NAME="name";
	public static final String VERSION="version";
	public static final String ORCHESTRATOR="orchestrator";
	public static final String INPUT_OPERATION_SIGNATURES="input_operation_signatures";
	public static final String OPERATION="operation";
	public static final String PROBE_INDOCATOR="probeIndocator";
	public static final String PROBE_INDICATOR="probeIndicator";
	public static final String OPERATION_SIGNATURE="operation_signature";
	public static final String CONNECTED_TO="connected_to";
	public static final String OPERATION_NAME="operation_name";
	public static final String INPUT_MESSAGE_NAME="input_message_name";
	public static final String OUTPUT_MESSAGE_NAME="output_message_name";
	public static final String BLUEPRINT_CONTAINER="BluePrintContainer";
	public static final String CONTAINER_TEST="test";
	public static final String TRAINING_CLIENTS="training_clients";
	public static final String INPUT_PORTS="input_ports";
	public static final String OPERATION_SIGNATURE_LIST="operation_signature_list";
	public static final String NODE_TYPE="node_type";
	public static final String PROTO_URI="proto_uri";
	public static final String SCRIPT="script";
	public static final String DATA_BROKER_MAP="data_broker_map";
	public static final String DATA_BROKER_TYPE="data_broker_type";
	public static final String DEFAULT="Default";
	public static final String DATA_BROKER="DataBroker";
	public static final String CSV_FILE_NAME="csv";
	public static final String TARGET_SYSTEM_URL="target_system_url";
	public static final String LOCAL_SYSTEM_DATA_FILE_PATH="local_system_data_file_path";
	public static final String FIRST_ROW="first_row";
	public static final String CSV_FILE_FIELD_SEPARATOR="csv_file_field_separator";
	public static final String MAP_INPUTS="map_inputs";
	public static final String INPUT_FIELD="input_field";
	public static final String TYPE="type";
	public static final String CHECKED="checked";
	public static final String MAPPED_TO_FIELD="mapped_to_field";
	public static final String MAP_OUTPUTS="map_outputs";
	public static final String OUTPUT_FIELD="output_field";
	public static final String TAG="tag";
	public static final String TYPE_AND_ROLE_HIERARCHY_LIST="type_and_role_hierarchy_list";
	public static final String ROLE="role";
	public static final String VM_CREATION_ERROR="role";
	public static final String NGINX_CONTAINER="Nginx";
	public static final String NGINX_IMAGE="nginx";
	public static final String DATAFILE_FOLDER="/home/dockerUser/AzureDataFiles/";
	
	public static final String OPENSTACK_FLAVOURNAME="docker.openstack.flavourName";
	public static final String OPENSTACK_ENDPOINT="docker.openstack.endpoint";
	public static final String OPENSTACK_SECURITYGROUPNAME="docker.openstack.securityGroupName";
	public static final String OPENSTACK_USERNAME="docker.openstack.userName";
	public static final String OPENSTACK_PD="docker.openstack.password";
	public static final String OPENSTACK_SCOPEPROJECT="docker.openstack.scopeProject";
	public static final String OPENSTACK_KEY="docker.openstack.key";
	public static final String OPENSTACK_KEYNAME="docker.openstack.keyName";
	public static final String OPENSTACK_IDENTIFIERNAME="docker.openstack.IdentifierName";
	public static final String OPENSTACK_VMREGISTERNUMBER="docker.openstack.vmRegisterNumber";
	public static final String OPENSTACK_HOSTOPENSTACK="docker.openstack.hostOpenStack";
	public static final String OPENSTACK_HOSTUSERNAME="docker.openstack.hostUserName";
	public static final String OPENSTACK_VMUSERNAME="docker.openstack.vmUserName";
	public static final String OPENSTACK_DOCKERUSERNAME="docker.openstack.dockerUserName";
	public static final String OPENSTACK_DOCKERPD="docker.openstack.dockerPassword";
	
	public static final String OPENSTACK_SOLUTIONPORT="docker.openstack.solutionPort";
	public static final String OPENSTACK_SLEEPTIME="docker.openstack.Sleeptime";
	public static final String OPENSTACK_PROXYIP="docker.openstack.proxyIP";
	public static final String OPENSTACK_PROXYPORT="docker.openstack.proxyPort";
	public static final String OPENSTACK_OPENSTACKIP="docker.openstack.openStackIP";
	public static final String OPENSTACK_REPOSITYNAMES="docker.openstack.reposityNames";
	public static final String OPENSTACK_EXPOSEDATABROKERPORT="docker.openstack.exposeDataBrokerPort";
	public static final String OPENSTACK_INTERNALDATABROKERPORT="docker.openstack.internalDataBrokerPort";
	public static final String OPENSTACK_NEXUSREGISTYNAME="docker.openstack.nexusRegistyName";
	public static final String OPENSTACK_NEXUSREGISTYUSERNAME="docker.openstack.nexusRegistyUserName";
	public static final String OPENSTACK_NEXUSREGISTYPD="docker.openstack.nexusRegistyPwd";
	public static final String OPENSTACK_REPOSITYDETAILS="docker.openstack.reposityDetails";
	
	public static final String BLUEPRINT_IMAGENAME="blueprint.bluePrintImageName";
	public static final String BLUEPRINT_NAME="blueprint.bluePrintName";
	public static final String BLUEPRINT_USERNAME="blueprint.bluePrintUserName";
	public static final String BLUEPRINT_PD="blueprint.bluePrintPassword";
	public static final String BLUEPRINT_PORTNUMBER="docker.openstack.bluePrintPortNumber";
	
	public static final String NGINX_MAPFOLDER="nginx.nginxMapFolder";
	public static final String NGINX_WEBFOLDER="nginx.nginxWebFolder";
	public static final String NGINX_IMAGENAME="nginx.nginxImageName";
	public static final String NGINX_INTERNALPORT="nginx.nginxInternalPort";

	public static final String CMNDATASVC_ENDPOINURL="cmndatasvc.cmndatasvcendpoinurl";
	public static final String CMNDATASVC_USER="cmndatasvc.cmndatasvcuser";
	public static final String CMNDATASVC_PD="cmndatasvc.cmndatasvcpwd";

	public static final String NEXUS_URL="nexus.url";
	public static final String NEXUS_USERNAME="nexus.username";
	public static final String NEXUS_PD="nexus.password";

	public static final String PROBE_IMAGENAME="probe.ImageName";
	public static final String PROBE_NAME="probe.name";
	public static final String PROBE_USERNAME="probe.username";
	public static final String PROBE_PD="probe.password";
	public static final String PROBE_PROBENEXUSENDPOINT="probe.probeNexusEndPoint";
	public static final String PROBE_INTERNALPORT="probe.internalPort";
	
	
	
	
}
