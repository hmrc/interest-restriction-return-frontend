#!/bin/bash

echo ""
echo "Applying migration InvestmentName"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/elections.routes
echo "### InvestmentName Controller" >> ../conf/elections.routes
echo "### ----------------------------------------" >> ../conf/elections.routes

export kebabClassName=$(sed -e 's/\([^A-Z]\)\([A-Z0-9]\)/\1-\2/g' -e 's/\([A-Z0-9]\)\([A-Z0-9]\)\([^A-Z]\)/\1-\2\3/g' <<< "InvestmentName" | tr '[:upper:]' '[:lower:]')
echo "GET        /$kebabClassName                          controllers.elections.InvestmentNameController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/elections.routes
echo "POST       /$kebabClassName                          controllers.elections.InvestmentNameController.onSubmit(mode: Mode = NormalMode)" >> ../conf/elections.routes
echo "GET        /$kebabClassName/change                   controllers.elections.InvestmentNameController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/elections.routes
echo "POST       /$kebabClassName/change                   controllers.elections.InvestmentNameController.onSubmit(mode: Mode = CheckMode)" >> ../conf/elections.routes

echo "Adding messages to English conf.messages"
echo "" >> ../conf/messages.en
echo "# InvestmentNamePage Messages" >> ../conf/messages.en
echo "# ----------------------------------------------------------" >> ../conf/messages.en
echo "investmentName.title = investmentName" >> ../conf/messages.en
echo "investmentName.heading = investmentName" >> ../conf/messages.en
echo "investmentName.checkYourAnswersLabel = investmentName" >> ../conf/messages.en
echo "investmentName.label= Enter the name of the investment" >> ../conf/messages.en
echo "investmentName.error.required = Enter investmentName" >> ../conf/messages.en
echo "investmentName.error.length = InvestmentName must be 160 characters or less" >> ../conf/messages.en

echo "Adding messages to Welsh conf.messages"
echo "" >> ../conf/messages.cy
echo "# InvestmentNamePage Messages" >> ../conf/messages.cy
echo "# ----------------------------------------------------------" >> ../conf/messages.cy
echo "investmentName.title = investmentName" >> ../conf/messages.cy
echo "investmentName.heading = investmentName" >> ../conf/messages.cy
echo "investmentName.checkYourAnswersLabel = investmentName" >> ../conf/messages.cy
echo "investmentName.label = Enter the name of the investment" >> ../conf/messages.cy
echo "investmentName.error.required = Enter investmentName" >> ../conf/messages.cy
echo "investmentName.error.length = InvestmentName must be 160 characters or less" >> ../conf/messages.cy

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryInvestmentNameUserAnswersEntry: Arbitrary[(InvestmentNamePage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[InvestmentNamePage.type]";\
    print "        value <- arbitrary[String].suchThat(_.nonEmpty).map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryInvestmentNamePage: Arbitrary[InvestmentNamePage.type] =";\
    print "    Arbitrary(InvestmentNamePage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to UserAnswersGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(InvestmentNamePage.type, JsValue)] ::";\
    next }1' ../test/generators/UserAnswersGenerator.scala > tmp && mv tmp ../test/generators/UserAnswersGenerator.scala

echo "Adding helper method to CheckYourAnswersHelper"
awk '/class/ {\
     print;\
     print "";\
     print "  def investmentName: Option[SummaryListRow] = answer(InvestmentNamePage, electionsRoutes.InvestmentNameController.onPageLoad(CheckMode))";\
     next }1' ../app/utils/CheckYourAnswersHelper.scala > tmp && mv tmp ../app/utils/CheckYourAnswersHelper.scala

echo "Adding to Pages map"
awk '/val pages/ {\
    print;\
    print "    InvestmentNamePage.toString -> InvestmentNamePage,";\
    next }1' ../app/pages/Page.scala > tmp && mv tmp ../app/pages/Page.scala

echo "adding to Pages map spec"
awk '/val expected/ {\
    print;\
    print "    InvestmentNamePage.toString -> InvestmentNamePage,";\
    next }1' ../test/pages/PageSpec.scala > tmp && mv tmp ../test/pages/PageSpec.scala

echo "adding to PageTitles"
awk '/object PageTitles/ {\
    print;\
    print "  val investmentName = \"Enter the name of the investment\"";\
    next }1' ../it/assets/PageTitles.scala > tmp && mv tmp ../it/assets/PageTitles.scala

echo "adding route to integration test"

sed -i "" "s|ROUTING_PLACEHOLDER|elections\/${kebabClassName}|g" ../generated-it/controllers/elections/InvestmentNameControllerISpec.scala

echo "Migration InvestmentName completed"
