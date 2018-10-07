mongoimport -d admin -c delivery --type csv --file delivery.csv --headerline
mongoimport -d admin -c clients --type csv --file clients.csv --headerline
mongoimport -d admin -c software --type csv --file software.csv --headerline
mongoimport -d admin -c contracts --type csv --file contracts.csv --headerline
 