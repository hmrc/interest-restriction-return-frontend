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
import forms.elections.OtherInvestorGroupElectionsFormProvider
import models.InvestorRatioMethod.{FixedRatioMethod, GroupRatioMethod}
import models.{InvestorRatioMethod, OtherInvestorGroupElections}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.CheckboxViewBehaviours
import views.html.elections.OtherInvestorGroupElectionsView

class OtherInvestorGroupElectionsViewSpec extends CheckboxViewBehaviours[OtherInvestorGroupElections] {

  val messageKeyPrefix = "otherInvestorGroupElections"
  val section = Some(messages("section.elections"))
  val form = new OtherInvestorGroupElectionsFormProvider()()
  val view = viewFor[OtherInvestorGroupElectionsView]()
  val name = "investor group name"


  def applyView(ratioMethod: InvestorRatioMethod)(form: Form[Set[OtherInvestorGroupElections]]): HtmlFormat.Appendable =
    view.apply(form, name, onwardRoute, ratioMethod)(fakeRequest, messages, frontendAppConfig)

  for (ratioMethod <- Seq(FixedRatioMethod, GroupRatioMethod)) {

    s"OtherInvestorGroupElectionsView for the $ratioMethod" should {

      behave like normalPage(applyView(ratioMethod)(form), messageKeyPrefix, section = section)

      behave like pageWithBackLink(applyView(ratioMethod)(form))

      behave like pageWithSubHeading(applyView(ratioMethod)(form), name)

      behave like checkboxPage(form,
        applyView(ratioMethod),
        messageKeyPrefix,
        OtherInvestorGroupElections.options(form, ratioMethod),
        messages("section.elections")
      )

      behave like pageWithSubmitButton(applyView(ratioMethod)(form), BaseMessages.saveAndContinue)

      behave like pageWithSaveForLater(applyView(ratioMethod)(form))
    }
  }
}
