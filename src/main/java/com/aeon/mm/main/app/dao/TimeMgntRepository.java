package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aeon.mm.main.app.dto.TimeManagementInfoReqDto;


@Repository
@Transactional
public interface TimeMgntRepository extends JpaRepository<TimeManagementInfoReqDto, Long> {
	
	TimeManagementInfoReqDto findByFileId(int fileId);
}
