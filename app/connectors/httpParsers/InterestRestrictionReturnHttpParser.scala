/*
 * Copyright 2020 HM Revenue & Customs
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

package connectors.httpParsers

import play.api.Logger
import play.api.http.Status._
import uk.gov.hmrc.http.{HttpReads, HttpResponse}

object InterestRestrictionReturnHttpParser {


  type InterestRestrictionReturnResponse = Either[ErrorResponse, SuccessResponse]

  implicit object InterestRestrictionReturnReads extends HttpReads[InterestRestrictionReturnResponse]{

    def read(method: String, url: String, response: HttpResponse): InterestRestrictionReturnResponse = {
      response.status match {
        case NO_CONTENT =>
          Logger.debug("[InterestRestrictionReturnHttpParser][read]: Status OK")
          Logger.debug(s"[InterestRestrictionReturnHttpParser][read]: Json Response: ${response.body}")
          Right(ValidCRN)
        case NOT_FOUND =>
          Logger.debug("[InterestRestrictionReturnHttpParser][read]: Status NOT FOUND")
          Logger.debug(s"[InterestRestrictionReturnHttpParser][read]: Json Response: ${response.body}")
          Left(InvalidCRN)
        case status =>
          Logger.warn(s"[InterestRestrictionReturnHttpParser][read]: Unexpected response, status $status returned")
          Left(UnexpectedFailure(status, s"Unexpected response, status $status returned"))
      }
    }
  }
}

trait SuccessResponse

case object ValidCRN extends SuccessResponse

trait ErrorResponse {
  val status: Int
  val body: String
}

case object InvalidCRN extends ErrorResponse {
  override val status: Int = NOT_FOUND
  override val body: String = "CRN Not Found on Companies House"
}

case class UnexpectedFailure(override val status: Int,override val body: String) extends ErrorResponse