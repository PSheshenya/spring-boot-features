package my.sheshenya.springbootfeatures.performance.tests.rest

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.core.protocol.Protocol
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.Random
import java.util.UUID.randomUUID

class DemoPerformanceTest extends Simulation {

  def getCurrentDate(): String = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    format.format(Calendar.getInstance().getTime)
  }

  val httpConf: Protocol = http
    .baseUrl("http://localhost:8080/projects/")
    .acceptHeader(HttpHeaderValues.ApplicationJson)
    .contentTypeHeader(HttpHeaderValues.ApplicationJson)

  // #####################################################
  // ################# execs #############################
  // #####################################################


  val getUserProjectDescriptions_NotFoundError: ChainBuilder = exec(
    http("getUserProjectDescriptions_Fail random user")
      .get((s: Session) => "/" + Random.nextInt(Integer.MAX_VALUE))
      .check(status.is(500))
      .check(jsonPath("$.error").exists)
  )

  val getUserProjectDescriptions_Success: ChainBuilder = exec(
    http("getUserProjectDescriptions_Success")
      .get("/Alex")
      .check(status.is(200))
      .check(jsonPath("$.error").notExists)
      .check(jsonPath("$.userName").exists)
  )

  val getUserProject_Success: ChainBuilder = exec(
    http("getUserProject_Success")
      .get("/Alex/1")
      .check(status.is(200))
      .check(jsonPath("$.error").notExists)
      .check(jsonPath("$.readme").exists)
  )


  // #####################################################
  // ################# scenarios #########################
  // #####################################################


  val getUserProjectDescriptionsScn: ScenarioBuilder = scenario("Get UserProjectDescriptions").repeat(100) {
    exec(
      getUserProjectDescriptions_NotFoundError,
      getUserProjectDescriptions_Success
    ).pause(Duration.apply(20, TimeUnit.MILLISECONDS))
  }

  val getUserProjectScn: ScenarioBuilder = scenario("Get UserProject").repeat(100) {
    exec(
      getUserProject_Success
    ).pause(Duration.apply(20, TimeUnit.MILLISECONDS))
  }

  // #####################################################
  // ################# setUp #############################
  // #####################################################

  setUp(
    // postAndGetScn.inject(atOnceUsers(3)),
    getUserProjectDescriptionsScn.inject(atOnceUsers(2)),
    getUserProjectScn.inject(atOnceUsers(3))
  )
    .protocols(httpConf)
    .maxDuration(FiniteDuration.apply(30, TimeUnit.SECONDS))
}
