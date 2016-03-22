package com.github.tweets.gateway.github

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.tweets.gateway.DataLoader
import org.scalatest.FunSuite

class GitHubProjectInfoCollectionWrapperTest extends FunSuite {
  test("verify jackson can map github response") {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)

    val parsedResponse = om.readValue(gitHubSampleResponse, classOf[GitHubProjectInfoCollectionWrapper])
    assert(parsedResponse.items.size === 4)
  }

  private def gitHubSampleResponse = DataLoader.loadText("GitHubSampleResponse.json")
}

