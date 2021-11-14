package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.knowledge.Answer;
import com.jit.aquaculture.dto.AnswerDto;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("answerMapper")
public interface AnswerMapper extends BaseMapper<Answer> {


    @Select("select * from answer where id=#{id}")
    Answer getOne(@Param("id") Integer id);

    @Select("select * from answer where question_id=#{question_id}")
    List<Answer> getAllAnswers(@Param("question_id") Integer question_id);

    @Select("SELECT a.*,u.image AS userImage, u.id AS userId FROM answer a LEFT JOIN user u ON a.`username` = u.`username` WHERE a.question_id=#{question_id}")
    List<AnswerDto> getAnswers(@Param("question_id") Integer question_id);


}
