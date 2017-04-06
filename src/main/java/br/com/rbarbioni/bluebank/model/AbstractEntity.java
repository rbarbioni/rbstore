package br.com.rbarbioni.bluebank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by renan on 13/02/17.
 */

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JsonProperty("created_date")
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @JsonProperty("modifield_date")
    @LastModifiedDate
    @Column(name = "modified_date")
    private Date modifieldDate;

    AbstractEntity() {
        this.createdDate = new Date();
    }

    public Long getId() {
        return id != null ? id : Long.valueOf(0);
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifieldDate() {
        return modifieldDate;
    }
}
