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

package controllers.ukCompanies

import assets.UkCompanyITConstants._
import assets.{BaseITConstants, PageTitles}
import models.NetTaxInterestIncomeOrExpense.NetTaxInterestExpense
import models.NormalMode
import pages.ukCompanies.UkCompaniesPage
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class NetTaxInterestIncomeOrExpenseControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /uk-companies/1/net-tax-interest-income-or-expense" when {

      "user is authorised" when {

        "data is retrieved for the uk companies model" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get)

            val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.netTaxInterestIncomeOrExpense(ukCompanyModelMax.companyDetails.companyName))
              )
            }
          }
        }

        "no data is retrieved for the uk companies model" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense")()

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

          val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /uk-companies/1/net-tax-interest-income-or-expense" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "redirect to NetTaxInterestIncomeOrExpense page" in {

            AuthStub.authorised()

            setAnswers(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get)

            val res = postRequest("/uk-companies/1/net-tax-interest-income-or-expense", Json.obj("value" -> NetTaxInterestExpense.toString))()

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(routes.NetTaxInterestAmountController.onPageLoad(1, NormalMode).url)
              )
            }
          }
        }
      }

      "NO data is retrieved for the uk companies model" should {

        "Return ISE (500)" in {

          AuthStub.authorised()

          val res = postRequest("/uk-companies/1/net-tax-interest-income-or-expense", Json.obj("value" -> 1))()
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

        val res = postRequest("/uk-companies/1/net-tax-interest-income-or-expense", Json.obj("value" -> NetTaxInterestExpense.toString))()

        whenReady(res) {
          result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
        }
      }
    }
  }


  "in Change mode" when {

    "GET /uk-companies/1/net-tax-interest-income-or-expense" when {

      "user is authorised" should {

        "data is retrieved for the uk companies model" should {

          "return OK (200)" in {

            AuthStub.authorised()
            setAnswers(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get)

            val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense/change")()

            whenReady(res) { result =>
              result should have(
                httpStatus(OK),
                titleOf(PageTitles.netTaxInterestIncomeOrExpense(ukCompanyModelMax.companyDetails.companyName))
              )
            }
          }
        }

        "no data is retrieved for the uk companies model" should {

          "return ISE (500)" in {

            AuthStub.authorised()

            val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense/change")()

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

          val res = getRequest("/uk-companies/1/net-tax-interest-income-or-expense/change")()

          whenReady(res) {
            result =>
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
