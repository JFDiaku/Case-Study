package com.capstone.CaseStudy.validation;


import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class EmailUniqueImpl implements ConstraintValidator<EmailUnique, String> {

    public static final Logger LOG = LoggerFactory.getLogger(EmailUniqueImpl.class);

    @Autowired
    private UserDAO userService;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // these 3 lines are the first 3 lines of every custom validator you build
        // BECAUSE .. we are not checking if this value is empty
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        User user = userService.findByEmail(value);

        return (user == null);
    }

}
