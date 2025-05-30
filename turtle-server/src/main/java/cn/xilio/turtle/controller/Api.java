package cn.xilio.turtle.controller;

import cn.xilio.turtle.domain.Result;
import cn.xilio.turtle.domain.entity.menu.MenuType;
import cn.xilio.turtle.domain.dto.SearchQueryDTO;
import cn.xilio.turtle.service.*;
import cn.xilio.turtle.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @Project Turtle
 * @Description 门户网站所有接口
 * @Author xilio
 * @Website https://xilio.cn
 * @Copyright (c) 2025 xilio. All Rights Reserved.
 */
@RestController
@RequestMapping("${cn.xilio.turtle.api.prefix}/v1")
@Tag(name = "API接口", description = "门户API接口")
public class Api {
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private NavLinkService navLinkService;

    @GetMapping(value = "article/list", name = "最近文章列表")
    public Mono<Result> list(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {
        return articleService.getArticles(keyword, page, size).map(Result::success);
    }

    @GetMapping(value = "article/get-by-tag", name = "根据标签name获取文章列表")
    public Mono<Result> getArticlesByTag(@RequestParam("tagName") String tagName,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return articleService.getArticlesByTag(tagName, page, size).map(Result::success);
    }

    @GetMapping(value = "tags", name = "分页获取所有标签")
    public Mono<Result> tags(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {
        return tagService.getTags(page, size).map(Result::success);
    }

    @GetMapping(value = "configs", name = "获取网站配置")
    public Mono<Result> configs() {
        return configService.getAllConfigs().map(Result::success);
    }

    @GetMapping(value = "detail", name = "获取文章详情")
    public Mono<Result> detail(@RequestParam("id") String id,
                               @RequestParam(value = "pwd", required = false) String pwd) {
        return articleService.getArticleDetail(id, pwd).map(Result::success);
    }

    @GetMapping(value = "about-me", name = "关于我")
    public Mono<Result> aboutMe() {
        return userService.getAboutMe().map(Result::success);
    }

    @PostMapping(value = "search", name = "统一搜索")
    public Mono<Result> search(@RequestBody @Validated SearchQueryDTO dto) {
        return searchService.search(dto).map(Result::success);
    }

    @GetMapping(value = "left-menus", name = "获取首页左侧导航菜单")
    public Mono<Result> getLeftMenus() {
        String userId = SecurityUtils.getLoginUserIdOrNull();
        return menuService.getMenusByMenuType(MenuType.PORTAL_LEFT, userId).map(Result::success);
    }

    @GetMapping(value = "nav-links", name = "获取导航链接")
    public Mono<Result> getNavLinks() {
        return navLinkService.getNavLinks().map(Result::success);
    }
}
