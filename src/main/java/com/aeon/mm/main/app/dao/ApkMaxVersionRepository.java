package com.aeon.mm.main.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.dto.ApkVersionResDto;

@Repository
public interface ApkMaxVersionRepository extends CrudRepository<ApkVersionResDto, Long> {

	String apkMaxVerQuery = "SELECT id,version,file_name,file_path FROM ass.apk_version ORDER BY id DESC LIMIT 1;";
	@Query(value = apkMaxVerQuery, nativeQuery = true)
	ApkVersionResDto findById();

	/*String apkCPUCheck = "SELECT id,version,file_name,file_path FROM ass.apk_version WHERE id = (SELECT MAX(id) FROM ass.apk_version) AND file_name LIKE '%?1%' LIMIT 1;";*/
	@Query(value = "SELECT id,version,file_name,file_path FROM ass.apk_version WHERE version = (SELECT version FROM ass.apk_version WHERE id = (SELECT MAX(id) FROM ass.apk_version)) AND file_name LIKE CONCAT('%',:instructionSet,'%') LIMIT 1;", nativeQuery = true)
	ApkVersionResDto findByInstructionSet(@Param("instructionSet") String instructionSet);
	
	String getUniversalApk = "SELECT id,version,file_name,file_path FROM ass.apk_version WHERE version LIKE (SELECT version FROM ass.apk_version WHERE id = (SELECT MAX(id) FROM ass.apk_version)) AND file_name LIKE '%universal%' LIMIT 1;";
	@Query(value = getUniversalApk, nativeQuery = true)
	ApkVersionResDto findUniversalApk();

	@Query(value = "UPDATE ass.apk_version SET count = count+1 WHERE file_path LIKE CONCAT('%',:fileName,'%') RETURNING id", nativeQuery = true)
	int updateDownloadCount(@Param("fileName") String fileName);
}
