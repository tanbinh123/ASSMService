package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.PrivacyInfoReqDto;
import com.aeon.mm.main.app.dto.PrivacyInfoResDto;

@Repository
public interface PrivacyCheckRepository extends CrudRepository<PrivacyInfoResDto, Long> {

	String infoQuery = "SELECT id" + " FROM ass.login_info" + " WHERE login_id = :#{#loginInfo.loginID}"
			+ " AND password = :#{#loginInfo.loginPassword}";

	@Query(value = infoQuery, nativeQuery = true)
	PrivacyInfoResDto findByLoginIdAndPassword(@Param("loginInfo") PrivacyInfoReqDto loginInfo);

}
