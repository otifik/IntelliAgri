package com.jit.aquaculture.mapper.daily;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.dto.DataListDto;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("dailyIncomeMapper")
public interface DailyIncomeMapper extends BaseMapper<DailyIncome> {
    @DeleteProvider(type = DynamicSql.class, method = "deleteIncomeBatch")
    void deleteIncomeBatch(@Param("ids") String ids);
    @Select("SELECT distinct name FROM daily_income where username=#{username} and type=0 or type=1")
    List<String> getAllCostNames(@Param("username") String username);
    @Select("SELECT distinct name FROM daily_income where type=2 and username=#{username}")
    List<String> getAllIncomeNames(@Param("username") String username);
    @Select("SELECT sum(total) as value,name as name  FROM daily_income  where name=#{name}")
    DataListDto getDataByType(@Param("name") String name);
}
