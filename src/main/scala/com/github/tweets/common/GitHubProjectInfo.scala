package com.github.tweets.common

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
private[tweets] case class GitHubProjectInfo(@JsonProperty("full_name") projectName: String,
                                             name: String,
                                             @JsonProperty("html_url") htmlUrl: String,
                                             description: String,
                                             url: String)