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

package viewmodels

import play.api.mvc.Call
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist._

trait SummaryListRowHelper {

  def summaryListRow(label: String, value: String, visuallyHiddenText: Option[String], actions: (Call, String)*): SummaryListRow = {
    summaryListRowSpecifyingClass(label, value, "govuk-!-width-one-third", visuallyHiddenText, actions: _*)
  }

  def summaryListRowWideKey(label: String, value: String, visuallyHiddenText: Option[String], actions: (Call, String)*): SummaryListRow = {
    summaryListRowSpecifyingClass(label, value, "govuk-!-width-two-thirds", visuallyHiddenText, actions: _*)
  }

  private def summaryListRowSpecifyingClass(label: String, value: String, keyClass: String, visuallyHiddenText: Option[String], actions: (Call, String)*): SummaryListRow = {
    SummaryListRow(
      key = Key(
        content = Text(label),
        classes = keyClass
      ),
      value = Value(
        content = HtmlContent(value),
        classes = "govuk-!-width-one-third"
      ),
      actions = Some(Actions(
        items = actions.map { case (call, linkText) => ActionItem(
          href = call.url,
          content = Text(linkText),
          visuallyHiddenText = Some(defaultVisuallyHiddenText(label, visuallyHiddenText))
        )},
        classes = "govuk-!-width-one-third")
      )
    )
  }

  def defaultVisuallyHiddenText(label: String, visuallyHiddenText: Option[String]): String = 
    visuallyHiddenText match {
      case Some(text) => text
      case None => label
    }

}
