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

package navigation.startReturn

import controllers.routes
import javax.inject.{Inject, Singleton}
import models._
import pages._
import play.api.mvc.Call

@Singleton
class StartReturnNavigator @Inject()() {

  private val normalRoutes: Page => UserAnswers => Call = {
    case IndexPage => _ => routes.ReportingCompanyAppointedController.onPageLoad(NormalMode)
    case ReportingCompanyAppointedPage => _.get(ReportingCompanyAppointedPage) match {
      case Some(true) => routes.AgentActingOnBehalfOfCompanyController.onPageLoad(NormalMode)
      case Some(false) => routes.ReportingCompanyRequiredController.onPageLoad
      case _ => routes.ReportingCompanyAppointedController.onPageLoad(NormalMode)
    }
    case AgentActingOnBehalfOfCompanyPage => _.get(AgentActingOnBehalfOfCompanyPage) match {
      case Some(true) => routes.AgentNameController.onPageLoad(NormalMode)
      case Some(false) => routes.FullOrAbbreviatedReturnController.onPageLoad(NormalMode)
      case _ => routes.AgentActingOnBehalfOfCompanyController.onPageLoad(NormalMode)
    }
    case AgentNamePage => _ => routes.FullOrAbbreviatedReturnController.onPageLoad(NormalMode)
    case FullOrAbbreviatedReturnPage => _ => nextSection(NormalMode)
    case _ => _ => routes.IndexController.onPageLoad()
  }

  private val checkRouteMap: Page => UserAnswers => Call = {
    _ => _ => routes.CheckYourAnswersController.onPageLoad()
  }

  private def nextSection(mode: Mode): Call = routes.ReportingCompanyNameController.onPageLoad(mode)

  def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call = mode match {
    case NormalMode => normalRoutes(page)(userAnswers)
    case CheckMode => checkRouteMap(page)(userAnswers)
  }
}
