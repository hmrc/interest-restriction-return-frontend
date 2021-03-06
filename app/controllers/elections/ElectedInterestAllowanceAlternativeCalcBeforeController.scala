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

package controllers.elections

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import controllers.BaseController
import controllers.actions._
import forms.elections.ElectedInterestAllowanceAlternativeCalcBeforeFormProvider

import javax.inject.Inject
import models.Mode
import navigation.ElectionsNavigator
import pages.elections.ElectedInterestAllowanceAlternativeCalcBeforePage
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.elections.ElectedInterestAllowanceAlternativeCalcBeforeView

import scala.concurrent.Future

class ElectedInterestAllowanceAlternativeCalcBeforeController @Inject()(override val messagesApi: MessagesApi,
                                                                        sessionRepository: SessionRepository,
                                                                        navigator: ElectionsNavigator,
                                                                        identify: IdentifierAction,
                                                                        getData: DataRetrievalAction,
                                                                        requireData: DataRequiredAction,
                                                                        formProvider: ElectedInterestAllowanceAlternativeCalcBeforeFormProvider,
                                                                        val controllerComponents: MessagesControllerComponents,
                                                                        view: ElectedInterestAllowanceAlternativeCalcBeforeView
                                                                       )(implicit appConfig: FrontendAppConfig) extends BaseController with FeatureSwitching {

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(fillForm(ElectedInterestAllowanceAlternativeCalcBeforePage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(formWithErrors, mode))),
      value =>
        for {
          updatedAnswers <- Future.fromTry(request.userAnswers.set(ElectedInterestAllowanceAlternativeCalcBeforePage, value))
          _              <- sessionRepository.set(updatedAnswers)
        } yield Redirect(navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, mode, updatedAnswers))
    )
  }
}
