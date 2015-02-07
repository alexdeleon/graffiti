# Graffiti

Graffiti is a utility library that allows you to use [Spray](http://spray.io) with a common pattern for building REST services.

## Install

```
com.github.alexdeleon % graffiti % 0.0.1
```

## Use
```scala
class HelloService extends Service("hello" ) {

  route {
    get {
      (pathEnd | path("")) {
        complete("Hello you")
      } ~
      path(Rest) { name =>
        complete(s"Hello $name")
      }
    }
  }
}

class ByeService extends Service {
  route {
    path("bye") {
      complete("bye")
    }
  }
}


object GreetingApp extends App {

  implicit val system = ActorSystem("greeting-app")

  val hello = new HelloService
  val bye = new ByeService
  val api = system.actorOf(ApiActor(Api(hello(), bye())))

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(api, interface = "localhost", port = 8080)
}
```

## License

The MIT License (MIT)

Copyright (c) 2015 Alexander De Leon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.