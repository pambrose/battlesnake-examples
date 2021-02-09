VERSION=0.2.0

default: build-all

build-all: clean stage

clean:
	./gradlew clean

compile: build

build:
	./gradlew build -xtest

uberjar:
	./gradlew uberjar

uber: uberjar
	java -jar build/libs/snake.jar

dist:
	./gradlew installDist

script: dist
	build/install/battlesnake-examples/bin/snake

stage:
	./gradlew stage

build-docker:
	docker build -t pambrose/battlesnake-examples:${VERSION} .

run-docker:
	docker run -p 8080:8080 pambrose/battlesnake-examples:${VERSION}

push-docker:
	docker push pambrose/battlesnake-examples:${VERSION}

versioncheck:
	./gradlew dependencyUpdates

upgrade-wrapper:
	./gradlew wrapper --gradle-version=6.8.2 --distribution-type=bin