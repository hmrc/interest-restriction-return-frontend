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

import com.google.inject.Inject
import config.FrontendAppConfig
import controllers.BaseController
import controllers.actions.{DataRequiredAction, DataRetrievalAction, IdentifierAction}
import models.NormalMode
import models.Section.Elections
import navigation.ElectionsNavigator
import pages.elections.CheckAnswersElectionsPage
import play.api.i18n.MessagesApi
import play.api.mvc._
import utils.CheckYourAnswersElectionsHelper
import views.html.CheckYourAnswersView

import scala.concurrent.Future

class CheckAnswersElectionsController @Inject()(override val messagesApi: MessagesApi,
                                                navigator: ElectionsNavigator,
                                                identify: IdentifierAction,
                                                getData: DataRetrievalAction,
                                                requireData: DataRequiredAction,
                                                val controllerComponents: MessagesControllerComponents,
                                                view: CheckYourAnswersView
                                               )(implicit appConfig: FrontendAppConfig)
  extends BaseController {

  def onPageLoad(): Action[AnyContent] = (identify andThen getData andThen requireData) {
    implicit request => {
      val checkAnswersHelper = new CheckYourAnswersElectionsHelper(request.userAnswers)
      Ok(view(checkAnswersHelper.rows, Elections, controllers.elections.routes.CheckAnswersElectionsController.onSubmit()))
    }
  }

  def onSubmit(): Action[AnyContent] = (identify andThen getData andThen requireData).async {
    implicit request => Future.successful(Redirect(navigator.nextPage(CheckAnswersElectionsPage,NormalMode,request.userAnswers)))
  }
}
