# Roadmap

We are considering several updates to this package, and would like your feedback on Github.

### Package Updates & Alternatives
-  Provide a Spring Boot release of CLAVIN-REST
-  Conduct more rigorous testing and analysis of micro-services on Jetty, Tomcat vs Deployable War releases on high performance cloud environments like Amazon Web Services using load balancing.
-  Incorporate better inline comments and usage examples for javadoc.

### API Changes
-  Include methods in CLAVIN that support the return of full (/geotag) or minified (/geotagmin) ResolvedLocation    
-  Enable Streaming APIs, which will require an update to CLAVIN to support streaming. Will result in better memory management. 
-  Incorporate a REST Documentation API for better documentation (-e.g. Swagger, RAML, etc)

### New functionality
-  Enable integration with AWS Services for stream processing -e.g. Kinesis, CloudWatch
-  Enable end users to easily switch out different natural language processors thru configuration
-  Enable end users to add or switch different Gazetteers. 