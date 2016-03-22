package com.github.tweets.gateway

import com.github.tweets.common.GitHubProjectInfo

private[tweets] trait GitHubAPIGateway {
  def fetchTopProjects(keyword: String): Seq[GitHubProjectInfo]
}
