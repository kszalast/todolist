node {
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
        try {
            withMaven(maven: 'maven') {
                sh "mvn test"
            }
        } finally {
           // junit testResults: '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('Build docker images') {
        docker.build("${name}/monolit:${BUILD_NUMBER}","${env.WORKSPACE}/monolit")
        docker.build("${name}/exchange-rates:${BUILD_NUMBER}","${env.WORKSPACE}/exchange-rates")
        docker.build("${name}/haproxy:${BUILD_NUMBER}","${env.WORKSPACE}/haproxy")
        docker.build("${name}/frontend-app:${BUILD_NUMBER}","${env.WORKSPACE}/frontend")
    }
    
    stage('Deploy on docker') {
        sh ("docker ps -q --filter name=todo-list | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter name=todo-list | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker run -d --network uam-network --hostname app --name todo-list -e DB_HOST='db' -e DB_PORT='3306' -e DB_USER='root' -e DB_PASSWORD='root' -v /var/log/:/var/log/ ${name}/monolit:${BUILD_NUMBER}")

        sh ("docker ps -q --filter name=exchange-rates | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter name=exchange-rates | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker run -d --network uam-network --hostname exchange-rates --name exchange-rates-container -v /var/log/:/var/log/ ${name}/exchange-rates:${BUILD_NUMBER}")

        sh ("docker ps -q --filter name=frontend-app | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter name=frontend-app | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker run -d --network uam-network --hostname web --name frontend-app -v /var/log/:/var/log/ ${name}/frontend-app:${BUILD_NUMBER}")

        sh ("docker ps -q --filter name=haproxy | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter name=haproxy | xargs -r docker rm")   // remove the existing container if it exists
        sh ("docker run -d --network uam-network --hostname haproxy -p 80:80 --name haproxy -v /var/log/:/var/log/ ${name}/haproxy:${BUILD_NUMBER}")
    }

}
