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

package forms

import play.api.data.validation.{Constraint, Invalid, Valid}

import scala.util.{Failure, Success, Try}

trait UTRFormValidation {

  def checksum(errorKey: String): Constraint[String] = {
    Constraint {
      case str if validateCheckSum(str) => Valid
      case _ => Invalid(errorKey)
    }
  }

  def optionalChecksum(errorKey: String): Constraint[Option[String]] = {
    Constraint {
      case Some(str) if validateCheckSum(str) => Valid
      case None => Valid
      case _ => Invalid(errorKey)
    }
  }

  private def validateCheckSum(utr: String) = Try {
    val utrInts = utr.map(_.asDigit)
    val utrSum = (utrInts(1) * 6) + (utrInts(2) * 7) + (utrInts(3) * 8) + (utrInts(4) * 9) + (utrInts(5) * 10) + (utrInts(6) * 5) + (utrInts(7) * 4) + (utrInts(8) * 3) + (utrInts(9) * 2)
    val utrCalc = 11 - (utrSum % 11)
    val checkSum = if (utrCalc > 9) utrCalc - 9 else utrCalc
    checkSum == utrInts.head
  } match {
    case Success(s) => s
    case Failure(_) => false
  }
}
