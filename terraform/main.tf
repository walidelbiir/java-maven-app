terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

variable "BUILD_NUMBER" {
  description = "The build number for the current deployment"
  type        = string
  default     = "94" # Default value, can be overridden by the -var flag
}


# Create a container
resource "docker_container" "java_maven_app" {
  image = "walidelbir/java_maven_app:${var.BUILD_NUMBER}"
  name  = "java_maven_app"
}