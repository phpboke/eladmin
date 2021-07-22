package me.zhengjie.modules.system.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.system.domain.Tag;
import me.zhengjie.modules.system.service.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * tagMapper
 *
 * @author kuxiaoqiang
 * @since 2021-07-21 17:09
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends BaseMapper<TagDto, Tag> {
}
