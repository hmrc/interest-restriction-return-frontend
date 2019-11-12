/*
 * Copyright 2019 HM Revenue & Customs
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

package models

import generators.ModelGenerators
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import play.api.libs.json.{JsError, JsString, Json}

class MyNewPageSpec extends WordSpec with MustMatchers with ScalaCheckPropertyChecks with OptionValues with ModelGenerators {

  "MyNewPage" must {

    "deserialise valid values" in {

      val gen = arbitrary[MyNewPage]

      forAll(gen) {
        myNewPage =>

          JsString(myNewPage.toString).validate[MyNewPage].asOpt.value mustEqual myNewPage
      }
    }

    "fail to deserialise invalid values" in {

      val gen = arbitrary[String] suchThat (!MyNewPage.values.map(_.toString).contains(_))

      forAll(gen) {
        invalidValue =>

          JsString(invalidValue).validate[MyNewPage] mustEqual JsError("error.invalid")
      }
    }

    "serialise" in {

      val gen = arbitrary[MyNewPage]

      forAll(gen) {
        myNewPage =>

          Json.toJson(myNewPage) mustEqual JsString(myNewPage.toString)
      }
    }
  }
}
