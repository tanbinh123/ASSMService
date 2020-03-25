package com.aeon.mm.main.app.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.ApplicationInfoReqDto;

@Repository
public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfoReqDto, Long> {
	List<ApplicationInfoReqDto> findAll();
	ApplicationInfoReqDto findByNrc(String nrc);
	
	List<ApplicationInfoReqDto> findAllByNrcAndUpdateTime(String nrc,Timestamp updateTime);
	
	@Query(value = "SELECT status FROM ass.file_info WHERE id = (SELECT file_id FROM ass.application_file WHERE application_id = :applicationId AND update_time = :updatedTimestamp) LIMIT 1;", nativeQuery = true)
	int findFileIdToResend(@Param("applicationId")int applicationId, @Param("updatedTimestamp")Timestamp updatedTimestamp);
	
	List<ApplicationInfoReqDto> findAllByNrc(String nrc);
}
