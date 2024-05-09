def call() {
    sh "terraform init"
    sh "terraform plan -var-file='variables.tfvars'"
    sh "terraform apply -auto-approve"
}

return this