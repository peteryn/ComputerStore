FROM maven AS stage1
COPY . .
RUN mvn clean package -Dmaven.test.skip -Dspring.profiles.active=production
# CMD ["ls", "/target"]

FROM tomcat:latest
COPY --from=stage1 /target/userauthdemo.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
# CMD ["ls", "/usr/local/tomcat/webapps"]
