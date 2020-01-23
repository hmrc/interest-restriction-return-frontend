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

package controllers

import base.SpecBase
import config.featureSwitch.{FeatureSwitching, UseNunjucks}
import controllers.actions.{DataRequiredActionImpl, FakeDataRetrievalActionEmptyAnswers, FakeIdentifierAction}
import play.api.test.Helpers._
import nunjucks.MockNunjucksRenderer
import nunjucks.UnderConstructionTemplate
import play.twirl.api.Html
import views.html.UnderConstructionView

class UnderConstructionControllerSpec extends SpecBase with MockNunjucksRenderer with FeatureSwitching {

  val view = injector.instanceOf[UnderConstructionView]

  def controller = new UnderConstructionController(
    messagesApi = messagesApi,
    identify = FakeIdentifierAction,
    getData = FakeDataRetrievalActionEmptyAnswers,
    requireData = new DataRequiredActionImpl,
    controllerComponents = messagesControllerComponents,
    view = view,
    mockNunjucksRenderer
  )

  "UnderConstruction Controller" must {

    "When Nunjucks rendering is enabled" must {

      "return OK and the correct view for a GET" in {

        enable(UseNunjucks)

        mockRender(UnderConstructionTemplate)(Html("Success"))

        val result = controller.onPageLoad(fakeRequest)

        status(result) mustBe OK
      }
    }

    "When Nunjucks rendering is disabled" must {

      "return OK and the correct view for a GET" in {

        disable(UseNunjucks)

        val result = controller.onPageLoad(fakeRequest)

        status(result) mustBe OK
      }
    }
  }
}