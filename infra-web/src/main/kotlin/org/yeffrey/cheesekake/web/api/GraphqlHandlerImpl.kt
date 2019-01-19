package org.yeffrey.cheesekake.web.api

import graphql.ExecutionInput.newExecutionInput
import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.yeffrey.cheesekake.api.usecase.activities.QueryMyActivities
import org.yeffrey.cheesekake.web.GraphqlHandler
import org.yeffrey.cheesekake.web.GraphqlRequest
import org.yeffrey.cheesekake.web.api.activities.activityMutations
import org.yeffrey.cheesekake.web.api.activities.activityQueries
import org.yeffrey.cheesekake.web.api.activities.activityType
import org.yeffrey.cheesekake.web.api.activities.skillsByActivityLoader
import org.yeffrey.cheesekake.web.api.skills.skillQueries
import org.yeffrey.cheesekake.web.routes
import java.io.File

class GraphqlHandlerImpl(private val queryMyActivities: QueryMyActivities) : GraphqlHandler {
    private var graphql: GraphQL

    init {
        val schemaParser = SchemaParser()
        val typeDefinitionRegistry = schemaParser.parse(File(ClassLoader.getSystemResource("schema.graphqls").file))
        val runtimeWiring = newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .routes {
                            activityQueries(queryMyActivities)
                            skillQueries()
                        }
                )
                .type(TypeRuntimeWiring.newTypeWiring("Activity")
                        .routes {
                            activityType()
                        }
                )
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .routes {
                            activityMutations()
                        }
                )
                .build()
        val schemaGenerator = SchemaGenerator()
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)
        graphql = GraphQL.newGraphQL(graphQLSchema).build()
    }

    override fun invoke(request: GraphqlRequest): MutableMap<String, Any> {
        val skillDataLoader = DataLoader.newDataLoader(skillsByActivityLoader())
        val registry = DataLoaderRegistry()
        registry.register("skill", skillDataLoader)
        val executionInput = newExecutionInput()
                .query(request.query)
                .dataLoaderRegistry(registry)
                .operationName(request.operationName)
                .variables(request.variables)
        val executionResult = graphql.execute(executionInput.build())
        return executionResult.toSpecification()
    }
}