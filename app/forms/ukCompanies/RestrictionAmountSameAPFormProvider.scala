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
import play.api.data.Form

class RestrictionAmountSameAPFormProvider @Inject() extends Mappings {

  def apply(netTaxInterestExpenseAmount: BigDecimal): Form[BigDecimal] =
    Form(
      "value" -> numeric(
        "restrictionAmountSameAP.error.required",
        "restrictionAmountSameAP.error.invalidNumeric",
        "restrictionAmountSameAP.error.nonNumeric")
        .verifying(inRange[BigDecimal](0, 999999999999999.99, "restrictionAmountSameAP.error.outOfRange"))
        .verifying("restrictionAmountSameAP.error.expenseAmount", _ <= netTaxInterestExpenseAmount)
    )
}
