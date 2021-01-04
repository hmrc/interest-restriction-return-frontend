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

package controllers

import base.SpecBase
import config.SessionKeys
import config.featureSwitch.FeatureSwitching
import controllers.actions.{FakeIdentifierAction, MockDataRetrievalAction}
import play.api.test.Helpers._
import views.html.ConfirmationView

class ConfirmationControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[ConfirmationView]

  val reference = "abc123"

  object Controller extends ConfirmationController(
    messagesApi = messagesApi,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  lazy val requestWithRef = fakeRequest.withSession(SessionKeys.acknowledgementReference -> reference)

  "Confirmation Controller" must {

    "when the acknowledgement reference is held in session" should {

      "return OK and the correct view for a GET" in {

        mockGetAnswers(Some(emptyUserAnswers))

        val result = Controller.onPageLoad(requestWithRef)

        status(result) mustBe OK
        contentAsString(result) mustEqual view(reference)(requestWithRef, frontendAppConfig, messages).toString
      }
    }

    "when the acknowledgement reference is NOT held in session" should {

      "return the ISE page" in {

        mockGetAnswers(Some(emptyUserAnswers))

        val result = Controller.onPageLoad(fakeRequest)

        status(result) mustBe INTERNAL_SERVER_ERROR
        contentAsString(result) mustEqual errorHandler.internalServerErrorTemplate(fakeRequest).toString
      }
    }
  }
}
