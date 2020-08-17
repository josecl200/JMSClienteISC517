FROM openjdk:11-jdk-slim
LABEL maintainer="José C. Ureña <20160138@cce.pucmm.edu.do>"
ENV NOMBRE_APP = 'clienteJMS'
EXPOSE 4567
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
