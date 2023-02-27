package com.gozip.repository.custom;

import com.gozip.dto.PostRequestDto;
import com.gozip.entity.Post;
import com.gozip.entity.QPost;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.gozip.entity.QPost.*;

public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostRequestDto> searchAllPosts(String city, String town, String street) {
        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(
                        cityEq(city),
                        townEq(town),
                        streetEq(street)
                ).fetch();
        List<PostRequestDto> results = new ArrayList<>();
        for (Post post : posts) {
            results.add(new PostRequestDto(post));
        }
        return results;
    }

    private Predicate cityEq(String city) {
        return city.length() == 0 ? null : post.address.city.eq(city);
    }

    private Predicate townEq(String town) {
        return town.length() == 0 ? null : post.address.town.eq(town);
    }

    private Predicate streetEq(String street) {
        return street.length() == 0 ? null : post.address.street.eq(street);
    }
}

/**
 *  조건부에 맞는 포스트를 가져와야 함.
 *  picture랑 일대다 관계 => 페치조인시 페이징 불가 => 배치를 이용해서 가져오기
 *  selectFrom post
 *  where city, town, street
 *
 */