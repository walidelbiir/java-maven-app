def call() {
    sh "mvn test > test-${env.BUILD_NUMBER}.log >" 
}

def postSuccess() {
    def testlog= readFile("test-${env.BUILD_NUMBER}.log");
    slackUploadFile filePath: "test-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the test-${env.BUILD_NUMBER}.log"
    slackSend channel: "#ci_info", message: "Unit Testing Successful", color: "green"
}

def postFailure() {
    def testlog= readFile("test-${env.BUILD_NUMBER}.log");
    slackUploadFile filePath: "test-${env.BUILD_NUMBER}.log", channel: '#ci_info', initialComment: "Here is the test-${env.BUILD_NUMBER}.log"
    slackSend channel: "#ci_info", message: "Unit Testing Failed", color: "danger"
}

return this