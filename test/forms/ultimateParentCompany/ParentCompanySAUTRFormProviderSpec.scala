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

package forms.ultimateParentCompany

import forms.behaviours.{StringFieldBehaviours, UTRFieldBehaviours}
import play.api.data.FormError

class ParentCompanySAUTRFormProviderSpec extends StringFieldBehaviours with UTRFieldBehaviours{

  val requiredKey = "parentCompanySAUTR.error.required"
  val regexpKey = "parentCompanySAUTR.error.regexp"
  val checksumKey = "parentCompanySAUTR.error.checksum"
  val maxLength = 10

  val form = new ParentCompanySAUTRFormProvider()()

  ".value" must {

    val fieldName = "value"

    behave like fieldThatBindsValidData(
      form,
      fieldName,
      stringsWithMaxLength(maxLength)
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )

    behave like validUTR(
      form = form,
      utrChecksumErrorKey = checksumKey,
      utrRegexpKey = regexpKey
    )

  }
}
