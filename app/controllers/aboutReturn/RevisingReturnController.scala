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

package controllers.aboutReturn

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import controllers.BaseController
import controllers.actions._
import forms.groupLevelInformation.RevisingReturnFormProvider
import javax.inject.Inject
import models.Mode
import navigation.AboutReturnNavigator
import pages.groupLevelInformation.RevisingReturnPage
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.aboutReturn.RevisingReturnView
import scala.concurrent.Future

class RevisingReturnController @Inject()(override val messagesApi: MessagesApi,
                                         sessionRepository: SessionRepository,
                                         navigator: AboutReturnNavigator,
                                         identify: IdentifierAction,
                                         getData: DataRetrievalAction,
                                         requireData: DataRequiredAction,
                                         formProvider: RevisingReturnFormProvider,
                                         val controllerComponents: MessagesControllerComponents,
                                         view: RevisingReturnView
                                        )(implicit appConfig: FrontendAppConfig) extends BaseController with FeatureSwitching {

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(fillForm(RevisingReturnPage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors =>
        Future.successful(BadRequest(view(formWithErrors, mode))),
      value =>
        for {
          updatedAnswers <- Future.fromTry(request.userAnswers.set(RevisingReturnPage, value))
          _              <- sessionRepository.set(updatedAnswers)
        } yield Redirect(navigator.nextPage(RevisingReturnPage, mode, updatedAnswers))
    )
  }
}
