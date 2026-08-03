package com.example.studentmanagementsystem.DTO;

import com.example.studentmanagementsystem.DTO.group.AddGroup;
import com.example.studentmanagementsystem.DTO.group.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @Null(groups = UpdateGroup.class,message = "Student No can not be changed.")
    @NotBlank(groups = AddGroup.class)
    @Size(min=5,max=5,message = "Student No is not valid.")
    private String studentNo;

    @NotBlank(groups = AddGroup.class)
    @Size(min=2,max=10,message = "Student name is not valid.")
    private String name;

    @NotBlank(groups = AddGroup.class)
    @Min(value = 0)
    private Integer age;

    @NotBlank(groups = AddGroup.class)
    private String className;
}
