package org.yeffrey.cheesekake.api.usecase.activities

import org.yeffrey.cheesekake.api.usecase.UseCaseRequest
import org.yeffrey.cheesekake.domain.ValidationError

interface UpdateActivitySummary {
    suspend fun update(request: Request, presenter: Presenter)
    data class Request(val activityId: Int, val title: String, val summary: String): UseCaseRequest() {
        override fun allow(/*authorId: Int*/) : Boolean {
            return this.userId.nonEmpty() // Should I get the author as a parameter ???
        }
    }
    interface Presenter {
        suspend fun validationFailed(errors: List<ValidationError>)
        suspend fun success(id: Int)
    }
}