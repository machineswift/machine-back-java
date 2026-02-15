## server
### machine-gateway-server
-it -d -p 19090:8080  --name machine-gateway-server-dev --cpus=1 -e JAVA_OPTS="-Xms256m -Xmx4096m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dreactor.netty.ioSelectCount=1 -Dreactor.netty.pool.maxConnections=96 -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-gateway-server -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine_gateway_server_dev:default


### machine-camunda-server
-it -d -p 19099:8080  --name machine-camunda-server-dev --cpus=0.4  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-camunda-server -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine-camunda-server-dev:default


## app
### machine-iam-app
-it -d -p 19092:8080 --name machine-iam-app-dev --cpus=0.4  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-iam-app -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-iam-app-dev:default

### machine-super-app
-it -d -p 19099:8080 --name machine-super-app-dev --cpus=0.4   -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-super-app -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine-super-app-dev:default

### machine-manage-app
-it -d -p 19094:8080 --name machine-manage-app-dev --cpus=0.4   -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-manage-app -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-manage-app-dev:default

### machine-openapi-app
-it -d -p 19091:8080 --name machine-openapi-app-dev --cpus=0.4   -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-openapi-app -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-openapi-app-dev:default

### machine-mq-app
-it -d -p 19084:8080 --name machine-mq-app-dev --cpus=0.4  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-mq-app -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine-mq-app-dev:default

## service
### machine-iam-service
-it -d -p 19093:8080 --name machine-iam-service-dev --cpus=0.4  -e JAVA_OPTS="-Xms256m -Xmx3072m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-iam-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine-iam-service-dev:default

### machine-data-service
-it -d -p 19096:8080  --name machine-data-service-dev --cpus=0.4 -e JAVA_OPTS="-Xms256m -Xmx3072m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-data-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-data-service-dev:default

### machine-hrm-service
-it -d -p 19089:8080 --name machine-hrm-service-dev --cpus=0.2  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-hrm-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-hrm-service-dev:default

### machine-crm-service
-it -d -p 19095:8080 --name machine-crm-service-dev --cpus=0.2  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-crm-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-crm-service-dev:default

### machine-scm-service
-it -d -p 19095:8080 --name machine-scm-service-dev --cpus=0.2  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-scm-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-scm-service-dev:default

### machine-tpp-service
-it -d -p 19095:8080 --name machine-tpp-service-dev --cpus=0.2  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-tpp-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800"  /machine-tpp-service-dev:default

### machine-plugin-service
-it -d -p 19085:8080 --name machine-plugin-service-dev --cpus=0.2  -e JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 -Dspring.profiles.active=DEV -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL -Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 -Dmachine.nacos.username=nacos -Dmachine.nacos.password=nacos"  -e SKY_AGENT="-javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.logging.dir=/opt/skywalking-agent/logs/ -Dskywalking.agent.service_name=machine-plugin-service -Dskywalking.collector.backend_service=XXX.XXX.XXX.XXX:11800" /machine-plugin-service-dev:default
