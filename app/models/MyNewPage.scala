/*
 * Copyright 2019 HM Revenue & Customs
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

package models

import play.api.data.Form
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.CheckboxItem

sealed trait MyNewPage

object MyNewPage extends Enumerable.Implicits {

  case object Option1 extends WithName("option1") with MyNewPage
  case object Option2 extends WithName("option2") with MyNewPage

  val values: Seq[MyNewPage] = Seq(
    Option1, Option2
  )

  def options(form: Form[_])(implicit messages: Messages): Seq[CheckboxItem] = values.map {
    value =>
      CheckboxItem(
        name = Some("value[]"),
        id = Some(value.toString),
        value = value.toString,
        content = Text(messages(s"myNewPage.${value.toString}")),
        checked = form.data.exists(_._2 == value.toString)
    )
  }

  implicit val enumerable: Enumerable[MyNewPage] =
    Enumerable(values.map(v => v.toString -> v): _*)
}
