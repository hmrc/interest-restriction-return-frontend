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

package models.returnModels

import models.{ErrorModel, UserAnswers}
import pages.groupStructure.{ParentCRNPage, ParentCompanyCTUTRPage, ParentCompanyNamePage, ParentCompanySAUTRPage}
import play.api.libs.json.Json

case class DeemedParentModel(companyName: CompanyNameModel,
                             knownAs: Option[String] = None,
                             ctutr: Option[UTRModel] = None,
                             sautr: Option[UTRModel] = None,
                             crn: Option[CRNModel] = None,
                             countryOfIncorporation: Option[CountryCodeModel] = None,
                             nonUkCrn: Option[String] = None,
                             limitedLiabilityPartnership: Option[Boolean] = None,
                             payTaxInUk: Option[Boolean] = None,
                             registeredCompaniesHouse: Option[Boolean] = None,
                             registeredForTaxInAnotherCountry: Option[Boolean] = None,
                             reportingCompanySameAsParent: Option[Boolean] = None
                            ) {
  val utr: Option[UTRModel] = ctutr.fold(sautr){ utr => Some(utr)}
}

object DeemedParentModel {

  implicit val format = Json.format[DeemedParentModel]

  def apply(userAnswers: UserAnswers): Either[ErrorModel, DeemedParentModel] = {

    val oName = userAnswers.get(ParentCompanyNamePage)
    val ctutr = userAnswers.get(ParentCompanyCTUTRPage)
    val crn = userAnswers.get(ParentCRNPage)
    val sautr = userAnswers.get(ParentCompanySAUTRPage)

    oName match {
      case Some(name) =>
        Right(DeemedParentModel(
          companyName = CompanyNameModel(name),
          ctutr = ctutr.map(UTRModel.apply),
          sautr = sautr.map(UTRModel.apply),
          crn = crn.map(CRNModel.apply),
          countryOfIncorporation = None, //TODO: Needs to be updated once page is created
          nonUkCrn = None, //TODO: Needs to be updated once page is created
          knownAs = None //TODO: Will be removed from model
        ))
      case _ => Left(ErrorModel("Cannot Construct Deemed Parent Model from User Answers as Company Name is missing"))
    }
  }
}
