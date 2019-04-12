package per.zongwlee.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StringUtils;
import per.zongwlee.gateway.feign.UserFeignClient;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/10
 */
public class AccessFilter extends ZuulFilter {

    private static final int HEADER_WRAPPER_FILTER = -1;

    public static final String HEADER_JWT = "Jwt_Token";

    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return HEADER_WRAPPER_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String authorizationToken = request.getHeader(HEADER_JWT);
        if (StringUtils.isEmpty(authorizationToken)) {
            if(!"/oauth/v1/login".equals(request.getRequestURI())){
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
            }
//            else {
//                //转发到oauth登录
//                ctx.put(FilterConstants.REQUEST_URI_KEY,
//                        "http://localhost:8080/oauth/v1/login");
//            }
        } else {
            //转发oauth认证token
            try {
                if (!userFeignClient.checkAuthorazition(authorizationToken)) {
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(401);
                }
            }catch (FeignException e){
                throw new CommonException("error.token.already.expired.or.wrong");
            }
        }
        return null;
    }
}
