package com.github.tweets.gateway

import java.nio.charset.StandardCharsets

import org.springframework.core.io.ClassPathResource
import org.springframework.util.StreamUtils

object DataLoader {
  def loadText(name: String) =
    StreamUtils.copyToString(new ClassPathResource(name).getInputStream, StandardCharsets.UTF_8)
}
