package com.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    private String fileName;
    private String uploadUrl;
    private String uuid;
    private boolean image;


    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    private WebBoard board;
}
