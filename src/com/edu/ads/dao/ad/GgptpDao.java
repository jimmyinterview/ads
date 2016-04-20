package com.edu.ads.dao.ad;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.edu.ads.bean.ggp.Ggp;
import com.edu.ads.bean.ggp.Ggptp;
import com.edu.ads.dao.imp.DaoSupport;

@Repository
public class GgptpDao extends DaoSupport<Ggptp>{
	public List<Ggptp> getAllGgpTpByGgp(Ggp ggp){
		return findList("from Ggptp where ggp=?", new Object[]{ggp});
	}

}
