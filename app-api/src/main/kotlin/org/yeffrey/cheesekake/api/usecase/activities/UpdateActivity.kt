package org.yeffrey.cheesekake.api.usecase.activities

import org.yeffrey.cheesekake.api.usecase.UseCase
import org.yeffrey.cheesekake.api.usecase.UseCasePresenter
import org.yeffrey.cheesekake.api.usecase.UseCaseRequest
import org.yeffrey.core.error.ErrorDescription


interface UpdateActivity : UseCase<UpdateActivity.Request, UpdateActivity.Presenter> {
    data class Request(val activityId: Int, val title: String, val summary: String) : UseCaseRequest()
    interface Presenter : UseCasePresenter {
        suspend fun validationFailed(errors: List<ErrorDescription>)
        suspend fun success(id: Int)
    }
}