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

package controllers.checkTotals

import assets.UkCompanyITConstants.ukCompanyModelMax
import assets.{BaseITConstants, PageTitles}
import pages.ukCompanies.UkCompaniesPage
import play.api.http.Status._
import play.api.libs.json.JsString
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class DerivedCompanyControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /check-totals/derived-company" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()
          appendList(UkCompaniesPage, ukCompanyModelMax)

          val res = getRequest("/check-totals/derived-company")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.derivedCompany)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/check-totals/derived-company")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /check-totals/derived-company" when {

      "user is authorised" when {

        "redirect to the next page" in {

          AuthStub.authorised()

          setAnswers(emptyUserAnswers)

          val res = postRequest("/check-totals/derived-company", JsString(""))()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.reviewAndComplete.routes.ReviewAndCompleteController.onPageLoad().url)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/check-totals/derived-company", JsString(""))()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }
  }
}
