package com.kiran.blog_app_rest.repository.impl;

import com.kiran.blog_app_rest.entity.QComment;
import com.kiran.blog_app_rest.entity.QPost;
import com.kiran.blog_app_rest.repository.BaseRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {



    EntityManager em;
    JPAQueryFactory jpaQueryFactory;


    protected final QPost post= QPost.post;
    protected final QComment comment = QComment.comment;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);

        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public T findByIdMandatory(ID id) throws IllegalArgumentException {
        return findById(id).orElseThrow(()-> new IllegalArgumentException("entity not found"+id));
    }
}
