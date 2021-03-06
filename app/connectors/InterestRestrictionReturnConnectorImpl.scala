/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package connectors

import com.google.inject.ImplementedBy
import play.api.Logging
import config.FrontendAppConfig
import models.FullOrAbbreviatedReturn.Full
import play.api.http.{HeaderNames, Status}
import models.{FullReturnModel, SuccessResponse}
import play.api.libs.json.{JsError, JsResultException, JsSuccess, Json}
import uk.gov.hmrc.http.{BadRequestException, HeaderCarrier, HttpClient}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Try}

@ImplementedBy(classOf[InterestRestrictionReturnConnectorImpl])
trait InterestRestrictionReturnConnector {
  def submitFullReturn(fullReturn: FullReturnModel)(implicit hc: HeaderCarrier): Future[SuccessResponse]
}

class InterestRestrictionReturnConnectorImpl @Inject()(val appConfig: FrontendAppConfig, http: HttpClient)(implicit ec: ExecutionContext) extends Logging with InterestRestrictionReturnConnector {
  def submitFullReturn(fullReturn: FullReturnModel)(implicit hc: HeaderCarrier): Future[SuccessResponse] = {
    val fullOrAbbr = if (fullReturn.aboutReturn.fullOrAbbreviatedReturn == Full) "full" else "abbreviated"
    val serviceUrl = s"${appConfig.interestRestrictionReturn}/internal/return/$fullOrAbbr"
    val parsedReturn = Json.toJson(fullReturn)

    logger.debug(s"[SUBMIT-IRR][PAYLOAD]$parsedReturn")

    http.POST(serviceUrl,parsedReturn,Seq(ContentTypeHeader("application/json"),AcceptHeader("application/vnd.hmrc.1.0+json"))).map {
      response => require(response.status == Status.OK)

        Json.parse(response.body).validate[SuccessResponse] match {
          case JsSuccess(value, _) => value
          case JsError(errors) => throw JsResultException(errors)
        }
    }
  } andThen logExceptions() recoverWith handleExceptions

  private def handleExceptions[I](): PartialFunction[Throwable, Future[I]] = {
    case _: BadRequestException => Future.failed(new InvalidPayloadException)
    case _ => Future.failed(new SubmissionFailureException)
  }

  private def logExceptions[I](): PartialFunction[Try[I], Unit] = {
    case Failure(t: Throwable) => logger.error("[SUBMIT-IRR][FAILURE]", t)
  }
}

object ContentTypeHeader {
  def apply(value: String): (String, String) = (HeaderNames.CONTENT_TYPE, value)
}

object AcceptHeader {
  def apply(value: String): (String, String) = (HeaderNames.ACCEPT, value)
}

sealed trait RegisterInterestRestrictionReturnException extends Exception
class InvalidPayloadException extends RegisterInterestRestrictionReturnException
class SubmissionFailureException extends RegisterInterestRestrictionReturnException
