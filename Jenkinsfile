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
                script {
                    build.call()
                }
            }
            post{
                success {
                    script {
                        build.postSuccess()
                    }
                }
                failure {
                    script {
                        build.postFailure()
                    }
                }
            }
        }

        stage("SonarQube analysis") {
            steps{
                echo "=========Analysis with SonarQube========="
                script {
                    sonar.call()
                }
            }
            post {
                always {
                    script {
                        sonar.waitForAnalysisReport()
                    }
                }
                success {
                    script {
                        sonar.postSuccess()
                    }
                }
                failure {
                    script {
                        sonar.postFailure()
                    }
                }
            }
        }

        stage("Unit Testing") {
            steps{
                script {
                    test.call()
                }
            }
            post {
                success {
                    script {
                        test.postSuccess()
                    }
                }
                failure {
                    script{
                        test.postFailure() 
                    }
                }
                
            }
        }
    }
    post{
        always{
            script{
                general.sendEmail()
            }
        }
        success{
            script {
                echo "========pipeline executed successfully ========"
                general.pipelineSuccess()
            }
        }
        failure{
            script {
                echo "========pipeline execution failed========"
                general.pipelineFailure()
            }
        }
    }
}
