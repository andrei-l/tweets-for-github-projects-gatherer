package com.github.tweets.common

import com.fasterxml.jackson.annotation.{JsonProperty, JsonIgnoreProperties}

@JsonIgnoreProperties(ignoreUnknown = true)
private[tweets] case class TweetInfo(text: String,
                                     @JsonProperty("created_at") createdDate: String,
                                     user: TwitterUser)

@JsonIgnoreProperties(ignoreUnknown = true)
private[tweets] case class TwitterUser(name: String, @JsonProperty("screen_name") alias: String)