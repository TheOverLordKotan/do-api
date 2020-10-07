package com.dongkap.security.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dongkap.common.exceptions.BaseControllerException;
import com.dongkap.feign.dto.security.MenuDto;
import com.dongkap.feign.dto.security.MenuItemDto;
import com.dongkap.feign.dto.select.SelectResponseDto;
import com.dongkap.feign.dto.tree.TreeDto;
import com.dongkap.security.entity.UserEntity;
import com.dongkap.security.service.MenuImplService;

@RestController
@RequestMapping("/api/security")
public class MenuController extends BaseControllerException {

	@Autowired
	private MenuImplService menuService;
	
	@RequestMapping(value = "/vw/get/menus/v.1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, List<MenuDto>>> getAllMenuI18n(Authentication authentication,
			@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) String locale) throws Exception {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		return new ResponseEntity<Map<String, List<MenuDto>>>(menuService.loadAllMenuByRole(user.getAuthorityDefault(), locale), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/vw/auth/tree/menus/v.1/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TreeDto<MenuItemDto>>> getTreeMenuI18n(Authentication authentication,
			@PathVariable(required = true) String type,
			@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) String locale) throws Exception {
		return new ResponseEntity<List<TreeDto<MenuItemDto>>>(menuService.loadTreeMenu(type, locale), HttpStatus.OK);
	}

	@RequestMapping(value = "/vw/auth/select/menus/v.1/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SelectResponseDto> getSelectRootMenus(Authentication authentication,
			@PathVariable(required = true) String type,
			@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) String locale) throws Exception {
		return new ResponseEntity<SelectResponseDto>(menuService.getSelectRootMenus(type,locale), HttpStatus.OK);
	}
	
}
