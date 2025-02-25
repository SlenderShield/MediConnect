package com.hungrycoder.auth.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a role in the system.
 */
@Document(collection = "roles")
public class Role {
  @Id
  private String id;

  private UserRole name;

  /**
   * Default constructor.
   */
  public Role() {}

  /**
   * Constructor with role name.
   *
   * @param name The role name.
   */
  public Role(UserRole name) {
    this.name = name;
  }

  // Getter and Setter for id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  // Getter and Setter for name
  public UserRole getName() {
    return name;
  }

  public void setName(UserRole name) {
    this.name = name;
  }
}
