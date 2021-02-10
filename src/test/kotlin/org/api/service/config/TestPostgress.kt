package com.project.config;

import javax.inject.Singleton

@Singleton
class TestPostgress : AutoCloseable {

    var psql: KGenericContainer = KGenericContainer("postgres:10.10")
        .withDatabaseName("test-db")
        .withUsername("test")
        .withPassword("test")
        .withExposedPorts(5432)

    override fun close() {
        psql.close()
    }

}
