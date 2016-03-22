package com.github.tweets.gateway.github

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.tweets.common.GitHubProjectInfo

@JsonIgnoreProperties(ignoreUnknown = true)
private[github] case class GitHubProjectInfoCollectionWrapper(items: List[GitHubProjectInfo])