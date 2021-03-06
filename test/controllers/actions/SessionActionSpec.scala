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

package controllers.actions

import base.SpecBase
import play.api.mvc.{BodyParsers, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class SessionActionSpec extends SpecBase {

  class Harness(action: IdentifierAction) {
    def onPageLoad() = action { request => Results.Ok }
  }

  "Session Action" when {

    "there's no active session" must {

      "redirect to the session expired page" in {

        val bodyParsers = app.injector.instanceOf[BodyParsers.Default]

        val sessionAction = new SessionIdentifierAction(frontendAppConfig, bodyParsers)

        val controller = new Harness(sessionAction)

        val result = controller.onPageLoad()(FakeRequest("", ""))

        status(result) mustBe SEE_OTHER
        redirectLocation(result).get must startWith(controllers.errors.routes.SessionExpiredController.onPageLoad().url)
      }
    }

    "there's an active session" must {

      "perform the action" in {

        val bodyParsers = app.injector.instanceOf[BodyParsers.Default]

        val sessionAction = new SessionIdentifierAction(frontendAppConfig, bodyParsers)

        val controller = new Harness(sessionAction)

        val result = controller.onPageLoad()(fakeRequest)

        status(result) mustBe OK
      }
    }
  }
}
