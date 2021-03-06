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

package forms.ukCompanies

import forms.mappings.Mappings
import javax.inject.Inject
import models.NetTaxInterestIncomeOrExpense
import play.api.data.Form

class NetTaxInterestAmountFormProvider @Inject() extends Mappings {

  def apply(incomeOrExpense: NetTaxInterestIncomeOrExpense): Form[BigDecimal] =
    Form(
      "value" -> numeric(
        s"netTaxInterestAmount.$incomeOrExpense.error.required",
        s"netTaxInterestAmount.$incomeOrExpense.error.invalidNumeric",
        s"netTaxInterestAmount.$incomeOrExpense.error.nonNumeric")
        .verifying(inRange[BigDecimal](0, 999999999999999.99, s"netTaxInterestAmount.$incomeOrExpense.error.outOfRange"))
    )
}
