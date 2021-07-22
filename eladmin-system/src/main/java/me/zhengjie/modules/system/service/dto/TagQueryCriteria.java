package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.annotation.Query;

/**
 * tag查询对象
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 17:43
 */
@Data
@NoArgsConstructor
public class TagQueryCriteria {

    /** tag名字 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
