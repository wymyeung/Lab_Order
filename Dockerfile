FROM anapsix/alpine-java:latest 
COPY target/order-0.5.3-K8sReady.jar app.jar
EXPOSE 9743 
ENTRYPOINT java -jar ./app.jar
