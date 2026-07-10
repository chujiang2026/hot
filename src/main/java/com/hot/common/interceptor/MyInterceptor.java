package com.hot.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hot.common.constant.AuthConstant;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.StringUtils;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截器：校验请求头中的令牌，未登录或令牌失效则返回 401。
 * 放行规则由 WebMvcConfig 配置（/login、/register）。
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    private final SysUserService sysUserService;

    private final ObjectMapper objectMapper;

    // 白名单路径（不需要 token 校验）
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/material/queryBatch",
            "/material/queryBatch/erp"  // 如果有多个路径，都加上
    );

    public MyInterceptor(SysUserService sysUserService, ObjectMapper objectMapper) {
        this.sysUserService = sysUserService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.debug("intercept {} {}", request.getMethod(), request.getRequestURI());

        // CORS 预检请求（OPTIONS）不带业务令牌，直接放行，交给 CORS 处理，否则会被当成未登录拦掉导致前端报跨域
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = request.getHeader(AuthConstant.TOKEN_HEADER);
        if (!StringUtils.hasText(token)) {
            writeUnauthorized(response);
            return false;
        }
        SysUser user = sysUserService.getUserByToken(token);
        if (user == null) {
            writeUnauthorized(response);
            return false;
        }
        request.setAttribute(AuthConstant.CURRENT_USER, user);

        try {
            //获取body里的form-data请求参数
            Map<String, String> data = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                for (String paramValue : paramValues) {
                    data.put(paramName, paramValue);
                }
            }
            //map转json
            String json = objectMapper.writeValueAsString(data);

            // 登录成功，将用户信息放入 request，避免重复查询数据库
            sysUserService.insertLog(request.getRequestURL().toString(), json, StringUtils.getRemoteAddr(request),user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void writeUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Result<Void> body = Result.of(ResultCode.NO_LOGIN);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
