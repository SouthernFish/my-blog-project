package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/12 16:53
 * @Title: ArticleInfo.java
 * @Description: 文章信息实体类
 */
/**
 * @Author TR
 * @Create 2021/8/18 10:09
 * @Title: ArticleListVo
 * @Description: 文章列表请求实体类VO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticleListVo extends BaseEntity {
    private static final long serialVersionUID = 1242208911602966069L;

    private Integer position;
    private Integer tabType;
    private Integer operatorId;
    private Integer categoryInfoId;
    private Integer tagInfoId;
    private String searchText;
    private Integer pageNum;
    private Integer pageSize;
}
