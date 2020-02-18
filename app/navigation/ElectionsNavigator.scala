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

package navigation

import controllers.elections.routes
import javax.inject.{Inject, Singleton}
import models._
import pages._
import pages.elections._
import play.api.mvc.{Call, NoHeaderRangeSet}

@Singleton
class ElectionsNavigator @Inject()() extends Navigator {

  //TODO update with next page
  val normalRoutes: Map[Page, UserAnswers => Call] = Map(
    GroupRatioElectionPage -> (_ => routes.EnterANGIEController.onPageLoad(NormalMode)),
    EnterANGIEPage -> (_.get(GroupRatioElectionPage) match {
      case Some(true) => routes.EnterQNGIEController.onPageLoad(NormalMode)
      case Some(false) => routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
      case None => routes.GroupRatioElectionController.onPageLoad(NormalMode)
    }),
    EnterQNGIEPage -> (_ => routes.GroupEBITDAController.onPageLoad(NormalMode)),
    GroupEBITDAPage -> (_ => routes.GroupRatioPercentageController.onPageLoad(NormalMode)),
    GroupRatioPercentagePage -> (_ => routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode)),
    GroupRatioBlendedElectionPage -> (_.get(GroupRatioBlendedElectionPage) match {
      case Some(true) => routes.AddInvestorGroupController.onPageLoad(NormalMode)
      case Some(false) => routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
      case _ => routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode)
    }),
    ElectedGroupEBITDABeforePage -> (_.get(ElectedGroupEBITDABeforePage) match {
      case Some(true) => routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
      case Some(false) => routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(NormalMode)
      case _ => routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
    }),
    GroupEBITDAChargeableGainsElectionPage -> (_ => routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)),
    ElectedInterestAllowanceAlternativeCalcBeforePage -> (_.get(ElectedInterestAllowanceAlternativeCalcBeforePage) match {
      case Some(true) => routes.InterestAllowanceNonConsolidatedInvestmentsElectionController.onPageLoad(NormalMode)
      case Some(false) => routes.InterestAllowanceAlternativeCalcElectionController.onPageLoad(NormalMode)
      case _ => routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode)
    }),
    InterestAllowanceAlternativeCalcElectionPage -> (_ => routes.InterestAllowanceNonConsolidatedInvestmentsElectionController.onPageLoad(NormalMode)),
    InterestAllowanceNonConsolidatedInvestmentsElectionPage -> (_ => routes.ElectedInterestAllowanceConsolidatedPshipBeforeController.onPageLoad(NormalMode)),
    ElectedInterestAllowanceConsolidatedPshipBeforePage -> (_.get(ElectedInterestAllowanceConsolidatedPshipBeforePage) match {
      case Some(true) => routes.PartnershipNameController.onPageLoad(NormalMode)
      case Some(false) => routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
      case _ => routes.ElectedInterestAllowanceConsolidatedPshipBeforeController.onPageLoad(NormalMode)
    }),
    InterestAllowanceConsolidatedPshipElectionPage -> (_.get(InterestAllowanceConsolidatedPshipElectionPage) match {
      case Some(true) => routes.PartnershipNameController.onPageLoad(NormalMode)
      case Some(false) => checkYourAnswers
      case _ => routes.InterestAllowanceConsolidatedPshipElectionController.onPageLoad(NormalMode)
    }),
    AddInvestorGroupPage -> (_.get(AddInvestorGroupPage) match {
      case Some(true) => routes.InvestorGroupNameController.onPageLoad(NormalMode)
      case Some(false) => routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)
      case _ => routes.AddInvestorGroupController.onPageLoad(NormalMode)
    }),
    InvestorGroupNamePage -> (_ => routes.InvestorRatioMethodController.onPageLoad(NormalMode)),
    InvestorRatioMethodPage -> (_ => routes.OtherInvestorGroupElectionsController.onPageLoad(NormalMode)),
    OtherInvestorGroupElectionsPage -> (_ => routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode)),
    PartnershipNamePage -> (_ => routes.IsUkPartnershipController.onPageLoad(NormalMode)),
    IsUkPartnershipPage -> (_.get(IsUkPartnershipPage) match {
      case Some(true) => routes.PartnershipSAUTRController.onPageLoad(NormalMode)
      case Some(false) => checkYourAnswers
      case _ => routes.IsUkPartnershipController.onPageLoad(NormalMode)
    }),
    PartnershipSAUTRPage -> (_ => checkYourAnswers),
    CheckAnswersElectionsPage -> (_ => controllers.routes.UnderConstructionController.onPageLoad())
  )

  val checkRouteMap: Map[Page, UserAnswers => Call] = Map().withDefaultValue(_ =>
    routes.CheckAnswersElectionsController.onPageLoad()
  )

  //TODO update with CYA and Next Section calls
  private def checkYourAnswers: Call = routes.CheckAnswersElectionsController.onPageLoad()
  private def nextSection(mode: Mode): Call = controllers.routes.UnderConstructionController.onPageLoad()

  def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call = mode match {
    case NormalMode => normalRoutes(page)(userAnswers)
    case CheckMode => checkRouteMap(page)(userAnswers)
  }
}
