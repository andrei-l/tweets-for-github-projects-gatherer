package com.github.tweets.config

import com.fasterxml.jackson.databind.{SerializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

import scala.collection.JavaConversions._

@Configuration
private class AppConfig {
  @Bean
  def objectMapper(): ObjectMapper = {
    val om = new ObjectMapper()
    om.registerModule(DefaultScalaModule)
    om.enable(SerializationFeature.INDENT_OUTPUT)
    om
  }

  @Bean
  def restTemplate(jacksonMessageConverter: MappingJackson2HttpMessageConverter): RestTemplate = {
    val restTemplate = new RestTemplate()
    restTemplate.setMessageConverters(List(jacksonMessageConverter, new StringHttpMessageConverter))
    restTemplate
  }

  @Bean
  def jacksonMessageConverter(objectMapper: ObjectMapper) = new MappingJackson2HttpMessageConverter(objectMapper)
}
