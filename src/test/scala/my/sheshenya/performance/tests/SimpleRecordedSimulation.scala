package my.sheshenya.springbootfeatures.performance.tests

import java.util.concurrent.TimeUnit

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.FiniteDuration

class SimpleRecordedSimulation extends Simulation {

  val userCount = Integer.getInteger("userCount", 100).toInt
  //val duration = Integer.getInteger("duration",1).toInt //in minutes

//  val httpProtocol = http
//    .baseUrl("http://computer-database.gatling.io")
//    .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
//    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//    .acceptEncodingHeader("gzip, deflate")
//    .acceptLanguageHeader("it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3")
//    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")

  val httpProtocol = http
      .baseUrl("http://localhost:8080")
//
  val gethealth = exec(http("/actuator/health")
    .get("/actuator/health"))
  val getinfo = exec(http("/actuator/info")
    .get("/actuator/info"))
  val scn1 = scenario("RecordedSimulation-actutors-1")
    .exec(gethealth)
    .pause(10)
    .exec(getinfo)

  val scn2 = scenario("RecordedSimulation-actutors-2")
    .exec(http("/actuator/health")
      .get("/actuator/health"))
    .pause(10)
    .exec(http("/actuator/info")
      .get("/actuator/info"))


  setUp(scn1.inject(atOnceUsers(100)), scn2.inject(atOnceUsers(100)))
    .protocols(httpProtocol)
    .maxDuration(FiniteDuration.apply(30, TimeUnit.SECONDS))

}