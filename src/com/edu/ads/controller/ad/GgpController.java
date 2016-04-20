package com.edu.ads.controller.ad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.ads.bean.ggp.Ggp;
import com.edu.ads.bean.ggp.GgpType;
import com.edu.ads.bean.ggp.Ggptp;
import com.edu.ads.bean.ggp.Message;
import com.edu.ads.bean.ggp.Status;
import com.edu.ads.bean.user.User;
import com.edu.ads.common.page.Page;
import com.edu.ads.common.page.PageResult;
import com.edu.ads.common.utils.CommonUtils;
import com.edu.ads.common.utils.ConfigUtil;
import com.edu.ads.controller.BaseController;
import com.edu.ads.service.ad.AdService;

@Controller
@RequestMapping("/ggp")
public class GgpController extends BaseController {
	@Autowired
	private AdService adService;

	/*
	 * =======================================广告牌类型==============================
	 * ==============================================================================
	 */
	@RequestMapping("/loadGgpTypeManger.do")
	public String loadGgpTypeManger() {
		return "/ad/ggpTypeManage.jsp";
	}

	@RequestMapping("/getGgTypeList.do")
	public String getGgTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		String mc = request.getParameter("mc");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		Page page = bulidPage(currentPage, pageSize);
		Map<String, Object> param = new HashMap<String, Object>();
		if (mc != null && !"".equals(mc)) {
			param.put("mc", mc);
		}
		String ordery = " order by mc desc";
		PageResult<GgpType> pageResult = adService.ggTypeList(param, page,
				ordery);
		double totalCount = pageResult.getTotalRecords();
		double perPageSize = Integer.valueOf(pageSize);
		double pageSzie = Math.ceil(totalCount / perPageSize);
		pageResult.setTotPage((int) pageSzie);
		pageResult.setPage(page);
		request.setAttribute("pageResult", pageResult);
		return "/ad/ggpTypeList.jsp";
	}

	@RequestMapping("/loadGgpTypeAdd.do")
	public String loadGgpTypeAdd() {
		return "/ad/addGgpType.jsp";
	}

	@RequestMapping("/saveGgpType.do")
	public String saveGgpType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GgpType ggpType = new GgpType();
		getBean(ggpType, request);
		ggpType.setId(CommonUtils.getUUid());
		adService.addggpType(ggpType);
		return "/ggp/loadGgpTypeManger.do";
	}

	@RequestMapping("/updateGgpType.do")
	public String updateGgpType(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String mc = request.getParameter("updatemc");
		String ms = request.getParameter("updatems");
		GgpType ggpType = adService.findggpType(id);
		ggpType.setMc(mc);
		ggpType.setMs(ms);
		adService.updateGgp(ggpType);
		return "/ggp/loadGgpTypeManger.do";
	}

	@RequestMapping("/showGgpType.do")
	public String showGgpType(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		GgpType ggpType = adService.findggpType(id);
		request.setAttribute("ggpType", ggpType);
		return "/ad/ggpTypeEedit.jsp";
	}

	@RequestMapping("/deleteGgpType.do")
	public String deleteGgpType(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		GgpType ggpType = adService.findggpType(id);
		adService.delete(ggpType);
		return "/ggp/loadGgpTypeManger.do";
	}

	/*
	 * =======================================广告牌================================
	 * =============================================================================
	 */
	@RequestMapping("/loadGgpManger.do")
	public String loadGgpManger(HttpServletRequest request,
			HttpServletResponse response) {
		List<GgpType> ggpTypeList = adService.getAllGgType();
		request.setAttribute("ggpTypeList", ggpTypeList);
		return "/ad/ggpManage.jsp";
	}

	@RequestMapping("/getGgpList.do")
	public String getGgpList(HttpServletRequest request,
			HttpServletResponse response) {
		String ms = request.getParameter("ms");
		String useInfo = request.getParameter("useInfo");
		String ggpType = request.getParameter("ggpType");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		Page page = bulidPage(currentPage, pageSize);
		Map<String, Object> param = new HashMap<String, Object>();
		if (ms != null && !"".equals(ms)) {
			param.put("ms", ms);
		}
		if (useInfo != null && !"".equals(useInfo)) {
			param.put("zt", Integer.valueOf(useInfo));
		}
		if (ggpType != null && !"".equals(ggpType)) {
			param.put("lx", adService.findggpType(ggpType));
		}
		String ordery = " order by jg desc";
		PageResult<Ggp> pageResult = adService.ggpList(param, page, ordery);
		double totalCount = pageResult.getTotalRecords();
		double perPageSize = Integer.valueOf(pageSize);
		double pageSzie = Math.ceil(totalCount / perPageSize);
		pageResult.setTotPage((int) pageSzie);
		pageResult.setPage(page);
		request.setAttribute("pageResult", pageResult);
		return "/ad/ggpList.jsp";
	}

	@RequestMapping("/loadGgpAdd.do")
	public String loadGgpAdd(HttpServletRequest request,
			HttpServletResponse response) {
		List<GgpType> ggpTypeList = adService.getAllGgType();
		request.setAttribute("ggpTypeList", ggpTypeList);
		return "/ad/addGgp.jsp";
	}

	@RequestMapping("/saveGgp.do")
	public String saveGgp(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String lx = request.getParameter("lx");
		User user = (User) request.getSession().getAttribute("user");
		Ggp ggp = new Ggp();
		getBean(ggp, request);
		GgpType type = adService.findggpType(lx);
		ggp.setLx(type);
		ggp.setTjry(user != null ? user.getName() : "");
		ggp.setId(CommonUtils.getUUid());
		ggp.setZt(1);
		ggp.setGgptps(new HashSet<Ggptp>());
		adService.addggp(ggp);
		return "/ggp/loadGgpManger.do";
	}

	@RequestMapping("/checkGgpLxCount.do")
	public void checkGgpLxCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (adService.getAllTypeCount() == 0) {
			response.getWriter().write("1");
		} else {
			response.getWriter().write("0");
		}
		response.getWriter().flush();
	}

	@RequestMapping("/showGgp.do")
	public String showGgp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Ggp ggp = adService.findggp(id);
		List<GgpType> ggpTypeList = adService.getAllGgType();
		request.setAttribute("ggpTypeList", ggpTypeList);
		request.setAttribute("ggp", ggp);
		return "/ad/ggpEedit.jsp";
	}

	@RequestMapping("/updateGgp.do")
	public String updateGgp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String lx = request.getParameter("lx");
		String ms = request.getParameter("updatems");
		String zt = request.getParameter("zt");
		String updatejg = request.getParameter("updatejg");
		Ggp Ggp = adService.findggp(id);
		GgpType type = adService.findggpType(lx);
		Ggp.setLx(type);
		Ggp.setMs(ms);
		Ggp.setZt(Integer.valueOf(zt));
		Ggp.setJg(Double.valueOf(updatejg));
		adService.upGgp(Ggp);
		return "/ggp/loadGgpManger.do";
	}

	@RequestMapping("/deleteGgp.do")
	public String deleteGgp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Ggp Ggp = adService.findggp(id);
		adService.deleteGgp(Ggp);
		return "/ggp/loadGgpManger.do";
	}

	/*
	 * =======================================广告牌图片==============================
	 * ==============================================================================
	 */

	@RequestMapping("/loadGgpTpManger.do")
	public String loadGgpTpManger(HttpServletRequest request,
			HttpServletResponse response) {
		List<GgpType> ggpTypeList = adService.getAllGgType();
		request.setAttribute("ggpTypeList", ggpTypeList);
		return "/ad/ggpTpManage.jsp";
	}

	@RequestMapping("/getGgpTpList.do")
	public String getGgpTpList(HttpServletRequest request,
			HttpServletResponse response) {
		String ggpType = request.getParameter("ggpType");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		Page page = bulidPage(currentPage, pageSize);
		page.setPageLength(8);
		Map<String, Object> param = new HashMap<String, Object>();
		if (ggpType != null && !"".equals(ggpType)) {
			param.put("lx", adService.findggpType(ggpType));
		}
		PageResult<Ggptp> pageResult = adService.ggptpList(param, page, "");
		double totalCount = pageResult.getTotalRecords();
		double perPageSize = Integer.valueOf(pageSize);
		double pageSzie = Math.ceil(totalCount / perPageSize);
		pageResult.setTotPage((int) pageSzie);
		pageResult.setPage(page);
		request.setAttribute("pageResult", pageResult);
		return "/ad/ggpTpList.jsp";
	}

	@RequestMapping("/loadGgpTpAdd.do")
	public String loadGgpTpAdd(HttpServletRequest request,
			HttpServletResponse response) {
		List<Ggp> ggpList = adService.getAllGgp();
		request.setAttribute("ggpList", ggpList);
		return "/ad/addGgpTp.jsp";
	}

	@RequestMapping("/saveGgpTp.do")
	public String saveGgpTp(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String lx = request.getParameter("lx");
		List<String> paths = (List<String>) request.getSession().getAttribute("upImageFile");
		System.out.println(paths.size());
		for(String path:paths){
			Ggptp ggptp = new Ggptp();
			ggptp.setId(CommonUtils.getUUid());
			Ggp ggp = adService.findggp(lx);
			ggptp.setGgp(ggp);
			ggptp.setUrl(path);
			adService.addGgptp(ggptp);
		}
		request.getSession().removeAttribute("upImageFile");
		return "/ggp/loadGgpTpManger.do";
	}

	@RequestMapping("/checkGgpTpLxCount.do")
	public void checkGgpTpLxCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (adService.getAllGgpCount() == 0) {
			response.getWriter().write("1");
		} else {
			response.getWriter().write("0");
		}
		response.getWriter().flush();
	}

	@RequestMapping("/showGgpTp.do")
	public String showGgpTp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Ggp ggp = adService.findggp(id);
		List<GgpType> ggpTypeList = adService.getAllGgType();
		request.setAttribute("ggpTypeList", ggpTypeList);
		request.setAttribute("ggp", ggp);
		return "/ad/ggpEedit.jsp";
	}

	@RequestMapping("/updateGgpTp.do")
	public String updateGgpTp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String lx = request.getParameter("lx");
		String ms = request.getParameter("updatems");
		String zt = request.getParameter("zt");
		String updatejg = request.getParameter("updatejg");
		Ggp Ggp = adService.findggp(id);
		GgpType type = adService.findggpType(lx);
		Ggp.setLx(type);
		Ggp.setMs(ms);
		Ggp.setZt(Integer.valueOf(zt));
		Ggp.setJg(Double.valueOf(updatejg));
		adService.upGgp(Ggp);
		return "/ggp/loadGgpManger.do";
	}

	/**
	 * 上传多图
	 */
	@RequestMapping(value = "/uploadMultipleFile.do", method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public Message uploadMultipleFileHandler(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("moreFile") MultipartFile[] files) throws IOException {
		Message msg = new Message();
		List<Integer> arr = new ArrayList<Integer>();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			msg.setStatus(Status.ERROR);
			msg.setError("请先登录！");
			return msg;
		}
		List<String> filespath=null;
		synchronized (request.getSession()) {
			filespath=(List<String>)request.getSession().getAttribute("upImageFile");
			if(filespath==null){
				filespath = new ArrayList<String>();
			}
		}
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			try {
				if (!file.isEmpty()) {
					InputStream in = null;
					OutputStream out = null;
					try {
						File dir = new File(ConfigUtil.updatePath(user));
						if (!dir.exists())
							dir.mkdirs();
						String path=dir.getAbsolutePath()
								+ File.separator + file.getOriginalFilename();
						File serverFile = new File(path);
						path=path.substring(path.indexOf(ConfigUtil.getString("UPLOAD_IMG")));
						System.out.println(path);
						boolean ISexist=false;
						for(String src:filespath){
							if(src.equals(path)){
								ISexist=true;
								break;
							}
						}
						if(!ISexist)
							filespath.add(path.substring(path.indexOf(ConfigUtil.getString("UPLOAD_IMG"))));
						in = file.getInputStream();
						out = new FileOutputStream(serverFile);
						byte[] b = new byte[1024];
						int len = 0;
						while ((len = in.read(b)) > 0) {
							out.write(b, 0, len);
						}
						out.close();
						in.close();
					} catch (Exception e) {
						arr.add(i);
					} finally {
						if (out != null) {
							out.close();
							out = null;
						}
						if (in != null) {
							in.close();
							in = null;
						}
					}
				} else {
					arr.add(i);
				}
			} catch (Exception e) {
				msg.setStatus(Status.ERROR);
				msg.setStatusMsg(file.getOriginalFilename() + "上传失败");
				e.printStackTrace();
			}
		}
		if (arr.size() > 0) {
			msg.setStatus(Status.ERROR);
			msg.setError("Files upload fail");
			msg.setErrorKys(arr);
		} else {
			msg.setStatus(Status.SUCCESS);
			msg.setStatusMsg("Files upload success");
			request.getSession().setAttribute("upImageFile",filespath);
		}
		System.out.println(filespath.size());
		return msg;
	}
	
	@RequestMapping("/checkGgpTpCount.do")
	public void checkGgpTpCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<String> paths=(List<String>)request.getSession().getAttribute("upImageFile");
		if (paths==null) {
			response.getWriter().write("1");
		} else {
			response.getWriter().write("0");
		}
		response.getWriter().flush();
	}
	
	@RequestMapping("/deleteGgpTp.do")
	public String deleteGgpTp(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		Ggptp ggptp = adService.findggpTp(id);
		adService.deleteGgpTp(ggptp);
		return "/ggp/loadGgpTpManger.do";
	}
}
