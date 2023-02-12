package com.levelup.recruit;

import com.levelup.recruit.domain.enumeration.Company;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SeleniumTest {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    @Test
    void line_test() {
        System.setProperty("webdriver.chrome.driver", "C:/Task/tools/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://careers.linecorp.com/ko/jobs?ca=Engineering&ci=Bundang,Seoul");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));
        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector("a h3.title")).getText();

            String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String url = Company.LINE.getUrl() + jobNoticePath;

            String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();

            System.out.println("title: " + title);
            System.out.println("url: " + url);
            System.out.println("noticeEndDate: " + noticeEndDate);
        }

        driver.quit();
    }

    @Test
    void bamin_test() {
        System.setProperty("webdriver.chrome.driver", "C:/Task/tools/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://career.woowahan.com/?keyword=&category=jobGroupCodes%3ABA005001#recruit-list");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.recruit-type-list > li"));
        for (WebElement element : elements) {
            System.out.println(element.getText());
        }

        driver.quit();
    }

    @Test
    void coupang_test() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.coupang.jobs/kr/jobs?page=1&search=Software+Engineer&location=Seoul%2c+South+Korea&pagesize=20#results");
        List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
        for (WebElement element : elements) {
            String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
            final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link"))
                            .getAttribute("href");
            final String noticeEndDate = "채용 마감시";
            System.out.println("title: " + title);
            System.out.println("url: " + url);
            System.out.println("noticeEndDate: " + noticeEndDate);
        }

        driver.quit();
    }
}
