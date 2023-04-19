package com.project.travelTracer.Post.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "img_table")
@NoArgsConstructor
public class ImgEntity {

    @Id @GeneratedValue
    @Column(name = "img_id")
    private Long id;


}
