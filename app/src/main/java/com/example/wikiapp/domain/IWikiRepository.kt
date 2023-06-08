package com.example.wikiapp.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.example.wikiapp.*

interface IWikiRepository {

    suspend fun getPageList(): ApolloResponse<PagesListQuery.Data>

    suspend fun getPageListByCreator(id: Int): ApolloResponse<PagesListByCreatorQuery.Data>

    suspend fun getSinglePage(id: Int): ApolloResponse<SinglePageQuery.Data>

    suspend fun getPageTree(id: Int): ApolloResponse<PagesTreeQuery.Data>

    suspend fun getUsersProfile(): ApolloResponse<UsersProfileQuery.Data>

    suspend fun getCommentsList(path: String): ApolloResponse<CommentsListQuery.Data>

    suspend fun searchPage(query: String): ApolloResponse<SearchPageQuery.Data>

    suspend fun authLogin(): ApolloResponse<AuthLoginMutation.Data>

    suspend fun commentCreate(
        pageId: Int,
        content: String,
        name: String?,
        email: String?
    ): ApolloResponse<CommentCreateMutation.Data>

    suspend fun createNewPage(
        content: String,
        description: String,
        editor: String,
        isPublished: Boolean,
        isPrivate: Boolean,
        locale: String,
        path: String,
        tags: List<String>,
        title: String
    ): ApolloResponse<CreateNewPageMutation.Data>

    suspend fun updateName(
        name: String
    ): ApolloResponse<UpdateNameMutation.Data>

    suspend fun updateLocation(
        location: String
    ): ApolloResponse<UpdateLocationMutation.Data>

    suspend fun updateJob(
        job: String?
    ): ApolloResponse<UpdateJobMutation.Data>

    suspend fun updateTimezone(
        timezone: String?
    ): ApolloResponse<UpdateTimezoneMutation.Data>

    suspend fun updateDateFormat(
        dateFormat: String?
    ): ApolloResponse<UpdateDateFormatMutation.Data>

    suspend fun updateAppearance(
        appearance: String?
    ): ApolloResponse<UpdateAppearanceMutation.Data>
}