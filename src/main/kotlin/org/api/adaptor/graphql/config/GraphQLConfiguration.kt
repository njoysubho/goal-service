package org.api.adaptor.graphql.config

import graphql.GraphQL
import graphql.kickstart.tools.SchemaParser
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.api.adaptor.graphql.resolver.GoalQueryResolver
import org.api.adaptor.graphql.resolver.QueryResolver
import javax.inject.Singleton

@Factory
class GraphQLConfiguration {
    @Bean
    @Singleton
    fun graphQL(queryResolver: QueryResolver,goalQueryResolver: GoalQueryResolver): GraphQL {

        val schemaParserBuilder = SchemaParser.newParser()
            .file("graphql/schema/goal-schema.graphqls")
            .resolvers(queryResolver,goalQueryResolver)

        // Create the executable schema.
        val graphQLSchema = schemaParserBuilder.build().makeExecutableSchema()

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build()
    }
}