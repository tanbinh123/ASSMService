package com.aeon.mm.main.app.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.aeon.mm.main.app.bean.LoginInfoResBean;
import com.aeon.mm.main.app.bean.LoginInfoReqBean;


public interface Mapper {

	List<LoginInfoResBean> findByIdAndPassword(@Param("loginInfo") LoginInfoReqBean loginInfo);

}
