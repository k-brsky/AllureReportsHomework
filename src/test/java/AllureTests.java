import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class AllureTests {
    private final String baseUrl = "https://github.com";
    private final String repoName = "eroshenkoam/allure-example";
    private final String issueNumber = "#88";

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 5000;
    }
    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }

    @Test
    public void selenideTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open(baseUrl);
        $(".search-input").click();
        $("#query-builder-test").setValue(repoName).pressEnter();
        $("[data-testid='results-list']").$(byLinkText(repoName)).click();
        $("#issues-tab").click();
        $(withText(issueNumber)).shouldBe(Condition.exist);

    }

    @Test
    public void lambdaStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });
        step(("Ищем репозиторий " + repoName), () -> {
            $(".search-input").click();
            $("#query-builder-test").setValue(repoName).pressEnter();
        });
        step(("Кликаем по табу репозитория " + repoName), () -> {
            $("[data-testid='results-list']").$(byLinkText(repoName)).click();
        });
        step("Нажимаем таб Issue", () -> {
            $("#issues-tab").click();
        });
        step(("Проверяем наличие issue с номером " + issueNumber), () -> {
            $(withText(issueNumber)).shouldBe(Condition.exist);
        });
    }

    @Test
    public void annotatedStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage(baseUrl);
        steps.searchRepository(repoName);
        steps.clickOnRepositoryTab(repoName);
        steps.clickOnIssueTab();
        steps.checkIssue(issueNumber);
    }
}
