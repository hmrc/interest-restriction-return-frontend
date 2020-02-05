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

package controllers.actions

import base.SpecBase
import mocks.MockSessionRepository
import models.UserAnswers
import models.requests.{IdentifierRequest, OptionalDataRequest}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataRetrievalActionSpec extends SpecBase with MockSessionRepository with ScalaFutures {

  object Action extends DataRetrievalActionImpl(mockSessionRepository) {
    def callTransform[A](request: IdentifierRequest[A]): Future[OptionalDataRequest[A]] = transform(request)
  }

  "Data Retrieval Action" when {

    "there is no data in the cache" must {

      "set userAnswers to 'None' in the request" in {

        mockGetAnswers(None)

        val futureResult = Action.callTransform(IdentifierRequest(fakeRequest, "id"))

        whenReady(futureResult) { result =>
          result.userAnswers.isEmpty mustBe true
        }
      }
    }

    "there is data in the cache" must {

      "build a userAnswers object and add it to the request" in {

        mockGetAnswers(Some(emptyUserAnswers))

        val futureResult = Action.callTransform(IdentifierRequest(fakeRequest, "id"))

        whenReady(futureResult) { result =>
          result.userAnswers.isDefined mustBe true
        }
      }
    }
  }
}
