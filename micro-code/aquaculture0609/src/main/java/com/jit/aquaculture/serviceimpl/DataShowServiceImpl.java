package com.jit.aquaculture.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.dto.ShowDto;
import com.jit.aquaculture.mapper.daily.DailyIncomeMapper;
import com.jit.aquaculture.mapper.daily.DailyObserveMapper;
import com.jit.aquaculture.mapper.daily.DailyThrowMapper;
import com.jit.aquaculture.serviceinterface.DataShowService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataShowServiceImpl implements DataShowService {
    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;
    @Autowired
    private DailyThrowMapper dailyThrowMapper;
    @Autowired
    private DailyObserveMapper dailyObserveMapper;
    @Override
    public List<ShowDto> getShowNum() {
        List<ShowDto> showDtoList = new ArrayList<>();
        ShowDto showDto = new ShowDto();
        Integer incomeCount=dailyIncomeMapper.selectCount(new EntityWrapper<DailyIncome>().eq("type","饲料"));
        showDto.setName("饲料");
        showDto.setValue(incomeCount);

        return null;
    }
}
