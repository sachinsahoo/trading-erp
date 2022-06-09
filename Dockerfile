FROM tomcat:latest
ADD ./spring-boot-multitenant/target/smulti.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]