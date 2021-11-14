package com.jit.authority.responseResult.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@ApiModel(value = "WebProResult",description = "统一数据返回对象")
public class WebProResult<T> implements Serializable {

    @ApiModelProperty(value = "返回状态码")
    private int code;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "返回信息")
    private String message;
}
