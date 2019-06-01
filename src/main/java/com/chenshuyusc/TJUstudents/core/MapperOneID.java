package com.chenshuyusc.TJUstudents.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.select.SelectMapper;
import tk.mybatis.mapper.common.base.select.SelectOneMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface MapperOneID<T> extends
        Mapper<T>,
        IdsMapper<T>{
}
