package org.example

import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import com.microsoft.azure.functions.HttpMethod
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.HttpRequestMessage
import com.microsoft.azure.functions.ExecutionContext
import com.microsoft.azure.functions.HttpResponseMessage
import com.microsoft.azure.functions.HttpStatus
import java.util.*

/**
 * Azure Functions with HTTP Trigger.
 */
class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    fun run(
        @HttpTrigger(
            name = "req",
            methods = [HttpMethod.GET, HttpMethod.POST],
            authLevel = AuthorizationLevel.ANONYMOUS
        ) request: HttpRequestMessage<Optional<String?>>,
        context: ExecutionContext
    ): HttpResponseMessage {
        context.logger.info("Kotlin HTTP trigger processed a request.")

        // Parse query parameter
        val query = request.queryParameters["name"]
        val name = request.body.orElse(query)
        return if (name == null) {
            request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Please pass a name on the query string or in the request body").build()
        } else {
            request.createResponseBuilder(HttpStatus.OK).body("Hello, $name. I am a Kotlin Translator").build()
        }
    }
}