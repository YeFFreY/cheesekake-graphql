package org.yeffrey.cheesekake.web.activities

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import org.yeffrey.cheesekake.api.usecase.activities.QueryActivities
import org.yeffrey.cheesekake.web.WebResource

class QueryActivitiesPresenter(private val call: ApplicationCall) : QueryActivities.Presenter {
    override suspend fun notFound(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun accessDenied() {
        call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "access denied"))
    }

    override suspend fun success(activities: List<QueryActivities.Presenter.Activity>) {
        val webResources = activities.map { activity ->
            WebResource(activity, ActivitiesRoutes.hrefs(activity, call))
        }
        call.respond(webResources)
    }
}