package com.levelup.core.repository.post;



import com.levelup.core.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    Optional<List<Post>> findByMemberId(Long memberId);

}
