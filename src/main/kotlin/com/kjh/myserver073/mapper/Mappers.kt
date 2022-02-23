package com.kjh.myserver073.mapper

import com.kjh.myserver073.model.PlaceVo
import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.vo.PostVo
import com.kjh.myserver073.model.vo.RankingVo
import com.kjh.myserver073.model.vo.UserVo

object Mappers {

    fun makeUserVoWithPosts(
        userItem     : User,
        postItems    : List<Post>,
        bookmarkItems: List<Post>
    ): UserVo {

        val convertedPosts = postListToPostVoList(postItems).map { post ->
            post.copy(isBookmarked = bookmarkItems.find { it.place.placeName == post.placeName} != null)
        }

        val convertedBookmarks = postListToPostVoList(bookmarkItems).map { post ->
            post.copy(isBookmarked = true)
        }

        return UserVo(
            userId          = userItem.userId!!,
            email           = userItem.email,
            nickName        = userItem.nickName,
            introduce       = userItem.introduce,
            followingCount  = userItem.followingCount,
            followCount     = userItem.followCount,
            postCount       = userItem.postCount,
            profileImg      = userItem.profileImg,
            followList      = userItem.followList,
            followingList   = userItem.followingList,
            posts       = convertedPosts,
            bookMarks   = convertedBookmarks,
            isFollowing = userItem.isFollowing
        )
    }

    fun postToPostVo(postEntity: Post) = PostVo(
        postId      = postEntity.postId!!,
        content     = postEntity.content,
        createdDate = postEntity.createdDate,
        imageUrl    = postEntity.imageUrl,
        profileImg  = postEntity.user.profileImg,
        email       = postEntity.user.email,
        nickName    = postEntity.user.nickName,
        cityName    = postEntity.place.cityName,
        subCityName = postEntity.place.subCityName,
        placeName   = postEntity.place.placeName,
        placeAddress    = postEntity.place.placeAddress,
        placeRoadAddress= postEntity.place.placeRoadAddress,
        x               = postEntity.place.x,
        y               = postEntity.place.y,
    )

    fun postListToPostVoList(postEntities: List<Post>): List<PostVo> {
        return postEntities.map { postEntity ->
            postToPostVo(postEntity)
        }
    }

    fun placeToPlaceVoWithPosts(placeEntity: Place, postEntities: List<Post>): PlaceVo {
        return PlaceVo(
            placeId = placeEntity.placeId,
            cityName = placeEntity.cityName,
            subCityName = placeEntity.subCityName,
            placeName = placeEntity.placeName,
            placeAddress = placeEntity.placeAddress,
            placeRoadAddress = placeEntity.placeRoadAddress,
            x = placeEntity.x,
            y = placeEntity.y,
            placeImg = placeEntity.placeImg,
            posts = postListToPostVoList(postEntities)
        )
    }

    fun placeToRankingVo(placeEntities: List<Place>): List<RankingVo> {
        return placeEntities.mapIndexed { index, place ->
            RankingVo(
                rank = index + 1,
                place = PlaceVo(
                    placeId = place.placeId,
                    cityName = place.cityName,
                    subCityName = place.subCityName,
                    placeName = place.placeName,
                    placeAddress = place.placeAddress,
                    placeRoadAddress = place.placeRoadAddress,
                    x = place.x,
                    y = place.y,
                    placeImg = place.placeImg
                )
            )
        }
    }
}