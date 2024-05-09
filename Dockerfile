FROM tomcat:8.5.66

COPY ./server/target/server.jar /usr/maven-project/

WORKDIR /usr/maven-project/

CMD [ "java" , "-jar" , "server.jar" ]