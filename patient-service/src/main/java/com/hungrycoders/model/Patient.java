package com.hungrycoders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(value = "patients")
public class Patient {

    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Integer age;

}
