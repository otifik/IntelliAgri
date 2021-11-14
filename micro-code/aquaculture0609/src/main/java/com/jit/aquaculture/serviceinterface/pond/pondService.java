package com.jit.aquaculture.serviceinterface.pond;

import com.jit.aquaculture.domain.pond.Pond;
import com.jit.aquaculture.dto.pond.PondDto;
import java.util.List;
public interface pondService {
    PondDto<?> addpond(String name, Double length, Double width, Double depth, String orientation, Double longitude, Double latitude, String address, String product, String username);
    PondDto<?> selectAll(Integer pageNum, Integer pageSize,String username);
    PondDto<?> select(String username);
    PondDto<?> deletepond(Integer id);
    PondDto<?> updatepond(String name, Double length, Double width, Double depth, String orientation, Double longitude, Double latitude, String address, String product, String username,Integer id);
}

