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
        stage("Maven Build Status") {
            steps{
                echo "=========Executing Sonar Analysis========="
                sh 'mvn clean install > build.log'  
            }
            post{
                success {
                    script {
                        def buildlog= readFile('build.log')
                        slackSend channel: "#ci_info,walid.elbir", message: "Maven Build Successful: \n\n``` ${buildlog}```"
                    }
                }
                failure {
                    script {
                        def buildlog= readFile('build.log')
                        slackSend channel: "#ci_info,walid.elbir", message: "Maven Build Failed: \n\n``` ${buildlog}```"
                    }
                }
            }
        }

        stage("SonarQube analysis") {
            steps{
                withSonarQubeEnv('local_sonarQube_server'){
                    sh 'mvn sonar:sonar > sonar.log'
                }
            }
            post {
                always {
                    script {
                        timeout(time: 1, unit: 'HOURS') {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                slackSend channel: "#ci_info,walid.elbir", message: "Pipeline aborted due to quality gate failure: ${qg.status}"
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                                } 
                            if(qg.status == "OK"){
                                slackSend channel: "#ci_info,walid.elbir", message: "Quality Gate Passed: ${qg.status}"
                            }
                            }
                    }
                }
                success {
                    script {
                        def sonarlog= readFile('sonar.log');
                        slackSend channel: "#ci_info,walid.elbir", message: "Sonar Analysis Successful: \n\n ``` ${sonarlog}```"
                    }
                }
                failure {
                    script {
                        def sonarlog= readFile('sonar.log');
                        slackSend channel: "#ci_info,walid.elbir", message: "Sonar Analysis Failed: \n\n ``` ${sonarlog}```"
                    }

                }
            }
        }

        stage("Unit Testing") {
            steps{
                sh 'mvn test > test.log'
            }
            post {
                success {
                    script {
                        def sonarlog= readFile('sonar.log');
                        slackSend channel: "#ci_info,walid.elbir", message: "Unit Testing Successful: \n\n ``` ${test.log} ```"
                    } 
                }
                failure {
                    success {
                    script {
                        def sonarlog= readFile('sonar.log');
                        slackSend channel: "#ci_info,walid.elbir", message: "Unit Testing Failed: \n\n ``` ${test.log} ```"
                    } 
                }
                }
            }
        }
    }
    post{
        always{
            emailext(
                subject: '$DEFAULT_SUBJECT',
                body: '$DEFAULT_CONTENT',
                to: 'walid.el.biir@gmail.com',    
            )
        }
        success{
            echo "========pipeline executed successfully ========"
            slackSend channel: "#ci_info", message: "Build Successful: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
        failure{
            echo "========pipeline execution failed========"
            slackSend channel: "#ci_info", message: "Build Failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}", color: "danger"
        }
    }
}
