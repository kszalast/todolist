docker compose up -d --force-recreate
sudo rm -r mysql_data/
mvn clean package


Jenkins
docker-compose up

Konfiguracja joba w Jenkinsie:
Pool SCM: * * * * *
Git URL: https://github.com/kszalast/todolist.git
Git branch: observability_1_jenkins
Jenkins file how-to: https://www.jenkins.io/doc/book/pipeline/jenkinsfile/