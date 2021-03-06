#!/bin/bash

echo "Applying migrations..."
echo ""

cd migrations
for file in *.sh
do
    echo "Applying migration $file"
    chmod u+x $file
    /bin/bash $file
    mv $file ./applied_migrations
done

echo ""
echo "Moving test files from generated-test/ to test/"
echo ""

rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo ""

echo ""
echo "Moving integration test files from generated-it/ to it/"
echo ""

rsync -avm --include='*.scala' -f 'hide,! */' ../generated-it/ ../it/
rm -rf ../generated-it/

echo ""

echo "Migrations complete"