package com.jit.aquaculture.serviceimpl.knowledge;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.commons.util.ImageUtils;
import com.jit.aquaculture.config.UserInfoContext;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import com.jit.aquaculture.mapper.knowledge.KnowledgeMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Value("${image.knowledgeStore.path}")
    private String knowledgeStore_path;

    @Value("${image.knowledgeStore.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

//    @Autowired
//    private AuthRemoteClient authRemoteClient;

    /**
     * 插入百科知识
     *
     * @param image
     * @param name
     * @param content
     * @return
     * @throws IOException
     */
    @Override
    public Knowledge insertKnowledge(MultipartFile image, String name, String content) throws IOException {

        Knowledge isExist = knowledgeMapper.select(name);
        if (isExist != null) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        Knowledge knowledge = Knowledge.of();

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = UserInfoContext.getUser().getUsername();
        knowledge.setName(name);
        knowledge.setSource(username);
        knowledge.setContent(content);
        knowledge.setPublish_time(new Date());
        if (image != null) {
            String fileName = ImageUtils.ImgReceive(image, knowledgeStore_path);
            knowledge.setImage(url + image_url + fileName);
        }

        int flag = knowledgeMapper.insert(knowledge);
        if (flag > 0) {
            return knowledge;

        } else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG, null);
        }
    }

    /**
     * 根据id获取某一条知识
     *
     * @param id
     * @return
     */
    @Override
    public Knowledge selectKnowledge(Integer id) {
        Knowledge knowledge = knowledgeMapper.selectById(id);
        return knowledge;
    }

    /**
     * 更新某一条百科知识
     *
     * @param image
     * @param name
     * @param content
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public Knowledge updateKnowledge(MultipartFile image, String name, String content, Integer id) throws IOException {
        Knowledge knowledge = knowledgeMapper.selectById(id);
        if (knowledge == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        knowledge.setName(name);
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = UserInfoContext.getUser().getUsername();
        knowledge.setSource(username);
        knowledge.setContent(content);
        knowledge.setId(id);
        knowledge.setPublish_time(new Date());
        if (image != null) {
            String fileName = ImageUtils.ImgReceive(image, knowledgeStore_path);
            knowledge.setImage(url + image_url + fileName);
        }
        int flag = knowledgeMapper.updateById(knowledge);
        return knowledgeMapper.selectById(id);
    }

    /**
     * 获取所有百科知识
     *
     * @return
     */
    @Override
    public PageVO<Knowledge> selectAll(PageQO pageQO) {
//        String username = UserInfoContext.getUser().getUsername();
//        System.out.println("username========:"+username );
        Page<Knowledge> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<Knowledge> knowledgeList = knowledgeMapper.selectAll();
        return PageVO.build(page);
    }

    @Override
    public Boolean deleteKnowledge(String ids) {

        if (ids.contains("-")) {
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            knowledgeMapper.deleteKnowledgeBatch(delIds.substring(1, delIds.length() - 1));
        } else {
            Integer id = Integer.parseInt(ids);
            Knowledge knowledge = knowledgeMapper.selectById(id);
            if (knowledge == null) {
                throw new BusinessException(ResultCode.RESULE_DATA_NONE);
            }
            knowledgeMapper.deleteById(id);
        }
        return true;
    }
}
