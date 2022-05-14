package org.spring.notice.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);

    Page<Post> findAll(Pageable pageable);

    // 테스트 수행 시 sequence reset 용 쿼리
    @Query(value="ALTER SEQUENCE hibernate_sequence RESTART WITH 1", nativeQuery = true)
    @Modifying
    void resetSequence();
}
