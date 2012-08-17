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
import groovy.xml.MarkupBuilder

class TwilioClientGrailsPlugin
{
	def observe = ['controllers']

	// the plugin version
	def version = "0.0.2"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "2.0 > *"
	// the other plugins this plugin depends on
	def dependsOn = [:]
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
			"grails-app/views/error.gsp"
	]

	def title = "Twilio Client Plugin" // Headline display name of the plugin
	def author = "Bob Florian"
	def authorEmail = "bob.florian@reachlocal.com"
	def description = '''\
Adds lighweight Twilio REST service and support for TwiML responses via Groovy MarkupBuilder.
'''

	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/twilio-client"

	// Extra (optional) plugin metadata

	// License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

	// Details of company behind the plugin (if there is one)
    def organization = [ name: "ReachLocal", url: "http://reachlocal.com/" ]

	// Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

	// Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

	// Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

	def doWithWebDescriptor = { xml ->
		// Implement additions to web.xml (optional), this event occurs before
	}

	def doWithSpring = {
		// Implement runtime spring config (optional)
	}

	def doWithDynamicMethods = { ctx ->

		addDynamicMethods(application)
	}

	def doWithApplicationContext = { applicationContext ->
		// Implement post initialization spring config (optional)
	}

	def onChange = { event ->
		// Implement code that is executed when any artefact that this plugin is
		// watching is modified and reloaded. The event contains: event.source,
		// event.application, event.manager, event.ctx, and event.plugin.
		addDynamicMethods(application)
	}

	def onConfigChange = { event ->
		// Implement code that is executed when the project configuration changes.
		// The event is the same as for 'onChange'.
	}

	def onShutdown = { event ->
		// Implement code that is executed when the application shuts down (optional)
	}

	private static addDynamicMethods(application)
	{
		application.controllerClasses.toList()*.metaClass*.renderTwiML = {block ->
			delegate.render(contentType: "text/xml", encoding: "UTF-8", block)
			delegate.log.info("action=${delegate.params?.action}, params=${delegate.params}, response=\n${resp}")
		}
	}
}
