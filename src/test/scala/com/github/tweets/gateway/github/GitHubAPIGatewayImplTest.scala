package com.github.tweets.gateway.github

import com.github.tweets.common.GitHubProjectInfo
import org.mockito.Mockito
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.springframework.web.client.RestTemplate

class GitHubAPIGatewayImplTest extends FunSuite with MockitoSugar {
  implicit val restTemplate = mock[RestTemplate]

  val projectInfo = mock[GitHubProjectInfo]
  val projectsCollection = GitHubProjectInfoCollectionWrapper(List.fill(15)(projectInfo))

  val gateway = new GitHubAPIGatewayImpl

  test("should create appropriate request, execute it and fetch top 10 projects") {
    assert(projectsCollection.items.size === 15)

    Mockito.when(
      restTemplate.getForObject("https://api.github.com/search/repositories?q=word&sort=stars", classOf[GitHubProjectInfoCollectionWrapper])
    ).thenReturn(projectsCollection)
    val result = gateway.fetchTopProjects("word")

    assert(result === List.fill(10)(projectInfo))


    Mockito.verify(restTemplate)
      .getForObject("https://api.github.com/search/repositories?q=word&sort=stars", classOf[GitHubProjectInfoCollectionWrapper])
  }

}
