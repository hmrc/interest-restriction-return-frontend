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

package forms.behaviours

import play.api.data.{Form, FormError}
import utils.RemoveWhitespace

trait UTRFieldBehaviours extends FieldBehaviours with RemoveWhitespace{

  def validUTR(
                form: Form[_],
                utrFieldName: String = "value",
                utrChecksumErrorKey: String,
                utrRegexpKey: String
              ): Unit = {
    s"$utrFieldName" must {

      val validUTR: String = "1111111111"
      val invalidUTR: String = "1234567899"
      val validRegexp: String = "^[0-9]{10}$"
      val invalidRegexp: String = "1234"
      val whitespaceUTR: String = "     1    1     1   111    1   1  1  1        "

      "when binding a value which does match the regexp" when {

        "checksum fails" should {

          s"return the checksum error for $utrFieldName" in {
            val result = form.bind(Map(utrFieldName -> invalidUTR))
            result.errors.headOption mustEqual Some(FormError(utrFieldName, utrChecksumErrorKey))
          }
        }

        "whitespace is removed before binding" when {

          "entered value contains whitespace" in {

            val result = form.bind(Map(utrFieldName -> whitespaceUTR))
            result.value mustBe Some(validUTR)
          }
        }

        "checksum is successful" should {

          "return the field value" in {
            val result = form.bind(Map(utrFieldName -> validUTR))
            result.value mustBe Some(validUTR)
          }
        }

        "when binding a value which does not match the regexp" should {

          "return the regexp error" in {
            val result = form.bind(Map(utrFieldName -> invalidRegexp))
            result.errors.headOption mustEqual Some(FormError(utrFieldName, utrRegexpKey, Seq(validRegexp)))
          }
        }
      }
    }
  }
}

