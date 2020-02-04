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
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class LimitedLiabilityPartnershipControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /limited-liability-partnership" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/limited-liability-partnership")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.limitedLiabilityPartnership)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/limited-liability-partnership")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /limited-liability-partnership" when {

      "user is authorised" when {

        "enters true" when {

          "redirect to UnderConstruction page" in {

            AuthStub.authorised()

            val res = postRequest("/limited-liability-partnership", Json.obj("value" -> true))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.routes.UnderConstructionController.onPageLoad().url)
              )
            }
          }
        }

        "enters false" when {

          "redirect to InfrastructureCompanyElection page" in {

            AuthStub.authorised()

            val res = postRequest("/limited-liability-partnership", Json.obj("value" -> false))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.routes.UnderConstructionController.onPageLoad().url)
              )
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/limited-liability-partnership", Json.obj("value" -> true))()

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

    "GET /limited-liability-partnership" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/limited-liability-partnership/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.limitedLiabilityPartnership)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/limited-liability-partnership/change")()

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
