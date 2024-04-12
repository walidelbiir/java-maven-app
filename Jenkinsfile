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
                            if (qg.status != 'OK') {
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
            emailext(
                subject: '$DEFAULT_SUBJECT',
                body: '$DEFAULT_CONTENT',
                to: 'walid.el.biir@gmail.com',    
                presendScript: '''msg.addHeader("Authorization", "Bearer mlsn.4f30c464c1acfd5b6e31b25d51d5ab1d182da65fc42a6b0bb4e68b1b9471b125")'''
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
