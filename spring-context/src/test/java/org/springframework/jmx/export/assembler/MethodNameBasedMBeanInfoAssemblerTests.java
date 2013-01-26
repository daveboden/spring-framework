/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.jmx.export.assembler;

import java.util.Arrays;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;

import org.springframework.jmx.support.JmxUtils;

/**
 * @author Rob Harrop
 * @author David Boden
 */
public class MethodNameBasedMBeanInfoAssemblerTests extends AbstractJmxAssemblerTests {

	protected static final String OBJECT_NAME = "bean:name=testBean5";

	@Override
	protected String getObjectName() {
		return OBJECT_NAME;
	}

	@Override
	protected int getExpectedOperationCount() {
		return 5;
	}

	@Override
	protected int getExpectedAttributeCount() {
		return 2;
	}

	@Override
	protected MBeanInfoAssembler getAssembler() {
		MethodNameBasedMBeanInfoAssembler assembler = new MethodNameBasedMBeanInfoAssembler();
		assembler.setManagedMethods(new String[] {"add", "myOperation", "getName", "setName", "getAge"});
		return assembler;
	}

	public void testGetAgeIsReadOnly() throws Exception {
		ModelMBeanInfo info = getMBeanInfoFromAssembler();
		ModelMBeanAttributeInfo attr = info.getAttribute(AGE_ATTRIBUTE);

		assertTrue(attr.isReadable());
		assertFalse(attr.isWritable());
	}

	public void testSetNameParameterIsNamed() throws Exception {
		ModelMBeanInfo info = getMBeanInfoFromAssembler();

		MBeanOperationInfo operationSetName = info.getOperation("setName");
		assertEquals("name", operationSetName.getSignature()[0].getName());
	}

	public void testSetNameParameterIsNamedWithNoAnnotations() throws Exception {
		MBeanServer server = JmxUtils.locateMBeanServer();
		System.out.println(Arrays.toString(server.getDomains()));
		MBeanInfo info = server.getMBeanInfo(new ObjectName("bean:name=testBean6"));

		for(MBeanOperationInfo operation : info.getOperations()) {
			if(operation.getName().equals("publishDetails")) {
				assertEquals("name", operation.getSignature()[0].getName());
				return;
			}
		}
		
		fail("Didn't find publishDetails operation");
	}

	@Override
	protected String getApplicationContextPath() {
		return "org/springframework/jmx/export/assembler/methodNameAssembler.xml";
	}

}
