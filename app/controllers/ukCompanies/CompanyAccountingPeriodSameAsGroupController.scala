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

package controllers.ukCompanies

import config.FrontendAppConfig
import controllers.actions._
import forms.ukCompanies.CompanyAccountingPeriodSameAsGroupFormProvider

import javax.inject.Inject
import models.Mode
import pages.ukCompanies.{CompanyAccountingPeriodSameAsGroupPage, UkCompaniesPage}
import config.featureSwitch.FeatureSwitching
import controllers.BaseController
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.ukCompanies.CompanyAccountingPeriodSameAsGroupView

import scala.concurrent.Future
import navigation.UkCompaniesNavigator
import handlers.ErrorHandler

class CompanyAccountingPeriodSameAsGroupController @Inject()(
                                         override val messagesApi: MessagesApi,
                                         sessionRepository: SessionRepository,
                                         navigator: UkCompaniesNavigator,
                                         identify: IdentifierAction,
                                         getData: DataRetrievalAction,
                                         requireData: DataRequiredAction,
                                         formProvider: CompanyAccountingPeriodSameAsGroupFormProvider,
                                         val controllerComponents: MessagesControllerComponents,
                                         view: CompanyAccountingPeriodSameAsGroupView
                                 )(implicit appConfig: FrontendAppConfig, errorHandler: ErrorHandler) extends BaseController with FeatureSwitching {

  def onPageLoad(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(UkCompaniesPage, idx) { ukCompany =>
      Future.successful(
        Ok(view(
          form = ukCompany.accountPeriodSameAsGroup.fold(formProvider())(formProvider().fill),
          companyName = ukCompany.companyDetails.companyName,
          postAction = routes.CompanyAccountingPeriodSameAsGroupController.onSubmit(idx, mode)
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
              companyName = ukCompany.companyDetails.companyName,
              postAction = routes.CompanyAccountingPeriodSameAsGroupController.onSubmit(idx, mode)
            ))
          ),
        value => {
          val updatedModel = ukCompany.copy(accountPeriodSameAsGroup = Some(value))

          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set(UkCompaniesPage, updatedModel, Some(idx)))
            _              <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage(CompanyAccountingPeriodSameAsGroupPage, mode, updatedAnswers, Some(idx)))
        }
      )
    }
  }
}
