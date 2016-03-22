package com.github.tweets.gateway.github

import com.github.tweets.common.GitHubProjectInfo
import com.github.tweets.gateway.GitHubAPIGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype
import org.springframework.web.client.RestTemplate
import GitHubApiDSL._

import scala.language.postfixOps

@stereotype.Component
private[github] class GitHubAPIGatewayImpl @Autowired()(implicit restTemplate: RestTemplate) extends GitHubAPIGateway {

  override def fetchTopProjects(keyword: String): Seq[GitHubProjectInfo] = {
    val result = github search "repositories" `for` keyword sortBasedOn "stars" please

    result.items.take(10)
  }
}