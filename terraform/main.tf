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

# Pulls the image
resource "docker_image" "java_maven_app" {
  name = "walidelbir/java_maven_app:${env.BUILD_NUMBER}"
}

# Create a container
resource "docker_container" "java_maven_app" {
  image = docker_image.java_maven_app.image_id
  name  = "java_maven_app"
}