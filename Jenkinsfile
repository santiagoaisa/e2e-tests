pipeline {
    agent any
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['ephemeral', 'dev'], description: 'Selecciona el entorno')
        choice(name: 'BROSWER', choices: ['chrome', 'edge', 'firefox'], description: 'Selecciona el navegador')
        string(name: 'TAGS', defaultValue: '@listarPropietarios', description: 'Especifica los tags')
    }
    stages {
        stage('Prepare environment') {
            steps {
                dir('frontend'){
                    git branch: "master",
                    url: "https://github.com/devops-mitocode/spring-petclinic-angular.git"
                }
                sh 'env | sort'
                sh 'docker system df'
                sh "docker compose --project-name ${BUILD_TAG} up -d --quiet-pull"
                sh 'docker ps -a'
                sh 'docker network ls'
            }
        }
        stage('End2End Tests') {
            steps {
                sh 'sleep 3m'
//                sh "docker run --rm -v ${WORKSPACE}:/usr/src/app -w /usr/src/app --name ${BUILD_TAG} --network ${BUILD_TAG}_default maven:3.8.8-eclipse-temurin-17 sleep 5m"
                sh "docker run --rm -v ${WORKSPACE}:/usr/src/app -w /usr/src/app  --name ${BUILD_TAG} --network ${BUILD_TAG}_default maven:3.8.8-eclipse-temurin-17 mvn clean verify -B -ntp -Dwebdriver.remote.url=http://selenium-hub:4444/wd/hub -Dwebdriver.remote.driver=${BROSWER} -Denvironment=${ENVIRONMENT} -Dwebdriver.base.url=http://frontend:8080 -Dcucumber.filter.tags=\"${TAGS}\""
                publishHTML(
                    target: [
                        reportName           : 'Serenity Report',
                        reportDir            : 'target/site/serenity',
                        reportFiles          : 'index.html',
                        keepAll              : true,
                        alwaysLinkToLastBuild: true,
                        allowMissing         : false
                    ]
                )
            }
        }
    }
    post {
        always {
            //sh "docker compose --project-name ${BUILD_TAG} down --rmi all --volumes"
            sh "docker compose --project-name ${BUILD_TAG} down --volumes"
            sh 'docker builder prune -f'
            sh 'docker system df'
            cleanWs()
        }
    }
}
