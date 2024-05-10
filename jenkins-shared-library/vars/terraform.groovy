def init() {
    sh "terraform init"
}

def plan() {
    sh "terraform plan -var='BUILD_NUMBER=${env.BUILD_NUMBER}'"
}

def apply() {
    sh "terraform apply -auto-approve"
}

def destroy () {
    sh "terraform destroy"
}

return this