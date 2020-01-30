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

import models.returnModels.UltimateParentModel
import play.api.libs.json.Json

object UltimateParentConstants extends BaseConstants {

  val otherUkTaxReference = "other reference"

  val ultimateParentModelMax = UltimateParentModel(
    isUk = true,
    companyName = companyNameModel,
    knownAs = Some(knownAs),
    ctutr = Some(ctutrModel),
    sautr = Some(sautrModel),
    crn = Some(crnLetters),
    nonUkCrn = Some(nonUkCrn),
    countryOfIncorporation = Some(nonUkCountryCode)
  )

  val ultimateParentModelMin = UltimateParentModel(
    isUk = true,
    companyName = companyNameModel,
    knownAs = None,
    ctutr = None,
    sautr = None,
    crn = None,
    nonUkCrn = None,
    countryOfIncorporation = None
  )

  val ultimateParentModelUkCompany = UltimateParentModel(
    isUk = true,
    companyName = companyNameModel,
    knownAs = Some(knownAs),
    ctutr = Some(ctutrModel),
    sautr = None,
    crn = Some(crnLetters),
    nonUkCrn = None,
    countryOfIncorporation = None
  )

  val ultimateParentModelNonUkCompany = UltimateParentModel(
    isUk = false,
    companyName = companyNameModel,
    knownAs = Some(knownAs),
    ctutr = None,
    sautr = None,
    crn = None,
    nonUkCrn = Some(nonUkCrn),
    countryOfIncorporation = Some(nonUkCountryCode)
  )

  val ultimateParentModelUkPartnership = UltimateParentModel(
    isUk = true,
    companyName = companyNameModel,
    knownAs = Some(knownAs),
    ctutr = None,
    sautr = Some(sautrModel),
    crn = Some(crnLetters),
    nonUkCrn = None,
    countryOfIncorporation = None
  )

  val ultimateParentJsonMax = Json.obj(
    "isUk" -> true,
    "companyName" -> companyNameModel,
    "knownAs" -> knownAs,
    "ctutr" -> ctutrModel,
    "sautr" -> sautrModel,
    "crn" -> crnLetters,
    "nonUkCrn" -> nonUkCrn,
    "countryOfIncorporation" -> nonUkCountryCode
  )

  val ultimateParentJsonMin = Json.obj(
    "isUk" -> true,
    "companyName" -> companyNameModel
  )

  val ultimateParentJsonUkCompany = Json.obj(
    "isUk" -> true,
    "companyName" -> companyNameModel,
    "knownAs" -> knownAs,
    "ctutr" -> ctutrModel,
    "crn" -> crnLetters
  )

  val ultimateParentJsonNonUkCompany = Json.obj(
    "isUk" -> false,
    "companyName" -> companyNameModel,
    "knownAs" -> knownAs,
    "nonUkCrn" -> nonUkCrn,
    "countryOfIncorporation" -> nonUkCountryCode
  )

  val ultimateParentJsonUkPartnership = Json.obj(
    "isUk" -> true,
    "companyName" -> companyNameModel,
    "knownAs" -> knownAs,
    "sautr" -> ctutrModel,
    "crn" -> crnLetters
  )
}
