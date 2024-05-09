def call() {
    sh "terraform init"
    sh "terraform plan -var='BUILD_NUMBER=${env.BUILD_NUMBER}"
    sh "terraform apply -auto-approve"
}

return this