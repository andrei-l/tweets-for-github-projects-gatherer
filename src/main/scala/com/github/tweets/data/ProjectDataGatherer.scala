package com.github.tweets.data

import com.github.tweets.common.ProjectData
import com.github.tweets.gateway.{TwitterAPIGateway, GitHubAPIGateway}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
private[tweets] class ProjectDataGathererImpl @Autowired()(gitHubAPIGateway: GitHubAPIGateway,
                                                           twitterAPIGateway: TwitterAPIGateway) extends ProjectDataGatherer {
  override def gatherProjectData(keyword: String): Seq[ProjectData] = {
    val topProjects = gitHubAPIGateway.fetchTopProjects(keyword)

    topProjects.map(githubProject => ProjectData(githubProject, twitterAPIGateway.fetchTweets(githubProject)))
  }
}

private[tweets] trait ProjectDataGatherer {
  def gatherProjectData(keyword: String): Seq[ProjectData]
}

