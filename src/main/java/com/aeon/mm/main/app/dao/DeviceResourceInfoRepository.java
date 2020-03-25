package com.aeon.mm.main.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aeon.mm.main.app.bean.DeviceResourcesInformation;

@Repository
public interface DeviceResourceInfoRepository extends CrudRepository<DeviceResourcesInformation, Long>{

}
