package com.github.tweets.gateway.github

import com.github.tweets.gateway.dsl.{ApiDSLBaseObj, ApiDSL}
import org.springframework.web.client.RestTemplate

import scala.language.implicitConversions

private[github] class GitHubApiDSL(override val url: StringBuilder = StringBuilder.newBuilder)
                                  (override implicit val restTemplate: RestTemplate) extends ApiDSL[GitHubApiDSL] {
  def sortBasedOn(byWhat: String) = ?& ++= "sort=" ++= byWhat

  override def please: GitHubApiDSL = this
}

private[github] object GitHubApiDSL extends ApiDSLBaseObj[GitHubApiDSL] {
  final val GitHubUrlBase = "https://api.github.com"

  def github(implicit restTemplate: RestTemplate) = stringToApiDSL(GitHubUrlBase)

  override def apply(sb: StringBuilder)(implicit restTemplate: RestTemplate): GitHubApiDSL = new GitHubApiDSL(sb)

  implicit def dslToResult(dsl: GitHubApiDSL): GitHubProjectInfoCollectionWrapper =
    dsl.restTemplate.getForObject(dsl.url, classOf[GitHubProjectInfoCollectionWrapper])
}