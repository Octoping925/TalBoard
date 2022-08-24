package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }
}
