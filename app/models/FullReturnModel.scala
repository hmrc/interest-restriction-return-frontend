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

import models.returnModels.{AgentDetailsModel, DeemedParentModel}
import models.sections._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsArray, JsObject, JsPath, Json, Writes}

case class FullReturnModel(aboutReturn: AboutReturnSectionModel,
                           ultimateParentCompany: UltimateParentCompanySectionModel,
                           elections: Option[ElectionsSectionModel] = None,
                           groupLevelInformation: Option[GroupLevelInformationSectionModel] = None,
                           ukCompanies: Option[UkCompaniesSectionModel] = None)

object FullReturnModel {
  val revisedReturn = "revised"
  val originalReturn = "original"

  val writes: Writes[FullReturnModel] = (
    (JsPath \ "appointedReportingCompany").write[Boolean] and
      (JsPath \ "agentDetails").write[AgentDetailsModel] and
      (JsPath \ "submissionType").write[String] and
      (JsPath \ "revisedReturnDetails").writeNullable[String] and
      (JsPath \ "reportingCompany").write[JsObject] and
      (JsPath \ "groupCompanyDetails" \ "accountingPeriod").write[JsObject] and
      (JsPath \ "parentCompany").write[JsObject]
    ) (
    fullReturn => (
      fullReturn.aboutReturn.appointedReportingCompany,
      fullReturn.aboutReturn.agentDetails,
      if (fullReturn.aboutReturn.isRevisingReturn) revisedReturn else originalReturn,
      fullReturn.aboutReturn.revisedReturnDetails,
      Json.obj(
        "companyName" -> fullReturn.aboutReturn.companyName,
        "ctutr" -> fullReturn.aboutReturn.ctutr,
        "sameAsUltimateParent" -> fullReturn.ultimateParentCompany.reportingCompanySameAsParent
      ),
      Json.obj(
        "startDate" -> fullReturn.aboutReturn.periodOfAccount.startDate,
        "endDate" -> fullReturn.aboutReturn.periodOfAccount.endDate
      ),
      {
        if (fullReturn.ultimateParentCompany.reportingCompanySameAsParent) {
          Json.obj("ultimateParent" -> Json.obj("companyName" -> fullReturn.aboutReturn.companyName,
                                                              "ctutr" -> fullReturn.aboutReturn.ctutr))
        } else {
          if (fullReturn.ultimateParentCompany.hasDeemedParent == Some(true)) {
            Json.obj("deemedParent" -> JsArray(fullReturn.ultimateParentCompany.parentCompanies.map(parent => {
                deemedParent(parent)
              })))
          } else {
            Json.obj("ultimateParent" -> deemedParent(fullReturn.ultimateParentCompany.parentCompanies.head))
          }
        }
      }
    )
  )

  private def deemedParent(parent: DeemedParentModel) = {
    Json.obj("companyName" -> parent.companyName.name,
      "ctutr" -> parent.ctutr,
      "sautr" -> parent.sautr,
      "countryOfIncorporation" -> parent.countryOfIncorporation.map(c => c.code))
  }
}