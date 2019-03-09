VERSION=0.1.0
default: build-all

build-all: clean stage

clean:
	./gradlew clean

stage:
	./gradlew stage

build-docker:
	docker build -t pambrose/simple-snake:${VERSION} .

run-docker:
	docker run -p 8080:8080 pambrose/simple-snake:${VERSION}

push-docker:
	docker push pambrose/simple-snake:${VERSION}


