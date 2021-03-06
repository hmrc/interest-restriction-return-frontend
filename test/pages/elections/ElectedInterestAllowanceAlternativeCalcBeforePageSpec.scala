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

package pages.elections

import pages.behaviours.PageBehaviours
import models.UserAnswers

class ElectedInterestAllowanceAlternativeCalcBeforePageSpec extends PageBehaviours {

  "ElectedInterestAllowanceAlternativeCalcBeforePage" must {

    beRetrievable[Boolean](ElectedInterestAllowanceAlternativeCalcBeforePage)

    beSettable[Boolean](ElectedInterestAllowanceAlternativeCalcBeforePage)

    beRemovable[Boolean](ElectedInterestAllowanceAlternativeCalcBeforePage)
  }

  "Cleanup" when {
    "ElectedInterestAllowanceAlternativeCalcBeforePage" when {
      "Remove AlternativeCalcElect when there is a change of the answer to 'Yes'" in {
        val userAnswers = for {
          ebaUa <- UserAnswers(id = "id").set(ElectedInterestAllowanceAlternativeCalcBeforePage, false)
          invUa <- ebaUa.set(InterestAllowanceAlternativeCalcElectionPage, false)
          finalUa <- invUa.set(ElectedInterestAllowanceAlternativeCalcBeforePage, true)
        } yield finalUa

        val result = userAnswers.map(_.getList(InterestAllowanceAlternativeCalcElectionPage)).get
        result mustBe Seq()
      }

      "Do not Remove AlternativeCalcElect when the answer to 'No'" in {
        val userAnswers = for {
          ebaUa <- UserAnswers(id = "id").set(ElectedInterestAllowanceAlternativeCalcBeforePage, true)
          invUa <- ebaUa.set(InterestAllowanceAlternativeCalcElectionPage, true)
          finalUa <- invUa.set(ElectedInterestAllowanceAlternativeCalcBeforePage, false )
        } yield finalUa

        val result = userAnswers.map(_.getList(InterestAllowanceAlternativeCalcElectionPage)).get
        result mustBe Seq()
      }
    }
  }
}
