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

package views.ultimateParentCompany

import assets.constants.BaseConstants
import assets.messages.{BaseMessages, SectionHeaderMessages}
import forms.ultimateParentCompany.CountryOfIncorporationFormProvider
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.AutocompleteViewBehaviours
import views.html.ultimateParentCompany.CountryOfIncorporationView

class CountryOfIncorporationViewSpec extends AutocompleteViewBehaviours with BaseConstants {

  val messageKeyPrefix = "countryOfIncorporation"
  val section = Some(messages("section.ultimateParentCompany"))
  val form = new CountryOfIncorporationFormProvider(frontendAppConfig)()

  override val answerValue: String = frontendAppConfig.countryCodeMap("GB")

  "CountryOfIncorporationView" must {

    def applyView(form: Form[_]): HtmlFormat.Appendable = {
      val view = viewFor[CountryOfIncorporationView](Some(emptyUserAnswers))
      view.apply(form, NormalMode, companyNameModel.name, onwardRoute)(fakeRequest, messages, frontendAppConfig)
    }

    behave like normalPage(applyView(form),
                           messageKeyPrefix,
                           section = section,
                           headingArgs = Seq(companyNameModel.name)
    )

    behave like pageWithBackLink(applyView(form))

    behave like pageWithSubHeading(applyView(form), companyNameModel.name)

    behave like selectPage(form,
                           applyView,
                           messageKeyPrefix,
                           onwardRoute.url,
                           section = section,
                           headingArgs = Seq(companyNameModel.name)
                          )

    behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

    behave like pageWithSaveForLater(applyView(form))
  }
}
