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

package controllers.groupStructure

import com.google.inject.Inject
import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import controllers.actions.{DataRequiredAction, DataRetrievalAction, IdentifierAction}
import models.NormalMode
import models.Section.ReportingCompany
import models.requests.DataRequest
import navigation.GroupStructureNavigator
import pages.aboutReportingCompany.{CheckAnswersReportingCompanyPage, ReportingCompanyNamePage}
import pages.groupStructure.{CheckAnswersGroupStructurePage, ParentCompanyNamePage}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.SummaryListRow
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import utils.CheckYourAnswersHelper
import views.html.CheckYourAnswersView

import scala.concurrent.{ExecutionContext, Future}

class CheckAnswersGroupStructureController @Inject()(
                                                      override val messagesApi: MessagesApi,
                                                      identify: IdentifierAction,
                                                      getData: DataRetrievalAction,
                                                      requireData: DataRequiredAction,
                                                      val controllerComponents: MessagesControllerComponents,
                                                      navigator: GroupStructureNavigator,
                                                      view: CheckYourAnswersView
                                                    )(implicit ec: ExecutionContext, appConfig: FrontendAppConfig)
  extends FrontendBaseController with I18nSupport with FeatureSwitching {

  private def renderView(answers: Seq[SummaryListRow], postAction: Call)(implicit request: Request[_]): Future[Html] = Future.successful(view(answers, ReportingCompany, postAction))

  private def parentCompanyNamePredicate(f: String => Future[Result])(implicit request: DataRequest[_]) =
    request.userAnswers.get(ParentCompanyNamePage) match {
      case Some(parentCompanyName) => f(parentCompanyName)
      case _ => f("the parent company")
    }

  private def reportingCompanyNamePredicate(f: String => Future[Result])(implicit request: DataRequest[_]) =
    request.userAnswers.get(ReportingCompanyNamePage) match {
      case Some(reportingCompanyName) => f(reportingCompanyName)
      case _ => f("the parent company")
    }

  def onPageLoad(): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>
      reportingCompanyNamePredicate { reportingCompanyName =>
        parentCompanyNamePredicate { parentCompanyName =>

          val checkYourAnswersHelper = new CheckYourAnswersHelper(request.userAnswers)

          val sections = Seq(
            checkYourAnswersHelper.reportingCompanySameAsParent,
            checkYourAnswersHelper.deemedParent,
            checkYourAnswersHelper.parentCompanyName,
            checkYourAnswersHelper.payTaxInUk,
            checkYourAnswersHelper.registeredForTaxInAnotherCountry,
            checkYourAnswersHelper.limitedLiabilityPartnership,
            checkYourAnswersHelper.parentCompanyCTUTR,
            checkYourAnswersHelper.parentCompanySAUTR,
            checkYourAnswersHelper.registeredCompaniesHouse,
            checkYourAnswersHelper.parentCRN
          ).flatten

          renderView(sections, controllers.groupStructure.routes.CheckAnswersGroupStructureController.onSubmit()).map(Ok(_))
        }
      }
  }

  def onSubmit(): Action[AnyContent] = (identify andThen getData andThen requireData) {
    implicit request =>
      Redirect(navigator.nextPage(CheckAnswersGroupStructurePage, NormalMode, request.userAnswers))
  }
}
