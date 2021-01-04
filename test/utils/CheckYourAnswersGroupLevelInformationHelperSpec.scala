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

import assets.constants.BaseConstants
import assets.messages.{BaseMessages, CheckAnswersGroupLevelInformationMessages}
import base.SpecBase
import controllers.groupLevelInformation.{routes => groupLevelInformationRoutes}
import models.{CheckMode, UserAnswers}
import pages.groupLevelInformation._
import viewmodels.SummaryListRowHelper

class CheckYourAnswersGroupLevelInformationHelperSpec extends SpecBase with BaseConstants with SummaryListRowHelper with CurrencyFormatter {

  val helper = new CheckYourAnswersGroupLevelInformationHelper(
    UserAnswers("id")
      .set(ReturnContainEstimatesPage, false).get
      .set(GroupInterestAllowancePage, groupInterestAllowance).get
      .set(GroupInterestCapacityPage, groupInterestCapacity).get
      .set(GroupSubjectToRestrictionsPage, true).get
      .set(InterestReactivationsCapPage, interestReactivationCap).get
      .set(GroupSubjectToReactivationsPage, false).get
      .set(InterestAllowanceBroughtForwardPage, interestAllowanceBroughtForward).get
  )

  "Check Your Answers Helper" must {

    "For the ReturnContainEstimates answer" must {

      "have a correctly formatted summary list row" in {

        helper.returnContainEstimates mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.returnContainEstimates,
          BaseMessages.no,
          groupLevelInformationRoutes.ReturnContainEstimatesController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the GroupInterestAllowance answer" must {

      "have a correctly formatted summary list row" in {

        helper.groupInterestAllowance mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.groupInterestAllowance,
          currencyFormat(groupInterestAllowance),
          groupLevelInformationRoutes.GroupInterestAllowanceController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the GroupInterestCapacity answer" must {

      "have a correctly formatted summary list row" in {

        helper.groupInterestCapacity mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.groupInterestCapacity,
          currencyFormat(groupInterestCapacity),
          groupLevelInformationRoutes.GroupInterestCapacityController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the GroupSubjectToRestrictions answer" must {

      "have a correctly formatted summary list row" in {

        helper.groupSubjectToRestrictions mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.groupSubjectToRestrictions,
          BaseMessages.yes,
          groupLevelInformationRoutes.GroupSubjectToRestrictionsController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the InterestReactivationsCap answer" must {

      "have a correctly formatted summary list row" in {

        helper.interestReactivationsCap mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.interestReactivationsCap,
          currencyFormat(interestReactivationCap),
          groupLevelInformationRoutes.InterestReactivationsCapController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the GroupSubjectToReactivations answer" must {

      "have a correctly formatted summary list row" in {

        helper.groupSubjectToReactivations mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.groupSubjectToReactivations,
          BaseMessages.no,
          groupLevelInformationRoutes.GroupSubjectToReactivationsController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }

    "For the InterestAllowanceBroughtForward answer" must {

      "have a correctly formatted summary list row" in {

        helper.interestAllowanceBroughtForward mustBe Some(summaryListRow(
          CheckAnswersGroupLevelInformationMessages.interestAllowanceBroughtForward,
          currencyFormat(interestAllowanceBroughtForward),
          groupLevelInformationRoutes.InterestAllowanceBroughtForwardController.onPageLoad(CheckMode) -> BaseMessages.changeLink
        ))
      }
    }
  }
}
