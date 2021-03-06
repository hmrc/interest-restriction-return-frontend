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
import controllers.BaseController
import controllers.actions._
import forms.elections.OtherInvestorGroupElectionsFormProvider
import handlers.ErrorHandler

import javax.inject.Inject
import models.returnModels.InvestorGroupModel
import models.{InvestorRatioMethod, Mode}
import navigation.ElectionsNavigator
import pages.elections.{InvestorGroupsPage, OtherInvestorGroupElectionsPage}
import play.api.i18n.MessagesApi
import play.api.mvc._
import repositories.SessionRepository
import views.html.elections.OtherInvestorGroupElectionsView

import scala.concurrent.Future

class OtherInvestorGroupElectionsController @Inject()(override val messagesApi: MessagesApi,
                                                      sessionRepository: SessionRepository,
                                                      navigator: ElectionsNavigator,
                                                      identify: IdentifierAction,
                                                      getData: DataRetrievalAction,
                                                      requireData: DataRequiredAction,
                                                      formProvider: OtherInvestorGroupElectionsFormProvider,
                                                      val controllerComponents: MessagesControllerComponents,
                                                      view: OtherInvestorGroupElectionsView
                                                     )(implicit appConfig: FrontendAppConfig, errorHandler: ErrorHandler) extends BaseController {

  private val form = formProvider()

  private def getRatioMethod(investorGroups: InvestorGroupModel, idx: Int, mode: Mode)(f: InvestorRatioMethod => Future[Result]) =
    investorGroups.ratioMethod match {
      case Some(ratioMethod) => f(ratioMethod)
      case _ => Future.successful(Redirect(routes.InvestorRatioMethodController.onPageLoad(idx, mode)))
    }

  def onPageLoad(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(InvestorGroupsPage, idx) { investorGroups =>
      getRatioMethod(investorGroups, idx, mode) { ratioMethod =>
        Future.successful(Ok(view(
          form = investorGroups.otherInvestorGroupElections.fold(form)(form.fill),
          investorGroups.investorName,
          postAction = routes.OtherInvestorGroupElectionsController.onSubmit(idx, mode),
          ratioMethod
        )))
      }
    }
  }

  def onSubmit(idx: Int, mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    answerFor(InvestorGroupsPage, idx) { investorGroups =>
      getRatioMethod(investorGroups, idx, mode) { ratioMethod =>
        form.bindFromRequest().fold(
          formWithErrors =>
            Future.successful(BadRequest(view(
              form = formWithErrors,
              investorGroups.investorName,
              postAction = routes.OtherInvestorGroupElectionsController.onSubmit(idx, mode),
              ratioMethod
            ))),
          value => {
            val updatedModel = investorGroups.copy(otherInvestorGroupElections = Some(value))

            for {
              updatedAnswers <- Future.fromTry(request.userAnswers.set(InvestorGroupsPage, updatedModel, Some(idx)))
              _              <- sessionRepository.set(updatedAnswers)
            } yield Redirect(navigator.nextPage(OtherInvestorGroupElectionsPage, mode, updatedAnswers))
          }
        )
      }
    }
  }
}
