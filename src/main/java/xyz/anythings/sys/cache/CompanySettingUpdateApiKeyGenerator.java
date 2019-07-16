/* Copyright © HatioLab Inc. All rights reserved. */
package xyz.anythings.sys.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.ClassUtil;

/**
 * 고객사 별 설정 (domainId, comCd, name)에 대한 업데이트하는 (Update) 경우 
 * {domainId}-{comCd}-{name} 으로 캐쉬 키를 생성한다.
 * 
 * @author shortstop
 */
@Component
public class CompanySettingUpdateApiKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		String domainIdStr = null;
		try {
			domainIdStr = ValueUtil.toString(Domain.currentDomainId());
		} catch (Exception e) {
			domainIdStr = "null";
		}
		
		Object obj = params[params.length - 1];
		Object comCdVal = ClassUtil.getFieldValue(obj, "comCd");
		Object nameVal = ClassUtil.getFieldValue(obj, "name");
		return domainIdStr + SysConstants.DASH + comCdVal.toString() + SysConstants.DASH + nameVal.toString();
	}
}