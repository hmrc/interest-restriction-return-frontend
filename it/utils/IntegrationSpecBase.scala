package utils

import models.UserAnswers
import org.scalatest._
import org.scalatest.concurrent.{Eventually, IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.{Application, Environment, Mode}
import repositories.SessionRepository
import stubs.AuthStub
import org.scalatest.TryValues

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration, FiniteDuration, SECONDS}

trait IntegrationSpecBase extends WordSpec
  with GivenWhenThen with TestSuite with ScalaFutures with IntegrationPatience with Matchers
  with WiremockHelper
  with GuiceOneServerPerSuite with TryValues
  with BeforeAndAfterEach with BeforeAndAfterAll with Eventually with CreateRequestHelper with CustomMatchers {

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Dev))
    .configure(config)
    .build
  val mockHost = WiremockHelper.wiremockHost
  val mockPort = WiremockHelper.wiremockPort.toString
  val mockUrl = s"http://$mockHost:$mockPort"

  def config: Map[String, String] = Map(
    "play.filters.csrf.header.bypassHeaders.Csrf-Token" -> "nocheck",
    "application.router" -> "testOnlyDoNotUseInAppConf.Routes",
    "microservice.services.auth.host" -> mockHost,
    "microservice.services.auth.port" -> mockPort,
    "microservice.services.interest-restriction-return.host" -> mockHost,
    "microservice.services.interest-restriction-return.port" -> mockPort
  )

  val userAnswersId = "id"

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)

  val emptyUserAnswers = UserAnswers(userAnswersId, Json.obj())

  lazy val mongo = app.injector.instanceOf[SessionRepository]


  override def beforeEach(): Unit = {
    resetWiremock()

    AuthStub.authorised()

    whenReady(getRequest("/", follow = true)) { result =>
      result should have(
        httpStatus(OK)
      )
    }
  }

  override def beforeAll(): Unit = {
    super.beforeAll()
    startWiremock()
  }

  override def afterAll(): Unit = {
    stopWiremock()
    super.afterAll()
  }

}
