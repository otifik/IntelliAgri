package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface KnowledgeService {
    Knowledge insertKnowledge(MultipartFile image, String name, String content) throws IOException;

    Knowledge selectKnowledge(Integer id);

    Knowledge updateKnowledge(MultipartFile image, String name, String content, Integer id) throws IOException;

    PageVO<Knowledge> selectAll(PageQO pageQO);

    Boolean deleteKnowledge(String ids);
}
