pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Login"){
            steps{
                echo "========executing Login========"
                withCredentials([usernamePassword(credentialsId: 'github_credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'git config --global user.name ${USERNAME}'
                    sh 'git config --global user.password ${PASSWORD}'
                }
            }
        }
        stage("Execute Sonar Analysis") {
            steps{
                echo "=========Executing Sonar Analysis========="
                withSonarQubeEnv('local_sonarQube_server'){
                    sh 'mvn clean verify sonar:sonar'  
                }
            }
            post{
                success {
                    script {
                        timeout(time: 1, unit: 'HOURS') {
                        def qg = waitForQualityGate()     
                        if(qg.status == 'SUCCESS') {
                            echo "Quality gate passed. Exiting timeout block"
                            return
                        }
                        if (qg.status != 'SUCCESS') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
                        }
                    }
                }
            }
        }
    }
    post{
        always{
            echo "========always========="
        }
        success{
            echo "========pipeline executed successfully ========"
        }
        failure{
            echo "========pipeline execution failed========"
        }
    }
}