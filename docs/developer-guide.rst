.. ===============LICENSE_START=======================================================
.. Acumos CC-BY-4.0
.. ===================================================================================
.. Copyright (C) 2017-2018 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
.. ===================================================================================
.. This Acumos documentation file is distributed by AT&T and Tech Mahindra
.. under the Creative Commons Attribution 4.0 International License (the "License");
.. you may not use this file except in compliance with the License.
.. You may obtain a copy of the License at
..
.. http://creativecommons.org/licenses/by/4.0
..
.. This file is distributed on an "AS IS" BASIS,
.. WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
.. See the License for the specific language governing permissions and
.. limitations under the License.
.. ===============LICENSE_END=========================================================

==========================================
Openstack Client Developers Guide
==========================================

1. Introduction
---------------

This is the developers guide to Openstack Client.

**1.1 What is Openstack client?**

Acumos provides deployment of model in Openstack cloud :

   1. Deploy single solution from Acumos marketplace in Openstack clould.

   2. Deploy composite solution from Acumos marketplace in Openstack clould. 

   
**1.2 Target Users**

   This guide is targeted towards the open source user community that:

   1. Intends to understand the functionality of the Openstack client.

**1.3 Openstack client - Flow Chart**

         .. image:: images/openstack_flowchart.jpg
            :alt: Openstack Flow Chart



**1.5 Openstack client Flow Structure:**

   

    **Page Name:** Model/Solution Landing Page

      

      1.  Clicking on <Deploy to Cloud> for Deploy model .

      2.  <Deploy to Cloud>  should prompt details about MS Azure (Inputs
          TBD),Rackspace etc..
	   
      3. Select <rackspace> from Drop down and fill all details for Deployment.
      	  


  

2. Model Deployment
-------------------------------

**2.1 Single Solution**

 - openstack/singleImageOpenstackDeployment
~~~~~~~~~~~~~~~

**- Trigger**

This API is used to deploy single solution in openstack cloud.

**- Request**

{
  "imagetag": "cognita-nexus01:8001/newadder1:1",
  
  "solutionId": "02eab846-2bd0-4cfe8470-9fc69fa0d877",
  
  "solutionRevisionId": "a9e68bc6-f4b4-41c6-ae8e-4e97ec3916a6",
  
  "userId": "0505e537-ce79-4b1f-bf43-68d88933c369",
  
  "vmName": "Gold80"
}

**- Response**

{
"status": "SUCCESS",
"UIDNumber": "Unique Transaction Number"
}

**2.2 Composite Solution**

- openstack/compositeSolutionOpenstackDeployment
~~~~~~~~~~~~~~~~~~~~

**- Trigger:**

    This API is used to deploy Composite solution in openstack cloud.

**- Request:**

{ 
  "jsonMapping": "testMapping",
  
  "jsonPosition": "testPosition",
  
  "urlAttribute": "testUrl",
  
  "imagetag": "cognita-nexus01:8001/newadder1:1",
  
  "solutionId": "02eab846-2bd0-4cfe8470-9fc69fa0d877",
  
  "solutionRevisionId": "a9e68bc6-f4b4-41c6-ae8e-4e97ec3916a6",
  
  "userId": "0505e537-ce79-4b1f-bf43-68d88933c369",
  
  "vmName": "Gold80"
}

**- Response:**

{
"status": "SUCCESS",
"UIDNumber": "Unique Transaction Number"
}
