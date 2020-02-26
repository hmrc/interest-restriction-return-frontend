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

import assets.UkCompanyITConstants.ukCompanyModelMin
import assets.{BaseITConstants, PageTitles}
import models.NetTaxInterestIncomeOrExpense.NetTaxInterestIncome
import pages.ukCompanies.{NetTaxInterestIncomeOrExpensePage, UkCompaniesPage}
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class NetTaxInterestAmountControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /uk-companies/net-tax-interest-amount" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val userAnswers = emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMin.copy(netTaxInterestIncome = None, netTaxInterestExpense = None)).success.value
            .set(NetTaxInterestIncomeOrExpensePage, NetTaxInterestIncome).success.value

          setAnswers(userAnswers)

          val res = getRequest("/uk-companies/net-tax-interest-amount")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.netTaxInterestAmount(ukCompanyModelMin.companyName.name))
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/uk-companies/net-tax-interest-amount")()

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /uk-companies/net-tax-interest-amount" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "redirect to NetTaxInterestAmount page" in {

            AuthStub.authorised()

            val res = postRequest("/uk-companies/net-tax-interest-amount", Json.obj("value" -> 1))()
//TODO: Implement
//            whenReady(res) { result =>
//              result should have(
//                httpStatus(SEE_OTHER),
//                redirectLocation(controllers.ukCompanies.routes.NetTaxInterestAmountController.onPageLoad(NormalMode).url)
//              )
//            }
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = postRequest("/uk-companies/net-tax-interest-amount", Json.obj("value" -> 1))()

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

    "GET /uk-companies/net-tax-interest-amount" when {

      "user is authorised" should {

        "return OK (200)" in {

          val userAnswers = emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMin.copy(netTaxInterestIncome = None, netTaxInterestExpense = None)).success.value
            .set(NetTaxInterestIncomeOrExpensePage, NetTaxInterestIncome).success.value

          setAnswers(userAnswers)

          AuthStub.authorised()

          val res = getRequest("/uk-companies/net-tax-interest-amount/change")()

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf(PageTitles.netTaxInterestAmount(ukCompanyModelMin.companyName.name))
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/uk-companies/net-tax-interest-amount/change")()

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
