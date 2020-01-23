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

package connectors

import config.FrontendAppConfig
import connectors.httpParsers.CRNValidationHttpParser.{CRNValidationReads, CRNValidationResponse}
import javax.inject.Inject
import models.requests.DataRequest
import play.api.Logger
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.http.HttpClient
import play.api.http.HeaderNames.ACCEPT

import scala.concurrent.{ExecutionContext, Future}

class CRNValidationConnector @Inject()(httpClient: HttpClient,
                                       implicit val appConfig: FrontendAppConfig) {

  private[connectors] lazy val validateCrnUrl: String => String = crn =>
    s"${appConfig.interestRestrictionReturn}/validate-crn/$crn"

  def validateCRN(crn: String)
                 (implicit hc: HeaderCarrier, ec: ExecutionContext, request: DataRequest[_]): Future[CRNValidationResponse] = {
    Logger.debug(s"[CRNValidationConnector][validateCRN] URL: ${validateCrnUrl(crn)}")
    httpClient.GET(validateCrnUrl(crn))(CRNValidationReads, hc.withExtraHeaders(ACCEPT -> "application/vnd.hmrc.1.0+json"), ec)
  }
}