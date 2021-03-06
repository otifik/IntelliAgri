package com.jit.aquaculture.serviceimpl.knowledge;


import com.jit.aquaculture.config.UserInfoContext;
import com.jit.aquaculture.domain.knowledge.Answer;
import com.jit.aquaculture.mapper.knowledge.AnswerMapper;
import com.jit.aquaculture.mapper.knowledge.QuestionMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;


    /**
     * 新增回答
     *
     * @param content
     * @param question_id
     * @return
     */
    @Override
    public Answer insertAnswer(String content, Integer question_id) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = UserInfoContext.getUser().getUsername();
//        QuestionDto questionDto = questionMapper.getOne(question_id);
//        if (questionDto.getUsername().equals(username)){//自己的问题不能自己回答
//            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
//        }
        Answer answer = Answer.of();
        answer.setUsername(username);
        answer.setContent(content);
        answer.setQuestionId(question_id);
        answer.setPublishTime(new Date());

        int flag = answerMapper.insert(answer);
        if (flag > 0) {
            questionMapper.updateType(1, question_id);
            return answer;
        } else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 更新答案
     *
     * @param content
     * @param id
     * @return
     */
    @Override
    public Answer updateAnswer(String content, Integer id) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = UserInfoContext.getUser().getUsername();
        Answer answer = answerMapper.getOne(id);
        if (!answer.getUsername().equals(username)) {//只能修改自己的回答
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        answer.setContent(content);
        answer.setPublishTime(new Date());

        int flag = answerMapper.updateById(answer);
        if (flag > 0) {
            questionMapper.updateType(1, id);
            return answer;
        } else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 删除当前问题的答案
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteAnswer(Integer id) {
        Answer answer = answerMapper.getOne(id);
        if (answer == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        //只有回答者才能删除自己的答案
        String username = UserInfoContext.getUser().getUsername();
        if (!answer.getUsername().equals(username)) {
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        Integer question_id = answer.getQuestionId();

        int flag = answerMapper.deleteById(id);
        List<Answer> answerList = answerMapper.getAllAnswers(question_id);
        if (answerList == null) {
            questionMapper.updateType(0, question_id);
        }
        if (flag < 0) {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
        return true;
    }
}
