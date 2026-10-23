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
                // Nota: Si el pom.xml está dentro de una subcarpeta llamada e2e-tests, usa -w /usr/src/app/e2e-tests
                // Si el pom.xml está en la raíz, se mantiene -w /usr/src/app
                sh """
                    docker run --rm \
                      -v ${WORKSPACE}:/usr/src/app \
                      -w /usr/src/app \
                      --name ${BUILD_TAG} \
                      --network ${BUILD_TAG}_default \
                      maven:3.8.8-eclipse-temurin-17 \
                      mvn clean verify -B -ntp \
                        -Dwebdriver.remote.url=http://selenium-hub:4444/wd/hub \
                        -Dwebdriver.remote.driver=${params.BROSWER} \
                        -Denvironment=${params.ENVIRONMENT} \
                        -Dwebdriver.base.url=http://petclinic-frontend:8080/petclinic/ \
                        -Dcucumber.filter.tags="${params.TAGS}"
                """
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
            sh "docker compose --project-name ${BUILD_TAG} down --volumes"
            sh 'docker builder prune -f'
            sh 'docker system df'
            cleanWs()
        }
    }
}
//sh "docker compose --project-name ${BUILD_TAG} down --rmi all --volumes"