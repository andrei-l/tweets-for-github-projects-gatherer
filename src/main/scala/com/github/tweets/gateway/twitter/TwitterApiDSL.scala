package com.github.tweets.gateway.twitter

import com.github.tweets.gateway.dsl.{ApiDSL, ApiDSLBaseObj}
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod, MediaType}
import org.springframework.web.client.RestTemplate

import scala.language.implicitConversions

private[twitter] class TwitterApiDSL(override val url: StringBuilder = StringBuilder.newBuilder)
                                    (override implicit val restTemplate: RestTemplate) extends ApiDSL[TwitterApiDSL] {
  override def search(where: String) = super.search(where) ++= ".json"

  def and(what: String) = url ++= " AND " ++= what

  override def please = this
}

private[twitter] object TwitterApiDSL extends ApiDSLBaseObj[TwitterApiDSL] {
  final val TwitterUrlBase = "https://api.twitter.com"
  final val ApiVersion = "1.1"

  def twitter(implicit restTemplate: RestTemplate) = stringToApiDSL(s"$TwitterUrlBase/$ApiVersion")

  override def apply(sb: StringBuilder)(implicit restTemplate: RestTemplate): TwitterApiDSL = new TwitterApiDSL(sb)

  implicit def dslToResult(requestBuilder: TwitterApiDSL)
                          (implicit authorizationData: TwitterAuthorizationData): TweetInfoCollectionWrapper = {
    val restTemplate = requestBuilder.restTemplate
    val headers = new HttpHeaders()
    val token = accessToken.getOrElseUpdate(AnyRef, authorize(restTemplate, authorizationData, headers))
    headers.set(HttpHeaders.AUTHORIZATION, s"Bearer $token")

    restTemplate.exchange(
      requestBuilder.url,
      HttpMethod.GET,
      new HttpEntity[String](headers),
      classOf[TweetInfoCollectionWrapper]
    ).getBody
  }

  private val accessToken = scala.collection.mutable.Map[AnyRef, String]()

  private def authorize(restTemplate: RestTemplate, authorizationData: TwitterAuthorizationData, headers: HttpHeaders): String = {
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
    headers.set(HttpHeaders.AUTHORIZATION, s"Basic ${authorizationData.bearerTokenCredentials}")

    val authEntity = new HttpEntity(TwitterAuthorizationRequest(), headers)
    val authResponse = restTemplate
      .postForObject(s"$TwitterUrlBase/oauth2/token", authEntity, classOf[TwitterAuthorizationResponse])
    authResponse.accessToken
  }
}