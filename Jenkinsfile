node {
    def name = "uam"
    
    stage('Clone repository') {
        checkout scm
    }

    stage('Initialize Docker env'){
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
            withMaven(maven: 'maven') {
                sh "mvn test"
            }
    }

    stage('Build docker images') {
        docker.build("${name}/monolit:${BUILD_NUMBER}","${env.WORKSPACE}/monolit")
        docker.build("${name}/exchange-rates:${BUILD_NUMBER}","${env.WORKSPACE}/exchange-rates")
        docker.build("${name}/haproxy:${BUILD_NUMBER}","${env.WORKSPACE}/haproxy")
        docker.build("${name}/frontend-app:${BUILD_NUMBER}","${env.WORKSPACE}/frontend")
    }
    
    stage('Deploy apps on docker') {
        cleanDockerContainer("todo-list")
        sh ("docker run -d --network uam-network --hostname app --name todo-list -e DB_HOST='db' -e DB_PORT='3306' -e DB_USER='root' -e DB_PASSWORD='root' -v /var/log/:/var/log/ ${name}/monolit:${BUILD_NUMBER}")

        cleanDockerContainer("exchange-rates")
        sh ("docker run -d --network uam-network --hostname exchange-rates --name exchange-rates-container -v /var/log/:/var/log/ ${name}/exchange-rates:${BUILD_NUMBER}")

        cleanDockerContainer("frontend-app")
        sh ("docker run -d --network uam-network --hostname web --name frontend-app -v /var/log/:/var/log/ ${name}/frontend-app:${BUILD_NUMBER}")

        cleanDockerContainer("haproxy")
        sh ("docker run -d --network uam-network --hostname haproxy -p 80:80 --name haproxy -v /var/log/:/var/log/ ${name}/haproxy:${BUILD_NUMBER}")
    }

}

def cleanDockerContainer(String dockerContainerName) {
        sh ("docker ps -q --filter name=${dockerContainerName} | xargs -r docker stop")  // stop the existing container if it's running
        sh ("docker ps -a -q --filter name=${dockerContainerName} | xargs -r docker rm")   // remove the existing container if it exists
}