package com.soldoc.tech.domain.like.model;

import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Like {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User에 대한 외래키(N : 1)
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    // Post에 대한 외래키(N : M)
    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

}
