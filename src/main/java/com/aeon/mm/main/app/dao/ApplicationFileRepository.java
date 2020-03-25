package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aeon.mm.main.app.dto.ApplicationFileReqDto;


@Repository
@Transactional
public interface ApplicationFileRepository extends JpaRepository<ApplicationFileReqDto, Long> {}
