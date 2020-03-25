package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;


@Repository
@Transactional
public interface InsertApplicationInfoRepository extends JpaRepository<ApplicationInfoReqDto, Long> {
	long countByNrc(String nrc);
	//ApplicationInfoReqDto findByNrc(String nrc);
	//modified @23Jan2019.
	long countByNrcIgnoreCase(String nrc);
	ApplicationInfoReqDto findByNrcIgnoreCase(String nrc);
}
