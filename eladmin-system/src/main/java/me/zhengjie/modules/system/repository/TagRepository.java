package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

/**
 * TagRepository
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 15:54
 */
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

  /**
   * * 根据名字查询tag
   *
   * @param name
   * @return null
   * @author kuxiaoqiang
   * @since 2021-07-21 16:33
   */
  Tag findByName(String name);

  /**
   * * 删除
   *
   * @param ids
   * @author kuxiaoqiang
   * @since 2021-07-21 17:20
   */
  void deleteAllByIdIn(Set<Long> ids);
}
