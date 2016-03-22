package com.github.tweets.data

import com.github.tweets.common.{ProjectData, TweetInfo, GitHubProjectInfo}
import com.github.tweets.gateway.{TwitterAPIGateway, GitHubAPIGateway}
import org.mockito.Mockito
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

class ProjectDataGathererImplTest extends FunSuite with MockitoSugar {
  val gitHubAPIGateway = mock[GitHubAPIGateway]
  val twitterAPIGateway = mock[TwitterAPIGateway]

  val project1 = mock[GitHubProjectInfo]
  val project2 = mock[GitHubProjectInfo]
  val githubProjects = List(project1, project2)

  val tweet1 = mock[TweetInfo]
  val tweet2 = mock[TweetInfo]
  val tweet3 = mock[TweetInfo]

  val tweetsForProject1 = List(tweet1, tweet2)
  val tweetsForProject2 = List(tweet3)

  val dataGatherer = new ProjectDataGathererImpl(gitHubAPIGateway, twitterAPIGateway)

  test("should gather project data for 'random' keyword") {
    Mockito.when(gitHubAPIGateway.fetchTopProjects("random")).thenReturn(List(project1, project2))
    Mockito.when(twitterAPIGateway.fetchTweets(project1)).thenReturn(tweetsForProject1)
    Mockito.when(twitterAPIGateway.fetchTweets(project2)).thenReturn(tweetsForProject2)

    val result = dataGatherer.gatherProjectData("random")

    assert(result === List(ProjectData(project1, tweetsForProject1), ProjectData(project2, tweetsForProject2)))
  }
}
