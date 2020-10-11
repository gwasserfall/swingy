package controllers;

import models.Hero;
import swingy.Swingy;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ModelValidation {
    public static Boolean validatePlayer(Hero player) {
       Set<ConstraintViolation<Hero>> errors = Swingy.validator.validate(player);

       return (errors.size() == 0);
    }
}
