def call() {
    sh "terraform init"
    sh "terraform plan"
    sh "terraform apply -auto-approve"
}

return this