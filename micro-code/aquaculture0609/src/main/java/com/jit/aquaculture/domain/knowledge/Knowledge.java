package com.jit.aquaculture.domain.knowledge;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("knowledge")
public class Knowledge {
    private Integer id;
    private String name;
    private String content;
    private String image;
    private String source;
    private Date publish_time;

}
