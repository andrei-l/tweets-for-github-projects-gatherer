package com.github.tweets.gateway.twitter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.tweets.gateway.DataLoader
import org.scalatest.FunSuite

class TweetInfoCollectionWrapperTest extends FunSuite {
  test("verify jackson can map twitter response") {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)

    val parsedResponse = om.readValue(twitterSampleResponse, classOf[TweetInfoCollectionWrapper])
    assert(parsedResponse.statuses.size === 15)
  }

  private def twitterSampleResponse = DataLoader.loadText("TwitterSampleResponse.json")
}
