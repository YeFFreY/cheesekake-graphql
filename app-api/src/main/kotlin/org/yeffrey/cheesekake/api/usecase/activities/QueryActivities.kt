package org.yeffrey.cheesekake.api.usecase.activities

import org.yeffrey.cheesekake.api.usecase.Resource
import org.yeffrey.cheesekake.api.usecase.UseCase
import org.yeffrey.cheesekake.api.usecase.UseCasePresenter
import org.yeffrey.cheesekake.api.usecase.UseCaseRequest

interface QueryActivities : UseCase<QueryActivities.Request, QueryActivities.Presenter> {
    data class Request(val titleContains: String?): UseCaseRequest()
    interface Presenter : UseCasePresenter {
        data class Activity(override val id: Int, val title: String, val summary: String, override val actions: List<Resource.Action>) : Resource
        suspend fun success(activities: List<Activity>)
    }
}