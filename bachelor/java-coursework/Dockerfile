FROM gradle:latest as builder

COPY . /home/gradle/project
USER root
RUN cd /home/gradle/project && \
    gradle war  --no-daemon --console plain

FROM jboss/wildfly

COPY --from=builder /home/gradle/project/build/libs/*.war /opt/jboss/wildfly/standalone/deployments/
