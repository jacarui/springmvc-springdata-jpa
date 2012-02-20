package foo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
 
  @Column private String username;

  @Column String firstName;
 
  @Column String lastName;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
   return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
   return lastName;
  }

  public void setLastName(String lastName) {
   this.lastName = lastName;
 }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}
 
}