package com.github.tweets.gateway.twitter

import java.nio.charset.Charset
import java.util.Base64

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

private[twitter] object TwitterAuthorizationRequest {
  def apply(): String = "grant_type=client_credentials"
}

private[twitter] case class TwitterAuthorizationResponse(@JsonProperty("token_type") tokenType: String,
                                                         @JsonProperty("access_token") accessToken: String)

@Component
private[twitter] class TwitterAuthorizationData {

  @Value("${twitter.consumer.key}")
  private val twitterConsumerKey = ""

  @Value("${twitter.consumer.secret}")
  private val twitterConsumerSecret = ""

  lazy val bearerTokenCredentials =
    encodeBase64(s"$twitterConsumerKey:$twitterConsumerSecret")

  private def encodeBase64(s: String): String =
    Base64.getEncoder.encodeToString(s.getBytes(Charset.defaultCharset()))
}
