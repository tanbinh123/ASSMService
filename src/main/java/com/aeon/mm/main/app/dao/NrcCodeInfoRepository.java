package com.aeon.mm.main.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.NrcCodeInfoResDto;

@Repository
public interface NrcCodeInfoRepository extends CrudRepository<NrcCodeInfoResDto, Long> {

	String activateInfoQuery = "SELECT SD.STATE_ID, ARRAY_AGG(TOWNSHIP_CODE) AS TOWNSHIP_CODE_LIST"
			+ " FROM ASS.STATE_DIVISION_INFO SD " 
			+ " RIGHT JOIN ASS.TOWNSHIP_INFO T"
			+ " ON SD.STATE_ID = T.STATE_ID"  
			+ " GROUP BY SD.STATE_ID"
			+ " ORDER BY SD.STATE_ID";

	@Query(value = activateInfoQuery, nativeQuery = true)
	List<NrcCodeInfoResDto> findByALL();

}
