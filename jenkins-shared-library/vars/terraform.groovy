def call() {
    sh "cd terraform"
    sh "/var/jenkins_home/tools/org.jenkinsci.plugins.terraform.TerraformInstallation/terraform init"
    sh "/var/jenkins_home/tools/org.jenkinsci.plugins.terraform.TerraformInstallation/terraform plan"
    sh "/var/jenkins_home/tools/org.jenkinsci.plugins.terraform.TerraformInstallation/terraform apply -auto-approve"
}

return this