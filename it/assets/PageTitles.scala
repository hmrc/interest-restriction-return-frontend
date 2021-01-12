package assets

object PageTitles {
  val tellUsWhatHasChanged = "Tell us what has changed"
  val restrictionAmountSameAP = "Enter the restriction amount for this company"
  val addRestriction = "Do you need to add a restriction to this company?"
  val companyAccountingPeriodSameAsGroup = "Is this company’s accounting period the same as the group’s period of account?"
  val partnershipDeletionConfirmation: String => String = name => s"Are you sure you want to delete partnership ‘$name’?"
  val partnershipsReviewAnswersList: Int => String = i => s"$i partnership${if (i > 1) 's'} added"
  val addAnReactivationQuery = "Do you need to add a reactivation to this company?"
  val reactivationAmount = "Add a reactivation amount"
  val reviewReactivations = "Review reactivations for companies in the group"
  val accountingPeriod = "Worldwide group’s period of account"
  val reviewAndComplete = "Interest Restriction Return"
  val checkAnswersUkCompany: String => String = name => s"Check $name details"
  val derivedCompany = "Check the totals for this return"
  val companyDetails = "Company details"
  val aboutAddingUKCompanies = "In this section you will need to tell us about eligible UK companies in the group"
  val netTaxInterestAmount: String => String = name => s"What is the $name’${if (name.last.toLower != 's') 's'} total net-tax interest income?"
  val investorGroupsDeletionConfirmation: String => String = name => s"Are you sure you want to delete investor group ‘$name’?"
  val investmentsDeletionConfirmation: String => String = name => s"Are you sure you want to delete investment ‘$name’?"
  val investmentName = "Enter the name of the investment"
  val deletionConfirmation: String => String = name => s"Are you sure you want to delete deemed parent ‘$name’?"
  val ukCompaniesDeletionConfirmation: String => String = name => s"Are you sure you want to delete company ‘$name’?"
  val consentingCompany = "Is this a consenting company?"
  val netTaxInterestIncomeOrExpense: String => String = name => s"Which net tax-interest does $name have?"
  val partnershipSAUTR = "Enter the Self Assessment Unique Taxpayer Reference"
  val isUkPartnership = "Is this a UK Partnership?"
  val deemedParentReviewAnswersList: Int => String = i => s"$i deemed parent${if (i > 1) 's'} added"
  val investmentsReviewAnswersList: Int => String = i => s"$i investment${if (i > 1) 's'} added"
  val partnershipName = "Enter the name of the partnership"
  val investorRatioMethod = "Do you want to treat the investor group as if they have made the group ratio election?"
  val investorGroupName = "Enter the name of the investor group"
  val addInvestorGroup = "Do you want to add an investor group?"
  val otherInvestorGroupElections = "Which elections are being treated as if they have been made by the investor group?"
  val groupEBITDA = "Enter the group-EBITDA"
  val interestAllowanceConsolidatedPshipElection = "Do you want to make an interest allowance election for consolidated partnerships?"
  val electedInterestAllowanceConsolidatedPshipBefore = "Has the group made an interest allowance election for consolidated partnerships before?"
  val interestAllowanceNonConsolidatedInvestmentsElection = "Do you want to make an interest allowance election for non-consolidated investments?"
  val groupRatioPercentage = "Enter the group ratio percentage"
  val interestAllowanceAlternativeCalcElection = "Do you want to make an interest allowance (alternative calculation) election for this return?"
  val electedInterestAllowanceAlternativeCalcBefore = "Has the group made an interest allowance (alternative calculation) election before?"
  val groupEBITDAChargeableGainsElection = "Do you want to make a group-EBITDA (chargeable gains) election for this return?"
  val electedGroupEBITDABefore = "Has the group made a group-EBITDA (chargeable gains) election before?"
  val groupRatioBlendedElection = "Do you want to make a blended group ratio election?"
  val enterQNGIE = "Enter the qualifying net group-interest expense (QNGIE)"
  val checkAnswersGroupStructure = "Check ultimate parent details"
  val reviewNetTaxInterest = "Review the net tax-interest for companies in the group"
  val reviewTaxEBITDA = "Review the Tax-EBITDA for companies in the group"
  val enterANGIE = "Enter the adjusted net group-interest expense (ANGIE)"
  val groupRatioElection = "Do you want to make a group ratio election?"
  val checkAnswersAboutReturn = "Check answers - About the return"
  val reportingCompanyCTUTR = "Enter the Corporation Tax Unique Taxpayer Reference for the reporting company"
  val reportingCompanyName = "Enter the name of the reporting company"
  val groupInterestAllowance = "What is the group interest allowance for the period?"
  val groupInterestCapacity = "What is the group interest capacity for the period?"
  val groupSubjectToReactivations = "Is the group subject to reactivations?"
  val groupSubjectToRestrictions = "Is the group subject to interest restrictions?"
  val infrastructureCompanyElection = "Has the group made the Infrastructure company election?"
  val interestAllowanceBroughtForward = "Enter the amount of interest allowance the group is bringing forward"
  val interestReactivationsCap = "What is the group reactivation cap?"
  val returnContainEstimates = "Will you be submitting estimated figures?"
  val revisingReturn = "Are you revising a return that has already been submitted?"
  val agentActingOnBehalfOfCompany = "Are you an agent?"
  val agentName = "Enter the name of the agent"
  val fullOrAbbreviatedReturn = "Are you submitting a full or abbreviated return?"
  val reportingCompanyAppointed = "Has the group appointed a reporting company?"
  val reportingCompanyRequired = "You need to appoint a reporting company"
  val confirmation = "Return submitted"
  val continueSavedReturn = "Do you want to start a new return or continue working on a saved return?"
  val deemedParent = "Is the ultimate parent a 'deemed' parent?"
  val parentCompanyName = "Enter the name of the ultimate parent"
  val payTaxInUk: String = "Does the ultimate parent have a Unique Taxpayer Reference?"
  val reportingCompanySameAsParent = "Is the reporting company also the ultimate parent of the worldwide group?"
  val parentCompanySAUTR = "Enter the Self Assessment Unique Taxpayer Reference"
  val parentCompanyCTUTR = "Enter the Corporation Tax Unique Taxpayer Reference"
  val savedReturn = "Your return has been saved"
  val limitedLiabilityPartnership = "Is the ultimate parent a Limited Liability Partnership?"
  val countryOfIncorporation = "Which country is the ultimate parent incorporated in?"
  val checkAnswersElections = "Check your answers for this section"
  val companyTaxEBITDA = "Enter company’s Tax-EBITDA"
  val ukCompaniesReviewAnswersList: Int => String = {
    case x if x > 1 => s"$x companies added"
    case _ => "1 company added"
  }


}
