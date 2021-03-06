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

package controllers.ultimateParentCompany

import assets.constants.BaseConstants
import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import controllers.errors
import forms.ultimateParentCompany.LimitedLiabilityPartnershipFormProvider
import models.{NormalMode, UserAnswers}
import pages.ultimateParentCompany.DeemedParentPage
import play.api.test.Helpers._
import views.html.ultimateParentCompany.LimitedLiabilityPartnershipView
import navigation.FakeNavigators.FakeUltimateParentCompanyNavigator
import assets.constants.DeemedParentConstants._
import models.returnModels.UTRModel

import scala.concurrent.Future

class LimitedLiabilityPartnershipControllerSpec extends SpecBase with FeatureSwitching with BaseConstants with MockDataRetrievalAction {

  val view = injector.instanceOf[LimitedLiabilityPartnershipView]
  val formProvider = injector.instanceOf[LimitedLiabilityPartnershipFormProvider]
  val form = formProvider()

  object Controller extends LimitedLiabilityPartnershipController(
    messagesApi = messagesApi,
    sessionRepository = mockSessionRepository,
    navigator = FakeUltimateParentCompanyNavigator,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    formProvider = formProvider,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "LimitedLiabilityPartnership Controller" must {

    "return OK and the correct view for a GET" in {

      val userAnswers = emptyUserAnswers
        .set(DeemedParentPage, deemedParentModelMin, Some(1)).success.value

      mockGetAnswers(Some(userAnswers))

      val result = Controller.onPageLoad(1, NormalMode)(fakeRequest)

      status(result) mustEqual OK
      contentAsString(result) mustEqual view(
        form = form,
        mode = NormalMode,
        companyName = companyNameModel.name,
        postAction = routes.LimitedLiabilityPartnershipController.onSubmit(1, NormalMode)
      )(fakeRequest, messages, frontendAppConfig).toString
    }

    "populate the view correctly on a GET when the question has previously been answered" in {

      val userAnswers = emptyUserAnswers
        .set(DeemedParentPage, deemedParentModelUkPartnership, Some(1)).success.value

      mockGetAnswers(Some(userAnswers))

      val result = Controller.onPageLoad(1, NormalMode)(fakeRequest)

      status(result) mustEqual OK
    }

    "redirect to the next page when valid data is submitted" in {

      val userAnswers = emptyUserAnswers
        .set(DeemedParentPage, deemedParentModelMin, Some(1)).success.value

      val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

      mockGetAnswers(Some(userAnswers))
      mockSetAnswers

      val result = Controller.onSubmit(1, NormalMode)(request)

      status(result) mustEqual SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted" in {

      val userAnswers = emptyUserAnswers
        .set(DeemedParentPage, deemedParentModelMin, Some(1)).success.value

      mockGetAnswers(Some(userAnswers))

      val request = fakeRequest.withFormUrlEncodedBody(("value", ""))

      val result = Controller.onSubmit(1, NormalMode)(request)

      status(result) mustEqual BAD_REQUEST
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      mockGetAnswers(None)

      val result = Controller.onPageLoad(1, NormalMode)(fakeRequest)

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
    }

    "redirect to Session Expired for a POST if no existing data is found" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

      mockGetAnswers(None)

      val result = Controller.onSubmit(1, NormalMode)(request)

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
    }

    "Data Cleanup" when {
      "User selects `yes` then we delete CTUTR" in {
        val userAnswers = emptyUserAnswers
          .set(DeemedParentPage, deemedParentModelMin.copy(ctutr = Some(UTRModel("Test"))), Some(1)).success.value

        val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

        mockGetAnswers(Some(userAnswers))

        (mockSessionRepository.set(_: UserAnswers))
          .expects(emptyUserAnswers
            .set(DeemedParentPage, deemedParentModelMin.copy(ctutr = None,limitedLiabilityPartnership = Some(true)), Some(1)).success.value)
          .returns(Future.successful(true))

        val result = Controller.onSubmit(1, NormalMode)(request)

        status(result) mustEqual SEE_OTHER
      }

      "User selects `no` then we delete SAUTR" in {
        val userAnswers = emptyUserAnswers
          .set(DeemedParentPage, deemedParentModelMin.copy(sautr = Some(UTRModel("Test"))), Some(1)).success.value

        val request = fakeRequest.withFormUrlEncodedBody(("value", "false"))

        mockGetAnswers(Some(userAnswers))

        (mockSessionRepository.set(_: UserAnswers))
          .expects(emptyUserAnswers
            .set(DeemedParentPage, deemedParentModelMin.copy(sautr = None,limitedLiabilityPartnership = Some(false)), Some(1)).success.value)
          .returns(Future.successful(true))

        val result = Controller.onSubmit(1, NormalMode)(request)

        status(result) mustEqual SEE_OTHER
      }
    }
  }
}
