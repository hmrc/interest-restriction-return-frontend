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

package controllers.ultimateParentCompany

import assets.DeemedParentITConstants.{deemedParentModelNonUkCompany, deemedParentModelUkCompany}
import assets.{BaseITConstants, PageTitles}
import controllers.ultimateParentCompany.{routes => ultimateParentCompanyRoutes}
import models.{CheckMode, NormalMode}
import pages.ultimateParentCompany.DeemedParentPage
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class PayTaxInUkControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /ultimate-parent-company/1/pay-tax-in-uk" when {

      "user is authorised" should {

        "user answer for parent company name exists" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
            )

            val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.payTaxInUk)
              )
            }
          }
        }

        "user answer for parent company name does NOT exist" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk")()

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

          val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /ultimate-parent-company/1/pay-tax-in-uk" when {

      "user is authorised" when {

        "enters true" should {

          "redirect to under Limited Liability Partnership page" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk", Json.obj("value" -> "true"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(ultimateParentCompanyRoutes.LimitedLiabilityPartnershipController.onPageLoad(1, NormalMode).url)
              )
            }
          }
        }

        "enters false" should {

          "redirect to Country of Incorporation page" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk", Json.obj("value" -> "false"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(ultimateParentCompanyRoutes.CountryOfIncorporationController.onPageLoad(1, NormalMode).url)
              )
            }
          }
        }

        "enters an invalid answer" when {

          "user answer for parent company name exists" should {

            "return a BAD_REQUEST (400)" in {

              AuthStub.authorised()
              setAnswers(
                emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
              )

              val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk", Json.obj("value" -> ""))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(BAD_REQUEST)
                )
              }
            }
          }

          "user answer for parent company name does NOT exist" should {

            "return a ISE (500)" in {

              AuthStub.authorised()

              val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk", Json.obj("value" -> ""))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(INTERNAL_SERVER_ERROR)
                )
              }
            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk", Json.obj("value" -> companyName))()

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

    "GET /ultimate-parent-company/1/pay-tax-in-uk/change" when {

      "user is authorised" should {

        "user answer for parent company name exists" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
            )

            val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk/change")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.payTaxInUk)
              )
            }
          }
        }

        "user answer for parent company name does NOT exist" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk/change")()

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

          val res = getRequest("/ultimate-parent-company/1/pay-tax-in-uk/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /ultimate-parent-company/1/pay-tax-in-uk" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "redirect to CheckYourAnswers page when user selects `yes` and already has `llp`" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelUkCompany, Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> "true"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.ultimateParentCompany.routes.CheckAnswersGroupStructureController.onPageLoad(1).url)
              )
            }
          }

          "redirect to LLP page when user selects `yes` and does not have `llp`" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelUkCompany.copy(limitedLiabilityPartnership = None), Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> "true"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.ultimateParentCompany.routes.LimitedLiabilityPartnershipController.onPageLoad(1,CheckMode).url)
              )
            }
          }

          "redirect to Country Of Incorporation page when user selects `no` and does not have `country of incorporation`" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelUkCompany.copy(countryOfIncorporation = None), Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> "false"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.ultimateParentCompany.routes.CountryOfIncorporationController.onPageLoad(1,CheckMode).url)
              )
            }
          }

          "redirect to CYA page when user selects `no` and does have `country of incorporation`" in {

            AuthStub.authorised()
            setAnswers(
              emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
            )

            val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> "false"))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.ultimateParentCompany.routes.CheckAnswersGroupStructureController.onPageLoad(1).url)
              )
            }
          }
        }

        "enters an invalid answer" when {

          "user answer for parent company name exists" should {

            "return a BAD_REQUEST (400)" in {

              AuthStub.authorised()
              setAnswers(
                emptyUserAnswers.set(DeemedParentPage, deemedParentModelNonUkCompany, Some(1)).success.value
              )

              val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> ""))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(BAD_REQUEST)
                )
              }
            }
          }

          "user answer for parent company name does NOT exist" should {

            "return a ISE (500)" in {

              AuthStub.authorised()

              val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> ""))()

              whenReady(res) { result =>
                result should have(
                  httpStatus(INTERNAL_SERVER_ERROR)
                )
              }
            }
          }
        }

      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/ultimate-parent-company/1/pay-tax-in-uk/change", Json.obj("value" -> companyName))()

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
