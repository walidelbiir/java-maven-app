def call() {
    sh "docker build -t 'java_maven_app:${env.BUILD_NUMBER}' ."
}

def login() {
    sh "docker login -u walidelbir -p walidelbir"
}

def push_to_dockerhub() {
    sh "docker tag java_maven_app:${env.BUILD_NUMBER} walidelbir/java_maven_app:${env.BUILD_NUMBER}"
    sh "docker push walidelbir/java_maven_app:${env.BUILD_NUMBER}"
}

def imageDestroy() {
    sh "docker rmi  walidelbir/java_maven_app:${env.BUILD_NUMBER}"
    sh "docker rmi  java_maven_app:${env.BUILD_NUMBER}"
}

def postSuccess() {
    slackSend channel: "#ci_info", message: "Docker Build Successfull", color: "good"
}

def postFailure() {
    slackSend channel: "#ci_info", message: "Docker Build Failed", color: "danger"
}

return this