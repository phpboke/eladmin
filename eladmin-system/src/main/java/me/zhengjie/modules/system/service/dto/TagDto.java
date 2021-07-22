package me.zhengjie.modules.system.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;

import java.io.Serializable;

/**
 * tagDto
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 16:17
 */
@Getter
@Setter
@NoArgsConstructor
public class TagDto extends BaseDTO implements Serializable {

    /** 主键id */
    private Long id;

    /** tag名字 */
    private String name;

    /** tag类型(resource/network/datacenter/region) */
    private String type;

    /** tag排序*/
    private Integer tagSort;

    public TagDto(String name, String type){
        this.name = name;
        this.type = type;
    }
}
