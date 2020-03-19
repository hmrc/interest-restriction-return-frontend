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

package pages

import models.Section
import pages.aboutReportingCompany._
import pages.aboutReturn._
import pages.checkTotals.{ReviewNetTaxInterestPage, ReviewTaxEBITDAPage}
import pages.elections.{IsUkPartnershipPage, _}
import pages.groupStructure._
import pages.reviewAndComplete.ReviewAndCompletePage
import pages.startReturn._
import pages.ukCompanies._
import play.api.libs.json.{JsPath, JsString, Reads, Writes}

import scala.language.implicitConversions

trait Page

object Page {

  implicit def toString(page: Page): String = page.toString

  val startReturnSectionPages: Seq[Page] = List(
    AgentActingOnBehalfOfCompanyPage,
    AgentNamePage,
    FullOrAbbreviatedReturnPage,
    ReportingCompanyAppointedPage,
    ReportingCompanyRequiredPage,
    AccountingPeriodStartPage,
    AccountingPeriodEndPage,
    ReportingCompanyNamePage,
    ReportingCompanyCTUTRPage,
    RevisingReturnPage,
    IndexPage,
    CheckAnswersReportingCompanyPage
  )

  val aboutReturnSectionPages: Seq[Page] = List(
    GroupInterestAllowancePage,
    GroupInterestCapacityPage,
    GroupSubjectToReactivationsPage,
    GroupSubjectToRestrictionsPage,
    InfrastructureCompanyElectionPage,
    InterestAllowanceBroughtForwardPage,
    InterestReactivationsCapPage,
    ReturnContainEstimatesPage
  )


  val ukCompaniesSectionPages: Seq[Page] = List(
    UkCompaniesPage,
    CheckAnswersUkCompanyPage,
    CompanyDetailsPage,
    ConsentingCompanyPage,
    EnterCompanyTaxEBITDAPage,
    NetTaxInterestIncomeOrExpensePage,
    NetTaxInterestAmountPage,
    ReactivationAmountPage,
    UkCompaniesDeletionConfirmationPage,
    AddRestrictionPage,
    CompanyAccountingPeriodSameAsGroupPage,
    RestrictionAmountSameAPPage
  )

  val electionsSectionPages: Seq[Page] = List(
    AddInvestorGroupPage,
    ElectedGroupEBITDABeforePage,
    ElectedInterestAllowanceAlternativeCalcBeforePage,
    ElectedInterestAllowanceConsolidatedPshipBeforePage,
    EnterQNGIEPage,
    EnterANGIEPage,
    GroupEBITDAChargeableGainsElectionPage,
    GroupEBITDAPage,
    GroupRatioBlendedElectionPage,
    GroupRatioPercentagePage,
    InterestAllowanceAlternativeCalcElectionPage,
    InterestAllowanceConsolidatedPshipElectionPage,
    InterestAllowanceNonConsolidatedInvestmentsElectionPage,
    InvestmentNamePage,
    InvestmentsDeletionConfirmationPage,
    InvestmentsReviewAnswersListPage,
    CheckAnswersElectionsPage,
    InvestorGroupNamePage,
    InvestorGroupsDeletionConfirmationPage,
    InvestorGroupsPage,
    InvestorRatioMethodPage,
    IsUkPartnershipPage,
    OtherInvestorGroupElectionsPage,
    PartnershipSAUTRPage,
    PartnershipNamePage,
    GroupRatioElectionPage,
    PartnershipsPage,
    PartnershipDeletionConfirmationPage,
    PartnershipsReviewAnswersListPage
  )

  val groupStructureSectionPages: Seq[Page] = List(
    CheckAnswersGroupStructurePage,
    DeletionConfirmationPage,
    CountryOfIncorporationPage,
    ParentCompanySAUTRPage,
    PayTaxInUkPage,
    LimitedLiabilityPartnershipPage,
    ParentCompanyCTUTRPage,
    ParentCompanyNamePage,
    HasDeemedParentPage,
    ReportingCompanySameAsParentPage,
    DeemedParentPage
  )

  val checkTotalsSectionPages: Seq[Page] = List(
    DerivedCompanyPage,
    ReviewTaxEBITDAPage,
    ReviewNetTaxInterestPage
  )

  val reviewAndCompleteSectionPages: Seq[Page] = List(
    ReviewAndCompletePage
  )

  val sections = Map(
    Section.StartReturn -> startReturnSectionPages,
    Section.AboutReturn -> aboutReturnSectionPages,
    Section.UkCompanies -> ukCompaniesSectionPages,
    Section.Elections -> electionsSectionPages,
    Section.CheckTotals -> checkTotalsSectionPages,
    Section.ReviewAndComplete -> reviewAndCompleteSectionPages,
    Section.GroupStructure -> groupStructureSectionPages
  )


  val pages: Map[String, Page] = sections.flatMap{
    section => section._2.map(page => page.toString -> page)
  } ++ Map(
    ConfirmationPage.toString -> ConfirmationPage,
    ContinueSavedReturnPage.toString -> ContinueSavedReturnPage
  )

  val allQuestionPages = pages.values.collect{ case a: QuestionPage[_] => a}.toList

  def apply(page: String): Page = pages(page)

  def unapply(arg: Page): String = pages.map(_.swap).apply(arg)

  implicit val reads: Reads[Page] = JsPath.read[String].map(apply)
  implicit val writes: Writes[Page] = Writes { page => JsString(unapply(page)) }
}
