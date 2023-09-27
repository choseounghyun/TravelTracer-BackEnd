package com.project.travelTracer.location.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckPoint is a Querydsl query type for CheckPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckPoint extends EntityPathBase<CheckPoint> {

    private static final long serialVersionUID = 493090047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckPoint checkPoint = new QCheckPoint("checkPoint");

    public final com.project.travelTracer.global.time.QBaseTimeEntity _super = new com.project.travelTracer.global.time.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Integer> locationId = createNumber("locationId", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final com.project.travelTracer.member.entity.QMember member;

    public final EnumPath<com.project.travelTracer.member.entity.Role> role = createEnum("role", com.project.travelTracer.member.entity.Role.class);

    public QCheckPoint(String variable) {
        this(CheckPoint.class, forVariable(variable), INITS);
    }

    public QCheckPoint(Path<? extends CheckPoint> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckPoint(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckPoint(PathMetadata metadata, PathInits inits) {
        this(CheckPoint.class, metadata, inits);
    }

    public QCheckPoint(Class<? extends CheckPoint> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.travelTracer.member.entity.QMember(forProperty("member")) : null;
    }

}

