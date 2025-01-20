package com.capstone.CaseStudy.form;

import com.capstone.CaseStudy.validation.EmailUnique;
import com.capstone.CaseStudy.validation.TwoFieldsAreEqual;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TwoFieldsAreEqual(fieldOneName = "confirmPassword", fieldTwoName = "password", message = "Password and Conform Password must be the same.")
public class NewUserDTO {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @EmailUnique(message = "Account already exists")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    private String confirmPassword;

    @AssertTrue(message = "You must agree to the terms and conditions.")
    private boolean terms;


}
