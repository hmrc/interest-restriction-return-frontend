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

package utils

import models.{NormalMode, Section, UserAnswers}
import models.returnModels.ReviewAndCompleteModel
import pages.aboutReturn.FullOrAbbreviatedReturnPage
import pages.ultimateParentCompany.{HasDeemedParentPage, ReportingCompanySameAsParentPage}
import play.api.i18n.Messages
import viewmodels.TaskListRow

import models.FullOrAbbreviatedReturn._

class ReviewAndCompleteHelper(implicit val messages: Messages) {

  def rows(reviewAndCompleteModel: ReviewAndCompleteModel, userAnswers: UserAnswers): Seq[TaskListRow] = {

    val ultimateParentLink = (userAnswers.get(ReportingCompanySameAsParentPage), userAnswers.get(HasDeemedParentPage)) match {
      case (Some(true), _) => controllers.ultimateParentCompany.routes.ReportingCompanySameAsParentController.onPageLoad(NormalMode)
      case (Some(false), Some(true)) => controllers.ultimateParentCompany.routes.DeemedParentReviewAnswersListController.onPageLoad()
      case (_, _) => controllers.ultimateParentCompany.routes.CheckAnswersGroupStructureController.onPageLoad(1)
    }

    val aboutReturnRow = TaskListRow(
      messages(s"section.${Section.AboutReturn}"),
      controllers.aboutReturn.routes.CheckAnswersAboutReturnController.onPageLoad(),
      messages(s"reviewAndComplete.${reviewAndCompleteModel.aboutReturnStatus}")
    )
    val ultimateParentRow = TaskListRow(
      messages(s"section.${Section.UltimateParentCompany}"),
      ultimateParentLink,
      messages(s"reviewAndComplete.${reviewAndCompleteModel.ultimateParentCompanyStatus}")
    )
    val electionsRow = TaskListRow(
      messages(s"section.${Section.Elections}"),
      controllers.elections.routes.CheckAnswersElectionsController.onPageLoad(),
      messages(s"reviewAndComplete.${reviewAndCompleteModel.electionsStatus}")
    )
    val groupLevelRow = TaskListRow(
      messages(s"section.${Section.GroupLevelInformation}"),
      controllers.routes.UnderConstructionController.onPageLoad(),
      messages(s"reviewAndComplete.${reviewAndCompleteModel.groupLevelInformationStatus}")
    )
    val ukCompaniesRow = TaskListRow(
      messages(s"section.${Section.UkCompanies}"),
      controllers.ukCompanies.routes.UkCompaniesReviewAnswersListController.onPageLoad(),
      messages(s"reviewAndComplete.${reviewAndCompleteModel.ukCompaniesStatus}")
    )
    val checkTotalsRow = TaskListRow(
      messages(s"section.${Section.CheckTotals}"),
      controllers.checkTotals.routes.DerivedCompanyController.onPageLoad(),
      messages(s"reviewAndComplete.${reviewAndCompleteModel.checkTotalsStatus}")
    )

    userAnswers.get(FullOrAbbreviatedReturnPage) match {
      case Some(Full) => 
        Seq(
          aboutReturnRow,
          ultimateParentRow,
          electionsRow,
          groupLevelRow,
          ukCompaniesRow,
          checkTotalsRow
        )
      case _ => 
        Seq(
          aboutReturnRow,
          ultimateParentRow,
          electionsRow,
          ukCompaniesRow
        )
    }
  }
}
