def call() {
    script {
        echo "=========Building with Maven========="
        sh "mvn clean install > build-${env.BUILD_NUMBER}.log"  
    }
}

def postSuccess() {
    script{
        def buildlog= readFile("build-${env.BUILD_NUMBER}.log")
        slackUploadFile filePath: "build-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the build-${env.BUILD_NUMBER}.log"
        slackSend channel: "#ci_info,walid.elbir", message: "Maven Build Successful", color: "green"}
}

def postFailure() {
    script {
        def buildlog= readFile("build-${env.BUILD_NUMBER}.log")
        slackUploadFile filePath: "build-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the build-${env.BUILD_NUMBER}.log"
        slackSend channel: "#ci_info,walid.elbir", message: "Maven Build Failed", color: "danger"
    }
}

return this
