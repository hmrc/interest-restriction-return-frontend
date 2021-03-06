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

package controllers

import controllers.actions.{DataRetrievalAction, IdentifierAction}
import javax.inject.Inject
import models.{NormalMode, UserAnswers}
import navigation.AboutReturnNavigator
import pages.IndexPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository

import scala.concurrent.Future

class IndexController @Inject()(identify: IdentifierAction,
                                getData: DataRetrievalAction,
                                sessionRepository: SessionRepository,
                                navigator: AboutReturnNavigator,
                                val controllerComponents: MessagesControllerComponents) extends BaseController {

  def onPageLoad: Action[AnyContent] = (identify andThen getData).async { implicit request =>
    val userAnswers = request.userAnswers.fold(UserAnswers(request.internalId))(x => x)
    if(userAnswers.data.fields.isEmpty) {
      sessionRepository.set(userAnswers).map { _ =>
        Redirect(navigator.nextPage(IndexPage, NormalMode, userAnswers))
      }
    } else {
      Future.successful(Redirect(controllers.routes.ContinueSavedReturnController.onPageLoad()))
    }
  }
}
