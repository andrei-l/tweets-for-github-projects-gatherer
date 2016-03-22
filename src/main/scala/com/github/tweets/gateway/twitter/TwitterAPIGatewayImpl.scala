package com.github.tweets.gateway.twitter

import com.github.tweets.common.{TweetInfo, GitHubProjectInfo}
import com.github.tweets.gateway.TwitterAPIGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import TwitterApiDSL._

import scala.language.postfixOps

@Component
private[twitter] class TwitterAPIGatewayImpl @Autowired()(implicit val restTemplate: RestTemplate,
                                                          implicit val twitterAuthorizationData: TwitterAuthorizationData) extends TwitterAPIGateway {

  override def fetchTweets(projectInfo: GitHubProjectInfo): List[TweetInfo] = {

    val result = twitter search "tweets" `for` projectInfo.projectName and "github" please

    result.statuses
  }

}
