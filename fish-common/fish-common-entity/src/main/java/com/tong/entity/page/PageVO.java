package com.tong.entity.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 15:19
 * @Title: PageVO
 * @Description: 列表分页实体类
 */
@Data
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = 3144492446897635526L;

    /*
     * 具体数据
     */
    private List<T> list;

    /*
     * 当前页数
     */
    private Long currentPage;

    /*
     * 单页条数
     */
    private Long pageSize;

    /*
     * 总数据量
     */
    private Long total;

    /*
     * 总页数
     */
    private Long pages;

    public PageVO() {
        super();
    }

    public PageVO(IPage<T> iPage) {
        super();
        this.list = iPage.getRecords();
        this.currentPage = iPage.getCurrent();
        this.pageSize = iPage.getSize();
        this.total = iPage.getTotal();
        this.pages = iPage.getPages();
    }
    public PageVO(Integer pageNum, Integer pageSize) {
        super();
        this.currentPage = pageNum.longValue();
        this.pageSize = pageSize.longValue();
    }

    public PageVO<T> setPage(IPage<T> iPage) {
        this.list = iPage.getRecords();
        this.currentPage = iPage.getCurrent();
        this.pageSize = iPage.getSize();
        this.total = iPage.getTotal();
        this.pages = iPage.getPages();
        return this;
    }


}
