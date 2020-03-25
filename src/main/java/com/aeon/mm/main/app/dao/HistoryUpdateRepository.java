package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aeon.mm.main.app.dto.FileInfoResDto;

public interface HistoryUpdateRepository extends JpaRepository<FileInfoResDto, Long> {
	FileInfoResDto findById(int id);
}
