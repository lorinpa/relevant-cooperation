echo "buidling frontend"
ng build -d /co/
cd ../
echo "copying frontend to deployment target"
#mvn process-resources
mvn install -Plocal
cd frontend
echo "done"