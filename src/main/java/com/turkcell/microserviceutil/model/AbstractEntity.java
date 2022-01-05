package com.turkcell.microserviceutil.model;

import com.turkcell.microserviceutil.util.ModelUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@MappedSuperclass
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractEntity implements Serializable
{
    public static String DEFAULT_DELETED_VALUE = "0";

    @Transient
    protected static DateFormat timestampFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.turkcell.microserviceutil.util.ModelUtil")
    @Access(AccessType.PROPERTY)
    private String id;
    private Date createDate;
    private String createUser;
    private Date updateDate;
    private String updateUser;
    private String deleted = DEFAULT_DELETED_VALUE;

    public void setId(String id)
    {
        this.id = id;
    }

    @Transient
    public void delete(String deletedBy)
    {
        this.setUpdateUser(deletedBy);
        this.setUpdateDate(Calendar.getInstance().getTime());
        this.setDeleted(ModelUtil.generateUUID());
    }

    @Transient
    public Date getUpdateOrCreateTime()
    {
        return updateDate == null ? createDate : updateDate;
    }
}
