package com.jit.aquaculture.domain.knowledge;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("answer")
public class Answer {
    private Integer id;
    private Integer questionId;
    private String content;
    private String username;
    private Date publishTime;
}
