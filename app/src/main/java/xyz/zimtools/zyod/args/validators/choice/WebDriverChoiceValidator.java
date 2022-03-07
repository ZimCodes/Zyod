package xyz.zimtools.zyod.args.validators.choice;

public class WebDriverChoiceValidator extends ChoiceValidator {

    @Override
    void initChoices() {
        this.choices = new String[]{"chrome","firefox","edge"};
    }
}