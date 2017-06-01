echo "buidling frontend"
ng build -d /co/
cd ../
mvn wildfly:redeploy
cd frontend
echo "done"