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

package controllers.elections

import assets.{BaseITConstants, PageTitles}
import models.{CheckMode, NormalMode}
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class ElectedGroupEBITDABeforeControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /elections/elected-group-ebitda-before" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()
          val res = getRequest("/elections/elected-group-ebitda-before")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.electedGroupEBITDABefore)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/elections/elected-group-ebitda-before")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /elections/elected-group-ebitda-before" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "the answer is false" should {

            "redirect to Group EBITDA Chargeable Gains Election page" in {

              AuthStub.authorised()

              val res = postRequest("/elections/elected-group-ebitda-before", Json.obj("value" -> false))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(SEE_OTHER),
                  redirectLocation(routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(NormalMode).url)
                )
              }
            }
          }

          "the answer is true" should {

            "redirect to Elected Interest Allowance Alternative Calculation Before page" in {

              AuthStub.authorised()

              val res = postRequest("/elections/elected-group-ebitda-before", Json.obj("value" -> true))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(SEE_OTHER),
                  redirectLocation(routes.ElectedInterestAllowanceAlternativeCalcBeforeController.onPageLoad(NormalMode).url)
                )
              }
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/elections/elected-group-ebitda-before", Json.obj("value" -> true))()

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

    "GET /elections/elected-group-ebitda-before" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/elections/elected-group-ebitda-before/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.electedGroupEBITDABefore)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/elections/elected-group-ebitda-before/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /elections/elected-group-ebitda-before" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "the answer is false" should {

            "redirect to Group EBITDA Chargeable Gains Election page" in {

              AuthStub.authorised()

              val res = postRequest("/elections/elected-group-ebitda-before/change", Json.obj("value" -> false))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(SEE_OTHER),
                  redirectLocation(routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(CheckMode).url)
                )
              }
            }
          }

          "the answer is true" should {

            "redirect to Elected Interest Allowance Alternative Calculation Before page" in {

              AuthStub.authorised()

              val res = postRequest("/elections/elected-group-ebitda-before/change", Json.obj("value" -> true))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(SEE_OTHER),
                  redirectLocation(routes.CheckAnswersElectionsController.onPageLoad().url)
                )
              }
            }
          }
        }
      }
    }
  }
}
