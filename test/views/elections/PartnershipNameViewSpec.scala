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

package views.elections

import assets.messages.{BaseMessages, SectionHeaderMessages}
import controllers.elections.routes
import forms.elections.PartnershipNameFormProvider
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.StringViewBehaviours
import views.html.elections.PartnershipNameView

class PartnershipNameViewSpec extends StringViewBehaviours  {

  val messageKeyPrefix = "partnershipName"
  val section = Some(messages("section.elections"))
  val form = new PartnershipNameFormProvider()()

    "PartnershipNameView" must {

      def applyView(form: Form[_]): HtmlFormat.Appendable = {
          val view = viewFor[PartnershipNameView](Some(emptyUserAnswers))
          view.apply(form, routes.PartnershipNameController.onSubmit(1, NormalMode))(fakeRequest, messages, frontendAppConfig)
        }

      behave like normalPage(applyView(form), messageKeyPrefix, section = section)

      behave like pageWithBackLink(applyView(form))

      behave like pageWithSubHeading(applyView(form), SectionHeaderMessages.elections)

      behave like stringPage(form, applyView, messageKeyPrefix, routes.PartnershipNameController.onSubmit(1, NormalMode).url, section = section)

      behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

      behave like pageWithSaveForLater(applyView(form))
    }
  }
