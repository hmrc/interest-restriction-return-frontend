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

package assets.messages

object CheckAnswersUkCompanyMessages {

  val title: String => String = name => s"Check this company’s details"
  val subheading: String => String = x => x

  val companyName= "Name"
  val companyCTUTR = "CTUTR"
  val consenting = "Consenting"
  val taxEBITDA = "Tax-EBITDA"
  val netTaxInterest = "Net tax-interest"
  val income = "Income"
  val expense = "Expense"
  val reactivationAmount = "Company reactivations"

  //SummaryList Row

  val companyNameLabel= "Company Name"
  val companyCTUTRLabel = "UTR"
  val consentingLabel = "Consenting"
  val taxEBITDALabel = "Tax-EBITDA"
  val netTaxInterestLabel = "Net tax-interest expense or income"
  val netTaxInterestAmountLabel = "Net tax-interest amount"
  val incomeLabel = "Income"
  val expenseLabel = "Expense"
  val reactivationAmountLabel = "Company reactivations"
}
