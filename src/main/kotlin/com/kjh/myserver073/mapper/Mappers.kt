package com.kjh.myserver073.mapper

import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.model.PlaceWithRankingModel
import com.kjh.myserver073.model.model.toModel
import com.kjh.myserver073.model.vo.UserAndPostsAndBookmarks


object Mappers {

    fun makeUserAndPostsAndBookmarks(
        userEntity : User
    ): UserAndPostsAndBookmarks =
        userEntity.toModel().run {
            UserAndPostsAndBookmarks(
                userId          = this.userId,
                email           = this.email,
                nickName        = this.nickName,
                introduce       = this.introduce,
                followingCount  = this.followingCount,
                followCount     = this.followCount,
                postCount       = this.postCount,
                profileImg      = this.profileImg,
                followList      = this.followList,
                followingList   = this.followingList,
                isFollowing     = this.isFollowing,
                posts           = this.posts.map { post -> post.copy(
                    isBookmarked = bookmarks.find { it.placeName == post.placeName } != null
                ) }.reversed(),
                bookMarks       = this.bookmarks
            )
        }

    fun makePlaceWithRanking(
        placeEntities: List<Place>
    ): List<PlaceWithRankingModel> =
        placeEntities.mapIndexed { index, place ->
            PlaceWithRankingModel(
                rank  = index + 1,
                place = place.toModel()
            )
        }
}