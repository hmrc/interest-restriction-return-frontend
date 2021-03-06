package forms.$section;format="decap"$

import java.time.{LocalDate, ZoneOffset}

import forms.behaviours.DateBehaviours
import play.api.data.FormError

class $className;format="cap"$FormProviderSpec extends DateBehaviours {

  val form = new $className;format="cap"$FormProvider()()

  ".value" should {

    val validData = datesBetween(
      min = LocalDate.of(2000, 1, 1),
      max = LocalDate.now(ZoneOffset.UTC)
    )

    behave like dateField(form, "value", validData)

    behave like mandatoryDateField(form, "value", "$className;format="decap"$.error.required.all")
  }
}
