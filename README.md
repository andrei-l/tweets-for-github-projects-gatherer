# Tweets Gatherer For GitHub Projects
Simple console app in Scala which fetch tweets for most popular GitHub repositories by `keyword`.

Application first searches GitHub for `keyword`, sort projects by stars, limit the number of retrieved project by 10
and then fetches most recent tweets for these projects.

### Implementation details
App is built using SBT + Scala + Spring Boot.

### Build Details
Application can be run via either `com.github.tweets.TweetsGatherer` Main Class or via the built Jar file.

1. In order to build the jar file call

`sbt clean oneJar`
2. in order to launch the jar call

`java -jar tweets-for-github-projects-gatherer_2.11-1.0-one-jar.jar --keyword="star wars" --output=out1.json`
3. `keyword` argument is mandatory. App will fail without it. If no `ouput` argument is provided, then json will be printed in console
4. there should application.properties file next to the jar with two properties e.g.

```
twitter.consumer.key=xvz1evFS4wEEPTGEFPHBog
twitter.consumer.secret=L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg
```
otherwise program will fail. Key and Secret should be valid twitter keys. They are private so they are not provided.
5. in order to launch tests call

`sbt test`