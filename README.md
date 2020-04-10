# Battlesnake Quickstart Snake Examples

[![Build Status](https://travis-ci.org/pambrose/battlesnake-examples.svg?branch=master)](https://travis-ci.org/pambrose/battlesnake-examples)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/026f7e49beb9432fbdf0cf47b5e40eb3)](https://www.codacy.com/app/pambrose/battlesnake-examples?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pambrose/battlesnake-examples&amp;utm_campaign=Badge_Grade)

A collection of simple [Battlesnakes](http://battlesnake.io) written in Kotlin and Java using 
the [Battlesnake Quickstart](https://github.com/pambrose/battlesnake-quickstart) framework.

Visit [Battlesnake](https://docs.battlesnake.io) for API documentation and instructions for creating a game.

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/pambrose/battlesnake-examples)
[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)
[![Run on Repl.it](https://repl.it/badge/github/pambrose/battlesnake-examples)](https://repl.it/github/pambrose/battlesnake-examples)

## Requirements
* Java 8+
* [Kotlin](https://kotlinlang.org)
* [Gradle](https://gradle.org/install/)

## Example Snakes

Visit [Battlesnake Quickstart](https://github.com/pambrose/battlesnake-quickstart) for a description
of the framework.

### Kotlin
* [SimpleSnake](src/main/kotlin/io/battlesnake/examples/kotlin/SimpleSnake.kt)
* [PerimeterSnake](src/main/kotlin/io/battlesnake/examples/kotlin/PerimeterSnake.kt)
* [CenterSquareSnake](src/main/kotlin/io/battlesnake/examples/kotlin/CenterSquareSnake.kt)
* [StripedSnake](src/main/kotlin/io/battlesnake/examples/kotlin/StripedSnake.kt)

### Java
* [SimpleSnake](src/main/java/io/battlesnake/examples/java/SimpleSnake.java)
* [PerimeterSnake](src/main/java/io/battlesnake/examples/java/PerimeterSnake.java)

## Specify desired snake

Assign the `mainName` variable in [build.gradle](./build.gradle) to the desired snake classname.
For example:
```groovy
def mainName = 'io.battlesnake.examples.kotlin.PerimeterSnake'
```

## Run a snake using a script

Build the snake locally with: 
```bash
make script
```

Run the snake locally with: 
```bash
build/install/battlesnake-examples/bin/snake
```

Use [ngrok](https://ngrok.com) to make a locally running snake visible to the Battlesnake server:
```bash
ngrok http 8080
```


## Run a snake using an executable uberJar

Build the uberjar `build/libs/snake.jar` with:

```bash
./gradlew build
```

Run the uberjar with:

```bash
java -jar build/libs/snake.jar
```

## Deploying to Heroku

1) Create a new Heroku app with:
```bash
heroku create [APP_NAME]
```

2) Deploy code to Heroku with:
```bash
git push heroku master
```

3) Open Heroku app in browser with:
```bash
heroku open
```
or visit [http://APP_NAME.herokuapp.com](http://APP_NAME.herokuapp.com).

4) View server logs with the `heroku logs` command with:
```bash
heroku logs --tail
```