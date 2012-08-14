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

import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.auth.AuthScope
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity

/**
 * @author: Bob Florian
 */
class TwilioHttpClient  extends DefaultHttpClient
{
	public TwilioHttpClient(String host, Integer port, String username, String password)
	{
		super()
		getCredentialsProvider().setCredentials(
				new AuthScope(host, port),
				new UsernamePasswordCredentials(username, password));
	}

	void get(String url, arg1=null, arg2=null) {
		def params = arg2 ? arg1 : null
		def block = arg2 ? arg2 : arg1

		HttpGet get = new HttpGet(queryString(url,params));
		HttpResponse response = execute(get)
		block(response)

		connectionManager.shutdown()
	}

	void post(String url, arg1=null, arg2=null) {
		def params = arg2 ? arg1 : null
		def block = arg2 ? arg2 : arg1

		HttpPost post = new HttpPost(url);
		def nameValuePairs = params?.collect{new BasicNameValuePair(it.key, it.value as String)}
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = execute(post)
		block(response)

		connectionManager.shutdown()
	}

	private queryString(String url, data) {
		def sb = new StringBuffer(url)
		if (data) {
			def delim = '?'
			data.each {k,v->
				sb.append(delim)
				sb.append(k)
				sb.append('=')
				sb.append(v != null ? URLEncoder.encode(v.toString(), 'UTF-8') : null)
				delim = '&'
			}
		}
		sb.toString()
	}
}
