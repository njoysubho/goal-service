package org.api.service.config


import com.project.config.TestPostgress
import io.micronaut.configuration.jdbc.tomcat.DatasourceConfiguration
import io.micronaut.configuration.jdbc.tomcat.DatasourceFactory
import io.micronaut.context.annotation.*
import io.micronaut.jdbc.DataSourceResolver
import javax.inject.Singleton
import javax.sql.DataSource

@Factory
@Replaces(factory = DatasourceFactory::class)
@Requires(env = ["db-test"])
class SharedTestDatasourceFactory(dataSourceResolver: DataSourceResolver) : DatasourceFactory(dataSourceResolver) {
    @Context
    @EachBean(DatasourceConfiguration::class)
    @Requires(beans = [TestPostgress::class])
    override fun dataSource(configuration: DatasourceConfiguration): DataSource {
        val managedPostgress = testPostgres()
        val dataSource = org.apache.tomcat.jdbc.pool.DataSource()
        dataSource.url = managedPostgress.psql.jdbcUrl
        dataSource.username = managedPostgress.psql.username
        dataSource.password = managedPostgress.psql.password
        dataSource.driverClassName = "org.postgresql.Driver"
        return dataSource
    }

    @Singleton
    fun testPostgres(): TestPostgress {
        val managedPostgress = TestPostgress()
        managedPostgress.psql.start()
        return managedPostgress
    }
}
