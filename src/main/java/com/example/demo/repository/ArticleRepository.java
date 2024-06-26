package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,// QuerydslPredicateExecutor - 이 엔티티 안의 모든 필드에 대한 기본 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> //
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 리스팅하지 않은 항목 검색 대상 제외
        bindings.including(root.title, root.content, root.hashTag, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '%{v}
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%{v}%
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%{v}%
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like '%{v}%
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%{v}%
    }
}