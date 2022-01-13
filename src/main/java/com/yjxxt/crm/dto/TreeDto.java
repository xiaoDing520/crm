package com.yjxxt.crm.dto;

/**
 * @ClassName TreeDto
 * @Desc 资源树查询
 * @Author xiaoding
 * @Date 2022-01-02 15:14
 * @Version 1.0
 */
public class TreeDto {
    private int id;
    private String name;
    private int pId;
    private boolean checked;

    public TreeDto() {
    }

    public TreeDto(int id, String name, int pId,boolean checked) {
        this.id = id;
        this.name = name;
        this.pId = pId;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "TreeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pId=" + pId +
                '}';
    }
}
