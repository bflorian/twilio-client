/*
 * Copyright 2012 ReachLocal Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reachlocal.grails.plugins.twilio

import org.apache.http.HttpEntity
import groovy.json.JsonSlurper
import org.springframework.beans.factory.InitializingBean

class TwilioService implements InitializingBean
{
	static transactional = false

	def grailsApplication

	def scheme
	def hostname
	def version
	def port
	def accountSid
	def authToken

	void afterPropertiesSet ()
	{
		def config = grailsApplication.config.twilio
		scheme = config?.scheme ?: "https"
		hostname = config?.hostname ?: "api.twilio.com"
		version = config?.version ?: "2010-04-01"
		port = config?.port ?:  443
		accountSid = config?.accountSid ?: System.getProperty("twilio.sid")
		authToken = config?.authToken ?: System.getProperty("twilio.token")
	}

	/**
	 * Make a GET request to the Twilio REST API
	 * @param path Method path, i.e. 'Recordings'
	 * @param params Map of input parameter values
	 * @return Parsed JSON objects
	 */
	def get(String path, Map params=[:])
	{
		def result = null
		def http = new TwilioHttpClient(hostname, port, accountSid, authToken)
		http.get(uri(path), params) {response ->
			HttpEntity entity = response.entity
			result = new JsonSlurper().parse(new InputStreamReader(entity.content, "UTF-8"))
		}
		return result
	}

	/**
	 * Make a POST request to the Twilio REST API
	 * @param path Method path, i.e. 'SMS/Messages'
	 * @param params Map of input parameter values
	 * @return Parsed JSON objects
	 */
	def post(String path, Map params=[:])
	{
		def result = null
		def http = new TwilioHttpClient(hostname, port, accountSid, authToken)
		http.post(uri(path), params) {response ->
			HttpEntity entity = response.entity
			result = new JsonSlurper().parse(new InputStreamReader(entity.content, "UTF-8"))
		}
		return result
	}

	private uri(String path)
	{
		"${scheme}://${hostname}/${version}/Accounts/${accountSid}/${path}.json".toString()
	}
}
