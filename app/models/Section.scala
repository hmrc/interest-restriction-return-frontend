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

package models

import play.api.libs.json.{JsValue, Json, Writes}

sealed trait Section

object Section {

  object AboutReturn extends Section {
    override val toString = "aboutReturn"
  }

  object GroupLevelInformation extends Section {
    override val toString = "groupLevelInformation"
  }

  object Elections extends Section {
    override val toString = "elections"
  }

  object UltimateParentCompany extends Section {
    override val toString = "ultimateParentCompany"
  }

  object UkCompanies extends Section {
    override val toString = "ukCompanies"
  }

  object ReviewTaxEBITDA extends Section {
    override val toString = "reviewTaxEBITDA"
  }

  object ReviewReactivations extends Section {
    override val toString = "reviewReactivations"
  }

  object CheckTotals extends Section {
    override val toString = "checkTotals"
  }

  object ReviewAndComplete extends Section {
    override val toString = "reviewAndComplete"
  }

  implicit object SectionWrites extends Writes[Section]{
    def writes(section: Section): JsValue = Json.toJson(section.toString)
  }
}