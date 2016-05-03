package com.edu.ads.controller.portal;

import java.util.List;

import com.edu.ads.bean.portal.OrderRanger;
import com.edu.ads.controller.BaseController;
import com.edu.ads.service.portal.PortalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangran on 2016/4/17.
 */
@Controller
@RequestMapping("/portal")
public class PortalController extends BaseController {

	@Autowired
	private PortalService portalService;
	
	@RequestMapping("/loadPortalIndex.do")
	public String loadPortalIndex(HttpServletRequest request, HttpServletResponse response){
		return "/portal/index.jsp";
	}
	
    /**
     * 查询销售排名数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loadAdsRank.do")
    public String loadAdsRank(HttpServletRequest request, HttpServletResponse response){
    	List<OrderRanger> rankList = portalService.getorderCountRanger();
    	request.setAttribute("rankList", rankList);
        return "/portal/util/ads_rank_util.jsp";
    }

    /**
     * 查询本周销售数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loadAdsWeekData.do")
    public String loadAdsWeekData(HttpServletRequest request, HttpServletResponse response){
        // TODO
    	String echarValue = portalService.getWeekRankArray();
    	request.setAttribute("echarValue", echarValue);
    	return "/portal/util/week_rank_util.jsp";
    }
}
