grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		compile 'org.apache.httpcomponents:httpcore:4.1.3'
		compile 'org.apache.httpcomponents:httpclient:4.1.2'
        // runtime 'mysql:mysql-connector-java:5.1.5'
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:1.0.1") {
            export = false
        }
    }
}

grails.project.repos.beanstalkRepository.url = "https://mural.svn.beanstalkapp.com/grails-plugins"
grails.project.repos.beanstalkRepository.type = "svn"

grails.project.repos.dreamhostRepository.url = "http://cm.florian.org/grailsplugins"
grails.project.repos.dreamhostRepository.type = "svn"

grails.project.repos.default = "beanstalkRepository"
grails.release.scm.enabled = false