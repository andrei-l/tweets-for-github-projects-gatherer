package com.github.tweets

import java.io.File

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tweets.data.ProjectDataGatherer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{ApplicationArguments, ApplicationRunner, SpringApplication}
import org.springframework.context.annotation.ComponentScan

import scala.collection.JavaConversions._

@SpringBootApplication
@ComponentScan(Array("com.github.tweets"))
private class TweetsGatherer extends ApplicationRunner {
  final val ConsoleOutputLocation = "console"

  @Autowired
  val projectDataGatherer: ProjectDataGatherer = null

  @Autowired
  val objectMapper: ObjectMapper = null

  override def run(applicationArguments: ApplicationArguments): Unit = {
    val searchKeyword = expectSearchKeyword(applicationArguments)
    val outputLocation = expectOutputLocation(applicationArguments)

    val result = projectDataGatherer.gatherProjectData(searchKeyword)

    if (outputLocation == ConsoleOutputLocation)
      objectMapper.writeValue(System.out, result)
    else
      objectMapper.writeValue(new File(outputLocation), result)
  }

  private def expectParam(applicationArguments: ApplicationArguments)(paramName: String)(defaultValue: => String) = {
    if (applicationArguments.containsOption(paramName))
      applicationArguments.getOptionValues(paramName).head
    else
      defaultValue
  }

  private def expectSearchKeyword(applicationArguments: ApplicationArguments) =
    expectParam(applicationArguments)("keyword")(throw new RuntimeException("expected 'keyword' argument"))


  private def expectOutputLocation(applicationArguments: ApplicationArguments) =
    expectParam(applicationArguments)("output")(ConsoleOutputLocation)
}

object TweetsGatherer {
  def main(args: Array[String]): Unit =
    SpringApplication.run(classOf[TweetsGatherer], args: _*)
}