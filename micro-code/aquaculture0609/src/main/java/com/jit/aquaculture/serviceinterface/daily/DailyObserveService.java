package com.jit.aquaculture.serviceinterface.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyObserve;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

public interface DailyObserveService {
    DailyObserve insertObserve(String name, String content, Integer poundId, MultipartFile file, String time, String remark) throws IOException, ParseException;

    DailyObserve updateObserve(String name, String content, Integer poundId, MultipartFile file, Integer id, String time, String remark) throws IOException, ParseException;

    Boolean deleteObserve(String ids);

    PageVO<DailyObserve> getAll(PageQO pageQO);

    DailyObserve getOne(Integer id);

}
