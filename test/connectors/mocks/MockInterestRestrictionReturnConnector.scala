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

package connectors.mocks

import connectors.httpParsers.InterestRestrictionReturnHttpParser.InterestRestrictionReturnResponse
import models.requests.DataRequest
import org.scalamock.scalatest.MockFactory
import connectors.InterestRestrictionReturnConnector
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.{ExecutionContext, Future}

trait MockInterestRestrictionReturnConnector extends MockFactory {

  lazy val mockInterestRestrictionReturnConnector: InterestRestrictionReturnConnector = mock[InterestRestrictionReturnConnector]

  def mockInterestRestrictionReturn(crn: String)(response: InterestRestrictionReturnResponse): Unit = {
    (mockInterestRestrictionReturnConnector.validateCRN(_: String)(_: HeaderCarrier, _: ExecutionContext, _: DataRequest[_]))
      .expects(crn, *, *, *)
      .returns(Future.successful(response))
  }
}
