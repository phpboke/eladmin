package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.domain.Tag;
import me.zhengjie.modules.system.repository.TagRepository;
import me.zhengjie.modules.system.service.TagService;
import me.zhengjie.modules.system.service.dto.TagDto;
import me.zhengjie.modules.system.service.dto.TagQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TagMapper;
import me.zhengjie.utils.CacheKey;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.RedisUtils;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * tagService实现类
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 16:25
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "tag")
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final TagMapper tagMapper;

  private final RedisUtils redisUtils;

  /**
   * * 通过id查找
   *
   * @param id
   * @return me.zhengjie.modules.system.service.dto.TagDto
   * @author kuxiaoqiang
   * @since 2021-07-21 17:12
   */
  @Override
  @Cacheable(key = "'id:' + #p0")
  public TagDto findById(Long id) {
    Tag tag = tagRepository.findById(id).orElseGet(Tag::new);
    ValidationUtil.isNull(tag.getId(), "tag", "id", id);
    return tagMapper.toDto(tag);
  }

  /**
   * 插入tag
   *
   * @param tag
   * @author kuxiaoqiang
   * @since 2021-07-21 16:40
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void create(Tag tag) {
    Tag tagObj = tagRepository.findByName(tag.getName());
    if (null != tagObj) {
      throw new EntityExistException(Tag.class, "name", tag.getName());
    }
    tagRepository.save(tag);
  }

  /**
   * * 更新
   *
   * @param tag
   * @return null
   * @author kuxiaoqiang
   * @since 2021-07-21 17:03
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  @CacheEvict(key = "'id:' + #p0.id")
  public void update(Tag tag) {
    Tag tagByName = tagRepository.findByName(tag.getName());
    if (tagByName != null && !tagByName.getId().equals(tag.getId())) {
      throw new EntityExistException(Tag.class, "name", tag.getName());
    }
    Tag tagById = tagRepository.findById(tag.getId()).orElseGet(Tag::new);
    ValidationUtil.isNull(tagById.getId(), "tag", "id", tag.getId());
    tagRepository.save(tag);
  }

  /**
   * * 删除
   *
   * @param ids
   * @return null
   * @author kuxiaoqiang
   * @since 2021-07-21 17:25
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(Set<Long> ids) {
    tagRepository.deleteAllByIdIn(ids);
    redisUtils.delByKeys(CacheKey.TAG_ID, ids);
  }

  /**
   * 分页
   *
   * @param queryCriteria
   * @param pageable
   * @return java.util.Map<java.lang.String, java.lang.Object>
   * @author kuxiaoqiang
   * @since 2021-07-22 10:16
   */
  @Override
  public Map<String, Object> queryAll(TagQueryCriteria queryCriteria, Pageable pageable) {
    Page<Tag> page =
        tagRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) ->
                QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder),
            pageable);
    return PageUtil.toPage(page.map(tagMapper::toDto).getContent(), page.getTotalElements());
  }

  /**
   * 查询全部数据
   * @param tagQueryCriteria /
   * @return /
   */
  @Override
  public List<TagDto> queryAll(TagQueryCriteria tagQueryCriteria){
    List<Tag> tagDtoList = tagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, tagQueryCriteria, criteriaBuilder));
    return tagMapper.toDto(tagDtoList);
  }

  /**
   * 导出excel
   *
   * @param queryAll
   * @param response
   * @author kuxiaoqiang
   * @since 2021-07-22 10:16
   */
  @Override
  public void download(List<TagDto> queryAll, HttpServletResponse response) throws IOException {
    List<Map<String, Object>> list = new ArrayList<>();
    for (TagDto tagDto : queryAll) {
      Map<String, Object> map = new HashMap<>();
      map.put("ID", tagDto.getId().toString());
      map.put("tag名字", tagDto.getName());
      map.put("排序", tagDto.getTagSort());
      map.put("创建时间", tagDto.getCreateTime());
      list.add(map);
    }
    FileUtil.downloadExcel(list, response);
  }
}
