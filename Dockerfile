FROM java:8-jre
MAINTAINER Aboubacar Toure

ADD ./build/libs/shoppingcart-service-1.0-SNAPSHOT.jar /service/
CMD ["java", "-Xmx200m", "-jar", "/service/shoppingcart-service-1.0-SNAPSHOT.jar"]

EXPOSE 8089
