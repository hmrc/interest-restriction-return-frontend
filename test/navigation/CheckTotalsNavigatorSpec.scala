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

package navigation

import base.SpecBase
import controllers.routes
import models._
import pages.checkTotals.ReviewTaxEBITDAPage
import pages.ukCompanies._

class CheckTotalsNavigatorSpec extends SpecBase {

  val navigator = new CheckTotalsNavigator

  "CheckTotalsNavigator" when {

    "in Normal mode" must {

      "go from the DerivedCompanyPage" should {

        "to the UnderConstructionController" in {

          navigator.nextPage(DerivedCompanyPage, NormalMode, emptyUserAnswers) mustBe
            routes.UnderConstructionController.onPageLoad()
        }

      }

      "from the EnterCompanyTaxEBITDAPage" should {

        //TODO: Update a part of routing sub-task
        "go to the ReviewTaxEBITDAPage page" in {
          navigator.nextPage(ReviewTaxEBITDAPage, NormalMode, emptyUserAnswers) mustBe
            controllers.routes.UnderConstructionController.onPageLoad()
        }
      }
    }
  }
}
