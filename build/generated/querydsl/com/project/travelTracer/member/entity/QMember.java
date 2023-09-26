package com.project.travelTracer.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 252997910L;

    public static final QMember member = new QMember("member1");

    public final com.project.travelTracer.global.time.QBaseTimeEntity _super = new com.project.travelTracer.global.time.QBaseTimeEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<com.project.travelTracer.location.entity.CheckPoint, com.project.travelTracer.location.entity.QCheckPoint> checkpointList = this.<com.project.travelTracer.location.entity.CheckPoint, com.project.travelTracer.location.entity.QCheckPoint>createList("checkpointList", com.project.travelTracer.location.entity.CheckPoint.class, com.project.travelTracer.location.entity.QCheckPoint.class, PathInits.DIRECT2);

    public final ListPath<com.project.travelTracer.comment.entity.Comment, com.project.travelTracer.comment.entity.QComment> commentList = this.<com.project.travelTracer.comment.entity.Comment, com.project.travelTracer.comment.entity.QComment>createList("commentList", com.project.travelTracer.comment.entity.Comment.class, com.project.travelTracer.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<com.project.travelTracer.Post.entity.Post, com.project.travelTracer.Post.entity.QPost> postList = this.<com.project.travelTracer.Post.entity.Post, com.project.travelTracer.Post.entity.QPost>createList("postList", com.project.travelTracer.Post.entity.Post.class, com.project.travelTracer.Post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public final StringPath userPassword = createString("userPassword");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

