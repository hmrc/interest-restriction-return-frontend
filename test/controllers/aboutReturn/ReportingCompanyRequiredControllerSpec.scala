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

import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions.{FakeIdentifierAction, MockDataRetrievalAction}
import play.api.test.Helpers._
import views.html.aboutReturn.ReportingCompanyRequiredView

class ReportingCompanyRequiredControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[ReportingCompanyRequiredView]

  def controller = new ReportingCompanyRequiredController(
    messagesApi = messagesApi,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "ReportingCompanyRequired Controller" must {

      "return OK and the correct view for a GET" in {

        mockGetAnswers(Some(emptyUserAnswers))

        val result = controller.onPageLoad(fakeRequest)

        status(result) mustBe OK
      }
  }
}
