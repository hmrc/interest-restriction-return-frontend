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

import assets.messages.SectionHeaderMessages
import assets.messages.ultimateParentCompany.DeemedParentReviewAnswersListMessages
import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import forms.ultimateParentCompany.DeemedParentReviewAnswersListFormProvider
import models.NormalMode
import assets.constants.DeemedParentConstants._
import navigation.FakeNavigators.FakeUltimateParentCompanyNavigator
import pages.elections.GroupRatioElectionPage
import pages.ultimateParentCompany.DeemedParentPage
import play.api.test.Helpers._
import views.html.ultimateParentCompany.DeemedParentReviewAnswersListView

class
DeemedParentReviewAnswersListControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[DeemedParentReviewAnswersListView]
  val formProvider = injector.instanceOf[DeemedParentReviewAnswersListFormProvider]
  val form = formProvider()

  object Controller extends DeemedParentReviewAnswersListController(
    messagesApi = messagesApi,
    navigator = FakeUltimateParentCompanyNavigator,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    controllerComponents = messagesControllerComponents,
    view = view,
    formProvider = formProvider
  )

  "Check Your Answers Controller" when {

    "calling the onPageLoad() method" when {

      "there are no deemed parents in the list" must {

        "return a SEE_OTHER (303)" in {

          mockGetAnswers(Some(emptyUserAnswers))

          val result = Controller.onPageLoad()(fakeRequest)

          status(result) mustEqual SEE_OTHER
          redirectLocation(result) mustBe Some(FakeUltimateParentCompanyNavigator.addParent(0).url)
        }
      }

      "there are deemed parents in the list" must {

        "return a OK (200)" in {

          mockGetAnswers(Some(emptyUserAnswers.set(DeemedParentPage, deemedParentModelUkCompany, Some(1)).get))

          val result = Controller.onPageLoad()(fakeRequest)

          status(result) mustEqual OK
          titleOf(contentAsString(result)) mustEqual title(DeemedParentReviewAnswersListMessages.title(1), Some(SectionHeaderMessages.ultimateParentCompany))
        }
      }
    }

    "calling the onSubmit() method" when {

      "add another parent answer is yes" should {

        "redirect to the Add Parent route" in {

          mockGetAnswers(Some(emptyUserAnswers))

          val request = fakeRequest.withFormUrlEncodedBody(("value", "true"))

          val result = Controller.onSubmit()(request)

          status(result) mustBe SEE_OTHER
          redirectLocation(result) mustBe Some(FakeUltimateParentCompanyNavigator.addParent(0).url)
        }
      }

      "add another parent answer is false" should {

        "redirect to the Next Section route" in {

          mockGetAnswers(Some(emptyUserAnswers))

          val request = fakeRequest.withFormUrlEncodedBody(("value", "false"))

          val result = Controller.onSubmit()(request)

          status(result) mustBe SEE_OTHER
          redirectLocation(result) mustBe Some(FakeUltimateParentCompanyNavigator.nextPage(
            page = GroupRatioElectionPage,
            mode = NormalMode,
            userAnswers = emptyUserAnswers).url)
        }
      }

      "when there is 3 deemed parents" should {

        "redirect to the Next Section route" in {

          mockGetAnswers(Some(emptyUserAnswers.set(DeemedParentPage, deemedParentModelUkCompany, Some(1)).success.value
            .set(DeemedParentPage, deemedParentModelUkCompany, Some(2)).success.value
            .set(DeemedParentPage, deemedParentModelUkCompany, Some(3)).success.value))

          val request = fakeRequest.withFormUrlEncodedBody(("value", ""))

          val result = Controller.onSubmit()(request)

          status(result) mustBe SEE_OTHER
          redirectLocation(result) mustBe Some(FakeUltimateParentCompanyNavigator.nextPage(
            page = GroupRatioElectionPage,
            mode = NormalMode,
            userAnswers = emptyUserAnswers).url)
        }
      }
    }
  }
}
