package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.ActivateInfoReqDto;
import com.aeon.mm.main.app.dto.ActivateInfoResDto;

@Repository
public interface ActivateInfoRepository extends CrudRepository<ActivateInfoResDto, Long> {

	String activateInfoQuery = "SELECT id FROM ass.app_login_info "
			+ "WHERE password = :#{#reqDto.password}";

	@Query(value = activateInfoQuery, nativeQuery = true)
	ActivateInfoResDto findByPassword(@Param("reqDto") ActivateInfoReqDto reqDto);

}
