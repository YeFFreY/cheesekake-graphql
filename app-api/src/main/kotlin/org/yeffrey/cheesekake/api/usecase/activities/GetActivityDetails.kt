package org.yeffrey.cheesekake.api.usecase.activities

import org.yeffrey.cheesekake.api.usecase.UseCase
import org.yeffrey.cheesekake.api.usecase.UseCasePresenter
import org.yeffrey.cheesekake.api.usecase.UseCaseRequest

interface GetActivityDetails : UseCase<GetActivityDetails.Request, GetActivityDetails.Presenter> {
    data class Request(val id: Int) : UseCaseRequest()
    interface Presenter : UseCasePresenter {
        data class ActivityDetails(val id: Int, val title: String, val summary: String, val resources: List<Int>, val skills: List<Int>)

        suspend fun success(activity: ActivityDetails)
    }
}