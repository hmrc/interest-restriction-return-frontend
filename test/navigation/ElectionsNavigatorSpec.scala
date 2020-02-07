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
import models._
import pages.elections.GroupRatioElectionPage
import pages.groupStructure._

class ElectionsNavigatorSpec extends SpecBase {

  val navigator = new ElectionsNavigator

  "ElectionsNavigator" when {

    "in Normal mode" must {

      "from the GroupRatioElectionPage" should {

        "go to the Under Construction page when given false" in {

          navigator.nextPage(GroupRatioElectionPage, NormalMode, emptyUserAnswers) mustBe
            controllers.routes.UnderConstructionController.onPageLoad()
        }
      }
    }

    "in Check mode" must {

      "go to Reporting Company Under construction" in {

        navigator.nextPage(DeemedParentPage, CheckMode, emptyUserAnswers) mustBe
          controllers.routes.UnderConstructionController.onPageLoad()
      }
    }
  }
}