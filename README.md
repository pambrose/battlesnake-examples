# Battlesnake Quickstart Snake Examples

[![Build Status](https://travis-ci.org/pambrose/battlesnake-examples.svg?branch=master)](https://travis-ci.org/pambrose/battlesnake-examples)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/026f7e49beb9432fbdf0cf47b5e40eb3)](https://www.codacy.com/app/pambrose/battlesnake-examples?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pambrose/battlesnake-examples&amp;utm_campaign=Badge_Grade)

A collection of simple [Battlesnakes](http://battlesnake.io) written in Kotlin and Java using 
the [Battlesnake Quickstart](https://github.com/pambrose/battlesnake-quickstart) framework.

Visit [Battlesnake.io](https://docs.battlesnake.io) for API documentation and instructions for creating a game.

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/pambrose/battlesnake-examples)
[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)
[![Run on Repl.it](https://repl.it/badge/github/pambrose/battlesnake-examples)](https://repl.it/github/pambrose/battlesnake-examples)

## Snake Definition

Visit [Battlesnake Quickstart](https://github.com/pambrose/battlesnake-quickstart) for a description
of how to define snakes for the framework.

## Snake Examples

### Kotlin
* [SimpleSnake](src/main/kotlin/io/battlesnake/examples/kotlin/SimpleSnake.kt)
* [PerimeterSnake](src/main/kotlin/io/battlesnake/examples/kotlin/PerimeterSnake.kt)
* [CenterSquareSnake](src/main/kotlin/io/battlesnake/examples/kotlin/CenterSquareSnake.kt)
* [StripedSnake](src/main/kotlin/io/battlesnake/examples/kotlin/StripedSnake.kt)

### Java
* [SimpleSnake](src/main/java/io/battlesnake/examples/java/SimpleSnake.java)
* [PerimeterSnake](src/main/java/io/battlesnake/examples/java/PerimeterSnake.java)

## Snake Choice

A server can support running multiple snakes, but they all must be the same snake object type.

Assign the `mainName` variable in [build.gradle](./build.gradle#L20) to the desired fully qualified 
snake classname. For example:
```groovy
def mainName = 'io.battlesnake.examples.kotlin.PerimeterSnake'
```

## Snake Execution

You can run a snake with a script or as an uberjar:

* Build and run the script **build/install/battlesnake-examples/bin/snake** with: `make script`.

* Build and run the uberjar **build/libs/snake.jar** with: `make uber`.

## Deployment Options

### localhost

Use [ngrok](https://ngrok.com) to make a locally running snake visible to the Battlesnake server.

1) Run ngrok with: `ngrok http 8080`

2) Use either of the `Forwarding` URLs displayed in the ngrok console as your snake URL.
 
3) Follow the [Snake Execution](#snake-execution) instructions to run a snake. 

### [Gitpod](https://gitpod.io)

1) Click on the [Open in Gitpod](https://gitpod.io/#https://github.com/pambrose/battlesnake-examples)
badge above.
 
2) Follow the [Snake Execution](#snake-execution) instructions to run a snake. 

3) After starting the snake, click on **Open Preview** on the pop-up window.

4) Use the URL displayed in the Gitpod browser window as your snake URL. You can
also visit the **/info** endpoint for more information.

### [repl.it](https://repl.it)

1) Click on the [run on repl.it](https://repl.it/github/pambrose/battlesnake-examples)
badge above.
 
2) Follow the [Snake Execution](#snake-execution) instructions to run a snake. 

3) Use the URL displayed in the repl.it browser window as your snake URL.
You can also visit the **/info** endpoint for more information.

### [Heroku](https://www.heroku.com)

1) Create a new Heroku app with: `heroku create [APP_NAME]`

2) Deploy code to Heroku with: `git push heroku master`

3) Open the Heroku app in a browser with: `heroku open` 
or visit [http://APP_NAME.herokuapp.com](http://APP_NAME.herokuapp.com).

4) Use the Heroku URL address as your snake URL. You can
also visit the **/info** endpoint for more information.

5) View the server logs with: `heroku logs --tail`

