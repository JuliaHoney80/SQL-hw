package page;

import com.codeborne.selenide.SelenideElement;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.awt.SystemColor.text;


public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id='dashboard']");


    public DashboardPage(){
        heading.shouldHave(text("Личный кабинет")).shouldBe(visible);
    }


}
