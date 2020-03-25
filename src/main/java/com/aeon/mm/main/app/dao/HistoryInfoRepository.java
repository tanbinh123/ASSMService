package com.aeon.mm.main.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.aeon.mm.main.app.dto.HistoryInfoResDto;

@Repository
public interface HistoryInfoRepository extends JpaRepository<HistoryInfoResDto,Long> {
	
	String historyInfoForHO = "SELECT fi.id,agency_id,nrc,ai.name,finish,file_name,type,is_valid,judgement_status,agreement_no,finance_term,finance_amount "
									+ "FROM file_info fi "
									+ "INNER JOIN application_file af "
									+ "ON fi.id=af.file_id "
									+ "INNER JOIN application_info ai "
									+ "ON ai.id=af.application_id "
									+ "INNER JOIN login_info li "
									+ "ON li.id=fi.login_id "
									+ "INNER JOIN time_management tm "
									+ "ON tm.file_id=fi.id "
									+ "WHERE li.agency_id = :agencyId "
									+ "AND is_valid = 1 AND status > 2 "
									+ "AND finish BETWEEN (NOW() - INTERVAL '14' DAY) AND NOW() "
									+ "ORDER BY tm.update_time DESC";

	@Query(value=historyInfoForHO, nativeQuery=true)
	List<HistoryInfoResDto> findByAgencyIdForHeadOffice(@Param("agencyId") int agencyId);

	String historyInfoForOU = "SELECT fi.id,ao.agency_id,nrc,ai.name,finish,file_name,type,is_valid,judgement_status,agreement_no,finance_term,finance_amount "
							+ "FROM file_info fi "
							+ "INNER JOIN application_file af ON fi.id=af.file_id "
							+ "INNER JOIN application_info ai ON ai.id=af.application_id "
							+ "INNER JOIN login_info li ON li.id=fi.login_id "
							+ "INNER JOIN agency_outlet ao ON ao.id = li.agency_outlet_id "
							+ "INNER JOIN outlet_info oi ON oi.id = ao.outlet_id "
							+ "INNER JOIN time_management tm "
							+ "ON tm.file_id=fi.id "
							+ "WHERE ao.outlet_id = :outletId "
							+ "AND is_valid = 1 AND status > 2 "
							+ "AND finish BETWEEN (NOW() - INTERVAL '14' DAY) AND NOW() "
							+ "ORDER BY tm.update_time DESC";

@Query(value=historyInfoForOU, nativeQuery=true)
List<HistoryInfoResDto> findByAgencyIdForOutlet(@Param("outletId") int outletId);

}
