./build.sh

sudo docker run --name=trax --publish=80:80 \
     trax:latest
