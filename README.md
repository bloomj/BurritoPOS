Burrito Point of Service
================================


Overview
-------------------------

This is simple Point of Service application for a small town burrito joint.  
It serves as a starting point to refresh memory of or learn about new/existing languages, frameworks, design patterns, etc.

Currently demonstrates
-------------------------
* Eclipse
* Java
* Ant
* JUnit
* Hibernate
* Spring
* MySQL
* Factory, Facade Patterns
* N-tier architecture
* client/server comms (Cleartext & TLS)
* Mongo (NoSQL)
* Javadoc
  
TODO
-------------------------
* Move connection strings, other properties to properties file
* Encrypt passwords
* Refactor to package by feature instead of by layer
  
Notes
-------------------------
* junit dlls were placed in ant\lib for proper ant build
* If a javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: 
	PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target 
	error occurs, check the keystore against the servername to ensure the certificate exists (trust is currently ignored)
* keytool -genkey -keyalg RSA -alias <machine_name> -keystore <pathtokeystore>.jks -storepass changeit -validity 360 -keysize 4096