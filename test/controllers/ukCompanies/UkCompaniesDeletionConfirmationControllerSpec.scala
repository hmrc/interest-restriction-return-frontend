/*
 * Copyright 2021 HM Revenue & Customs
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

import assets.constants.fullReturn.UkCompanyConstants._
import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import controllers.errors
import forms.ukCompanies.UkCompaniesDeletionConfirmationFormProvider
import navigation.FakeNavigators.FakeUkCompaniesNavigator
import pages.ukCompanies.{UkCompaniesPage, RestrictionAmountForAccountingPeriodPage, CompanyAccountingPeriodEndDatePage}
import play.api.test.Helpers._
import views.html.ukCompanies.UkCompaniesDeletionConfirmationView
import java.time.LocalDate

class UkCompaniesDeletionConfirmationControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[UkCompaniesDeletionConfirmationView]
  val formProvider = injector.instanceOf[UkCompaniesDeletionConfirmationFormProvider]
  val form = formProvider()

  object Controller extends UkCompaniesDeletionConfirmationController(
    messagesApi = messagesApi,
    sessionRepository = mockSessionRepository,
    navigator = FakeUkCompaniesNavigator,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    formProvider = formProvider,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "UkCompaniesDeletionConfirmation Controller" must {

    "onPageLoad" must {

      "when there is a uk company in the user answers" should {

        "return OK and the correct view" in {

          mockGetAnswers(Some(
            emptyUserAnswers
              .set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get
          ))

          val result = Controller.onPageLoad(idx = 1)(fakeRequest)

          status(result) mustEqual OK
          contentAsString(result) mustEqual view(
            form,
            routes.UkCompaniesDeletionConfirmationController.onSubmit(idx = 1),
            ukCompanyModelMax.companyDetails.companyName
          )(fakeRequest, messages, frontendAppConfig).toString
        }
      }

      "when there is no uk company in the user answers" should {

        "return ISE (500)" in {

          mockGetAnswers(Some(emptyUserAnswers))

          val result = Controller.onPageLoad(idx = 1)(fakeRequest)

          status(result) mustEqual INTERNAL_SERVER_ERROR
        }
      }

      "redirect to Session Expired for a GET if no existing data is found" in {

        mockGetAnswers(None)

        val result = Controller.onPageLoad(idx = 1)(fakeRequest)

        status(result) mustEqual SEE_OTHER

        redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
      }
    }

    "onSubmit" when {

      "user confirms deletion by choosing Yes" should {

        "delete the selected uk company and redirect to the next page" in {

          val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

          val userAnswersBefore = emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get
            .set(CompanyAccountingPeriodEndDatePage(1, 1), LocalDate.now).get
            .set(RestrictionAmountForAccountingPeriodPage(1, 1), BigDecimal(123)).get
            .set(UkCompaniesPage, ukCompanyModelMax, Some(2)).get
            .set(CompanyAccountingPeriodEndDatePage(2, 1), LocalDate.now).get
            .set(RestrictionAmountForAccountingPeriodPage(2, 1), BigDecimal(234)).get

          val userAnswersAfter = emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get
            .set(CompanyAccountingPeriodEndDatePage(1, 1), LocalDate.now).get
            .set(RestrictionAmountForAccountingPeriodPage(1, 1), BigDecimal(123)).get

          mockGetAnswers(Some(userAnswersBefore))
          mockSetAnswers(userAnswersAfter)

          val result = Controller.onSubmit(idx = 2)(request)

          status(result) mustEqual SEE_OTHER
          redirectLocation(result) mustBe Some(onwardRoute.url)
        }
      }

      "user declines deletion by choosing No" should {

        "redirect to the next page when valid data is submitted" in {

          val request = fakeRequest.withFormUrlEncodedBody(("value", "false"))

          val userAnswersBefore = emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get
            .set(UkCompaniesPage, ukCompanyModelMax, Some(2)).get

          mockGetAnswers(Some(userAnswersBefore))

          val result = Controller.onSubmit(idx = 2)(request)

          status(result) mustEqual SEE_OTHER
          redirectLocation(result) mustBe Some(onwardRoute.url)
        }
      }

      "return a Bad Request and errors when invalid data is submitted" in {

        mockGetAnswers(Some(
          emptyUserAnswers
            .set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get
        ))

        val request = fakeRequest.withFormUrlEncodedBody(("value", ""))

        val result = Controller.onSubmit(idx = 1)(request)

        status(result) mustEqual BAD_REQUEST
      }

      "redirect to Session Expired for a POST if no existing data is found" in {

        val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

        mockGetAnswers(None)

        val result = Controller.onSubmit(idx = 1)(request)

        status(result) mustEqual SEE_OTHER

        redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
      }
    }
  }
}
