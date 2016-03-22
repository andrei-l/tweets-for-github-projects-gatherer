package com.github.tweets.gateway.twitter

import com.github.tweets.common.{TweetInfo, GitHubProjectInfo}
import org.mockito.{ArgumentCaptor, Matchers, Mockito}
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.springframework.http.{HttpEntity, HttpMethod, ResponseEntity}
import org.springframework.web.client.RestTemplate

import scala.collection.JavaConversions._

//noinspection ComparingUnrelatedTypes
class TwitterAPIGatewayImplTest extends FunSuite with MockitoSugar {
  implicit val restTemplate = mock[RestTemplate]
  implicit val twitterAuthorizationData = mock[TwitterAuthorizationData]

  val projectInfo = mock[GitHubProjectInfo]
  val tweet1 = mock[TweetInfo]
  val tweet2 = mock[TweetInfo]
  val projectsCollection = TweetInfoCollectionWrapper(List(tweet1, tweet2))
  val tweetCollectionEntity = mock[ResponseEntity[TweetInfoCollectionWrapper]]


  val authorizationHttpEntityCaptor = ArgumentCaptor.forClass(classOf[HttpEntity[String]])
  val regularHttpEntityCaptor = ArgumentCaptor.forClass(classOf[HttpEntity[String]])

  val gateway = new TwitterAPIGatewayImpl

  test("should authorize, create appropriate request, execute it and fetch tweets for project") {

    expectations()
    val result = gateway.fetchTweets(projectInfo)

    assert(result === List(tweet1, tweet2))
    methodCallsVerifications()
    headersVerifications()
  }

  def expectations(): Unit = {
    Mockito.when(twitterAuthorizationData.bearerTokenCredentials).thenReturn("bearer token")
    Mockito.when(restTemplate.postForObject(
      Matchers.eq("https://api.twitter.com/oauth2/token"),
      Matchers.any(classOf[HttpEntity[String]]),
      Matchers.eq(classOf[TwitterAuthorizationResponse])
    )).thenReturn(TwitterAuthorizationResponse("", "access"))
    Mockito.when(projectInfo.projectName).thenReturn("project23")
    Mockito.when(restTemplate.exchange(
      Matchers.eq("https://api.twitter.com/1.1/search/tweets.json?q=project23 AND github"),
      Matchers.eq(HttpMethod.GET),
      Matchers.any(classOf[HttpEntity[String]]),
      Matchers.eq(classOf[TweetInfoCollectionWrapper])
    )).thenReturn(tweetCollectionEntity)

    Mockito.when(tweetCollectionEntity.getBody).thenReturn(projectsCollection)

    Mockito.when(
      restTemplate.getForObject("https://api.github.com/search/repositories?q=word&sort=stars",
        classOf[TweetInfoCollectionWrapper])
    ).thenReturn(projectsCollection)
  }

  def methodCallsVerifications(): Unit = {
    Mockito.verify(restTemplate).postForObject(
      Matchers.eq("https://api.twitter.com/oauth2/token"),
      authorizationHttpEntityCaptor.capture(),
      Matchers.eq(classOf[TwitterAuthorizationResponse])
    )
    Mockito.verify(projectInfo).projectName
    Mockito.verify(restTemplate).exchange(
      Matchers.eq("https://api.twitter.com/1.1/search/tweets.json?q=project23 AND github"),
      Matchers.eq(HttpMethod.GET),
      regularHttpEntityCaptor.capture(),
      Matchers.eq(classOf[TweetInfoCollectionWrapper])
    )
    Mockito.verify(tweetCollectionEntity).getBody
  }

  def headersVerifications(): Unit = {
    val authorizationHeaders = authorizationHttpEntityCaptor.getValue.getHeaders
    assert(authorizationHeaders.toList ===
      List(
        "Content-Type" -> seqAsJavaList(List("application/x-www-form-urlencoded")),
        "Authorization" -> seqAsJavaList(List("Basic bearer token")))
    )
    val regularHeaders = regularHttpEntityCaptor.getValue.getHeaders
    assert(regularHeaders.toList ===
      List(
        "Content-Type" -> seqAsJavaList(List("application/x-www-form-urlencoded")),
        "Authorization" -> seqAsJavaList(List("Bearer access"))
      )
    )
  }
}
