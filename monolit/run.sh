docker compose up -d --force-recreate
sudo rm -r mysql_data/
mvn clean package
mvn spring-boot:run