def call() {
    docker build -t "java_maven_app:latest" .
}

def postSuccess() {
    slackSend channel: "#ci_info", message: "Docker Build Successfull", color: "good"
}

def postFailure() {
    slackSend channel: "#ci_info", message: "Docker Build Failed", color: "danger"
}