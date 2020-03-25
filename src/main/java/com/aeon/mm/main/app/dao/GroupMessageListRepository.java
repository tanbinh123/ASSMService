package com.aeon.mm.main.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.MessageGroupInfoReqDto;
import com.aeon.mm.main.app.dto.MessageGroupInfoResDto;

@Repository
public interface GroupMessageListRepository extends CrudRepository<MessageGroupInfoResDto, Long> {

	String messageQuery = "SELECT mg.group_id, gi.agency_user_id, mg.message_id, mi.message_content, mi.message_type, mi.send_time, mi.sender, mi.op_send_flag, mi.read_flag, mi.read_time" + 
			" FROM ass.message_group mg" + 
			" LEFT JOIN ass.group_info gi" + 
			" ON gi.id = mg.group_id" + 
			" LEFT JOIN ass.message_info mi" + 
			" ON mi.id = mg.message_id" +
			" WHERE gi.agency_user_id = :#{#reqDto.agencyUserId}" +
			" AND gi.id = :#{#reqDto.groupId} "+
			" ORDER BY mi.send_time DESC LIMIT 120";

	@Query(value = messageQuery, nativeQuery = true)
	List<MessageGroupInfoResDto> findById(@Param("reqDto") MessageGroupInfoReqDto reqDto);

}
