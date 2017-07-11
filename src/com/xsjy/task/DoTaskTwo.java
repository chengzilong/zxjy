package com.xsjy.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.xsjy.pojo.Custom.pojo_1040000.Pojo1040120;
import com.xsjy.service.service1040000.Service1040120;

public class DoTaskTwo extends TimerTask {

	private Service1040120 service;
	
	@Override
	public void run() {
		service = new Service1040120();
		Pojo1040120 dataBean = new Pojo1040120();
		int resultCount  = 0;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(new Date());
			dataBean.setBCXX_GXR("1001");
			dataBean.setXTSJ(time);
			resultCount = service.updateBczt(dataBean, "end");
			System.out.println("执行记录个数：" + resultCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
