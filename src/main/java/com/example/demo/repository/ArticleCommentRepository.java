package com.example.demo.repository;

import com.example.demo.domain.ArticleComment;
import com.example.demo.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository
        extends JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment> // 동적 쿼리를 간단하게 생성 가능

{

    @Override
    default  void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content , root.createdAt , root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%{v}%
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like '%{v}%
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%{v}%
    }
}