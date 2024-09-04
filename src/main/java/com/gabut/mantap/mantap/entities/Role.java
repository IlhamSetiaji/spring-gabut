package com.gabut.mantap.mantap.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.gabut.mantap.mantap.entities.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Table(name = "roles")
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  private String id;

  @Column(unique = true, nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleEnum name;

  @Column(nullable = true)
  private String description;

  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  public Role(RoleEnum name, String description) {
    this.name = name;
    this.description = description;
  }
}
