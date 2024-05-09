def call() {
    sh "docker build -t 'java_maven_app:${env.BUILD_NUMBER}' ."
}

def postSuccess() {
    slackSend channel: "#ci_info", message: "Docker Build Successfull", color: "good"
}

def postFailure() {
    slackSend channel: "#ci_info", message: "Docker Build Failed", color: "danger"
}

return this