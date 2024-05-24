IMAGE_NAME = ghostshadow2/mail-service

all: clean build docker-build

clean:
		mvn clean

build:
		mvn package

docker-build:
		docker build -t $(IMAGE_NAME) .

.PHONY: all clean build docker-build
