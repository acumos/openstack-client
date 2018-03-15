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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Node implements Serializable {

	private static final long serialVersionUID = 1900236928331958666L;

	public Node() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("container_name")
	private String containerName = null;

	@JsonProperty("image")
	private String image = null;
	
	@JsonProperty("node_type")
	private String node_type = null;

	@JsonProperty("depends_on")
	private List<Component> dependsOn = null;
	
	public String getNode_type() {
		return node_type;
	}

	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Component> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<Component> dependsOn) {
		this.dependsOn = dependsOn;
	}

	public Node addDependsOn(Component component) {
		if (this.dependsOn == null) {
			this.dependsOn = new ArrayList<Component>();
		}
		this.dependsOn.add(component);
		return this;
	}

	@Override
	public String toString() {
		return "Node [containerName=" + containerName + ", image=" + image + ", dependsOn=" + dependsOn + "]";
	}
}
