package xyz.zimtools.zyod.args.validators.choice;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;

abstract class ChoiceValidator implements IParameterValidator {
    String[] choices;

    public ChoiceValidator() {
        this.initChoices();
    }
    //Initialize the 'choices' field
    abstract void initChoices();

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!Arrays.asList(choices).contains(value.toLowerCase())) {
            throw new ParameterException(String.format("'%s' is an invalid choice for '%s'", value
                    , name));
        }
    }
}