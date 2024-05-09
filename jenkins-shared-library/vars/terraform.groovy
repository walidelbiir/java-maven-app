def call() {
    sh "cd terraform"
    sh "pwd"
    sh "chmod +r main.tf"
    sh "terraform init"
    sh "terraform plan"
    sh "terraform apply -auto-approve"
}

return this