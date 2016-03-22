package com.github.tweets.gateway.twitter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.tweets.common.TweetInfo

@JsonIgnoreProperties(ignoreUnknown = true)
private[twitter] case class TweetInfoCollectionWrapper(statuses: List[TweetInfo])



