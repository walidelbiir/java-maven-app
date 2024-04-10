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
                        // Wait for SonarQube analysis to complete
                        timeout(time: 1, unit: 'HOURS') {
                            waitForQualityGate abortPipeline: true
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
