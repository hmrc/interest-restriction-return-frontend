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

import assets.constants.fullReturn.UkCompanyConstants._
import assets.messages.CheckRestrictionMessages
import base.SpecBase
import config.featureSwitch.FeatureSwitching
import controllers.actions._
import models.NormalMode
import navigation.FakeNavigators.FakeUkCompaniesNavigator
import pages.ukCompanies.{CheckAnswersUkCompanyPage, CompanyAccountingPeriodEndDatePage, UkCompaniesPage}
import play.api.test.Helpers._
import views.html.CheckYourAnswersView

import java.time.{LocalDate, ZoneOffset}

class CheckRestrictionControllerSpec extends SpecBase with FeatureSwitching with MockDataRetrievalAction {

  val view = injector.instanceOf[CheckYourAnswersView]
  val endDateNow = LocalDate.now(ZoneOffset.UTC)

  object Controller extends CheckRestrictionController(
    messagesApi = messagesApi,
    navigator = FakeUkCompaniesNavigator,
    identify = FakeIdentifierAction,
    getData = mockDataRetrievalAction,
    requireData = dataRequiredAction,
    controllerComponents = messagesControllerComponents,
    view = view
  )

  "Check Your Answers Controller" when {

    "calling the onPageLoad() method" must {

      "return a OK (200) when given empty answers" in {

        lazy val userAnswers = (for {
          comp <- emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelReactivationMaxIncome, Some(1))
          red  <- comp.set(CompanyAccountingPeriodEndDatePage(1, 1), endDateNow)
        } yield red).success.value

        mockGetAnswers(Some(userAnswers))

        val result = Controller.onPageLoad(1, 1)(fakeRequest)

        status(result) mustEqual OK
        titleOf(contentAsString(result)) mustEqual title(CheckRestrictionMessages.title, Some(ukCompanyModelReactivationMaxIncome.companyDetails.companyName))
      }
    }

    "calling the onSubmit() method" when {

      "given a uk company" when {

        "redirect to the next page in the navigator" in {

          lazy val userAnswers = emptyUserAnswers.set(UkCompaniesPage, ukCompanyModelReactivationMaxIncome, Some(1)).success.value

          mockGetAnswers(Some(userAnswers))

          val result = Controller.onSubmit(1, 1)(fakeRequest)

          status(result) mustBe SEE_OTHER
          redirectLocation(result) mustBe Some(FakeUkCompaniesNavigator.nextPage(
            page = CheckAnswersUkCompanyPage,
            mode = NormalMode,
            userAnswers = emptyUserAnswers,
            id = Some(1)
          ).url)
        }
      }
    }
  }
}
