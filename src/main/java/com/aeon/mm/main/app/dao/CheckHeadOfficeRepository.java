package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.bean.HistorySuccessfulReqInfo;
import com.aeon.mm.main.app.dto.OutletInfoResDto;

@Repository
public interface CheckHeadOfficeRepository extends CrudRepository<OutletInfoResDto, Long> {

	String activateInfoQuery = "SELECT id, outlet_name "
			+ "FROM ass.outlet_info "
			+ "WHERE id = :outletId";

	@Query(value = activateInfoQuery, nativeQuery = true)
	OutletInfoResDto findByOutletId(@Param("outletId") int outletId);

}
