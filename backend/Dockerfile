FROM gradle:8.10.2-jdk21 as build
WORKDIR /app
ADD src src
ADD . .

RUN gradle build -x test
RUN ls build

FROM eclipse-temurin:21

ARG PROFILE
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_URL
ARG KC_URL
ARG TODO_CLIENT_SECRET
ARG ADMIN_CLIENT_SECRET

ENV QUARKUS_PROFILE=$PROFILE
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD
ENV DB_URL=$DB_URL
ENV KC_URL=$KC_URL
ENV TODO_CLIENT_SECRET=$TODO_CLIENT_SECRET
ENV ADMIN_CLIENT_SECRET=$ADMIN_CLIENT_SECRET

ENV TZ=America/Fortaleza

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Duser.timezone=America/Fortaleza"

# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --from=build --chown=185 /app/build/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /app/build/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /app/build/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /app/build/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENTRYPOINT [ "java", "-jar", "/deployments/quarkus-run.jar" ]
