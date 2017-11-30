/**
* THIS FILE IS PART OF  egan
* FreightController.java - The core part of the freight Control layer
* @Copyright: 2017-11-21 www.egzosn.com Inc OR egan. All rights reserved.
*/




package com.egzosn.examples.controller;
import java.util.Map;
import com.egzosn.examples.entity.Freight;
import com.egzosn.examples.request.FreightBody;
import com.egzosn.examples.service.FreightService;
import com.egzosn.infrastructure.utils.validator.ValidateUtils;
import com.egzosn.infrastructure.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

/**
* freight 控制器
* 
* @author egan
* @email egzosn@gmail.com
* @date 2017-11-21 11:53:45
*/ 


@RestController
@RequestMapping("freight")
@Validated
@Api(description = "线路")
public class FreightController extends BaseController {
	@Autowired
	private FreightService service;

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	//@Autowired
	//private FreightRepository repository; 	//  FreightDaoParams

	//@RequestMapping("test")
	public Map<String, Object> test(){
		Map<String,Object> data = successData();
		return data;
	}

	@ApiOperation(value = "保存")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Map<String, Object> save(@Validated FreightBody body){

		Freight target = new Freight();
		BeanUtils.copyProperties(body, target);
		service.save(target);

		return successData("data", body);
	}

	@ApiOperation(value = "修改")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String, Object> update( Freight body) throws BindException {
		WebDataBinder binder = new WebDataBinder(body, body.getClass().getSimpleName());
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
		adapter.validate(body, binder.getBindingResult());
		if (binder.getBindingResult().hasErrors() ) {
			throw new BindException(binder.getBindingResult());
		}
		service.save(body);
		return successData("data", body);
	}
	/**
	 * Whether to raise a fatal bind exception on validation errors.
	 * @param binder the data binder used to perform data binding
	 * @param methodParam the method argument
	 * @return {@code true} if the next method argument is not of type {@link Errors}
	 */
	protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
		int i = methodParam.getParameterIndex();
		Class<?>[] paramTypes = methodParam.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
		return !hasBindingResult;
	}

	@ApiOperation(value = "返回ORM映射实体 名称查询")
	@RequestMapping(value ="find", method = RequestMethod.POST)
	public Map<String, Object> find(@ApiParam(value = "线路名称必填")@NotEmpty(message = "1002") String name){

		return successData("data", service.find(name));
	}

	@ApiOperation(value = "返回ORM映射实体 模糊名称查询")
	@RequestMapping(value ="findLK", method = RequestMethod.POST)
	public Map<String, Object> findLK(@RequestParam(defaultValue = "UPS专线") String name){

		return successData("data", service.findLK(name));
	}

	@ApiOperation(value = "返回ORM映射实体 查询id")
	@RequestMapping(value ="get", method = RequestMethod.POST)
	public Map<String, Object> get(@RequestParam(defaultValue = "2e33fa4d7131496bbf6c3274049bce31") String id){

		return successData("data", service.get(id));
	}


	@ApiOperation(value = "返回MAP 名称查询")
	@RequestMapping(value ="findMap", method = RequestMethod.POST)
	public Map<String, Object> findMap(@RequestParam(defaultValue = "UPS2222专线") String name){

		return successData("data", service.findMap(name));
	}


	@ApiOperation(value = "返回自定义对象 名称查询")
	@RequestMapping(value ="customFreight", method = RequestMethod.POST)
	public Map<String, Object> customFreight(@RequestParam(defaultValue = "UPS专线444") String name){

		return successData("data", service.customFreight(name));
	}

}

