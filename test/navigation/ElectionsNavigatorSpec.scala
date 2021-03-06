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

package navigation

import base.SpecBase
import assets.constants.PartnershipsConstants._
import controllers.elections.routes
import models._
import models.FullOrAbbreviatedReturn._
import models.returnModels.InvestorGroupModel
import pages.aboutReturn.FullOrAbbreviatedReturnPage
import pages.elections._

class ElectionsNavigatorSpec extends SpecBase {

  val navigator = new ElectionsNavigator

  "ElectionsNavigator" when {

    "in Normal mode" must {

      "from the GroupRatioElectionPage" when {

        "the answer is false" should {

          "go to the GroupEBITDcontrollers.elections.ElectedGroupEBITDABeforeController" in {

            val userAnswers = emptyUserAnswers.set(GroupRatioElectionPage, false).success.value

            navigator.nextPage(GroupRatioElectionPage, NormalMode, userAnswers) mustBe
              routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
          }
        }

        "the answer is true" should {

          "go to the GroupRatioBlendedElectionPage" in {

            val userAnswers = emptyUserAnswers.set(GroupRatioElectionPage, true).success.value

            navigator.nextPage(GroupRatioElectionPage, NormalMode, userAnswers) mustBe
              routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode)
          }
        }
      }

      "from the GroupRatioBlendedElection page" when {

        "the answer is false" should {

          "go to the Elected Group EBITDA Chargeable Gains Before page" in {

            val userAnswers = emptyUserAnswers.set(GroupRatioBlendedElectionPage, false).success.value

            navigator.nextPage(GroupRatioBlendedElectionPage, NormalMode, userAnswers) mustBe
              routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
          }
        }

        "the answer is true" should {

          "go to the Add Investor Group page" in {

            val userAnswers = emptyUserAnswers.set(GroupRatioBlendedElectionPage, true).success.value

            navigator.nextPage(GroupRatioBlendedElectionPage, NormalMode, userAnswers) mustBe
              routes.AddInvestorGroupController.onPageLoad(NormalMode)
          }
        }

        "there is no answer" should {

          "go to the Group Ratio Blended Election page" in {

            navigator.nextPage(GroupRatioBlendedElectionPage, NormalMode, emptyUserAnswers) mustBe
              routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode)
          }
        }
      }

      "from the ElectedGroupEBITDABefore page" should {

        "go to the Group EBITDA Chargeable Gains Election page when answer is false" in {

          val userAnswers = emptyUserAnswers.set(ElectedGroupEBITDABeforePage, false).success.value

          navigator.nextPage(ElectedGroupEBITDABeforePage, NormalMode, userAnswers) mustBe
            routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(NormalMode)
        }

        "go to the Elected Interest Allowance Alternative Calculation page when answer is true" in {

          val userAnswers = emptyUserAnswers.set(ElectedGroupEBITDABeforePage, true).success.value

          navigator.nextPage(ElectedGroupEBITDABeforePage, NormalMode, userAnswers) mustBe
            routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
        }

        "go to the Elected Group EBITDA Before page when there's no answer" in {

          navigator.nextPage(ElectedGroupEBITDABeforePage, NormalMode, emptyUserAnswers) mustBe
            routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
        }
      }

      "from the GroupEBITDAChargeableGainsElection page" should {

        "go to the Elected Interest Allowance Alternative Calculation Before page" in {

          navigator.nextPage(GroupEBITDAChargeableGainsElectionPage, NormalMode, emptyUserAnswers) mustBe
            routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
        }
      }

      "from the ElectedInterestAllowanceAlternativeCalcBefore page" should {

        "go to the Interest Allowance Non Consolidated Investments page when answer is true" in {

          val userAnswers = emptyUserAnswers.set(ElectedInterestAllowanceAlternativeCalcBeforePage, true).success.value

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, NormalMode, userAnswers) mustBe
            routes.InterestAllowanceNonConsolidatedInvestmentsElectionController.onPageLoad(NormalMode)
        }

        "go to the Interest Allowance Alternative Calculation page when answer is false" in {

          val userAnswers = emptyUserAnswers.set(ElectedInterestAllowanceAlternativeCalcBeforePage, false).success.value

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, NormalMode, userAnswers) mustBe
            routes.InterestAllowanceAlternativeCalcElectionController.onPageLoad(NormalMode)
        }

        "go to the Elected Interest Allowance Alternative Calc Before page when there's no answer" in {

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, NormalMode, emptyUserAnswers) mustBe
            routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
        }
      }

      "from the InterestAllowanceAlternativeCalcElectionPage page" should {

        "go to the Interest Allowance Non-Consolidated Investments page" in {

          navigator.nextPage(InterestAllowanceAlternativeCalcElectionPage, NormalMode, emptyUserAnswers) mustBe
            routes.InterestAllowanceNonConsolidatedInvestmentsElectionController.onPageLoad(NormalMode)
        }
      }

      "from the InterestAllowanceNonConsolidatedInvestmentsElection page" should {

        "go to the Investments page when answer is yes" in {

          val userAnswers = emptyUserAnswers.set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, true).success.value

          navigator.nextPage(InterestAllowanceNonConsolidatedInvestmentsElectionPage, NormalMode, userAnswers) mustBe
            routes.InvestmentsReviewAnswersListController.onPageLoad()
        }

        "go to the Elected Interest Allowance Consolidated Partnership Before page when answer is no" in {

          val userAnswers = emptyUserAnswers.set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, false).success.value

          navigator.nextPage(InterestAllowanceNonConsolidatedInvestmentsElectionPage, NormalMode, userAnswers) mustBe
            routes.ElectedInterestAllowanceConsolidatedPshipBeforeController.onPageLoad(NormalMode)
        }

        "go to InterestAllowanceNonConsolidatedInvestmentsElection when there is no answer" in {

          navigator.nextPage(InterestAllowanceNonConsolidatedInvestmentsElectionPage, NormalMode, emptyUserAnswers) mustBe
            routes.InterestAllowanceNonConsolidatedInvestmentsElectionController.onPageLoad(NormalMode)
        }
      }

      "from the ElectedInterestAllowanceConsolidatedPshipBefore page" should {

        "go to the Interest Allowance Consolidated Partnership Election page when answer is false" in {

          val userAnswers = emptyUserAnswers.set(ElectedInterestAllowanceConsolidatedPshipBeforePage, false).success.value

          navigator.nextPage(ElectedInterestAllowanceConsolidatedPshipBeforePage, NormalMode, userAnswers) mustBe
            routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
        }

        "go to the Interest Allowance Consolidated Partnership Election page when answer is true" in {

          val userAnswers = emptyUserAnswers.set(ElectedInterestAllowanceConsolidatedPshipBeforePage, true).success.value

          navigator.nextPage(ElectedInterestAllowanceConsolidatedPshipBeforePage, NormalMode, userAnswers) mustBe
            routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
        }

        "go to the Interest Allowance Consolidated Partnership Election page when answer there's no answer" in {

          navigator.nextPage(ElectedInterestAllowanceConsolidatedPshipBeforePage, NormalMode, emptyUserAnswers) mustBe
            routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
        }
      }

      "from the InterestAllowanceConsolidatedPshipElection page" when {

        "the answer is true" should {

          "go to the Partnerships Review Answers List page" in {

            val userAnswers = emptyUserAnswers.set(InterestAllowanceConsolidatedPshipElectionPage, true).success.value

            navigator.nextPage(InterestAllowanceConsolidatedPshipElectionPage, NormalMode, userAnswers) mustBe
              routes.PartnershipsReviewAnswersListController.onPageLoad()
          }
        }

        "the answer is false" should {

          "go to the check your answers page" in {

            val userAnswers = emptyUserAnswers.set(InterestAllowanceConsolidatedPshipElectionPage, false).success.value

            navigator.nextPage(InterestAllowanceConsolidatedPshipElectionPage, NormalMode, userAnswers) mustBe
              routes.CheckAnswersElectionsController.onPageLoad()
          }
        }

        "no answer is given" should {

          "go to the InterestAllowanceConsolidatedPshipElection page" in {

            navigator.nextPage(InterestAllowanceConsolidatedPshipElectionPage, NormalMode, emptyUserAnswers) mustBe
              routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
          }
        }
      }

      "from the AddInvestorGroup page" when {

        "the answer is true" should {

          "go to the Investor Group Name page" in {

            val userAnswers = emptyUserAnswers.set(AddInvestorGroupPage, true).success.value

            navigator.nextPage(AddInvestorGroupPage, NormalMode, userAnswers) mustBe
              routes.InvestorGroupsReviewAnswersListController.onPageLoad()
          }
        }

        "the answer is false" should {

          "go to the Elected Group EBITDA Before page" in {

            val userAnswers = emptyUserAnswers.set(AddInvestorGroupPage, false).success.value

            navigator.nextPage(AddInvestorGroupPage, NormalMode, userAnswers) mustBe
              routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
          }
        }

        "there is no answer" should {

          "go to the Add Investor Group page" in {

            navigator.nextPage(AddInvestorGroupPage, NormalMode, emptyUserAnswers) mustBe
              routes.AddInvestorGroupController.onPageLoad(NormalMode)
          }
        }
      }

      "from the InvestorGroupName page" should {

        "go to the Investor Ratio Method page" in {

          navigator.nextPage(InvestorGroupNamePage, NormalMode, emptyUserAnswers, Some(1)) mustBe
            routes.InvestorRatioMethodController.onPageLoad(1, NormalMode)
        }
      }

      "from the InvestorRatioMethod page" when {

        "answer is true" should {

          "go to the Other Investor Group Elections page" in {
            val investorGroupModel = InvestorGroupModel("investor name", ratioMethod = Some(InvestorRatioMethod.GroupRatioMethod))
            val userAnswers = emptyUserAnswers.set(
              page = InvestorGroupsPage,
              value = investorGroupModel,
              idx = Some(1)).success.value

            navigator.nextPage(InvestorRatioMethodPage, NormalMode, userAnswers, Some(1)) mustBe
              routes.OtherInvestorGroupElectionsController.onPageLoad(1, NormalMode)
          }
        }

        "answer is false" should {

          "go to the Other Investor Group Elections page" in {
            val investorGroupModel = InvestorGroupModel("investor name", ratioMethod = Some(InvestorRatioMethod.FixedRatioMethod))
            val userAnswers = emptyUserAnswers.set(
              page = InvestorGroupsPage,
              value = investorGroupModel,
              idx = Some(1)).success.value

            navigator.nextPage(InvestorRatioMethodPage, NormalMode, userAnswers, Some(1)) mustBe
              routes.OtherInvestorGroupElectionsController.onPageLoad(1, NormalMode)
          }
        }

        "answer is prefer not to answer" should {

          "go to the Other Investor Group Elections page" in {
            val investorGroupModel = InvestorGroupModel("investor name", ratioMethod = Some(InvestorRatioMethod.PreferNotToAnswer))
            val userAnswers = emptyUserAnswers.set(
              page = InvestorGroupsPage,
              value = investorGroupModel,
              idx = Some(1)).success.value

            navigator.nextPage(InvestorRatioMethodPage, NormalMode, userAnswers, Some(1)) mustBe
              routes.InvestorGroupsReviewAnswersListController.onPageLoad()
          }
        }

        "answer is not given" should {

          "go to the Other Investor Group Elections page" in {

            navigator.nextPage(InvestorRatioMethodPage, NormalMode, emptyUserAnswers, Some(1)) mustBe
              routes.InvestorRatioMethodController.onPageLoad(1, NormalMode)
          }
        }
      }

      "from the Other Investor Group Elections page" should {

        "go to the Investor Groups Review Answers List page" in {

          navigator.nextPage(OtherInvestorGroupElectionsPage, NormalMode, emptyUserAnswers) mustBe
            routes.InvestorGroupsReviewAnswersListController.onPageLoad()
        }
      }

      "from the Investor Groups Review Answers List page" should {

        "go to the Elected Group EBITDA Before page" in {

          navigator.nextPage(InvestorGroupsPage, NormalMode, emptyUserAnswers) mustBe
            routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
        }
      }

      "from the PartnershipName page" should {

        "go to the IsUkPartnership page" in {

          val userAnswers = emptyUserAnswers.set(
            page = PartnershipsPage,
            value = partnershipModelUK.copy(
              name = partnerName,
              isUkPartnership = None,
              sautr = None
            ),
            idx = Some(1)).success.value

          navigator.nextPage(PartnershipNamePage, NormalMode, userAnswers, Some(1)) mustBe
            routes.IsUkPartnershipController.onPageLoad(1, NormalMode)
        }
      }

      "from the IsUkPartnership page" when {

        "answer is true" should {

          "go to the PartnershipSAUTR page" in {

            val userAnswers = emptyUserAnswers.set(
              page = PartnershipsPage,
              value = partnershipModelUK.copy(
                name = partnerName,
                isUkPartnership = Some(IsUKPartnershipOrPreferNotToAnswer.IsUkPartnership),
                sautr = None
              ),
              idx = Some(1)).success.value

            navigator.nextPage(IsUkPartnershipPage, NormalMode, userAnswers, Some(1)) mustBe
              routes.PartnershipSAUTRController.onPageLoad(1, NormalMode)
          }
        }

        "answer is false" should {

          "go to the PartnershipsReviewAnswersList page" in {

            val userAnswers = emptyUserAnswers.set(
              page = PartnershipsPage,
              value = partnershipModelUK.copy(
                name = partnerName,
                isUkPartnership = Some(IsUKPartnershipOrPreferNotToAnswer.IsNotUkPartnership),
                sautr = None
              ),
              idx = Some(1)).success.value

            navigator.nextPage(IsUkPartnershipPage, NormalMode, userAnswers, Some(1)) mustBe
              routes.PartnershipsReviewAnswersListController.onPageLoad()
          }
        }

        "no answer given" should {

          "go to the IsUkPartnership page" in {

            navigator.nextPage(IsUkPartnershipPage, NormalMode, emptyUserAnswers, Some(1)) mustBe
              routes.IsUkPartnershipController.onPageLoad(1, NormalMode)
          }
        }
      }

      "from the PartnershipSAUTR page" should {

        "go to the PartnershipsReviewAnswersList page" in {

          val userAnswers = emptyUserAnswers.set(
            page = PartnershipsPage,
            value = partnershipModelUK,
            idx = Some(1)).success.value

          navigator.nextPage(PartnershipSAUTRPage, NormalMode, userAnswers, Some(1)) mustBe
            routes.PartnershipsReviewAnswersListController.onPageLoad()
        }
      }

      "from the PartnershipsReviewAnswersList page" should {

        "Do you need to add another partnership is" must {

          "yes, go to Partnership name page" in {

            navigator.addPartnership(1) mustBe
              routes.PartnershipNameController.onPageLoad(2, NormalMode)
          }

          "no, go to check answers page" in {

            val userAnswers = emptyUserAnswers.set(
              page = PartnershipsReviewAnswersListPage,
              value = false).success.value

            navigator.nextPage(PartnershipsReviewAnswersListPage, NormalMode, userAnswers) mustBe
              routes.CheckAnswersElectionsController.onPageLoad()
          }
        }

        "from the partnership deletion confirmation page" should {

          "Go to partnership review answers list page" in {

            navigator.nextPage(PartnershipDeletionConfirmationPage, NormalMode, emptyUserAnswers) mustBe
              routes.PartnershipsReviewAnswersListController.onPageLoad()
          }
        }
      }

      "from the CheckAnswersElections page" should {

        "go to the GroupSubjectToRestrictions page on full return" in {
          val userAnswers = emptyUserAnswers
            .set(FullOrAbbreviatedReturnPage, Full).success.value

          navigator.nextPage(CheckAnswersElectionsPage, NormalMode, userAnswers) mustBe
            controllers.groupLevelInformation.routes.GroupSubjectToRestrictionsController.onPageLoad(NormalMode)
        }

        "go to the AboutAddingUKCompanies page on abbreviated return" in {
          val userAnswers = emptyUserAnswers
            .set(FullOrAbbreviatedReturnPage, Abbreviated).success.value

          navigator.nextPage(CheckAnswersElectionsPage, NormalMode, userAnswers) mustBe
            controllers.ukCompanies.routes.AboutAddingUKCompaniesController.onPageLoad()
        }
      }

      "from the Investment Name page" should {

        "go to the Investments Review Answers List page" in {

          val userAnswers = emptyUserAnswers
              .set(InvestmentNamePage, companyNameModel.name).success.value

          navigator.nextPage(InvestmentNamePage, NormalMode, userAnswers, Some(1)) mustBe
            routes.InvestmentsReviewAnswersListController.onPageLoad()
        }
      }

      "from the Investments Review Answers List page" should {

        "go to the Elected Interest Allowance Consolidated Pship Before page" in {

          navigator.nextPage(InvestmentsReviewAnswersListPage, NormalMode, emptyUserAnswers) mustBe
            routes.ElectedInterestAllowanceConsolidatedPshipBeforeController.onPageLoad(NormalMode)
        }
      }

      "from the InvestmentsDeletionConfirmation page" should {

        "go to the InvestmentsReviewAnswersList page" in {

          navigator.nextPage(InvestmentsDeletionConfirmationPage, NormalMode, emptyUserAnswers) mustBe
            routes.InvestmentsReviewAnswersListController.onPageLoad()
        }
      }

      "from the Investor Groups Deletion Confirmation page" should {

        "go to the Investments Review Answers List page" in {

          navigator.nextPage(InvestorGroupsDeletionConfirmationPage, NormalMode, emptyUserAnswers) mustBe
            routes.InvestorGroupsReviewAnswersListController.onPageLoad()
        }
      }
    }

    "in Check mode" must {

      "from the Group Ratio Election page" should {

        "the answer is true" should {

          "go to the Blended Group Ratio Election page in normal mode" in {
            val userAnswers = emptyUserAnswers.set(GroupRatioElectionPage, true).success.value
            navigator.nextPage(GroupRatioElectionPage, CheckMode, userAnswers) mustBe
              routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode)
          }

        }

        "the answer is true and the rest of the journey is already filled in" should {

          "return to the check your answers page" in {
            val userAnswers = for {
              gre <- emptyUserAnswers.set(GroupRatioElectionPage, true)
              gbe <- gre.set(GroupRatioBlendedElectionPage, true)
            } yield gbe
            navigator.nextPage(GroupRatioElectionPage, CheckMode, userAnswers.success.value) mustBe
              routes.CheckAnswersElectionsController.onPageLoad()
          }

        }

        "the answer is false" should {

          "go to the GroupEBITDAChargeableGainsElectionController in normal mode" in {
            val userAnswers = emptyUserAnswers.set(GroupRatioElectionPage, false).success.value
            navigator.nextPage(GroupRatioElectionPage, CheckMode, userAnswers) mustBe
              routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(NormalMode)
          }

        }

        "the answer is false and the rest of the journey is already filled in" should {

          "return to the check your answers page" in {
            val userAnswers =
              for {
                gre <- emptyUserAnswers.set(GroupRatioElectionPage, false)
                iaa <- gre.set(GroupEBITDAChargeableGainsElectionPage, true)
              } yield
                iaa

            navigator.nextPage(GroupRatioElectionPage, CheckMode, userAnswers.success.value) mustBe
              routes.CheckAnswersElectionsController.onPageLoad()
          }
        }
      }

      "from the Investment Name page" should {

        "go to the Investments Review Answers List page" in {
          navigator.nextPage(InvestmentNamePage, CheckMode, emptyUserAnswers) mustBe
            routes.InvestmentsReviewAnswersListController.onPageLoad()
        }
      }

      "from the group EBITDA chargeable gains before page" when {

        "the answer is false" should {

          "go to the Group EBITDA chargeable gains elect page in CheckMode" in {
            val userAnswers = emptyUserAnswers.set(ElectedGroupEBITDABeforePage, false).success.value
            navigator.nextPage(ElectedGroupEBITDABeforePage, CheckMode, userAnswers) mustBe
              routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(CheckMode)
          }
        }

        "the answer is true" should {

          "go to the Check You Answers page" in {
            val userAnswers = emptyUserAnswers.set(ElectedGroupEBITDABeforePage, true).success.value
            navigator.nextPage(ElectedGroupEBITDABeforePage, CheckMode, userAnswers) mustBe
              routes.CheckAnswersElectionsController.onPageLoad()
          }
        }
      }

      "go to the Check Answers Elections page" in {
        navigator.nextPage(InterestAllowanceConsolidatedPshipElectionPage, CheckMode, emptyUserAnswers) mustBe
          routes.CheckAnswersElectionsController.onPageLoad()
      }

      "GroupRatioBlendedElectionPage" when {
        "go to check your answers if false" in {
          val userAnswers = UserAnswers(id = "id").set(GroupRatioBlendedElectionPage, false).get
          navigator.nextPage(GroupRatioBlendedElectionPage, CheckMode, userAnswers) mustBe
            routes.CheckAnswersElectionsController.onPageLoad()
        }

        "go to check your answers if true and next page IS populated" in {
          val userAnswers = for {
            addInv <- UserAnswers(id = "id").set(GroupRatioBlendedElectionPage, true)
            finalUa <- addInv.set(AddInvestorGroupPage, true)
          } yield finalUa

          navigator.nextPage(GroupRatioBlendedElectionPage, CheckMode, userAnswers.get) mustBe
            routes.CheckAnswersElectionsController.onPageLoad
        }

        "go to normal routes if true and next page IS NOT populated" in {
          val userAnswers = UserAnswers(id = "id").set(GroupRatioBlendedElectionPage, true).get
          navigator.nextPage(GroupRatioBlendedElectionPage, CheckMode, userAnswers) mustBe
            routes.AddInvestorGroupController.onPageLoad(NormalMode)
        }
      }

      "ElectedInterestAllowanceAlternativeCalcBeforePage" when {
        "go to check your answers when true" in {
          val userAnswers = UserAnswers(id = "id").set(ElectedInterestAllowanceAlternativeCalcBeforePage, true).get
          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, CheckMode, userAnswers) mustBe
            routes.CheckAnswersElectionsController.onPageLoad()
        }

        "go to InterestAllowanceAlternativeCalcElectionController when false" in {
          val userAnswers = UserAnswers(id = "id").set(ElectedInterestAllowanceAlternativeCalcBeforePage, false).get
          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, NormalMode, userAnswers) mustBe
            routes.InterestAllowanceAlternativeCalcElectionController.onPageLoad(NormalMode)
        }
      }

      "InterestAllowanceNonConsolidatedInvestmentsElectionPage" when {
        "true and investments is empty should go to investments addition page" in {
          val userAnswers = UserAnswers(id = "id").set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, true).get
          navigator.nextPage(InterestAllowanceNonConsolidatedInvestmentsElectionPage, CheckMode, userAnswers) mustBe
            routes.InvestmentsReviewAnswersListController.onPageLoad()
        }

        "true and one investment exists should go to CYA" in {
          val userAnswers = (for {
            ua <- UserAnswers("id").set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, true)
            finalUa <- ua.set(InvestmentNamePage, "Investment 1", Some(1))
          } yield finalUa).get

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, CheckMode, userAnswers) mustBe
            routes.CheckAnswersElectionsController.onPageLoad()
        }

        "true and multiple investments exist should go to CYA" in {
          val userAnswers = (for {
            ua <- UserAnswers("id").set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, true)
            ua2 <- ua.set(InvestmentNamePage, "Investment 1", Some(1))
            finalUa <- ua2.set(InvestmentNamePage, "Investment 2", Some(2))
          } yield finalUa).get

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, CheckMode, userAnswers) mustBe
            routes.CheckAnswersElectionsController.onPageLoad()
        }

        "false should go to CYA" in {
          val userAnswers = UserAnswers("id").set(InterestAllowanceNonConsolidatedInvestmentsElectionPage, false).get

          navigator.nextPage(ElectedInterestAllowanceAlternativeCalcBeforePage, CheckMode, userAnswers) mustBe
            routes.CheckAnswersElectionsController.onPageLoad()
        }
      }
    }
  }
}