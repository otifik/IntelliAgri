package com.jit.aquaculture.serviceinterface.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyThrow;

public interface DailyThrowService {
    DailyThrow insertDailyThrow(DailyThrow dailyThrow);

    Boolean deleteDailyThrow(String ids);

    DailyThrow updateDailyThrow(DailyThrow dailyThrow, Integer id);

    PageVO<DailyThrow> getAllDailyThrow(PageQO pageQO);

    PageVO<DailyThrow> getDailyThrowByDate(PageQO pageQO, String startDate, String endDate);

    DailyThrow getOneDailyThrow(Integer id);

    PageVO<DailyThrow> getAllDailyThrowByUsername(PageQO pageQO, String username);

    PageVO<DailyThrow> getDailyThrowByDateByUsername(PageQO pageQO, String startDate, String endDate, String username);

}
