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

package views.ukCompanies

import assets.constants.BaseConstants
import assets.messages.BaseMessages
import assets.messages.ukCompanies.NetTaxInterestIncomeOrExpenseMessages
import forms.ukCompanies.NetTaxInterestIncomeOrExpenseFormProvider
import models.{NetTaxInterestIncomeOrExpense, NormalMode}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.ukCompanies.NetTaxInterestIncomeOrExpenseView


class NetTaxInterestIncomeOrExpenseViewSpec extends ViewBehaviours with BaseConstants {

  val messageKeyPrefix = "netTaxInterestIncomeOrExpense"
  val section = Some(NetTaxInterestIncomeOrExpenseMessages.subheading(companyNameModel.name))
  val form = new NetTaxInterestIncomeOrExpenseFormProvider()()
  val view = viewFor[NetTaxInterestIncomeOrExpenseView]()

  "NetTaxInterestIncomeOrExpenseView" must {

    def applyView(form: Form[NetTaxInterestIncomeOrExpense]): HtmlFormat.Appendable =
      view.apply(form, NormalMode,companyNameModel.name, onwardRoute)(fakeRequest, messages, frontendAppConfig)

    behave like normalPage(applyView(form), messageKeyPrefix, section = section, headingArgs = Seq(companyNameModel.name))

    behave like pageWithSubHeading(applyView(form), section.get)

    behave like pageWithBackLink(applyView(form))

    behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

    behave like pageWithSaveForLater(applyView(form))

    NetTaxInterestIncomeOrExpense.options(form).zipWithIndex.foreach { case (option, i) =>

      val id = if (i == 0) "value" else s"value-${i + 1}"

      s"contain radio buttons for the value '${option.value.get}'" in {

        val doc = asDocument(applyView(form))
        assertContainsRadioButton(doc, id, "value", option.value.get, false)
      }

      s"rendered with a value of '${option.value.get}'" must {

        s"have the '${option.value.get}' radio button selected" in {

          val formWithData = form.bind(Map("value" -> s"${option.value.get}"))
          val doc = asDocument(applyView(formWithData))

          assertContainsRadioButton(doc, id, "value", option.value.get, true)
        }
      }
    }
  }
}
