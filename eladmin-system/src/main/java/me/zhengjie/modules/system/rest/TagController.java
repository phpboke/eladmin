package me.zhengjie.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Tag;
import me.zhengjie.modules.system.service.TagService;
import me.zhengjie.modules.system.service.dto.TagQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * tag控制器
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 18:01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

  private final TagService tagService;

  private static final String ENTITY_NAME = "tag";

  /**
   * 分页查询
   *
   * @param tagQueryCriteria
   * @param pageable
   * @return org.springframework.http.ResponseEntity<java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 18:23
   */
  @GetMapping
  @PreAuthorize("@el.check('tag:list', 'user:list')")
  public ResponseEntity<Object> query(TagQueryCriteria tagQueryCriteria, Pageable pageable) {
    return new ResponseEntity<>(tagService.queryAll(tagQueryCriteria, pageable), HttpStatus.OK);
  }

  /**
   * 导出
   *
   * @param response
   * @param tagQueryCriteria
   * @throws IOException
   * @author kuxiaoqiang
   * @since 2021-07-22 10:15
   */
  @GetMapping(value = "/download")
  @PreAuthorize("@el.check('tag:list')")
  public void download(HttpServletResponse response, TagQueryCriteria tagQueryCriteria) throws IOException {
    tagService.download(tagService.queryAll(tagQueryCriteria), response);
  }

  /**
   * 查询单个tag
   *
   * @param id
   * @return org.springframework.http.ResponseEntity<java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 22:46
   */
  @GetMapping(value = "/{id}")
  @PreAuthorize("@el.check('tag:list')")
  public ResponseEntity<Object> query(@PathVariable Long id) {
    return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
  }

  /**
   * 创建
   *
   * @param tag
   * @return org.springframework.http.ResponseEntity<java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 18:24
   */
  @PostMapping
  @PreAuthorize("@el.check('tag:add')")
  public ResponseEntity<Object> create(@Validated @RequestBody Tag tag) {
    if (tag.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    tagService.create(tag);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * 更新
   *
   * @param tag
   * @return org.springframework.http.ResponseEntity<java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 18:24
   */
  @PutMapping
  @PreAuthorize("@el.check('tag:edit')")
  public ResponseEntity<Object> update(@Validated(Tag.Update.class) @RequestBody Tag tag) {
    tagService.update(tag);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * 删除
   *
   * @param ids
   * @return org.springframework.http.ResponseEntity<java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 18:24
   */
  @DeleteMapping
  @PreAuthorize("@el.check('tag:del')")
  public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
    tagService.delete(ids);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
