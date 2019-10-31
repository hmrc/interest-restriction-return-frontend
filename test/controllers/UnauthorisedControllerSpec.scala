/*
 * Copyright 2019 HM Revenue & Customs
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
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.UnauthorisedView

class UnauthorisedControllerSpec extends SpecBase {

  val view = injector.instanceOf[UnauthorisedView]

  object Controller extends UnauthorisedController(
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "Unauthorised Controller" must {

    "return OK and the correct view for a GET" in {

      val result = Controller.onPageLoad()(fakeRequest)

      status(result) mustEqual OK

      contentAsString(result) mustEqual
        view()(fakeRequest, messages).toString
    }
  }
}
