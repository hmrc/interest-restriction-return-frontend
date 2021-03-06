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

package views

trait BaseSelectors {

  val panelHeading = "main div.govuk-panel.govuk-panel--confirmation h1"
  val panelBody = "main div.govuk-panel.govuk-panel--confirmation div.govuk-panel__body"
  val p: Int => String = i => s"main p:nth-of-type($i)"
  val indent = "div.govuk-inset-text"
  val hint = "main div.govuk-hint"
  val bullet: Int => String = i => s"main ul.govuk-list.govuk-list--bullet li:nth-of-type($i)"
  val label = "main label.govuk-label"

  def checkAnswersHeading(row: Int) = s"dl div:nth-of-type($row) dt"
  def checkAnswersAnswerValue(row: Int) = s"dl div:nth-of-type($row) dd:nth-of-type(1)"

}
