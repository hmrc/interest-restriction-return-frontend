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

import assets.constants.fullReturn.UkCompanyConstants.ukCompanyModelMax
import controllers.errors
import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import forms.ukCompanies.AddAnReactivationQueryFormProvider
import models.{NormalMode, UserAnswers}
import pages.ukCompanies.UkCompaniesPage
import play.api.test.Helpers._
import views.html.ukCompanies.AddAnReactivationQueryView
import navigation.FakeNavigators.FakeUkCompaniesNavigator
import scala.concurrent.Future

class AddAnReactivationQueryControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[AddAnReactivationQueryView]
  val formProvider = injector.instanceOf[AddAnReactivationQueryFormProvider]
  val form = formProvider()

  object Controller extends AddAnReactivationQueryController(
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

  "AddAnReactivationQuery Controller" must {

    "return OK and the correct view for a GET" in {

      mockGetAnswers(Some(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax.copy(reactivation = None, consenting = None), Some(1)).get))

      val result = Controller.onPageLoad(1,NormalMode)(fakeRequest)

      status(result) mustEqual OK
      contentAsString(result) mustEqual view(form = form,
        mode = NormalMode,
        companyName = ukCompanyModelMax.companyDetails.companyName,
        postAction = routes.AddAnReactivationQueryController.onSubmit(1, NormalMode))(fakeRequest, messages, frontendAppConfig).toString
    }

    "populate the view correctly on a GET when the question has previously been answered" in {

      mockGetAnswers(Some(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get))

      val result = Controller.onPageLoad(1, NormalMode)(fakeRequest)

      status(result) mustEqual OK
    }

    "redirect to the next page when valid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

      mockGetAnswers(Some(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get))
      mockSetAnswers

      val result = Controller.onSubmit(1,NormalMode)(request)

      status(result) mustEqual SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "clear the ReactivationAmount page when 'false' is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "false"))

      val company = ukCompanyModelMax.copy(reactivation = Some(false))
      val userAnswers = emptyUserAnswers.set(UkCompaniesPage, company, Some(1)).get

      val expectedCompany = ukCompanyModelMax.copy(
        reactivation = Some(false),
        allocatedReactivations = None
      )
      
      mockGetAnswers(Some(userAnswers))

      (mockSessionRepository.set(_: UserAnswers))
        .expects(emptyUserAnswers
          .set(UkCompaniesPage, expectedCompany, Some(1)).success.value)
        .returns(Future.successful(true))

      val result = Controller.onSubmit(1, NormalMode)(request)

      status(result) mustEqual SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", ""))

      mockGetAnswers(Some(emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelMax, Some(1)).get))

      val result = Controller.onSubmit(1,NormalMode)(request)

      status(result) mustEqual BAD_REQUEST
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      mockGetAnswers(None)

      val result = Controller.onPageLoad(1,NormalMode)(fakeRequest)

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
    }

    "redirect to Session Expired for a POST if no existing data is found" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

      mockGetAnswers(None)

      val result = Controller.onSubmit(1,NormalMode)(request)

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual errors.routes.SessionExpiredController.onPageLoad().url
    }
  }
}
