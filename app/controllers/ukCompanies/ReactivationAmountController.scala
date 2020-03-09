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

import controllers.actions._
import forms.ukCompanies.ReactivationAmountFormProvider
import javax.inject.Inject
import models.Mode
import pages.ukCompanies.{ReactivationAmountPage, UkCompaniesPage}
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.ukCompanies.ReactivationAmountView
import config.FrontendAppConfig
import play.api.data.Form
import config.featureSwitch.FeatureSwitching
import scala.concurrent.Future
import navigation.UkCompaniesNavigator
import services.QuestionDeletionLookupService
import controllers.BaseNavigationController
import handlers.ErrorHandler
import models.returnModels.fullReturn.AllocatedReactivationsModel

class ReactivationAmountController @Inject()(
                                       override val messagesApi: MessagesApi,
                                       val sessionRepository: SessionRepository,
                                       val navigator: UkCompaniesNavigator,
                                       val questionDeletionLookupService: QuestionDeletionLookupService,
                                       identify: IdentifierAction,
                                       getData: DataRetrievalAction,
                                       requireData: DataRequiredAction,
                                       formProvider: ReactivationAmountFormProvider,
                                       val controllerComponents: MessagesControllerComponents,
                                       view: ReactivationAmountView
                                     )(implicit appConfig: FrontendAppConfig, errorHandler: ErrorHandler) extends BaseNavigationController with FeatureSwitching {

  def onPageLoad(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(UkCompaniesPage, idx: Int) { ukCompany =>
      Future.successful(
        Ok(view(
          form = ukCompany.allocatedReactivations. fold(formProvider())(formProvider().fill),
          mode = mode,
          companyName = ukCompany.companyDetails.companyName,
          postAction = routes.ReactivationAmountController.onSubmit(idx, mode)
        ))
      )
    }
  }

  def onSubmit(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(UkCompaniesPage, idx) { ukCompany =>
      formProvider().bindFromRequest().fold(
        formWithErrors =>
          Future.successful(
            BadRequest(view(
              form = formWithErrors,
              mode = mode,
              companyName = ukCompany.companyDetails.companyName,
              postAction = routes.ReactivationAmountController.onSubmit(idx, mode)
            ))
          ),
        value => {
          val updatedModel = ukCompany.copy(allocatedReactivations = Some(value))
          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set(UkCompaniesPage, updatedModel, Some(idx)))
            _ <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage(ReactivationAmountPage, mode, updatedAnswers, Some(idx)))
        }
      )
    }
  }
}