package com.jit.aquaculture.domain.knowledge;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("question")
@Data
public class Question {
    private Integer id;
    private String username;
    private Integer type; //状态，默认0未回复，
    private String description;
    private String image;
    private Date publishTime;
}
