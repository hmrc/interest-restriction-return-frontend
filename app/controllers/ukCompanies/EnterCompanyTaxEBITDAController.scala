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
import controllers.BaseNavigationController
import controllers.actions._
import forms.ukCompanies.EnterCompanyTaxEBITDAFormProvider
import handlers.ErrorHandler
import javax.inject.Inject
import models.Mode
import navigation.UkCompaniesNavigator
import pages.ukCompanies.{EnterCompanyTaxEBITDAPage, UkCompaniesPage}
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import services.QuestionDeletionLookupService
import views.html.ukCompanies.EnterCompanyTaxEBITDAView

import scala.concurrent.Future

class EnterCompanyTaxEBITDAController @Inject()(override val messagesApi: MessagesApi,
                                                val sessionRepository: SessionRepository,
                                                val navigator: UkCompaniesNavigator,
                                                val questionDeletionLookupService: QuestionDeletionLookupService,
                                                identify: IdentifierAction,
                                                getData: DataRetrievalAction,
                                                requireData: DataRequiredAction,
                                                formProvider: EnterCompanyTaxEBITDAFormProvider,
                                                val controllerComponents: MessagesControllerComponents,
                                                view: EnterCompanyTaxEBITDAView
                                               )(implicit appConfig: FrontendAppConfig, errorHandler: ErrorHandler) extends BaseNavigationController with FeatureSwitching {

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(UkCompaniesPage) { ukCompany =>
      Future.successful(
        Ok(view(
          form = ukCompany.taxEBITDA.fold(formProvider())(formProvider().fill),
          mode = mode,
          companyName = ukCompany.companyName.name,
          postAction = routes.EnterCompanyTaxEBITDAController.onSubmit(mode)
        ))
      )
    }
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(UkCompaniesPage) { ukCompany =>
      formProvider().bindFromRequest().fold(
        formWithErrors =>
          Future.successful(
            BadRequest(view(
              form = formWithErrors,
              mode = mode,
              companyName = ukCompany.companyName.name,
              postAction = routes.EnterCompanyTaxEBITDAController.onSubmit(mode)
            ))
          ),
        value => {
          val updatedModel = ukCompany.copy(taxEBITDA = Some(value))
          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set(UkCompaniesPage, updatedModel))
            _ <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage(EnterCompanyTaxEBITDAPage, mode, updatedAnswers))
        }
      )
    }
  }
}