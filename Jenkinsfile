node {
    def application = "monolit"
    def name = "uam"
    
    stage('Clone repository') {
        checkout scm
    }

    stage('Initialize'){
        def dockerHome = tool 'docker'
        env.PATH = "${dockerHome}/bin:${env.PATH}"
    }
    
    stage('Build applications with Maven') {
            withMaven(
            maven: 'maven'
        ) {
          sh "mvn clean install -DskipTests=true"
        } 
    }

    stage('Run tests') {
      step {
          sh "mvn test"
        }
        post {
        always {
           junit 'target/surefire-report/*.xml'
        }
        } 
    }


    stage('Build docker images') {
        docker.build("${name}/monolit:${BUILD_NUMBER}","${env.WORKSPACE}/monolit")
        docker.build("${name}/exchange-rates:${BUILD_NUMBER}","${env.WORKSPACE}/exchange-rates")
        docker.build("${name}/haproxy:${BUILD_NUMBER}","${env.WORKSPACE}/haproxy")
    }
    
    stage('Deploy on docker') {
        sh ("docker ps -q --filter ancestor=todo-list | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter ancestor=todo-list | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker container ls -a -q --filter name=todo-list-container | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker run -d --network uam-network --hostname app --name todo-list-container -e DB_HOST='db' -e DB_PORT='3306' -e DB_USER='root' -e DB_PASSWORD='root' -v /var/log/:/var/log/ ${name}/monolit:${BUILD_NUMBER}")

 
        sh ("docker ps -q --filter ancestor=exchange-rates | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter ancestor=exchange-rates | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker container ls -a -q --filter name=exchange-rates-container | xargs -r docker rm")   // remove the existing container if it exists       
        sh ("docker run -d --network uam-network --hostname exchange-rates --name exchange-rates-container -v /var/log/:/var/log/ ${name}/exchange-rates:${BUILD_NUMBER}")
        
        sh ("docker ps -q --filter ancestor=haproxy | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter ancestor=haproxy | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker container ls -a -q --filter name=haproxy | xargs -r docker rm")   // remove the existing container if it exists       
        sh ("docker run -d --network uam-network --hostname haproxy -p 80:80 --name haproxy -v /var/log/:/var/log/ ${name}/haproxy:${BUILD_NUMBER}")
    }

}
