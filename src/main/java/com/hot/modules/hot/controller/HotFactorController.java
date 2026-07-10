package com.hot.modules.hot.controller;

import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.HotFactor;
import com.hot.modules.hot.service.HotFactorService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 渠道分摊系数控制器
 */
@RestController
@RequestMapping("/hot/factor")
public class HotFactorController {

    @Autowired
    private HotFactorService hotFactorService;

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 查询结果
     */
    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        HotFactor factor = hotFactorService.get(id);
        return factor == null ? Result.of(ResultCode.FAIL) : Result.success(factor);
    }

    /**
     * 分页查询列表
     * @param hotFactor 查询条件
     * @return 分页结果
     */
    @PostMapping("/list")
    public Result<?> list(@ModelAttribute HotFactor hotFactor) {
        return Result.success(hotFactorService.list(hotFactor));
    }

    /**
     * 新增或更新记录
     * @param hotFactor 渠道分摊系数对象
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<?> save(@ModelAttribute HotFactor hotFactor, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        hotFactor.setCreateUser(user.getId());
        hotFactor.setUpdateUser(user.getId());
        if (hotFactor.getId() == null) {
            return Result.of(ResultCode.FAIL, "渠道分摊系数不允许新增");
        }
        Result<?> invalid = validateFactor(hotFactor);
        if (invalid != null) {
            return invalid;
        }
        return hotFactorService.save(hotFactor) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 删除记录
     * @param id 主键ID
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/delete")
    public Result<?> delete(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return Result.success();
        //return hotFactorService.delete(id, user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 分摊系数合计不能超过100校验（供前端提交前预校验，不落库）
     * @param hotFactor 渠道分摊系数对象
     * @return 校验通过返回 success，否则返回带提示的 FAIL
     */
    @PostMapping("/check")
    public Result<?> check(@ModelAttribute HotFactor hotFactor) {
        Result<?> invalid = validateFactor(hotFactor);
        return invalid != null ? invalid : Result.success();
    }

    /**
     * 校验分摊系数：不能为负、且与其它记录合计不能超过100
     * @param hotFactor 待校验的渠道分摊系数对象
     * @return 校验不通过返回带提示的 FAIL，通过返回 null
     */
    private Result<?> validateFactor(HotFactor hotFactor) {
        // getNotId 通过 SUM 查询除当前记录外其它记录的系数合计，
        // 无其它记录时 SUM 返回 null，这里统一按 0 处理避免拆箱空指针
        HotFactor hot = hotFactorService.getNotId(hotFactor.getId());
        int salesK1 = hotFactor.getSalesK1() == null ? 0 : hotFactor.getSalesK1();
        int coopK2 = hotFactor.getCoopK2() == null ? 0 : hotFactor.getCoopK2();
        int salesSum = hot.getSalesK1() == null ? 0 : hot.getSalesK1();
        int coopSum = hot.getCoopK2() == null ? 0 : hot.getCoopK2();

        if (salesK1 < 0 || coopK2 < 0) {
            return Result.of(ResultCode.FAIL, "分摊系数不能为负数");
        }
        if (salesSum + salesK1 > 100) {
            return Result.of(ResultCode.FAIL, "销售数据分摊系数K1合计不能超过100");
        }
        if (coopSum + coopK2 > 100) {
            return Result.of(ResultCode.FAIL, "合作费用分摊系数K2合计不能超过100");
        }
        return null;
    }

    /**
     * 获取当前登录用户信息
     * @param request HTTP请求对象
     * @return 当前用户对象
     * @throws Exception 用户未登录异常
     */
    private SysUser currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user;
    }

}
