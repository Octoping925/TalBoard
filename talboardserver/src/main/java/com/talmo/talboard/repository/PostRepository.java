package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.PostRequirementVO;
import com.talmo.talboard.exception.NoPostFoundException;
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
        return em.createQuery("SELECT p FROM Post p WHERE p.deleteYn = 'N'")
                .getResultList();
    }

    public Post findOne(Long postNo) {
        Post post = em.find(Post.class, postNo);
        if(post == null){
            throw new NoPostFoundException();
        }

        return post;
    }

    public List<Post> searchById(String memberId) {
        return em.createQuery("SELECT p FROM Post p WHERE p.member.loginInfo.id like :memberId")
                    .setParameter("memberId", "%" + memberId + "%")
                    .getResultList();
    }

    public List<Post> searchByTitle(String title) {
        return em.createQuery("SELECT p FROM Post p WHERE p.title like :title")
                .setParameter("title", "%" + title +"%")
                .getResultList();
    }

}
