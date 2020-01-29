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

package assets.constants

import models.returnModels.PartnershipModel
import play.api.libs.json.Json

object PartnershipsConstants extends  BaseConstants {

  val partnerName = "some partner"

  val partnershipModel = PartnershipModel(
    partnershipName = partnerName,
    sautr = Some(sautr)
  )

  val partnershipJson = Json.obj(
    "partnershipName" -> partnerName,
    "sautr" -> sautr
  )
}