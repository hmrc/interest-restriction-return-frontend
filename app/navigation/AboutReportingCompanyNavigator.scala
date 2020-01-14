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

package navigation

import controllers.aboutReportingCompany.{routes => aboutReportingCompanyRoutes}
import controllers.routes
import javax.inject.{Inject, Singleton}
import models._
import pages._
import pages.aboutReportingCompany.{ReportingCompanyCRNPage, ReportingCompanyCTUTRPage, ReportingCompanyNamePage}
import play.api.mvc.Call

@Singleton
class AboutReportingCompanyNavigator @Inject()() extends BaseNavigator {

  private val normalRoutes: Page => UserAnswers => Call = {
    case ReportingCompanyNamePage => _ => aboutReportingCompanyRoutes.ReportingCompanyCTUTRController.onPageLoad(NormalMode)
    case ReportingCompanyCTUTRPage => _ => aboutReportingCompanyRoutes.ReportingCompanyCRNController.onPageLoad(NormalMode)
    case ReportingCompanyCRNPage => _ => nextSection(NormalMode)
    case _ => _ => routes.IndexController.onPageLoad()
  }

  private val checkRouteMap: Page => UserAnswers => Call = {
    _ => _ => routes.CheckYourAnswersController.onPageLoad()
  }

  private def nextSection(mode: Mode): Call = ??? //TODO: Link to About the Group Structure Section when implemented

  def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call = mode match {
    case NormalMode => normalRoutes(page)(userAnswers)
    case CheckMode => checkRouteMap(page)(userAnswers)
  }
}