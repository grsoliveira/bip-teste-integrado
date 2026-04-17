FROM quay.io/wildfly/wildfly:latest-jdk17
#FROM quay.io/wildfly/wildfly-preview:latest

# copiar o deploy
COPY target/ejb-module-1.0.0.jar /opt/jboss/wildfly/standalone/deployments/

# criar usuário automaticamente
# RUN /opt/jboss/wildfly/bin/add-user.sh appuser app123 --silent
RUN /opt/jboss/wildfly/bin/add-user.sh -a appuser app123 --silent

# expor porta
EXPOSE 8080
EXPOSE 8009
EXPOSE 9990

# subir servidor
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-full.xml"]
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-c", "standalone-full.xml"]