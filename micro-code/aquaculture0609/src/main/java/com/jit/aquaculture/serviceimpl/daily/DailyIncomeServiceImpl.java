package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.config.UserInfoContext;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.dto.AnalysisDto;
import com.jit.aquaculture.dto.DataListDto;
import com.jit.aquaculture.mapper.daily.DailyIncomeMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.daily.DailyIncomeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyIncomeServiceImpl implements DailyIncomeService {
    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;

    /**
     * 新增经济效益数据
     *
     * @param dailyIncome
     * @return
     */
    @Override
    public DailyIncome insertIncome(DailyIncome dailyIncome) {
        String username = UserInfoContext.getUser().getUsername();
        //System.out.println("username========:"+username );
        dailyIncome.setUsername(username);
        if (dailyIncome.getSysTime() == null) {
            dailyIncome.setSysTime(new Date());
        } else {
            dailyIncome.setSysTime(dailyIncome.getSysTime());
        }
        DailyIncome incomeIn = DailyIncome.of();
        BeanUtils.copyProperties(dailyIncome, incomeIn);

        Float all = 0.0f;
        if (incomeIn.getPrice() != null && incomeIn.getCount() != null) {
            if (Math.abs(incomeIn.getTotal() - 0) < 0.001 || incomeIn.getTotal() == null) {//总价没填，后台计算
                all = incomeIn.getCount() * incomeIn.getPrice();
                incomeIn.setTotal(all);
            }
        }

        Integer flag = dailyIncomeMapper.insert(incomeIn);
        if (flag > 0) {
            return incomeIn;
        } else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 更新经济效益数据
     *
     * @param dailyIncome
     * @return
     */
    @Override
    public DailyIncome updateIncome(DailyIncome dailyIncome) {
        if (dailyIncome.getId() == 0 || dailyIncome.getId() == null) {
            throw new BusinessException(ResultCode.PARAM_IS_INVALID);
        }
        DailyIncome isExist = dailyIncomeMapper.selectById(dailyIncome.getId());
        if (isExist == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (dailyIncome.getSysTime() == null) {
            dailyIncome.setSysTime(new Date());
        } else {
            dailyIncome.setSysTime(dailyIncome.getSysTime());
        }
        BeanUtils.copyProperties(dailyIncome, isExist);
        Integer flag = dailyIncomeMapper.updateById(isExist);
        if (flag > 0) {
            return isExist;
        } else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 批量删除经济效益数据
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIncome(String ids) {
        if (ids.contains("-")) {
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            dailyIncomeMapper.deleteIncomeBatch(delIds.substring(1, delIds.length() - 1));
        } else {
            Integer id = Integer.parseInt(ids);
            dailyIncomeMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 获取所有经济数据
     *
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAll(PageQO pageQO) {
        String username = UserInfoContext.getUser().getUsername();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username", username));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 获取一条经济数据
     *
     * @param id
     * @return
     */
    @Override
    public DailyIncome getOne(Integer id) {
        String username = UserInfoContext.getUser().getUsername();
        DailyIncome dailyIncome = dailyIncomeMapper.selectById(id);
        if (dailyIncome == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        return dailyIncome;
    }


    /**
     * 获取所有支出数据
     *
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyIncome> getIncomesByType(PageQO pageQO, Integer type) {
        String username = UserInfoContext.getUser().getUsername();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username", username).eq("type", type));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 根据日期获取所有经济效益数据
     *
     * @param pageQO
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAllByDate(PageQO pageQO, String startDate, String endDate) {
        String username = UserInfoContext.getUser().getUsername();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username", username).between("sys_time", startDate, endDate));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 根据日期获取所有固定成本数据列表
     *
     * @param pageQO
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAllCostsByDate(PageQO pageQO, String startDate, String endDate) {
        String username = UserInfoContext.getUser().getUsername();
        //String username ="mm";
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>()
                .eq("username", username)
                .eq("type", 0).or().eq("type", 1)
                .between("sys_time", startDate, endDate));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 根据日期获取所有收益数据列表
     *
     * @param pageQO
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAllIncomeByDate(PageQO pageQO, String startDate, String endDate) {
        String username = UserInfoContext.getUser().getUsername();
        //String username ="mm";
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>()
                .eq("username", username)
                .eq("type", 2)
                .between("sys_time", startDate, endDate));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 获取成本的统计数据
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public AnalysisDto getAnalysisByDate(String startDate, String endDate, Integer flag) {
        String username = UserInfoContext.getUser().getUsername();

        AnalysisDto analysisDto = new AnalysisDto();
        List<DataListDto> dataListDtos = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if (flag == 1) {
            names = dailyIncomeMapper.getAllCostNames(username);//获取成本的统计数据
        } else {
            names = dailyIncomeMapper.getAllIncomeNames(username);//获取收入的统计数据
        }

        for (String name : names) {
            DataListDto dataListDto = dailyIncomeMapper.getDataByType(name);
            dataListDtos.add(dataListDto);
        }
        analysisDto.setDataListDtos(dataListDtos);
        analysisDto.setStartDate(startDate);
        analysisDto.setEndDate(endDate);
        return analysisDto;
    }
}
