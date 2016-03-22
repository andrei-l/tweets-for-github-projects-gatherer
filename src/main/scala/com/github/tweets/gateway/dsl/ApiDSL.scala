package com.github.tweets.gateway.dsl

import org.springframework.web.client.RestTemplate

import scala.language.implicitConversions

private[gateway] abstract class ApiDSL[T <: ApiDSL[T]](val url: StringBuilder = StringBuilder.newBuilder)
                                                      (implicit val restTemplate: RestTemplate) {
  def search(where: String) = url ++= "/search/" ++= where

  def `for`(what: String) = ?& ++= "q=" ++= what

  def please: T

  protected def ?& =
    url += (if (url contains q) & else q)

  private final val & = '&'

  private final val q = '?'

}

private[gateway] abstract class ApiDSLBaseObj[T <: ApiDSL[T]] {
  def apply(sb: StringBuilder)(implicit restTemplate: RestTemplate): T

  implicit def sbToString(sb: StringBuilder): String = sb.toString()

  implicit def sbToApiDSL(sb: StringBuilder)(implicit restTemplate: RestTemplate): T = apply(sb)

  implicit def stringToApiDSL(s: String)(implicit restTemplate: RestTemplate): T = sbToApiDSL(new StringBuilder(s))

}