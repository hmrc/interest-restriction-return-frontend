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

package controllers.ukCompanies

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.CheckYourAnswersView
import controllers.BaseNavigationController
import handlers.ErrorHandler
import models.NormalMode
import models.Section.UkCompanies
import navigation.UkCompaniesNavigator
import pages.ukCompanies.{CheckAnswersUkCompanyPage, UkCompaniesPage}
import repositories.SessionRepository
import services.QuestionDeletionLookupService
import utils.CheckYourAnswersUkCompanyHelper
import views.html.CheckYourAnswersView
import views.ViewUtils._

import scala.concurrent.{ExecutionContext, Future}

class CheckAnswersUkCompanyController @Inject()(override val messagesApi: MessagesApi,
                                                val sessionRepository: SessionRepository,
                                                val questionDeletionLookupService: QuestionDeletionLookupService,
                                                identify: IdentifierAction,
                                                getData: DataRetrievalAction,
                                                requireData: DataRequiredAction,
                                                val controllerComponents: MessagesControllerComponents,
                                                val navigator: UkCompaniesNavigator,
                                                view: CheckYourAnswersView
                                               )(implicit ec: ExecutionContext, appConfig: FrontendAppConfig, errorHandler: ErrorHandler)
  extends FrontendBaseController with I18nSupport with FeatureSwitching with BaseNavigationController {

  def onPageLoad(idx: Int): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>
      val checkYourAnswersHelper = new CheckYourAnswersUkCompanyHelper(request.userAnswers)
      answerFor(UkCompaniesPage, idx) { ukCompany =>
        Future.successful(Ok(view(
          answers = checkYourAnswersHelper.rows(idx),
          section = UkCompanies,
          postAction = controllers.ukCompanies.routes.CheckAnswersUkCompanyController.onSubmit(idx),
          headingMsgArgs = Seq(addPossessive(ukCompany.companyDetails.companyName)),
          buttonMsg = "ukCompanies.checkYourAnswers.button"
        )))
      }
  }

  def onSubmit(idx: Int): Action[AnyContent] = (identify andThen getData andThen requireData) {
    implicit request => Redirect(navigator.nextPage(CheckAnswersUkCompanyPage, NormalMode, request.userAnswers, Some(idx)))
  }
}
