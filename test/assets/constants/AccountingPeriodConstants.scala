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

package assets.constants

import java.time.LocalDate

import models.returnModels.AccountingPeriodModel
import play.api.libs.json.Json

object AccountingPeriodConstants {

  val startDate: LocalDate = LocalDate.now().minusMonths(18)
  val endDate: LocalDate = startDate.plusMonths(18).minusDays(1)

  val accountingPeriodModel = AccountingPeriodModel(
    startDate = startDate,
    endDate = endDate
  )

  val accountingPeriodJson = Json.obj(
    "startDate" -> startDate,
    "endDate" -> endDate
  )
}
