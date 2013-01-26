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

package org.springframework.jmx;


public class JmxNoAnnotationsTestBean implements JmxNoAnnotationsTestBeanMBean {

	protected String name;
	protected String address;

	private int age;


	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public long myOperation() {
		return 1L;
	}

	@Override
	public void publishDetails(String name, String address) throws Exception {
		this.name = name;
		this.address = address;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int add(int x, int y) {
		return x + y;
	}

	protected void someProtectedMethod() {
	}

	@SuppressWarnings("unused")
	private void somePrivateMethod() {
	}

	protected void getSomething() {
	}

	@SuppressWarnings("unused")
	private void getSomethingElse() {
	}

}