package pet.project.network

import pet.project.domain.TodoItemRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoItemService {
    @GET("list")
    suspend fun getServerResponse(
        @Header("Authorization") token: String
    ): ServerResponse

    @POST("list")
    suspend fun addItemToServer(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: TodoItemRequestDto
    ): ServerResponse

    @PUT("list/{id}")
    suspend fun updateItemOnServer(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body element: TodoItemRequestDto
    ): ServerResponse

    @PATCH("list")
    suspend fun updateListOnServer(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: TodoListRequestDto
    ): ServerResponse

    @DELETE("list/{id}")
    suspend fun deleteItemFromServer(
        @Header("Authorization") token: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): ServerResponse
}
