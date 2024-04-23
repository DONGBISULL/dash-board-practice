package com.example.demo.repository;

import com.example.demo.config.JpaConfig;
import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // jpa 5부터 테스트에서도 생성자 주입 패턴 사용 가능 @ExtendWith(SpringExtension.class)
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
class JpaRepositoryTest {
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public JpaRepositoryTest(ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
//      given

//      when
        List<Article> articles = articleRepository.findAll();

//      then
        assertThat(articles).isNotNull().hasSize(0);

    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInsert_thenWorksFine() {
//      given
        long prevCount = articleRepository.count();

        Article article1 = Article.of("new Title", "asdjflkjsdlfjl ", "#dog");
//      when
        articleRepository.save(article1);

//      then
        assertThat(articleRepository.findAll()).isNotNull().hasSize((int) (prevCount + 1));
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdate_thenWorksFine() {
//      given 준비
        Article article1 = Article.of("new Title", "new Content", "#dog");
        articleRepository.saveAndFlush(article1);

        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashTag = "saveArticleHashTage";
        article.setHashTag(updatedHashTag);

//      when 실행
        Article saved = articleRepository.saveAndFlush(article);
//      then 검증
        assertThat(saved).hasFieldOrPropertyWithValue("hashTag", updatedHashTag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDelete_thenWorksFine() {
//      given 준비
        Article article = Article.of("new Title", "new Content", "#dog");
        Article saved = articleRepository.saveAndFlush(article);
        System.out.println("============");
        System.out.println(article);
        ArticleComment comment1 = ArticleComment.of(saved, "Article contents : 1 ");
        ArticleComment comment2 = ArticleComment.of(saved, "Article contents : 2 ");
        articleCommentRepository.saveAll(Arrays.asList(comment1, comment2));

        article.getArticleComments().add(comment1);
        article.getArticleComments().add(comment2);
        articleRepository.saveAndFlush(article);

        long prevArticleCount = articleRepository.count();
        long prevArticleCommentCount = articleCommentRepository.count();
        long deleteCommentsSize = article.getArticleComments().size();

//      when 실행
        Article finded = articleRepository.findById(1L).orElseThrow();
        articleRepository.delete(finded);

//      then 검증
//        long afterCount = articleRepository.count();
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleCommentCount - deleteCommentsSize);
    }


}