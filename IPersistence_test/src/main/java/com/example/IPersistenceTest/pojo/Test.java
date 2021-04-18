package com.example.IPersistenceTest.pojo;

import sun.util.resources.cldr.mg.LocaleNames_mg;

import java.time.LocalDateTime;

public class Test {
    private Long id;
    private Integer value;
    private Integer fakeValue;
    private String remark;
    private LocalDateTime createTime;

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", value=" + value +
                ", fakeValue=" + fakeValue +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getFakeValue() {
        return fakeValue;
    }

    public void setFakeValue(Integer fakeValue) {
        this.fakeValue = fakeValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
