package com.example.wikiapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.wikiapp.*
import com.example.wikiapp.domain.IWikiRepository
import javax.inject.Inject

class WikiRepository @Inject constructor() : IWikiRepository {

    @Inject
    lateinit var apolloClient: ApolloClient

    override suspend fun getPageList(): ApolloResponse<PagesListQuery.Data> {
        return apolloClient.query(PagesListQuery()).execute()
    }

    override suspend fun getPageListByCreator(id: Int): ApolloResponse<PagesListByCreatorQuery.Data> {
        return apolloClient.query(PagesListByCreatorQuery(id)).execute()
    }

    override suspend fun getSinglePage(id: Int): ApolloResponse<SinglePageQuery.Data> {
        return apolloClient.query(SinglePageQuery(id)).execute()
    }

    override suspend fun getPageTree(id: Int): ApolloResponse<PagesTreeQuery.Data> {
        return apolloClient.query(PagesTreeQuery(id)).execute()
    }

    override suspend fun getUsersProfile(): ApolloResponse<UsersProfileQuery.Data> {
        return apolloClient.query(UsersProfileQuery()).execute()
    }

    override suspend fun getCommentsList(path: String): ApolloResponse<CommentsListQuery.Data> {
        return apolloClient.query(CommentsListQuery(path)).execute()
    }

    override suspend fun searchPage(query: String): ApolloResponse<SearchPageQuery.Data> {
        return apolloClient.query(SearchPageQuery(query)).execute()
    }

    override suspend fun authLogin(): ApolloResponse<AuthLoginMutation.Data> {
        return apolloClient.mutation(AuthLoginMutation()).execute()
    }

    override suspend fun commentCreate(
        pageId: Int,
        content: String,
        name: String?,
        email: String?
    ): ApolloResponse<CommentCreateMutation.Data> {
        return apolloClient.mutation(CommentCreateMutation(
            pageId, content, Optional.present(name), Optional.present(email)
        )).execute()
    }

    override suspend fun createNewPage(
        content: String,
        description: String,
        editor: String,
        isPublished: Boolean,
        isPrivate: Boolean,
        locale: String,
        path: String,
        tags: List<String>,
        title: String
    ): ApolloResponse<CreateNewPageMutation.Data> {
        return apolloClient.mutation(CreateNewPageMutation(
            content, description, editor, isPublished, isPrivate, locale, path,
            tags, title
        )).execute()
    }

    override suspend fun updateName(
        name: String
    ): ApolloResponse<UpdateNameMutation.Data> {
        return apolloClient.mutation(
            UpdateNameMutation(
                name
            )
        ).execute()
    }

    override suspend fun updateLocation(
        location: String
    ): ApolloResponse<UpdateLocationMutation.Data> {
        return apolloClient.mutation(
            UpdateLocationMutation(
                location
            )
        ).execute()
    }

    override suspend fun updateJob(
        job: String?
    ): ApolloResponse<UpdateJobMutation.Data> {
        return apolloClient.mutation(
            UpdateJobMutation(
                job.toString()
            )
        ).execute()
    }

    override suspend fun updateTimezone(
        timezone: String?
    ): ApolloResponse<UpdateTimezoneMutation.Data> {
        return apolloClient.mutation(
            UpdateTimezoneMutation(
                timezone.toString()
            )
        ).execute()
    }

    override suspend fun updateDateFormat(
        dateFormat: String?
    ): ApolloResponse<UpdateDateFormatMutation.Data> {
        return apolloClient.mutation(
            UpdateDateFormatMutation(
                dateFormat.toString()
            )
        ).execute()
    }

    override suspend fun updateAppearance(
        appearance: String?
    ): ApolloResponse<UpdateAppearanceMutation.Data> {
        return apolloClient.mutation(
            UpdateAppearanceMutation(
                appearance.toString()
            )
        ).execute()
    }
}