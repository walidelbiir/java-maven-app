def sendEmail(String email="walid.el.biir@gmail.com") {
    emailext(
        subject: '$DEFAULT_SUBJECT',
        body: '$DEFAULT_CONTENT',
        to: "${email}",    
    )
}

def pipelineSuccess() {
    slackSend channel: "#ci_info,@walid.elbir", message: "Build Successful: ${env.JOB_NAME} ${env.BUILD_NUMBER}", color: "green"
}

def pipelineFailure() {
    slackSend channel: "#ci_info,@walid.elbir", message: "Build Failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}", color: "danger"
}

return this