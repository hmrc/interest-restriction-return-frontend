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
import models.NormalMode
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class GroupRatioElectionControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /elections/group-ratio-election" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()
          val res = getRequest("/elections/group-ratio-election")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.groupRatioElection)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/elections/group-ratio-election")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /elections/group-ratio-election" when {

      "user is authorised" when {

        "enters true" when {

          "redirect to ANGIE page" in {

            AuthStub.authorised()

            val res = postRequest("/elections/group-ratio-election", Json.obj("value" -> true))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode).url)
              )
            }
          }
        }

        "enters false" when {

          "redirect to EBITDA before page" in {

            AuthStub.authorised()

            val res = postRequest("/elections/group-ratio-election", Json.obj("value" -> false))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(routes.ElectedGroupEBITDABeforeController.onPageLoad(NormalMode).url)
              )
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/elections/group-ratio-election", Json.obj("value" -> true))()

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

    "GET /elections/group-ratio-election" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/elections/group-ratio-election/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.groupRatioElection)
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/elections/group-ratio-election/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }

      "POST elections/group-ratio-election" when {

        "user is authorised" when {

          "enters a valid answer" when {

            "the answer is true" should {

              "redirect to the Blended Ratio Election page" in {

                AuthStub.authorised()

                val res = postRequest("/elections/group-ratio-election/change", Json.obj("value" -> true))()

                whenReady(res) { result =>
                  result should have(
                    httpStatus(SEE_OTHER),
                    redirectLocation(routes.GroupRatioBlendedElectionController.onPageLoad(NormalMode).url)
                  )
                }

              }

            }

            "the answer is false" should {

              "redirect to the Blended Ratio Election page" in {

                AuthStub.authorised()

                val res = postRequest("/elections/group-ratio-election/change", Json.obj("value" -> false))()

                whenReady(res) { result =>
                  result should have(
                    httpStatus(SEE_OTHER),
                    redirectLocation(routes.GroupEBITDAChargeableGainsElectionController.onPageLoad(NormalMode).url)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
