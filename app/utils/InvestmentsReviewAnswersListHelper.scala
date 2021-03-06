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

package utils

import models.{CheckMode, UserAnswers}
import pages.elections.InvestmentNamePage
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist._

class InvestmentsReviewAnswersListHelper(val userAnswers: UserAnswers)
                                        (implicit val messages: Messages) extends CheckYourAnswersHelper {

  def rows: Seq[SummaryListRow] = userAnswers.getList(InvestmentNamePage).zipWithIndex.map {
    case (name, idx) => summaryListRow(
      name,
      "",
      visuallyHiddenText = None,
      controllers.elections.routes.InvestmentNameController.onPageLoad(idx + 1, CheckMode) -> messages("site.edit"),
      controllers.elections.routes.InvestmentsDeletionConfirmationController.onPageLoad(idx + 1) -> messages("site.delete")
    )
  }
}
