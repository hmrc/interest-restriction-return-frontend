package views.$section;format="decap"$

import assets.messages.{BaseMessages, SectionHeaderMessages}
import forms.$section;format="decap"$.$className;format="cap"$FormProvider
import models.{$className;format="cap"$, ContinueSavedReturn, NormalMode}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.$section;format="decap"$.$className;format="cap"$View

class $className;format="cap"$ViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"
  val section = Some(messages("section.$section;format="decap"$"))
  val form = new $className;format="cap"$FormProvider()()

  "$className;format="cap"$View" must {

    def applyView(form: Form[$className;format="cap"$]): HtmlFormat.Appendable = {
      val view = viewFor[$className;format="cap"$View](Some(emptyUserAnswers))
      view.apply(form, NormalMode)(fakeRequest, messages, frontendAppConfig)
    }

    behave like normalPage(applyView(form), messageKeyPrefix, section = section)

    behave like pageWithSubHeading(applyView(form), SectionHeaderMessages.$section;format="decap"$)

    behave like pageWithBackLink(applyView(form))

    behave like pageWithSubmitButton(applyView(form), BaseMessages.saveAndContinue)

    behave like pageWithSaveForLater(applyView(form))

    $className;format="cap"$.options(form).zipWithIndex.foreach { case (option, i) =>

      val id = if (i == 0) "value" else s"value-\${i + 1}"

      s"contain radio buttons for the value '\${option.value.get}'" in {

        val doc = asDocument(applyView(form))
        assertContainsRadioButton(doc, id, "value", option.value.get, false)
      }

      s"rendered with a value of '\${option.value.get}'" must {

        s"have the '\${option.value.get}' radio button selected" in {

          val formWithData = form.bind(Map("value" -> s"\${option.value.get}"))
          val doc = asDocument(applyView(formWithData))

          assertContainsRadioButton(doc, id, "value", option.value.get, true)
        }
      }
    }
  }
}
