package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/15 19:05
 * @Title: ArticleUploadObs
 * @Description: 将文字内容的文章转换为md文件上传到obs的实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticleUploadObs  extends BaseEntity{

    private static final long serialVersionUID = 7764788541211887394L;

    String content;
    String folderName;

}

