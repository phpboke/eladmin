package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * 系统tag
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 15:37
 */
@Getter
@Setter
@Entity
@Table(name = "sys_tag")
public class Tag extends BaseEntity implements Serializable {

  /** 主键id */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** tag名字 */
  private String name;

  /** tag类型(resource/network/datacenter/region) */
  private String type;

    /** tag排序*/
    private Long tagSort;

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (null == obj || getClass() != obj.getClass()){
            return false;
        }
        Tag tag = (Tag)obj;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
