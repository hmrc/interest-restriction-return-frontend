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

package views.groupStructure

import assets.constants.BaseConstants
import assets.messages.groupStructure.LocalRegistrationNumberMessages
import assets.messages.{BaseMessages, SectionHeaderMessages}
import controllers.groupStructure.routes
import forms.groupStructure.LocalRegistrationNumberFormProvider
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.BaseSelectors
import views.behaviours.StringViewBehaviours
import views.html.groupStructure.LocalRegistrationNumberView
import views.ViewUtils._

class LocalRegistrationNumberViewSpec extends StringViewBehaviours with BaseConstants {

  object Selectors extends BaseSelectors

  val messageKeyPrefix = "localRegistrationNumber"
  val section = Some(messages("section.groupStructure"))
  val form = new LocalRegistrationNumberFormProvider()()

  def applyView(companyName: String = companyNameModel.name)(form: Form[_]): HtmlFormat.Appendable = {
    val view = viewFor[LocalRegistrationNumberView](Some(emptyUserAnswers))
    view.apply(form, NormalMode, companyName)(fakeRequest, messages, frontendAppConfig)
  }

  "LocalRegistrationNumberView" must {

    behave like normalPage(
      view = applyView()(form),
      messageKeyPrefix = messageKeyPrefix,
      section = section,
      headingArgs = Seq(addPossessive(companyNameModel.name))
    )

    behave like pageWithBackLink(applyView()(form))

    behave like pageWithSubHeading(applyView()(form), SectionHeaderMessages.groupStructure)

    behave like pageWithSubmitButton(applyView()(form), BaseMessages.saveAndContinue)

    behave like stringPage(
      form = form,
      createView = applyView(),
      messageKeyPrefix = messageKeyPrefix,
      expectedFormAction = routes.LocalRegistrationNumberController.onSubmit(NormalMode).url,
      section = section,
      headingArgs = Seq(addPossessive(companyNameModel.name)))

    behave like pageWithSaveForLater(applyView()(form))

    lazy val document = asDocument(applyView()(form))

    "have a hint" which {

      lazy val hint = document.select(Selectors.hint)

      "has the correct text" in {
        hint.text mustBe LocalRegistrationNumberMessages.hint
      }
    }

    "have correct grammar" which {

      "uses possessives correctly when the company name doesn't end with 's'" in {
        lazy val hint = document.select(Selectors.label)
        hint.text mustBe LocalRegistrationNumberMessages.labelPossive
      }

      "uses possessives correctly when the company name ends with 's' (plural possessive)" in {
        lazy val document = asDocument(applyView(companyNameModelS.name)(form))
        lazy val hint = document.select(Selectors.label)
        hint.text mustBe LocalRegistrationNumberMessages.labelPossivePlural
      }
    }
  }
}