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
import config.featureSwitch.FeatureSwitching
import controllers.BaseController
import controllers.actions._
import forms.ukCompanies.CompanyDetailsFormProvider

import javax.inject.Inject
import models.returnModels.fullReturn.UkCompanyModel
import models.{CompanyDetailsModel, Mode}
import navigation.UkCompaniesNavigator
import pages.ukCompanies.{CompanyDetailsPage, UkCompaniesPage}
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.ukCompanies.CompanyDetailsView

import scala.concurrent.Future

class CompanyDetailsController @Inject()(override val messagesApi: MessagesApi,
                                         sessionRepository: SessionRepository,
                                         navigator: UkCompaniesNavigator,
                                         identify: IdentifierAction,
                                         getData: DataRetrievalAction,
                                         requireData: DataRequiredAction,
                                         formProvider: CompanyDetailsFormProvider,
                                         val controllerComponents: MessagesControllerComponents,
                                         view: CompanyDetailsView
                                        )(implicit appConfig: FrontendAppConfig) extends BaseController with FeatureSwitching {

  def onPageLoad(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    val form = formProvider()
    val companyDetails = getAnswer(UkCompaniesPage, Some(idx)).map(_.companyDetails)

    Future.successful(Ok(view(
      companyDetails.fold(form)(form.fill), mode, routes.CompanyDetailsController.onSubmit(idx, mode)))
    )
  }

  def onSubmit(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(
          form = formWithErrors,
          mode = mode,
          postAction = routes.CompanyDetailsController.onSubmit(idx, mode)
        ))),
      value => {
        val companyDetails = CompanyDetailsModel(value.companyName, value.ctutr)
        val ukCompanyModel = getAnswer(UkCompaniesPage, idx).fold(UkCompanyModel(companyDetails))(_.copy(companyDetails = companyDetails))

        for {
          updatedAnswers <- Future.fromTry(request.userAnswers.set(UkCompaniesPage, ukCompanyModel, Some(idx)))
          _              <- sessionRepository.set(updatedAnswers)
        } yield Redirect(navigator.nextPage(CompanyDetailsPage, mode, updatedAnswers,Some(idx)))
      }
    )
  }
}
