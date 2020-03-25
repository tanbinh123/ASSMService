package com.aeon.mm.main.app.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.StatusManagementReqDto;

@Repository
@Transactional
public interface StatusManagementRepository extends JpaRepository<StatusManagementReqDto, Long> {}
