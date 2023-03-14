package com.example.hack_todolist.Retrofit

// Retrofit interface

import com.example.hack_todolist.Models.Event
import com.example.hack_todolist.Models.EventList
import com.example.hack_todolist.Models.FakeUser
import com.example.hack_todolist.Models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface EventsApi {
    @GET("user/{user_id}/calendar")
    suspend fun getEvents(@Path("user_id") userId: Int): Response<EventList>

    @GET("/user/{user_id}/profile")
    suspend fun getUser(@Path("user_id") userId: Int): Response<FakeUser>

    @GET("/user/{user_id}/event/<event_id>")
    suspend fun getUser(@Path("userId") userId: Int,@Path("event_id") event_id: Int): Response<FakeUser>

    @POST("/login/")
    suspend fun login(@Query("username") username: String,@Query("password") password: String): Response<Int>


    @POST("/user/{user_id}/event/add")
    suspend fun addEvent(@Query("user_id") user_id: Int,@Body enter: Event): Response<String>
}

