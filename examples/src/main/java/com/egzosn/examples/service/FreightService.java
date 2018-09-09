/**
* THIS FILE IS PART OF egan
* FreightService.java - The core part of the freight Service layer
* @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
*/




package com.egzosn.examples.service;
import com.egzosn.examples.params.FreightDaoParams;
import com.egzosn.examples.repository.FreightRepository;
import com.egzosn.examples.entity.Freight;
import com.egzosn.examples.entity.CustomFreight;
import com.egzosn.infrastructure.params.SqlFilter;
import com.egzosn.infrastructure.params.enums.Restriction;
import com.egzosn.infrastructure.utils.common.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
* freight 服务
* 
* @author egan
* @email egzosn@gmail.com
* @date 2017-11-21 11:53:45
*/ 


@Service
@Transactional(readOnly = true)
public class FreightService {
	@Autowired
	private FreightRepository repository; 	//  FreightDaoParams

	/**
	 * @param sqlFilter 过滤对象
	 * @author egan
	 * @email egzosn@gmail.com
	 * @date 2017-8-21 0:36:46
	 */
	@Transactional(readOnly = true)
	public Page<Freight> findByFilter(SqlFilter sqlFilter){
		//设置排序
		sqlFilter.setOrder();
		sqlFilter.setPageing();
		return repository.queryPage(sqlFilter.setAlias(FreightDaoParams.ALIAS).getQueryParams(), true);
	}


	@Transactional
	public void save(Freight freight){
		if (null == freight.getPkId()){
			repository.save(freight);
		}else {
			repository.update(freight);
		}

	}

	/**
	 *  返回ORM映射实体
	 * @return
	 */
	public Freight find(String name){

		return repository.findUniqueByProperty(FreightDaoParams.Field.name.getColumn(), name);
	}
		/**
	 *  返回ORM映射实体
	 * @return
	 */
	public List<Freight> findLK(String name){

		return repository.findByProperty(FreightDaoParams.Field.name.getColumn(), name, Restriction.LK);
	}

	public Freight get(String id){

		return repository.get(id);
	}

	/**
	 * 自定义实体
	 * @param name
	 * @return
	 */
	public CustomFreight customFreight(String name){
		FreightDaoParams params = new FreightDaoParams()
				.setName(name, false)
				;
		return repository.customFreight(params);
	}

	/**
	 * 返回MAP
	 * @param name
	 * @return
	 */
	public Map findMap(String name){
		FreightDaoParams params = new FreightDaoParams()
				.setName(name, false)
				;

		return repository.findMap(params);
	}

}

