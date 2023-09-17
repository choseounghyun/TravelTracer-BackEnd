package com.project.travelTracer.location.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCheckPoint is a Querydsl query type for CheckPoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckPoint extends EntityPathBase<CheckPoint> {

    private static final long serialVersionUID = 493090047L;

    public static final QCheckPoint checkPoint = new QCheckPoint("checkPoint");

    public final DateTimePath<java.time.LocalDateTime> createtime = createDateTime("createtime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Integer> locationId = createNumber("locationId", Integer.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public QCheckPoint(String variable) {
        super(CheckPoint.class, forVariable(variable));
    }

    public QCheckPoint(Path<? extends CheckPoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCheckPoint(PathMetadata metadata) {
        super(CheckPoint.class, metadata);
    }

}

