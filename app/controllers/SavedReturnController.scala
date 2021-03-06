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

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import config.FrontendAppConfig
import controllers.actions._
import javax.inject.Inject
import navigation._
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.SavedReturnView

class SavedReturnController @Inject()(override val messagesApi: MessagesApi,
                                      identify: IdentifierAction,
                                      getData: DataRetrievalAction,
                                      requireData: DataRequiredAction,
                                      val controllerComponents: MessagesControllerComponents,
                                      val sessionRepository: SessionRepository,
                                      view: SavedReturnView,
                                      aboutReturnNavigator: AboutReturnNavigator,
                                      groupLevelInformationNavigator: GroupLevelInformationNavigator,
                                      electionsNavigator: ElectionsNavigator,
                                      ultimateParentCompanyNavigator: UltimateParentCompanyNavigator
                                     )(implicit appConfig: FrontendAppConfig) extends BaseController {

  def onPageLoad: Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    val savedTilDate = LocalDate.now().plusDays(appConfig.cacheTtlDays).format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    Ok(view(savedTilDate))
  }

  def deleteAndStartAgain: Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    sessionRepository.delete(request.userAnswers).map(_ =>
      Redirect(controllers.routes.IndexController.onPageLoad())
    )
  }
}
