FROM tomcat:9-jre8-alpine
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]

ADD ./target/*.war  /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080