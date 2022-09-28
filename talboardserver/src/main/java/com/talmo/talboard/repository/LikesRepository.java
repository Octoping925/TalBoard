package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Likes;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikesRepository {
    private final EntityManager em;

    public void save(Likes likes) {
        em.persist(likes);
    }

    public Likes findOne(Long likeId) {
        return em.find(Likes.class, likeId);
    }

}
