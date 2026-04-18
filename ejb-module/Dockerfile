FROM quay.io/wildfly/wildfly:latest

# copiar o deploy
COPY target/ejb-module-1.0.0.jar /opt/jboss/wildfly/standalone/deployments/

# expor porta
EXPOSE 8083

# subir servidor
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]