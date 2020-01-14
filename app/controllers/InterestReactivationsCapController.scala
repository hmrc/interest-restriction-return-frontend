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

package controllers

import controllers.actions._
import forms.InterestReactivationsCapFormProvider
import javax.inject.Inject
import models.Mode
import navigation.Navigator
import pages.InterestReactivationsCapPage
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.InterestReactivationsCapView
import config.FrontendAppConfig
import nunjucks.Renderer
import nunjucks.InterestReactivationsCapTemplate
import play.api.data.Form
import play.api.libs.json.Json
import config.featureSwitch.{FeatureSwitching, UseNunjucks}
import uk.gov.hmrc.nunjucks.NunjucksSupport
import nunjucks.viewmodels.BasicFormViewModel

import scala.concurrent.Future

class InterestReactivationsCapController @Inject()(
                                       override val messagesApi: MessagesApi,
                                       sessionRepository: SessionRepository,
                                       navigator: Navigator,
                                       identify: IdentifierAction,
                                       getData: DataRetrievalAction,
                                       requireData: DataRequiredAction,
                                       formProvider: InterestReactivationsCapFormProvider,
                                       val controllerComponents: MessagesControllerComponents,
                                       view: InterestReactivationsCapView,
                                       renderer: Renderer
                                     )(implicit appConfig: FrontendAppConfig) extends BaseController with NunjucksSupport with FeatureSwitching {

  private def viewHtml(form: Form[_], mode: Mode)(implicit request: Request[_]) = if(isEnabled(UseNunjucks)) {
    renderer.render(InterestReactivationsCapTemplate, Json.toJsObject(BasicFormViewModel(form, mode)))
  } else {
    Future.successful(view(form, mode))
  }

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>
      viewHtml(fillForm(InterestReactivationsCapPage, formProvider()), mode).map(Ok(_))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request =>

      formProvider().bindFromRequest().fold(
        formWithErrors =>
          viewHtml(formWithErrors, mode).map(BadRequest(_)),

        value =>
          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set(InterestReactivationsCapPage, value))
            _              <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage(InterestReactivationsCapPage, mode, updatedAnswers))
      )
  }
}
