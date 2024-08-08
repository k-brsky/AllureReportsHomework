import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebSteps {
    @Step("Открываем главную страницу")
    public void openMainPage(String url) {
        open(url);
    }

    @Step("Ищем репозиторий")
    public void searchRepository(String repo) {
        $(".search-input").click();
        $("#query-builder-test").setValue(repo).pressEnter();
    }

    @Step("Кликаем по табу репозитория")
    public void clickOnRepositoryTab(String repo) {
        $("[data-testid='results-list']").$(byLinkText(repo)).click();
    }

    @Step("Нажимаем таб Issue")
    public void clickOnIssueTab() {
        $("#issues-tab").click();
    }

    @Step("Проверяем наличие issue")
    public void checkIssue(String issueNumber) {
        $(withText(issueNumber)).shouldBe(Condition.exist);
    }
}
