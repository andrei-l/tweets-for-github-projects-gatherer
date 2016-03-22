package com.github.tweets.gateway

import com.github.tweets.common.{TweetInfo, GitHubProjectInfo}

private[tweets] trait TwitterAPIGateway {
  def fetchTweets(projectInfo: GitHubProjectInfo): List[TweetInfo]
}
