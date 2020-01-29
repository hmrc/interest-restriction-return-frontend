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

package assets.constants.abbreviatedReturn

import assets.constants.AgentDetailsConstants._
import assets.constants.GroupCompanyDetailsConstants._
import assets.constants.GroupLevelElectionsConstants._
import assets.constants.ParentCompanyConstants._
import assets.constants.ReportingCompanyConstants._
import assets.constants.abbreviatedReturn.UkCompanyConstants._
import models.returnModels.Revised
import models.returnModels.abbreviatedReturnModel.AbbreviatedReturnModel
import play.api.libs.json.Json

object AbbreviatedReturnConstants {

  val ackRef = "ackRef"

  val abbreviatedReturnUltimateParentJson = Json.obj(
    "agentDetails" -> agentDetailsJsonMax,
    "reportingCompany" -> reportingCompanyJson,
    "publicInfrastructure" -> true,
    "parentCompany" -> parentCompanyJsonUltUkCompany,
    "groupCompanyDetails" -> groupCompanyDetailsJson,
    "submissionType" -> Revised,
    "revisedReturnDetails" -> "revised details",
    "groupLevelElections" -> groupLevelElectionsJsonMax,
    "ukCompanies" -> Seq(ukCompanyJson)
  )

  val abbreviatedReturnUltimateParentModel = AbbreviatedReturnModel(
    agentDetails = agentDetailsModelMax,
    reportingCompany = reportingCompanyModel,
    publicInfrastructure = true,
    parentCompany = Some(parentCompanyModelUltUkCompany),
    groupCompanyDetails = groupCompanyDetailsModel,
    submissionType = Revised,
    revisedReturnDetails = Some("revised details"),
    groupLevelElections = Some(groupLevelElectionsModelMax),
    ukCompanies = Seq(ukCompanyModel)
  )

  val abbreviatedReturnDeemedParentJson = Json.obj(
    "agentDetails" -> agentDetailsJsonMax,
    "reportingCompany" -> reportingCompanyJson,
    "publicInfrastructure" -> true,
    "parentCompany" -> parentCompanyJsonDeemedMax,
    "groupCompanyDetails" -> groupCompanyDetailsJson,
    "submissionType" -> Revised,
    "revisedReturnDetails" -> "revised details",
    "groupLevelElections" -> groupLevelElectionsJsonMax,
    "ukCompanies" -> Seq(ukCompanyJson)
  )

  val abbreviatedReturnDeemedParentModel = AbbreviatedReturnModel(
    agentDetails = agentDetailsModelMax,
    reportingCompany = reportingCompanyModel,
    publicInfrastructure = true,
    parentCompany = Some(parentCompanyModelDeemedMax),
    groupCompanyDetails = groupCompanyDetailsModel,
    submissionType = Revised,
    revisedReturnDetails = Some("revised details"),
    groupLevelElections = Some(groupLevelElectionsModelMax),
    ukCompanies = Seq(ukCompanyModel)
  )

  val abbreviatedReturnJsonMin = Json.obj(
    "agentDetails" -> agentDetailsJsonMin,
    "reportingCompany" -> reportingCompanyJson,
    "publicInfrastructure" -> true,
    "groupCompanyDetails" -> groupCompanyDetailsJson,
    "submissionType" -> Original,
    "ukCompanies" -> Seq(ukCompanyJson)
  )

  val abbreviatedReturnModelMin = AbbreviatedReturnModel(
    agentDetails = agentDetailsModelMin,
    reportingCompany = reportingCompanyModel,
    publicInfrastructure = true,
    parentCompany = None,
    groupCompanyDetails = groupCompanyDetailsModel,
    submissionType = Original,
    revisedReturnDetails = None,
    groupLevelElections = None,
    ukCompanies = Seq(ukCompanyModel)
  )
}
