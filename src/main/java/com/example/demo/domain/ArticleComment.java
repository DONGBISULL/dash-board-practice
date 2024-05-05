package com.example.demo.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    @Setter
    @ManyToOne(optional = false)
    private Article article; // 게시글

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 내용

    public ArticleComment() {
    }
    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    //    factory 매서드 사용하는 이유
    //    객체의 생성 서브 클래스에 위임 / 개게 인스턴스화 방법 캡슐화하는데 사용
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}