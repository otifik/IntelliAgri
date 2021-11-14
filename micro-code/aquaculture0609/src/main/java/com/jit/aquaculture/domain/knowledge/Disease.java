package com.jit.aquaculture.domain.knowledge;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("disease")
public class Disease {
    private Integer id;
    private String big_category;
    private String small_category;
    private String diseaseName;
    private String symptom;
    private String treatment;
    private String image;
    private Date publishTime;
    private String source;
}
