package com.aeon.mm.main.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.SummaryFileReportResDto;

@Repository
public interface SummaryFIleReportRepository extends JpaRepository<SummaryFileReportResDto,Long> {
	
	String summaryReportQuery = "SELECT status.update_time, SUM(status.count) AS total_count, " + 
			"STRING_AGG(CONCAT(status.count,',',status.judgement \\:\\:text) ,',') AS status_list " + 
			"FROM ( SELECT fi.update_time\\:\\:DATE, " + 
			"	CASE  " + 
			"	WHEN judgement_status = 1 THEN 'ongoing' " + 
			"	WHEN judgement_status = 2 THEN 'approve' " + 
			"	WHEN judgement_status = 3 THEN 'reject' " + 
			"	WHEN judgement_status = 4 THEN 'cancel' END AS judgement,fi.login_id, COUNT(*) " + 
			"	FROM application_info ai " + 
			"	JOIN application_file af ON ai.id = af.application_id " + 
			"	JOIN file_info fi ON fi.id = af.file_id " + 
			"	WHERE fi.login_id = :loginId " + 
			"	GROUP BY judgement_status, fi.login_id,fi.update_time\\:\\:DATE " + 
			"	ORDER BY fi.update_time\\:\\:DATE) AS status " + 
			"GROUP BY status.update_time;";

	@Query(value=summaryReportQuery, nativeQuery=true)
	List<SummaryFileReportResDto> findByLoginId(@Param("loginId") int loginId);

}
