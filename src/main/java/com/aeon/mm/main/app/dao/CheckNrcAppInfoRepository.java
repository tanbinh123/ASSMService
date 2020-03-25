package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;

@Repository
public interface CheckNrcAppInfoRepository extends JpaRepository<ApplicationInfoReqDto, Long> {
	//long countByNrc(String applyNrc);IgnoreCase
	//ApplicationInfoReqDto findTopByNrc(String applyNrc);
	//modified @23Jan2019.
	long countByNrcIgnoreCase(String applyNrc);
	ApplicationInfoReqDto findTopByNrcIgnoreCase(String applyNrc);
}
