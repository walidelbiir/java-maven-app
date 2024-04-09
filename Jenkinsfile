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
                    sh git config --global user.name $USERNAME
                    sh git config --global user.password $PASSWORD
                }
            }
        }
        stage("Execute Sonar Analysis") {
            steps{
                echo "=========executing build with Maven========="
                sh 'mv clean install'
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