package cn.wyslkl.server.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class FileDto {

    /**
     * id
     */
    private String id;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小|字节B
     */
    private Long size;

    /**
     * 用途|枚举[FileUseEnum]: COURSE("C","讲师"), TEACHER("T","课程")
     */
    private String used;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date updatedAt;

    private Integer shardIndex;

    private Integer shardSize;

    private Integer shardTotal;

    private String keyId;

    /**
     * base64
     */
    private String shard;

    //private String vod;

    public Integer getShardIndex() {
        return shardIndex;
    }

    public void setShardIndex(Integer shardIndex) {
        this.shardIndex = shardIndex;
    }

    public Integer getShardSize() {
        return shardSize;
    }

    public void setShardSize(Integer shardSize) {
        this.shardSize = shardSize;
    }

    public Integer getShardTotal() {
        return shardTotal;
    }

    public void setShardTotal(Integer shardTotal) {
        this.shardTotal = shardTotal;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getShard() {
        return shard;
    }

    public void setShard(String shard) {
        this.shard = shard;
    }

    /**
    public String getVod() {

        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }
    */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", path=").append(path);
        sb.append(", name=").append(name);
        sb.append(", suffix=").append(suffix);
        sb.append(", size=").append(size);
        sb.append(", used=").append(used);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
    **/
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", suffix='").append(suffix).append('\'');
        sb.append(", size=").append(size);
        sb.append(", used='").append(used).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", shardIndex=").append(shardIndex);
        sb.append(", shardSize=").append(shardSize);
        sb.append(", shardTotal=").append(shardTotal);
        sb.append(", keyId='").append(keyId).append('\'');
        sb.append(", shard='").append(shard).append('\'');
       // sb.append(", vod='").append(vod).append('\'');
        sb.append('}');
        return sb.toString();
    }

}