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

import models.UserAnswers
import pages.ukCompanies.UkCompaniesPage
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist._

class UkCompaniesReviewAnswersListHelper(val userAnswers: UserAnswers)
                                        (implicit val messages: Messages) extends CheckYourAnswersHelper {

  def rows: Seq[SummaryListRow] = userAnswers.getList(UkCompaniesPage).zipWithIndex.map {
    case (model, idx) => summaryListRow(
      model.companyDetails.companyName,
      model.companyDetails.ctutr,
      visuallyHiddenText = None,
      controllers.ukCompanies.routes.CheckAnswersUkCompanyController.onPageLoad(idx + 1) -> messages("site.review"),
      controllers.ukCompanies.routes.UkCompaniesDeletionConfirmationController.onPageLoad(idx + 1) -> messages("site.delete")
    )
  }
}
