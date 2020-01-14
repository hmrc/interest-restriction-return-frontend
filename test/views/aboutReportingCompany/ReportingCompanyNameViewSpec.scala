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

package views.aboutReportingCompany

import assets.messages.{BaseMessages, SectionHeaderMessages}
import controllers.aboutReportingCompany.routes
import forms.aboutReportingCompany.ReportingCompanyNameFormProvider
import models.NormalMode
import nunjucks.ReportingCompanyNameTemplate
import nunjucks.viewmodels.BasicFormViewModel
import play.api.data.Form
import play.api.libs.json.Json
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.nunjucks.NunjucksSupport
import views.{Nunjucks, Twirl}
import views.behaviours.StringViewBehaviours
import views.html.aboutReportingCompany.ReportingCompanyNameView

class ReportingCompanyNameViewSpec extends StringViewBehaviours with NunjucksSupport {

  val messageKeyPrefix = "reportingCompanyName"

  val form = new ReportingCompanyNameFormProvider()()

  Seq(Nunjucks, Twirl).foreach { templatingSystem =>

    s"ReportingCompanyName ($templatingSystem) view" must {

      def applyView(form: Form[_]): HtmlFormat.Appendable =
        if (templatingSystem == Nunjucks) {
          await(nunjucksRenderer.render(ReportingCompanyNameTemplate, Json.toJsObject(BasicFormViewModel(form, NormalMode)))(fakeRequest))
        } else {
          val view = viewFor[ReportingCompanyNameView](Some(emptyUserAnswers))
          view.apply(form, NormalMode)(fakeRequest, messages, frontendAppConfig)
        }

      behave like normalPage(applyView(form), messageKeyPrefix)

      behave like pageWithBackLink(applyView(form))

      behave like pageWithSubHeading(applyView(form), SectionHeaderMessages.reportingCompany)

      behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

      behave like stringPage(form, applyView, messageKeyPrefix, routes.ReportingCompanyNameController.onSubmit(NormalMode).url)
    }
  }
}