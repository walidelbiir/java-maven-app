@Library('java-pipeline-library') _

pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{

        stage("Maven Build Status") {
            steps{
                echo "=========Building with Maven========="
                build.call()
            }
            post{
                success {
                    build.postSuccess()
                }
                failure {
                    build.postFailure()
                }
            }
        }

        stage("SonarQube analysis") {
            steps{
                echo "=========Analysis with SonarQube========="
                sonar.call()
            }
            post {
                always {
                    sonar.waitForAnalysisReport()
                }
                success {
                    sonar.postSuccess()
                }
                failure {
                    sonar.postFailure()
                }
            }
        }

        stage("Unit Testing") {
            steps{
                test.call()
            }
            post {
                success {
                    test.postSuccess()
                }
                failure {
                    test.postFailure() 
                }
                
            }
        }
    }
    post{
        always{
            general.sendEmail()
        }
        success{
            echo "========pipeline executed successfully ========"
            general.pipelineSuccess()
        }
        failure{
            echo "========pipeline execution failed========"
            general.pipelineFailure()
        }
    }
}
