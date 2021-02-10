package org.api.exception

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Produces
@Singleton
class CategoryNotFoundExceptionHandler : ExceptionHandler<CategoryNotFoundException, HttpResponse<Error>> {
    override fun handle(request: HttpRequest<*>?, exception: CategoryNotFoundException): HttpResponse<Error> {
        val error = Error(exception.message, ErrorCode.NOT_FOUND.code)
        return HttpResponse.notFound(error)
    }
}