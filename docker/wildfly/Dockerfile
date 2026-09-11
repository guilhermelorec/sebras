FROM quay.io/wildfly/wildfly:latest

USER root

COPY lib/ojdbc11-23.9.0.25.07.jar /opt/jboss/wildfly/modules/com/oracle/ojdbc/main/ojdbc11.jar
COPY docker/wildfly/module.xml /opt/jboss/wildfly/modules/com/oracle/ojdbc/main/module.xml
COPY docker/wildfly/configure.cli /tmp/configure.cli

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/tmp/configure.cli \
    && rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history \
    && chown -R jboss:jboss /opt/jboss/wildfly/modules /opt/jboss/wildfly/standalone

COPY target/sebras.war /opt/jboss/wildfly/standalone/deployments/sebras.war
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/deployments/sebras.war

USER jboss
