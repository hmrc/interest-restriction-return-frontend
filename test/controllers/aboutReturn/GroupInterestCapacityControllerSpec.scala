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

package controllers.aboutReturn

import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import controllers.errors
import forms.aboutReturn.GroupInterestCapacityFormProvider
import models.NormalMode
import navigation.FakeNavigators.FakeAboutReturnNavigator
import pages.aboutReturn.GroupInterestCapacityPage
import play.api.mvc.Call
import play.api.test.Helpers._
import views.html.aboutReturn.GroupInterestCapacityView

class GroupInterestCapacityControllerSpec extends SpecBase with FeatureSwitching {

  def onwardRoute = Call("GET", "/foo")

  val view = injector.instanceOf[GroupInterestCapacityView]
  val formProvider = new GroupInterestCapacityFormProvider()
  val form = formProvider()
  val validAnswer = 0

  def controller(dataRetrieval: DataRetrievalAction = FakeDataRetrievalActionEmptyAnswers) = new GroupInterestCapacityController(
    messagesApi = messagesApi,
    sessionRepository = sessionRepository,
    navigator = FakeAboutReturnNavigator,
    identify = FakeIdentifierAction,
    getData = dataRetrieval,
    requireData = new DataRequiredActionImpl,
    formProvider = new GroupInterestCapacityFormProvider,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "GroupInterestCapacity Controller" must {

    "If rendering using the Twirl templating engine" must {

      "return OK and the correct view for a GET" in {

        val result = controller(FakeDataRetrievalActionEmptyAnswers).onPageLoad(NormalMode)(fakeRequest)

        status(result) mustEqual OK
        contentAsString(result) mustEqual view(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString
      }
    }

    "populate the view correctly on a GET when the question has previously been answered" in {

      val userAnswers = emptyUserAnswers.set[BigDecimal](GroupInterestCapacityPage, validAnswer).success.value

      val result = controller(FakeDataRetrievalActionGeneral(Some(userAnswers))).onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe OK
    }

    "redirect to the next page when valid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "01"))

      val result = controller().onSubmit(NormalMode)(request)

      status(result) mustBe SEE_OTHER

      redirectLocation(result) mustBe Some("/foo")
    }

    "return a Bad Request and errors when invalid data is submitted" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "a"))

      val result = controller().onSubmit(NormalMode)(request)

      status(result) mustBe BAD_REQUEST
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      val result = controller(FakeDataRetrievalActionNone).onPageLoad(NormalMode)(fakeRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result).value mustBe errors.routes.SessionExpiredController.onPageLoad().url
    }

    "redirect to Session Expired for a POST if no existing data is found" in {

      val request = fakeRequest.withFormUrlEncodedBody(("value", "2"))

      val result = controller(FakeDataRetrievalActionNone).onSubmit(NormalMode)(request)

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustBe errors.routes.SessionExpiredController.onPageLoad().url
    }
  }
}
