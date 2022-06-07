FROM tomcat:latest
ADD target/smulti.war /usr/local/tomcat/webapps/
#ADD ../u-client/dist usr/local/tomcat/webapps/ui
EXPOSE 8080
CMD ["catalina.sh", "run"]