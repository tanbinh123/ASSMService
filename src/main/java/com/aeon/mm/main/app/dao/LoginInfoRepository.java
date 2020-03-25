package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.LoginInfoReqDto;
import com.aeon.mm.main.app.dto.LoginInfoResDto;

@Repository
public interface LoginInfoRepository extends CrudRepository<LoginInfoResDto, Long> {

	String loginUserInfoQuery = 
			"SELECT l.id,l.login_id,l.password,l.name,l.start_date, l.end_date,l.isvalid as user_valid"
					+ ",a.id as agency_id,a.agency_name ,a.isvalid as agency_valid"
					+ ",CASE WHEN (a.location = 0) THEN 'Yangon' ELSE 'Mandalay' END AS location"
					+ ",tai.mobile_team, tai.non_mobile_team" + ",aoi.outlet_id,aoi.agency_outlet_id,aoi.outlet_name,ar.role_id_list ,gi.id as group_id"
					+ " FROM ass.login_info l" 
					+ " JOIN ass.agency_info a" + " ON l.agency_id = a.id"
					+ " JOIN (SELECT STRING_AGG(role_id\\:\\:CHARACTER VARYING, ',') AS role_id_list, agency_id"
					+ " FROM ass.agency_role"
					+ " WHERE isvalid =1"
					+ " GROUP BY agency_id) AS ar"
					+ " ON ar.agency_id = a.id"
					+ " JOIN (SELECT tia.agency_id"
					+ ",CASE WHEN (tia.team_list[1] = '0') THEN team_list[2] ELSE team_list[4] END as mobile_team"
					+ ",CASE WHEN (tia.team_list[3] = '1') THEN team_list[4] ELSE team_list[2] END as non_mobile_team"
					+ " FROM (SELECT ta.agency_id,regexp_split_to_array(string_agg(concat(target \\:\\:text ,',', team_name \\:\\:text) ,','),',') as team_list"
					+ " FROM ass.team_info t " + " JOIN ass.team_agency ta " + " ON t.id = ta.team_id "
					+ " GROUP BY ta.agency_id) as tia) AS tai " + " ON l.agency_id = tai.agency_id "
					+ " JOIN (SELECT ao.id as agency_outlet_id,ao.agency_id, o.id as outlet_id,o.outlet_name "
					+ " FROM ass.agency_outlet ao " + " JOIN ass.outlet_info o " + " ON o.id = ao.outlet_id) AS aoi "
					+ " ON l.agency_outlet_id = aoi.agency_outlet_id "
					+ " LEFT JOIN ass.group_info gi"  
					+ " ON gi.agency_user_id = l.id"
					+ " WHERE l.login_id = :#{#loginInfo.loginID}"
					+ " AND l.password = :#{#loginInfo.loginPassword}";

	@Query(value = loginUserInfoQuery, nativeQuery = true)
	LoginInfoResDto findByLoginIdAndPassword(@Param("loginInfo") LoginInfoReqDto loginInfo);

}
