(cd ../trax-angular1-springbootbackend-jpa/ && ./gradlew bootJar)

mkdir tmp
cp ../trax-angular1-springbootbackend-jpa/build/libs/trax-backend-0.0.1-SNAPSHOT.jar tmp/trax.jar

sudo docker build --tag=alpine-java:base --rm=true . --file=Dockerfile.javabase 
sudo docker build --tag=trax:latest --rm=true . --file=Dockerfile.springboot

rm tmp -Rf
