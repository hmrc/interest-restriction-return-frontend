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

package controllers.groupStructure

import assets.{BaseITConstants, PageTitles}
import controllers.groupStructure.{routes => groupStructureRoutes}
import models.NormalMode
import pages.groupStructure.ParentCompanyNamePage
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class CountryOfIncorporationControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /group-structure/country-of-incorporation" when {

      "user is authorised" should {

        "user answer for parent company name exists" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(ParentCompanyNamePage, companyName)

            val res = getRequest("/group-structure/country-of-incorporation")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.countryOfIncorporation(companyName))
              )
            }
          }
        }

        "user answer for parent company name does NOT exist" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/group-structure/country-of-incorporation")()

            whenReady(res) { result =>
              result should have(
                httpStatus(INTERNAL_SERVER_ERROR)
              )
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/group-structure/country-of-incorporation")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /group-structure/country-of-incorporation" when {

      "user is authorised" when {

        "enters a valid country " should {

          "redirect to Local CRN page" in {

            AuthStub.authorised()

            val res = postRequest("/group-structure/country-of-incorporation", Json.obj("value" -> "United Kingdom"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(routes.LocalRegistrationNumberController.onPageLoad(NormalMode).url)
              )
            }
          }
        }

        "enters an invalid country" should {

          "return a BadRequest" in {

            AuthStub.authorised()
            setAnswers(ParentCompanyNamePage, companyName)

            val res = postRequest("/group-structure/country-of-incorporation", Json.obj("value" -> "Invalid Country"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(BAD_REQUEST)
              )
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/group-structure/country-of-incorporation", Json.obj("value" -> "United Kingdom"))()

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

  "in Change mode" when {

    "GET /group-structure/country-of-incorporation" when {

      "user is authorised" when {

        "user answer for parent company name exists" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(ParentCompanyNamePage, companyName)

            val res = getRequest("/group-structure/country-of-incorporation/change")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.countryOfIncorporation(companyName))
              )
            }
          }
        }

        "user answer for parent company name does NOT exist" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/group-structure/country-of-incorporation/change")()

            whenReady(res) { result =>
              result should have(
                httpStatus(INTERNAL_SERVER_ERROR)
              )
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/group-structure/country-of-incorporation/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    //TODO: Add Check Your Answers tests
  }
}