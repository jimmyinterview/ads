package com.edu.ads.service.portal;

import com.edu.ads.bean.order.Order;
import com.edu.ads.bean.portal.OrderRanger;
import com.edu.ads.dao.portal.PortalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by R on 2016/4/17.
 */
@Service
public class PortalService {

    @Autowired
    private PortalDao portalDao;

    /**
     * 查询销售排行数据
     * @return
     */
    public List<Order> getAdsOrderRankList(){
        return portalDao.getAdsOrderRankList();
    }

    
    public String getWeekRankArray(){
    	Date startTime = getWeekStartTime(1);	
    	List<OrderRanger> weekRanks = portalDao.getWeekRanger(startTime, new Date());
    	StringBuffer data = new StringBuffer();
    	data.append("[");
        for(int i=1;i<=7;i++){
        	Date week =  getWeekStartTime(i);
        	OrderRanger ranger = getRangerByDate(weekRanks, week);
        	if(null==ranger){
        		data.append("0").append(",");
        		continue;
        	}
        	data.append(ranger.getCount()).append(",");
        }
        data.deleteCharAt(data.length()-1).append("]");
    	return data.toString();
    }
    
    private OrderRanger getRangerByDate(List<OrderRanger> weekRanks,Date ddsj ){
    	for(OrderRanger ranger:weekRanks){
    		if(isSameDay(ranger.getDdsj(),ddsj)){
    			return ranger;
    		}
    	}
    	return null;
    }
    
    private boolean isSameDay(Date source,Date targt){
    	if(null==source||null==targt){
    		return false;
    	}
    	Calendar sourceCal =Calendar.getInstance();
    	sourceCal.setTime(source);
    	Calendar targtCal1 =Calendar.getInstance();
    	targtCal1.setTime(targt);
    	int sourceYear = sourceCal.get(Calendar.YEAR);
    	int targetYear = targtCal1.get(Calendar.YEAR);
    	if(sourceYear!=targetYear){
    		return false;
    	}
    	int sourceMounth = sourceCal.get(Calendar.MONTH);
    	int targetMounth = targtCal1.get(Calendar.MONTH); 
    	if(sourceMounth!=targetMounth){
    		return false;
    	}
    	int sourceDay = sourceCal.get(Calendar.DAY_OF_MONTH);
    	int targetDay = targtCal1.get(Calendar.DAY_OF_MONTH); 
    	if(sourceDay!=targetDay){
    		return false;
    	}
    	return true;
    }
    
    /**
     * 获取授业本月排行
     * @return
     */
    public List<OrderRanger> getorderCountRanger(){
    	Date kssj = getMouthStartTime();
    	return portalDao.getOrderAnger(kssj, new Date());
    }
    
    private Date getWeekStartTime(int day){
    	Calendar cal =Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, day);
    	return cal.getTime();
    }
    
    private Date getMouthStartTime(){
    	Calendar cal =Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	return cal.getTime();
    }

	public PortalDao getPortalDao() {
		return portalDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}
    
    
}
