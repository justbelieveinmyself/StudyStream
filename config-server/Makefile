IMAGE_NAME = ghostshadow2/config-server

all: clean build docker-build docker-push

clean:
	mvn clean

build:
	mvn package

docker-build:
	docker build -t $(IMAGE_NAME) .

docker-push:
	docker push $(IMAGE_NAME)

.PHONY: all clean build docker-build docker-push