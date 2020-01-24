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

package controllers.aboutReportingCompany

import assets.BaseITConstants
import models.NormalMode
import play.api.http.Status._
import play.api.libs.json.Json
import stubs.AuthStub
import utils.{CreateRequestHelper, CustomMatchers, IntegrationSpecBase}

class ReportingCompanyNameControllerISpec extends IntegrationSpecBase with CreateRequestHelper with CustomMatchers with BaseITConstants {

  "in Normal mode" when {

    "GET /reporting-company-name" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/reporting-company-name")

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf("Reporting company name")
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/reporting-company-name")

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /reporting-company-name" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "redirect to ReportingCompanyCTUTR page" in {

            AuthStub.authorised()

            val res = postRequest("/reporting-company-name", Json.obj("value" -> companyName))

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.aboutReportingCompany.routes.ReportingCompanyCTUTRController.onPageLoad(NormalMode).url)
              )
            }
          }

          "user not authorised" should {

            "return SEE_OTHER (303)" in {

              AuthStub.unauthorised()

              val res = postRequest("/reporting-company-name", Json.obj("value" -> companyName))

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
  }

  "in Change mode" when {

    "GET /reporting-company-name" when {

      "user is authorised" should {

        "return OK (200)" in {

          AuthStub.authorised()

          val res = getRequest("/reporting-company-name/change")

          whenReady(res) { result =>
            result should have(
              httpStatus(OK),
              titleOf("Reporting company name")
            )
          }
        }
      }

      "user not authorised" should {

        "return SEE_OTHER (303)" in {

          AuthStub.unauthorised()

          val res = getRequest("/reporting-company-name/change")

          whenReady(res) { result =>
            result should have(
              httpStatus(SEE_OTHER),
              redirectLocation(controllers.errors.routes.UnauthorisedController.onPageLoad().url)
            )
          }
        }
      }
    }

    "POST /reporting-company-name" when {

      "user is authorised" when {

        "enters a valid answer" when {

          "redirect to CheckYourAnswers page" in {

            AuthStub.authorised()

            val res = postRequest("/reporting-company-name/change", Json.obj("value" -> companyName))

            whenReady(res) { result =>
              result should have(
                httpStatus(SEE_OTHER),
                redirectLocation(controllers.aboutReportingCompany.routes.CheckAnswersReportingCompanyController.onPageLoad().url)
              )
            }
          }

          "user not authorised" should {

            "return SEE_OTHER (303)" in {

              AuthStub.unauthorised()

              val res = postRequest("/reporting-company-name/change", Json.obj("value" -> companyName))

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
  }
}
