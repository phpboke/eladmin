package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Tag;
import me.zhengjie.modules.system.service.dto.TagDto;
import me.zhengjie.modules.system.service.dto.TagQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * tag服务类
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 16:16
 */
public interface TagService {

  /** 查询 */
  TagDto findById(Long id);

  /** 创建 */
  void create(Tag tag);

  /** 更新 */
  void update(Tag tag);

  /** 删除 */
  void delete(Set<Long> ids);

  /**
   * 分页查询
   *
   * @param queryCriteria
   * @param pageable
   * @return java.util.Map<java.lang.String, java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-21 17:48
   */
  Map<String, Object> queryAll(TagQueryCriteria queryCriteria, Pageable pageable);

  /**
   * 查询全部数据
   * @param tagQueryCriteria /
   * @return /
   */
  List<TagDto> queryAll(TagQueryCriteria tagQueryCriteria);

  /**
   * 导出
   *
   * @param queryAll
   * @param response
   * @throws IOException
   * @author kuxiaoqiang
   * @since 2021-07-22 10:15
   */
  void download(List<TagDto> queryAll, HttpServletResponse response) throws IOException;
}
