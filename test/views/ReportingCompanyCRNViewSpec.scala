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

package views

import assets.messages.{BaseMessages, SectionHeaderMessages}
import controllers.routes
import forms.ReportingCompanyCRNFormProvider
import models.NormalMode
import play.api.data.Form
import play.api.libs.json.Json
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.nunjucks.NunjucksSupport
import uk.gov.hmrc.viewmodels.Radios
import views.behaviours.StringViewBehaviours
import views.html.ReportingCompanyCRNView
import nunjucks.ReportingCompanyCRNTemplate
import nunjucks.viewmodels.BasicFormViewModel

class ReportingCompanyCRNViewSpec extends StringViewBehaviours with NunjucksSupport {

  val messageKeyPrefix = "reportingCompanyCRN"

  val form = new ReportingCompanyCRNFormProvider()()

  Seq(Nunjucks, Twirl).foreach { templatingSystem =>

    s"ReportingCompanyCRN ($templatingSystem) view" must {

      def applyView(form: Form[_]): HtmlFormat.Appendable =
        if (templatingSystem == Nunjucks) {
          await(nunjucksRenderer.render(ReportingCompanyCRNTemplate, Json.toJsObject(BasicFormViewModel(form, NormalMode)))(fakeRequest))
        } else {
          val view = viewFor[ReportingCompanyCRNView](Some(emptyUserAnswers))
          view.apply(form, NormalMode)(fakeRequest, messages, frontendAppConfig)
        }

      behave like normalPage(applyView(form), messageKeyPrefix)

      behave like pageWithBackLink(applyView(form))

      behave like pageWithSubHeading(applyView(form), SectionHeaderMessages.reportingCompany)

      behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

      behave like stringPage(form, applyView, messageKeyPrefix, routes.ReportingCompanyCRNController.onSubmit(NormalMode).url)
    }
  }
}
