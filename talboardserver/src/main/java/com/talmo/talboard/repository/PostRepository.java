package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p WHERE p.delete_yn = 'N'")
                .getResultList();
    }
}
