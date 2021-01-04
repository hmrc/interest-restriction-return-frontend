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

import assets.messages.{ReviewAndCompleteMessages, SectionHeaderMessages}
import base.SpecBase
import models.NormalMode
import models.SectionStatus.{Completed, InProgress, NotStarted}
import models.returnModels.{ReviewAndCompleteModel, SectionState}
import pages.elections.GroupRatioBlendedElectionPage
import pages.ultimateParentCompany.{CheckAnswersGroupStructurePage, HasDeemedParentPage, ReportingCompanySameAsParentPage}
import pages.ukCompanies.{DerivedCompanyPage, UkCompaniesPage}
import viewmodels.{SummaryListRowHelper, TaskListRow}


class ReviewAndCompleteHelperSpec extends SpecBase with SummaryListRowHelper with CurrencyFormatter {

  lazy val reviewAndCompleteModel =   ReviewAndCompleteModel(
    aboutReturn = SectionState(NotStarted, None),
    elections = SectionState(InProgress, Some(GroupRatioBlendedElectionPage)),
    groupLevelInformation = SectionState(NotStarted, None),
    ultimateParentCompany = SectionState(Completed, Some(CheckAnswersGroupStructurePage)),
    ukCompanies = SectionState(InProgress, Some(UkCompaniesPage)),
    checkTotals = SectionState(Completed, Some(DerivedCompanyPage))
  )

  "ReviewAndCompleteHelper().rows" when {

    "Reporting Company is same as Ultimate Parent" should {

      "return the correct summary list row models" in {

        val userAnswers = emptyUserAnswers.set(ReportingCompanySameAsParentPage, true).get
        val helper = new ReviewAndCompleteHelper()

        helper.rows(reviewAndCompleteModel, userAnswers) mustBe Seq(
          TaskListRow(
            SectionHeaderMessages.aboutReturn,
            controllers.aboutReturn.routes.CheckAnswersAboutReturnController.onPageLoad(),
            ReviewAndCompleteMessages.notStarted
          ),
          TaskListRow(
            SectionHeaderMessages.ultimateParentCompany,
            controllers.ultimateParentCompany.routes.ReportingCompanySameAsParentController.onPageLoad(NormalMode),
            ReviewAndCompleteMessages.completed
          ),
          TaskListRow(
            SectionHeaderMessages.elections,
            controllers.elections.routes.CheckAnswersElectionsController.onPageLoad(),
            ReviewAndCompleteMessages.inProgress
          ),
          TaskListRow(
            SectionHeaderMessages.groupLevelInformation,
            controllers.routes.UnderConstructionController.onPageLoad(),
            ReviewAndCompleteMessages.notStarted
          ),
          TaskListRow(
            SectionHeaderMessages.ukCompanies,
            controllers.ukCompanies.routes.UkCompaniesReviewAnswersListController.onPageLoad(),
            ReviewAndCompleteMessages.inProgress
          ),
          TaskListRow(
            SectionHeaderMessages.checkTotals,
            controllers.checkTotals.routes.DerivedCompanyController.onPageLoad(),
            ReviewAndCompleteMessages.completed
          )
        )
      }
    }

    "Reporting Company is not the same as Ultimate Parent" when {

      "Ultimate Parent is deemed" should {

        "return the correct summary list row models" in {

          val userAnswers = emptyUserAnswers
            .set(ReportingCompanySameAsParentPage, false).get
            .set(HasDeemedParentPage, true).get

          val helper = new ReviewAndCompleteHelper()

          helper.rows(reviewAndCompleteModel, userAnswers) mustBe Seq(
            TaskListRow(
              SectionHeaderMessages.aboutReturn,
              controllers.aboutReturn.routes.CheckAnswersAboutReturnController.onPageLoad(),
              ReviewAndCompleteMessages.notStarted
            ),
            TaskListRow(
              SectionHeaderMessages.ultimateParentCompany,
              controllers.ultimateParentCompany.routes.DeemedParentReviewAnswersListController.onPageLoad(),
              ReviewAndCompleteMessages.completed
            ),
            TaskListRow(
              SectionHeaderMessages.elections,
              controllers.elections.routes.CheckAnswersElectionsController.onPageLoad(),
              ReviewAndCompleteMessages.inProgress
            ),
            TaskListRow(
              SectionHeaderMessages.groupLevelInformation,
              controllers.routes.UnderConstructionController.onPageLoad(),
              ReviewAndCompleteMessages.notStarted
            ),
            TaskListRow(
              SectionHeaderMessages.ukCompanies,
              controllers.ukCompanies.routes.UkCompaniesReviewAnswersListController.onPageLoad(),
              ReviewAndCompleteMessages.inProgress
            ),
            TaskListRow(
              SectionHeaderMessages.checkTotals,
              controllers.checkTotals.routes.DerivedCompanyController.onPageLoad(),
              ReviewAndCompleteMessages.completed
            )
          )
        }
      }

      "Ultimate Parent is NOT deemed" should {

        "return the correct summary list row models" in {

          val userAnswers = emptyUserAnswers
            .set(ReportingCompanySameAsParentPage, false).get
            .set(HasDeemedParentPage, false).get

          val helper = new ReviewAndCompleteHelper()

          helper.rows(reviewAndCompleteModel, userAnswers) mustBe Seq(
            TaskListRow(
              SectionHeaderMessages.aboutReturn,
              controllers.aboutReturn.routes.CheckAnswersAboutReturnController.onPageLoad(),
              ReviewAndCompleteMessages.notStarted
            ),
            TaskListRow(
              SectionHeaderMessages.ultimateParentCompany,
              controllers.ultimateParentCompany.routes.CheckAnswersGroupStructureController.onPageLoad(1),
              ReviewAndCompleteMessages.completed
            ),
            TaskListRow(
              SectionHeaderMessages.elections,
              controllers.elections.routes.CheckAnswersElectionsController.onPageLoad(),
              ReviewAndCompleteMessages.inProgress
            ),
            TaskListRow(
              SectionHeaderMessages.groupLevelInformation,
              controllers.routes.UnderConstructionController.onPageLoad(),
              ReviewAndCompleteMessages.notStarted
            ),
            TaskListRow(
              SectionHeaderMessages.ukCompanies,
              controllers.ukCompanies.routes.UkCompaniesReviewAnswersListController.onPageLoad(),
              ReviewAndCompleteMessages.inProgress
            ),
            TaskListRow(
              SectionHeaderMessages.checkTotals,
              controllers.checkTotals.routes.DerivedCompanyController.onPageLoad(),
              ReviewAndCompleteMessages.completed
            )
          )
        }
      }
    }
  }
}
