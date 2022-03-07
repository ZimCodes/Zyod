package xyz.zimtools.zyod.args.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.io.File;

public class PathExistValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        File file = new File(value);
        if (!file.exists()) {
            throw new ParameterException(String.format("Specified path, '%s', cannot be found!",
                    value));
        }
    }
}