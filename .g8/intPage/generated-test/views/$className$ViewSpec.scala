package views

import controllers.routes
import forms.$className$FormProvider
import models.NormalMode
import play.api.data.Form
import play.api.libs.json.Json
import play.twirl.api.HtmlFormat
import views.behaviours.IntViewBehaviours
import views.html.$className$View

class $className$ViewSpec extends IntViewBehaviours  {

  val messageKeyPrefix = "$className;format="decap"$"

  val form = new $className$FormProvider()()

  Seq(Twirl).foreach { templatingSystem =>

    s"$className $ (\$templatingSystem) view" must {

      def applyView(form: Form[_]): HtmlFormat.Appendable =
        if (templatingSystem == ) {
          await(Renderer.render($className$Template, Json.toJsObject(BasicFormViewModel(form, NormalMode)))(fakeRequest))
        } else {
          val view = viewFor[$className$View](Some(emptyUserAnswers))
          view.apply(form, NormalMode)(fakeRequest, messages, frontendAppConfig)
        }

      behave like normalPage(applyView(form), messageKeyPrefix)

      behave like pageWithBackLink(applyView(form))

      behave like intPage(form, applyView, messageKeyPrefix, routes.$className$Controller.onSubmit(NormalMode).url)
    }
  }
}
