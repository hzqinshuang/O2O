package com.qinshuang.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qinshuang.o2o.BaseTest;
import com.qinshuang.o2o.entity.Area;

public class AreaServicTest extends BaseTest {
@Autowired
	private AreaService areaService;
	@Test
	public void testGetAreaList() {
		List<Area> areaList=areaService.getAreaList();
		assertEquals("南区",areaList.get(0).getAreaName());
	}
}
