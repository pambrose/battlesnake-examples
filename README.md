#  Battlesnake Quickstart Snake Examples

[![Build Status](https://travis-ci.org/pambrose/battlesnake-examples.svg?branch=master)](https://travis-ci.org/pambrose/battlesnake-examples)

A collection of simple [Battlesnakes](http://battlesnake.io) written in Kotlin and Java using 
the [Battlesnake Quickstart](https://github.com/pambrose/battlesnake-quickstart) framework.

Visit [Battlesnake](https://docs.battlesnake.io) for API documentation and instructions for creating a game.

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Requirements
- Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Install [Kotlin](https://kotlinlang.org)
- Install [Gradle](https://gradle.org/install/)

## Snakes

### Kotlin
* [SimpleSnake](src/main/kotlin/io/battlesnake/examples/kotlin/SimpleSnake.kt)
* [CenterSquareSnake](src/main/kotlin/io/battlesnake/examples/kotlin/CenterSquareSnake.kt)
* [PerimeterSnake](src/main/kotlin/io/battlesnake/examples/kotlin/PerimeterSnake.kt)
* [StripedSnake](src/main/kotlin/io/battlesnake/examples/kotlin/StripedSnake.kt)

### Java
* [SimpleSnake](src/main/java/io/battlesnake/examples/java/SimpleSnake.java)
* [PerimeterSnake](src/main/java/io/battlesnake/examples/java/PerimeterSnake.java)


## Running a snake

Assign the `mainName` variable in [build.gradle](build.gradle#L28) to the desired snake classname.

Run the snake locally with: 
```bash
./gradlew run
```
Use [ngrok](https://ngrok.com) to make a locally running snake visible to the Battlesnake server.

## Executable Jar

Build an 
```bash
./gradlew stage
```

Will result in a jar file in `build/libs` called `battlesnake-examples.jar`

You can run this file with the command:

```bash
java -jar build/libs/battlesnake-examples.jar
```

## Deploying to Heroku

1) Create a new Heroku app with:
```
heroku create [APP_NAME]
```

2) Deploy code to Heroku servers with:
```
git push heroku master
```

3) Open Heroku app in browser with:
```
heroku open
```
or visit [http://APP_NAME.herokuapp.com](http://APP_NAME.herokuapp.com).

4) View server logs with the `heroku logs` command with:
```
heroku logs --tail
```

