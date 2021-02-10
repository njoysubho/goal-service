package com.project.config

import org.testcontainers.containers.PostgreSQLContainer

class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName) {
}