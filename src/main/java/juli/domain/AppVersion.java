package juli.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by pf on 2016/3/3.
 */
@Entity
public class AppVersion extends BaseEntity{

    @Column
    private String vsersionNumber;
    @Column
    private String downloadUrl;
    @Column
    private String content;
    @Column
    private Integer isNew;

    public String getVsersionNumber() {
        return vsersionNumber;
    }

    public void setVsersionNumber(String vsersionNumber) {
        this.vsersionNumber = vsersionNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }
}
