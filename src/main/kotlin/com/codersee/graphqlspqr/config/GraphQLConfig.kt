package com.codersee.graphqlspqr.config

import com.codersee.graphqlspqr.mutation.CompanyMutation
import com.codersee.graphqlspqr.mutation.EmployeeMutation
import com.codersee.graphqlspqr.query.CompanyQuery
import com.codersee.graphqlspqr.query.EmployeeQuery
import graphql.GraphQL
import graphql.analysis.MaxQueryComplexityInstrumentation
import graphql.analysis.MaxQueryDepthInstrumentation
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.schema.GraphQLSchema
import io.leangen.graphql.GraphQLSchemaGenerator
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfig {

    @Bean
    fun graphQL(
        companyQuery: CompanyQuery, employeeQuery: EmployeeQuery,
        companyMutation: CompanyMutation, employeeMutation: EmployeeMutation
    ): GraphQL {
        val schema = prepareGraphQLSchema(companyQuery, employeeQuery, companyMutation, employeeMutation)

        return GraphQL.newGraphQL(schema)
            .queryExecutionStrategy(AsyncExecutionStrategy())
            .instrumentation(
                ChainedInstrumentation(
                    listOf(
                        MaxQueryComplexityInstrumentation(100),
                        MaxQueryDepthInstrumentation(10)
                    )
                )
            )
            .build()
    }

    private fun prepareGraphQLSchema(
        companyQuery: CompanyQuery, employeeQuery: EmployeeQuery,
        companyMutation: CompanyMutation, employeeMutation: EmployeeMutation
    ): GraphQLSchema =
        GraphQLSchemaGenerator()
            .withResolverBuilders(
                AnnotatedResolverBuilder(),
                PublicResolverBuilder("com.codersee.graphqlspqr")
            )
            .withOperationsFromSingletons(companyQuery, employeeQuery, companyMutation, employeeMutation)
            .withValueMapperFactory(JacksonValueMapperFactory())
            .generate()
}