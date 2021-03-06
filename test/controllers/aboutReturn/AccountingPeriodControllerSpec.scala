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

package controllers.aboutReturn

import java.time.{LocalDate, ZoneOffset}

import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import controllers.errors
import forms.aboutReturn.AccountingPeriodFormProvider
import models.NormalMode
import navigation.FakeNavigators.FakeAboutReturnNavigator
import play.api.test.Helpers._
import views.html.aboutReturn.AccountingPeriodView
import models.returnModels.AccountingPeriodModel
import pages.aboutReturn.AccountingPeriodPage

class AccountingPeriodControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[AccountingPeriodView]
  val formProvider = injector.instanceOf[AccountingPeriodFormProvider]
  val form = formProvider()

  val validStartDate = LocalDate.now(ZoneOffset.UTC)
  val validEndDate = LocalDate.now(ZoneOffset.UTC).plusMonths(1L)
  val validAnswer = AccountingPeriodModel(validStartDate, validEndDate)

  object Controller extends AccountingPeriodController(
    messagesApi = messagesApi,
    sessionRepository = mockSessionRepository,
    navigator = FakeAboutReturnNavigator,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    formProvider = formProvider,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "AccountingPeriod Controller" must {

    "return OK and the correct view for a GET" in {

      mockGetAnswers(Some(emptyUserAnswers))

      val result = Controller.onPageLoad(NormalMode)(fakeRequest)

      status(result) mustEqual OK
      contentAsString(result) mustEqual view(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

    }

    "populate the view correctly on a GET when the question has previously been answered" in {

      val userAnswers = emptyUserAnswers.set(AccountingPeriodPage, validAnswer).success.value

      mockGetAnswers(Some(userAnswers))

      val result = Controller.onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe OK
    }

    "redirect to the next page when valid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(
        "startValue.day" -> validStartDate.getDayOfMonth.toString,
        "startValue.month" -> validStartDate.getMonthValue.toString,
        "startValue.year" -> validStartDate.getYear.toString,
        "endValue.day" -> validEndDate.getDayOfMonth.toString,
        "endValue.month" -> validEndDate.getMonthValue.toString,
        "endValue.year" -> validEndDate.getYear.toString
      )

      mockGetAnswers(Some(emptyUserAnswers))
      mockSetAnswers

      val result = Controller.onSubmit(NormalMode)(request)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))

      mockGetAnswers(Some(emptyUserAnswers))

      val result = Controller.onSubmit(NormalMode)(request)

      status(result) mustBe BAD_REQUEST
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      mockGetAnswers(None)

      val result = Controller.onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(errors.routes.SessionExpiredController.onPageLoad().url)
    }

    "redirect to Session Expired for a POST if no existing data is found" in {

      val request = fakeRequest.withFormUrlEncodedBody(
        "startValue.day" -> validStartDate.getDayOfMonth.toString,
        "startValue.month" -> validStartDate.getMonthValue.toString,
        "startValue.year" -> validStartDate.getYear.toString,
        "endValue.day" -> validEndDate.getDayOfMonth.toString,
        "endValue.month" -> validEndDate.getMonthValue.toString,
        "endValue.year" -> validEndDate.getYear.toString
      )

      mockGetAnswers(None)

      val result = Controller.onSubmit(NormalMode)(request)

      status(result) mustBe SEE_OTHER

      redirectLocation(result) mustBe Some(errors.routes.SessionExpiredController.onPageLoad().url)
    }
  }
}

