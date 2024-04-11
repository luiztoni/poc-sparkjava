FROM docker.io/eclipse-temurin:22-jre-alpine
ARG FAT_JAR=target/*.jar
RUN mkdir /opt/app
COPY ${FAT_JAR} /opt/app/app.jar
EXPOSE 8080
ENV JVM_ARGS="-Xmx256m"
ENV PG_HOST="localhost"
ENV PG_USER="postgres"
ENV PG_PWD="pgroot"
ENV PG_PORT="5432"
ENV PG_DB="pgdb"
ENV ADMIN_EMAIL="admin@admin.com"
ENV ADMIN_PASSWORD="password"
ENTRYPOINT ["sh", "-c", "java ${JVM_ARGS} -jar /opt/app/app.jar"]
