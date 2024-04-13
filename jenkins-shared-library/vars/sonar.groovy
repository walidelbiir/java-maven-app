def call() {
    withSonarQubeEnv('local_sonarQube_server'){
        sh "mvn sonar:sonar > sonar-${env.BUILD_NUMBER}.log"
    }
}

def postSuccess() {
    script {
        def sonarlog= readFile("sonar-${env.BUILD_NUMBER}.log");
        slackUploadFile filePath: "sonar-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the sonar-${env.BUILD_NUMBER}.log"
        slackSend channel: "#ci_info", message: "Sonar Analysis Successful", color: "green"
    }
}


def postFailure() {
    script {
        def sonarlog= readFile("sonar-${env.BUILD_NUMBER}.log");
        slackUploadFile filePath: "sonar-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the sonar-${env.BUILD_NUMBER}.log"
        slackSend channel: "#ci_info", message: "Sonar Analysis Failed", color: "danger"
    }
}

def waitForAnalysisReport() {
    script {
        timeout(time: 1, unit: 'HOURS') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                slackSend channel: "#ci_info", message: "Pipeline aborted due to quality gate failure: ${qg.status}", color: "danger"
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            } 
            if(qg.status == "OK"){
                slackSend channel: "#ci_info", message: "Quality Gate Passed: ${qg.status}", color: "green"
            }
        }
    }

}

return this